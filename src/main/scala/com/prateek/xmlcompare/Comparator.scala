package com.prateek.xmlcompare
import scala.xml.Node

import java.io.File

object Comparator {

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  // checking the "request" files to find matching "request" file
  def apply(
      first: Seq[XmlFile],
      second: Seq[XmlFile]
  ): Seq[ComparatorResult] = {
    first.map(s =>
      second.find(d => apply(s.node, d.node)) match {
        case Some(d) => Success(s.file, d.file)
        case None => RequestFailure(s.file)
      }
    )
  }

  def apply(first: Node, second: Node): Boolean = {
    val criterias = ComparingCriteria(first)
    criterias.forall(_(first, second))
  }

  sealed trait ComparatorResult {}

  case class Success(first: File, second: File) extends ComparatorResult

  //TODO: make reason a stack of nodes and add reason for failing
  case class ResponseFailure(first: File, second: File, reason: String)
      extends ComparatorResult

  case class RequestFailure(
      file: File,
      reason: String = "matching request not found"
  ) extends ComparatorResult
}
