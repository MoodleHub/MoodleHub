import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "MoodleHub",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.3",
    libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.1.3"
  )
