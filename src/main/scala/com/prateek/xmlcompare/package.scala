package com.prateek

import scala.xml.{ Elem, Node }

package object xmlcompare {
  def nodeToString(n: xml.Node): String = n match {
    case xml.Text(f) => f
    case Elem(_, f, _, _, _*) => f
  }

  object NodeToString {
    def unapply(node: Node): Option[String] = Option(nodeToString(node))
  }
}
