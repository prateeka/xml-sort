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
    first.map(f =>
      second.find(s => apply(f.node, s.node)) match {
        case Some(s) => Success(f.file, s.file)
        case None    => RequestFailure(f.file)
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
