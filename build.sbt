import sbt.Keys.libraryDependencies

name := "xml-comparator"

version := "0.1"

scalaVersion := "2.13.6"

lazy val xmlVersion = "1.2.0"
lazy val loggingVersion = "3.9.3"
lazy val scallopVersion = "4.0.3"
lazy val scalaTestVersion = "3.2.9"
lazy val snaymlVersion = "1.29"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % xmlVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % loggingVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.rogach" %% "scallop" % scallopVersion,
  "org.scalatest" %% "scalatest-funspec" % scalaTestVersion % "test",
  "org.yaml" % "snakeyaml" % snaymlVersion
)
