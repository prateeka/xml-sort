package com.prateek.xmlcompare

import scala.xml.{ Elem, Node }

trait ComparingCriteria extends ((Node, Node) => Boolean) {}

object ComparingCriteria {
  def apply(node: Node): Seq[ComparingCriteria] = {
    Seq(Label, Length, RecursiveMatch)
  }
}

object Length extends ComparingCriteria {
  override def apply(first: Node, second: Node): Boolean = {
    first.child.length == second.child.length
  }
}

object Label extends ComparingCriteria {
  override def apply(first: Node, second: Node): Boolean = {
    (first, second) match {
      case (_ @xml.Text(f), _ @xml.Text(s)) => f.equalsIgnoreCase(s)
      case (_ @Elem(_, f, _, _, _*), _ @Elem(_, s, _, _, _*)) =>
        f.equalsIgnoreCase(s)
    }
  }
}

// Match the nodes recursively
object RecursiveMatch extends ComparingCriteria {
  override def apply(first: Node, second: Node): Boolean = {
    first.child.forall(f => second.child.exists(s => Comparator(f, s)))
  }
}
