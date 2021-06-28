package com.prateek.xmlcompare

import scala.xml.{Node, Utility, XML}

import java.io.File

case class FileNodeTuple private (file: String, node: Node)

object FileNodeTuple {

  /** Validates f: [[File]] passed exists and accordingly returns:
    *   1. If "f" is a directory, sequence of [[FileNodeTuple]] for "Discover"
    *      requests inside "f".
    *   1. Else if "f" is a "Discover" request file, returns a sequence with a
    *      single element [[FileNodeTuple]].
    *   1. Else, return an empty sequence.
    */
  def apply(f: File): Seq[FileNodeTuple] = {
    assert(f.exists(), s"$f should refer to an existing file or directory")
    val xmlFiles: Seq[File] = if (f.isDirectory) {
      f.listFiles((_, name) => name.matches(".+-req\\.xml")).toList
    } else Seq(f)

    xmlFiles
      .map(f => {
        val doc = XML.loadFile(f)
        val trimmedNode = Utility.trim(doc)
        val nodeSeq = trimmedNode \ "Body" \ "Discover"
        assert(
          nodeSeq.isEmpty || nodeSeq.size == 1,
          s"multiple Discover nodes found in $f"
        )
        (f, nodeSeq)
      })
      .collect({
        case (f, ns) if ns.nonEmpty => new FileNodeTuple(f.getName, ns.head)
      })
  }
}
