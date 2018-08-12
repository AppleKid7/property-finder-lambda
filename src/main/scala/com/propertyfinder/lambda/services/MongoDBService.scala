package com.propertyfinder.lambda.services

import com.mongodb.DBObject
import com.mongodb.casbah.{MongoClient, MongoClientURI}
import com.mongodb.casbah.commons.MongoDBObject
import com.propertyfinder.lambda.utils.Config


object MongoFactory {
  //val uri = MongoClientURI("mongodb://th_re:mKicDe0000@mongodbre.titanhack.com/realestate")
  val uri = MongoClientURI(s"mongodb://${Config.dbUser}:${Config.dbPass}@${Config.dbUri}/${Config.authDb}")
  val mongoClient =  MongoClient(uri)
  val db = mongoClient(s"${Config.authDb}")
  val collection = db(s"${Config.dbTable}")
}

object MongoDBCommon {
  /**
    * Convert a Stock object into a BSON format that MongoDb can store.
    */
  def buildPropertyInfoMongoDbObject(info: PropertyInfo): DBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "email" -> info.email
    builder += "price" -> info.price
    builder += "address" -> info.address
    builder.result()
  }

  def saveData(data: PropertyInfo) {
    val mongoObj = buildPropertyInfoMongoDbObject(data)
    MongoFactory.collection.save(mongoObj)
  }
}

class MongoDBService {
  def insertRecord(propertyInfo: PropertyInfo) = {

    // save them to the mongodb database
    MongoDBCommon.saveData(propertyInfo)
  }
}
