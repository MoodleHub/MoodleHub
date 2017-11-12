import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "MoodleHub",

    resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
    resolvers += Resolver.sonatypeRepo("releases"),

    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.1.3",
    libraryDependencies += "com.typesafe.play" %% "play-ws-standalone-json" % "1.1.3",
    libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12",

    unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome)/"lib"/"jfxrt.jar"),

    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),

    libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.4"
  )
