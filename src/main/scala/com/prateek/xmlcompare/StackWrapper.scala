package com.prateek.xmlcompare

import scala.collection.mutable
import scala.xml.{ Elem, Node }

/** Wrapper for [[mutable.Stack]] to provide methods to push/pop Xml
  * [[scala.xml.Node]] into the wrapped [[mutable.Stack]]
  */
case class StackWrapper() {

  private val st: mutable.Stack[String] = new mutable.Stack[String]()

  def pop(): String = st.pop()

  def push(n: Node): StackWrapper = {
    st.push(n match {
      case xml.Text(t) => t
      case Elem(_, l, _, _, _*) => l
    })
    this
  }

  def top(): String = st.top
}
