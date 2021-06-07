package com.prateek.xmlcompare

import scala.xml.XML

import java.io.File

object Main extends App {

  private def files(): Seq[File] = {
    val dir = new File(getClass.getClassLoader.getResource("atscale-4").getPath)
    dir.listFiles((d, name) => name.endsWith(".xml")).toList
  }

  files().foreach(f => {
    val doc = XML.loadFile(f)
    val seq = doc \ "Body" \ "Discover"
    println(seq.nonEmpty)
    /*
  seq
    .foldLeft(new mutable.LinkedHashMap[String, String]())((a, b) => {
      val labelValuePairs = b.descendant.collect {
        case elem: Elem if elem.child.length == 1 =>
          val value = (elem.child map { case Text(data) =>
            data
          }).head
          (elem.label -> value)
      }
      a ++ labelValuePairs
    })
    .foreach(println)
     */
  })
}
