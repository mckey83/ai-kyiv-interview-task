ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.12"

lazy val root = (project in file("."))
  .settings(
    name := "ai-kyiv-interview-task"
  )


libraryDependencies ++= Seq(
  "org.apache.commons" % "commons-csv" % "1.10.0",
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
)
