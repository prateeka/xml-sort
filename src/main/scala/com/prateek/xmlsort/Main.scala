package com.prateek.xmlsort

object Main extends App {

  import scala.collection.mutable
  import scala.xml.{Elem, Text, XML}

  val doc = XML.loadFile("src/main/resources/ssas-response.xml")
  private val seq = doc \\ "row"
  seq
    .foldLeft(new mutable.LinkedHashMap[String, String]())((a, b) => {
      val labelValuePairs = b.descendant.collect {
        case elem: Elem if elem.child.length == 1 =>
          val value = (elem.child map {
            case Text(data) => data
          }).head
          (elem.label -> value)
      }
      a ++ labelValuePairs
    }).foreach(println)
//  println(seq)
}
