lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.nomadblacky",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "scala-bot",
    libraryDependencies ++= Seq(
      "io.monix" %% "monix-cats" % "2.3.2",
      "org.scalatest" %% "scalatest" % "3.0.3",
      "com.typesafe.akka" %% "akka-stream" % "2.5.9",
      "com.typesafe.akka" %% "akka-http" % "10.0.11"
    )
  )
