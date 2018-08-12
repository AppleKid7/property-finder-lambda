package com.propertyfinder.lambda

import java.text.SimpleDateFormat
import java.util.Calendar

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.SNSEvent
import com.propertyfinder.lambda.services.PropertyInfo
import com.propertyfinder.lambda.utils.Config
import play.api.libs.json._


class PropertyFinderHandler {

  def handleRequest(request: SNSEvent, context: Context): Unit = {
    val logger = context.getLogger
    var timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance.getTime)
    logger log s"Invocation started: $timeStamp"

    val message: String = request.getRecords.get(0).getSNS.getMessage
    val json: JsValue = Json.parse(message.toString)
    var content = (json \ "content").as[String]

    content = content.replaceAll("=\n", "")
    content = content.replaceAll("\n", "")
    content = content.toLowerCase


    val pricePattern = """>\s*[$](\d{1,3}(?:[, ]?\d{1,3})?(?:.\d+)?)</""".r
    val addressPattern = """"font-family:arial">[^<]+</""".r
    val emailPattern = """([A-Za-z0-9._-]+)@titanhack.com""".r

    var addressMatches = addressPattern.findAllIn(content).toVector
    val filter1 = "\"font-family:arial\">"
    val filter2 = "</"
    addressMatches = stringSanitizer(addressMatches, filter1, filter2).dropRight(1)

    var priceMatches: Vector[String] = pricePattern.findAllIn(content).toVector
    val filter3 = ","
    val filter4 = "</"
    priceMatches = stringSanitizer(priceMatches, filter3, filter4)
    val price = priceMatches.headOption.getOrElse("000").substring(2).toDouble

    val emailMatches = emailPattern.findAllIn(content).toVector

    logger log s"Got price matches: $priceMatches"
    logger log s"Will store price ${price} in DB"
    logger log s"Got address matches: $addressMatches"
    logger log s"Got email matches: ${emailMatches.headOption.getOrElse("")}"

    val addressLine1 = if(addressMatches.isEmpty) "" else addressMatches(0)
    val addressLine2 = if(addressMatches.length > 1) addressMatches(1) else ""

    val db = Config.propertyInfoRepository
    val data = PropertyInfo(email = emailMatches.headOption.getOrElse(""), price = price, address = s"${addressLine1}\n${addressLine2}")
    db.savePropertyInfo(data)

    timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance.getTime)
    logger log s"Invocation completed: $timeStamp"
  }

  def stringSanitizer(original: Vector[String], pattern1: String, pattern2: String): Vector[String] = {
    val result = for(str <- original) yield {
      var sanitized = str replaceAll(pattern1, "")
      sanitized = sanitized replaceAll(pattern2, "")
      sanitized
    }
    result
  }
}

object PropertyFinderHandler {
  def main(args: Array[String]): Unit = {

  }
}