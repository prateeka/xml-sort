package com.prateek.xmlcompare

import scala.xml.Node

object Comparator {
  private val logger = com.typesafe.scalalogging.Logger(getClass)

  // matching req files from one directory with another
  def apply(
      first: Seq[FileNodeTuple],
      second: Seq[FileNodeTuple]
  ): Seq[ComparatorResult] = {
    first.map({ case FileNodeTuple(f, fn) =>
      val maybeTuple = second.find({ case FileNodeTuple(s, sn) =>
        apply(fn, sn)(Context(f, s)) match {
          case NodeFound => true
          case _ => false
        }
      })
      maybeTuple match {
        case Some(snt) => Subset(f, snt.file)
        case None => FileNotFound(f)
      }
    })
  }

  // checking if two files match
  def apply(fn: Node, sn: Node)(implicit ctx: Context): ComparatorResult = {
    val ccs = ComparingCriteria(fn)
    logger.info(s"***comparing \n$fn\n$sn")
    lazyAllMatch(ccs, (cc: ComparingCriteria) => cc(fn, sn))
  }
}
