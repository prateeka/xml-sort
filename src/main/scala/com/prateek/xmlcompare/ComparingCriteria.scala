package com.prateek.xmlcompare

import scala.xml.Node

trait ComparingCriteria extends ((Node, Node) => Boolean) {}

object ComparingCriteria {
  def apply(node: Node): Seq[ComparingCriteria] = {
    Seq(ExactMatch)
  }
}

object Length extends ComparingCriteria {
  override def apply(first: Node, second: Node): Boolean = {
    first.child.length == second.child.length
  }
}

object Label extends ComparingCriteria {
  override def apply(first: Node, second: Node): Boolean = {
    first.label.equalsIgnoreCase(second.label)
  }
}

/*
 Match the node label and recursive children match by including:
 1. children count
 2. one to one exact match between first and second node
 */
object ExactMatch extends ComparingCriteria {
  override def apply(first: Node, second: Node): Boolean = {

    def childCompare(first1: Node, second1: Node) = {
      first1.child.forall(f => second1.child.exists(s => Comparator(f, s)))
    }

    Label(first, second) &&
    Length(first, second) &&
    childCompare(first, second) &&
    childCompare(second, first)
  }
}
