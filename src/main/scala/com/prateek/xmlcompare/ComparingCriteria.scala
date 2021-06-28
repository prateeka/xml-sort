package com.prateek.xmlcompare

import scala.xml.Node

trait ComparingCriteria {
  def apply(fn: Node, sn: Node)(implicit ctx: Context): ComparatorResult
}

object ComparingCriteria {
  def apply(fn: Node): Seq[ComparingCriteria] = {
    Seq(TextLabel, Length, RecursiveMatch)
  }
}

object Length extends ComparingCriteria {
  override def apply(fn: Node, sn: Node)(implicit
      ctx: Context
  ): ComparatorResult = {
    if (fn.child.length == sn.child.length) NodeFound else NodeNotFound(fn)
  }
}

object TextLabel extends ComparingCriteria {
  override def apply(fn: Node, sn: Node)(implicit
      ctx: Context
  ): ComparatorResult = {
    val bool = (fn, sn) match {
      case NodeToString(f, s) => f.equalsIgnoreCase(s)
    }
    if (bool) NodeFound else NodeNotFound(fn)
  }
}

// Match the nodes recursively
object RecursiveMatch extends ComparingCriteria {

  override def apply(fn: Node, sn: Node)(implicit
      ctx: Context
  ): ComparatorResult = {

    /** Matches f with all [[sn.child]] for ANY [[NodeFound]]. If no match
      * occurs, then returns the [[NodeNotFound]] with the longest
      * [[NodeNotFound.node]] as it is the one which has matched the deepest.
      * @param f
      *   one of [[fn.child]]
      * @return
      *   [[NodeFound]] for match else [[NodeNotFound]] for the deepest match
      */
    def anyMatches(f: Node): ComparatorResult = {
      val llm = sn.child
        .to(LazyList)
        .map(s => Comparator(f, s))

      llm
        .find({
          case NodeFound => true
          case NodeNotFound(_) => false
        })
        .getOrElse(llm.reduce((c1: ComparatorResult, c2: ComparatorResult) => {
          (c1, c2) match {
            case (nnf1 @ NodeNotFound(n1), _ @NodeNotFound(n2))
                if n1.length >= n2.length =>
              nnf1
            case (_ @NodeNotFound(n1), nnf2 @ NodeNotFound(n2))
                if n1.length < n2.length =>
              nnf2
          }
        }))
    }
    val childMatch = lazyAllMatch(fn.child, anyMatches)
    // prepending parent node text/label to child's for getting the exact depth of the child
    childMatch match {
      case nf @ NodeFound => nf
      case NodeNotFound(n) => NodeNotFound(s"${nodeToString(fn)}.$n")
    }
  }
}

case class Context(f1: String, f2: String) {
  val st: StackWrapper = new StackWrapper
}
