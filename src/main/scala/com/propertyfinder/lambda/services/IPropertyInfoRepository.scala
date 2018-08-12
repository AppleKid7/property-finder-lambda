package com.propertyfinder.lambda.services


case class PropertyInfo(
                         email: String,
                         price: Double,
                         address: String
                       )

trait IPropertyInfoRepository {
  def savePropertyInfo(data: PropertyInfo)
}
