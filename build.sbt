
lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.thoughtworks",
      scalaVersion := "2.12.1",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "tmt-akka-training",
    libraryDependencies ++= Seq(
      Akka.`akka-actor`,
      Akka.`akka-remote`,
//      Akka.`akka-cluster`,
//      Akka.`akka-stream`,
//      Akka.`akka-ddata`,
//      Libs.`scala-async`,
//      Libs.`chill-akka`,
      Libs.scalatest % Test
    )
  )