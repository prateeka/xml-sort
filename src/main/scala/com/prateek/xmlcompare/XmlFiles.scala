package com.prateek.xmlcompare

import scala.xml.{ Utility, XML }

import java.io.File

/** List all xml files
  */
object XmlFiles {

  /** Validates [[File]] passed as parameter "f" exists and accordingly returns:
    *   1. Sequence of xml [[File]] inside the "f" if "f" is a directory.
    *   1. Else, returns a single item list with "f".
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
        case (f, ns) if ns.nonEmpty => XmlFile(f, Utility.trim(ns.head))
      })
  }
}
