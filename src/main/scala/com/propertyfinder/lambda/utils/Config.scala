package com.propertyfinder.lambda.utils

import com.propertyfinder.lambda.services.{MongoDBService, PropertyInfoRepositoryImpl}

object Config {
  lazy val db = new MongoDBService
  lazy val dbUri = sys.env.getOrElse("MONGODB_RE_URI", "localhost:27017")
  lazy val dbUser = sys.env.getOrElse("MONGODB_USER", "admin")
  lazy val dbPass = sys.env.getOrElse("MONGODB_PASS", "password")
  lazy val dbTable = sys.env.getOrElse("MONGODB_PROPERTY_TABLE", "test")
  lazy val authDb = sys.env.getOrElse("MONGODB_AUTH_DB", "admin")
  lazy val propertyInfoRepository = new PropertyInfoRepositoryImpl(db)
}
