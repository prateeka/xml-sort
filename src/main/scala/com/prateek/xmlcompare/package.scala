package com.prateek

import scala.language.implicitConversions

import scala.xml.{ Elem, Node, Text }

package object xmlcompare {

  implicit def nodeToString(n: xml.Node): String = n match {
    case Text(f) => f
    case Elem(_, f, _, _, _*) => f
  }

/*
  def lazyAllMatch(nodes:Seq[Node], f:): ComparatorResult = {
    val childMatch = fn.child.view
      .map(anyMatches)
      .find({
        case NodeNotFound(_) => true
        case NodeFound => false
      })
      .getOrElse(NodeFound)
  }
*/

  object NodeToString {
    def unapply(tuple: (Node, Node)): Option[(String, String)] = {
      tuple match {
        case (Text(f), Text(s)) => Option((f, s))
        case (Elem(_, f, _, _, _*), Elem(_, s, _, _, _*)) => Option(f, s)
      }
    }
  }
}
