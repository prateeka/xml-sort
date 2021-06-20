package com.prateek.xmlcompare

import scala.collection.mutable
import scala.xml.{ Elem, Node }

trait ComparingCriteria {
  def apply(first: Node, second: Node)(implicit
      st: mutable.Stack[String]
  ): Boolean
}

object ComparingCriteria {
  def apply(
      node: Node
  )(implicit st: mutable.Stack[String]): Seq[ComparingCriteria] = {
    Seq(TextLabel, Length, RecursiveMatch)
  }
}

object Length extends ComparingCriteria {
  override def apply(first: Node, second: Node)(implicit
      st: mutable.Stack[String]
  ): Boolean = {
    first.child.length == second.child.length
  }
}

object TextLabel extends ComparingCriteria {
  override def apply(first: Node, second: Node)(implicit
      st: mutable.Stack[String]
  ): Boolean = {
//    TODO: extract to common function
    (first, second) match {
      case (xml.Text(f), xml.Text(s)) => f.equalsIgnoreCase(s)
      case (Elem(_, f, _, _, _*), Elem(_, s, _, _, _*)) =>
        f.equalsIgnoreCase(s)
    }
  }
}

// Match the nodes recursively
object RecursiveMatch extends ComparingCriteria {
  override def apply(first: Node, second: Node)(implicit
      st: mutable.Stack[String]
  ): Boolean = {
    first.child.forall(f => second.child.exists(s => Comparator(f, s)))
  }
}
