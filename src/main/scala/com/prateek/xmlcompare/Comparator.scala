package com.prateek.xmlcompare
import scala.collection.mutable
import scala.xml.{ Elem, Node }

import java.io.File

object Comparator {

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  // checking the "request" files to find matching "request" file
  def apply(
      first: Seq[FileNodeTuple],
      second: Seq[FileNodeTuple]
  ): Seq[ComparatorResult] = {
    first.map(f =>
      second.find(s =>
        apply(f.node, s.node)(new mutable.Stack[String]())
      ) match {
        case Some(s) => Subset(f.file, s.file)
        case None => NoMatch(f.file)
      }
    )
  }

  def apply(first: Node, second: Node)(implicit
      st: mutable.Stack[String]
  ): Boolean = {
    logger.debug(s"first node: $first")
    val ccs = ComparingCriteria(first)
    st.push(first match {
      case _ @xml.Text(t) => t
      case _ @Elem(_, l, _, _, _*) => l
    })
    val bool = ccs.forall(_(first, second))
    if (bool) {
      logger.info(
        s"success ${st.reverse.mkString(".")} " +
          s"${ccs.map(_.getClass.getSimpleName).mkString(",")}"
      )
    } else {
      logger.warn(
        s"failure ${st.reverse.mkString(".")} " +
          s"${ccs.map(_.getClass.getSimpleName).mkString(",")}"
      )
    }
    st.pop()
    bool
  }
}

sealed trait ComparatorResult {}

case class Subset(first: File, second: File) extends ComparatorResult

//TODO: make reason a stack of nodes and add reason for failing
case class ResponseFailure(first: File, second: File, reason: String)
    extends ComparatorResult

case class NoMatch(
    file: File,
    reason: String = "matching request not found"
) extends ComparatorResult
