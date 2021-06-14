package com.prateek.xmlcompare

import scala.xml.{ Node, Utility, XML }

import java.io.File

class XmlFile(val file: File, val node: Node)

object XmlFile {

  /** Validates f: [[File]] passed exists and accordingly returns:
    *   1. If "f" is a directory, sequence of [[XmlFile]] for "Discover"
    *      requests inside "f".
    *   1. Else if "f" is a "Discover" request file, returns a sequence with a
    *      single element [[XmlFile]].
    *   1. Else, return an empty sequence.
    */
  def apply(f: File): Seq[XmlFile] = {
    assert(f.exists(), s"$f should refer to an existing file or directory")
    val xmlFiles: Seq[File] = if (f.isDirectory) {
      f.listFiles((_, name) => name.matches(".+-req\\.xml")).toList
    } else Seq(f)

    xmlFiles
      .map(f => {
        val doc = XML.loadFile(f)
        val nodeSeq = doc \ "Body" \ "Discover"
        assert(
          nodeSeq.isEmpty || nodeSeq.size == 1,
          s"multiple Discover nodes found in $f"
        )
        (f, nodeSeq)
      })
      .collect({
        case (f, ns) if ns.nonEmpty => new XmlFile(f, Utility.trim(ns.head))
      })
  }
}
