import sbt._

object Libs {
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.1"
  val zeromq = "org.zeromq" % "jeromq" % "0.3.6"
  val redis = "com.github.etaty" %% "rediscala" % "1.8.0"
  //  val `scala-async` = "org.scala-lang.modules" %% "scala-async" % "0.9.6"
//  val `chill-akka` = "com.twitter" %% "chill-akka" % "0.9.1"
}

object Akka {
  private val version = "2.4.16"
  private val akkahttpversion = "10.0.1"
  val `akka-actor` = "com.typesafe.akka" %% "akka-actor" % version
  val `akka-remote` = "com.typesafe.akka" %% "akka-remote" % version
  val `akka-http` = "com.typesafe.akka" %% "akka-http" % akkahttpversion
  val `akka-http-core` = "com.typesafe.akka" %% "akka-http-core" % akkahttpversion
  val `akka-http-spray-json` = "com.typesafe.akka" %% "akka-http-spray-json" % akkahttpversion
//  val `akka-stream` = "com.typesafe.akka" %% "akka-stream" % version
//  val `akka-cluster` = "com.typesafe.akka" %% "akka-cluster" % version
//  val `akka-ddata` = "com.typesafe.akka" %% "akka-distributed-data-experimental" % version
}
