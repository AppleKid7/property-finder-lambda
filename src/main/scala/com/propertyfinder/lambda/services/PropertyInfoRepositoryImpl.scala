package com.propertyfinder.lambda.services

class PropertyInfoRepositoryImpl(mongoDBService: MongoDBService) extends IPropertyInfoRepository {
  override def savePropertyInfo(data: PropertyInfo): Unit = {
    mongoDBService.insertRecord(data)
  }
}
