import sbt.Keys.libraryDependencies

name := "xml-comparator"

version := "0.1"

scalaVersion := "2.13.1"

lazy val xmlVersion = "1.2.0"

lazy val loggingVersion = "3.9.3"
//lazy val loggingVersion = "5.0"

resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % xmlVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % loggingVersion,
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
