organization := "com.propertyfinder"

name := "property-finder-lambda"

version := "0.1"

scalaVersion := "2.12.6"
enablePlugins(UniversalPlugin, JavaAppPackaging)
topLevelDirectory := None

libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % Test

libraryDependencies += "org.mockito" % "mockito-all" % "1.10.19" % Test

libraryDependencies += "org.scala-lang.modules" %% "scala-java8-compat" % "0.9.0"

libraryDependencies += "joda-time" % "joda-time" % "2.9.9"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.0"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.+"

libraryDependencies += "org.mongodb" %% "casbah-core" % "3.1.1"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.25"


libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.2.0"

libraryDependencies += "com.amazonaws" % "aws-lambda-java-events" % "2.1.0"

