package com.prateek.xmlcompare
import scala.collection.mutable
import scala.xml.Node

import java.io.File

object Comparator {

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  // checking the "request" files to find matching "request" file
  def apply(
      first: Seq[XmlFile],
      second: Seq[XmlFile]
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
    val ccs = ComparingCriteria(first)
    st.push(first.label)
    val bool = ccs.forall(_(first, second))
    if (bool) {
      logger.info(
        s"success ${st.mkString(".")} ${ccs.map(_.getClass.getSimpleName).mkString(",")}"
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
