package com.prateek.xmlcompare

import scala.xml.XML

import java.io.File

object Discover {

  def apply(f: String): Seq[XmlFile] = {
    files(f)
      .map(f => {
        val doc = XML.loadFile(f)
        val nodeSeq = (doc \ "Body" \ "Discover")
        assert(
          nodeSeq.isEmpty || nodeSeq.size == 1,
          s"multiple Discover nodes found in $f"
        )
        (f, nodeSeq)
      })
      .collect({ case (f, ns) if ns.nonEmpty => XmlFile(f, ns.head) })
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
  }

  private def files(dir: String): Seq[File] = {
    val f = new File(getClass.getClassLoader.getResource(dir).getPath)
    f.listFiles((d, name) => name.matches(".+-req\\.xml")).toList
  }
}
