import sbt.Keys.libraryDependencies

name := "xml-sort"

version := "0.1"

scalaVersion := "2.13.1"

lazy val scalaXmlVersion = "1.2.0"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % scalaXmlVersion
)
