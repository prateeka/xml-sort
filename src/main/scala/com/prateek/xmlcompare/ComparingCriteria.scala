package com.prateek.xmlcompare

import scala.xml.Node

trait ComparingCriteria {
  def execute(first: Node, second: Node): Boolean
}

object ComparingCriteria {
  def apply(node: Node): Seq[ComparingCriteria] = {
    Seq(Length, Label)
  }
}

object Length extends ComparingCriteria {
  override def execute(first: Node, second: Node): Boolean = {
    first.child.length == second.child.length
  }
}

object Label extends ComparingCriteria {
  override def execute(first: Node, second: Node): Boolean = {
    first.label.equalsIgnoreCase(second.label)
  }
}
