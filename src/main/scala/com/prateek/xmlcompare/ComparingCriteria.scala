package com.prateek.xmlcompare

trait ComparingCriteria {

  def isSatisfied(first: FileNodeTuple, second: FileNodeTuple)(implicit
      st: StackWrapper
  ): Boolean

  def apply(first: FileNodeTuple, second: FileNodeTuple)(implicit
      st: StackWrapper
  ): ComparatorResult = {
    if (isSatisfied(first, second)) NodeFound else NodeNotFound("")
  }
}

object ComparingCriteria {
  def apply(fnt: FileNodeTuple): Seq[ComparingCriteria] = {
    Seq(TextLabel, Length, RecursiveMatch)
  }
}

object Length extends ComparingCriteria {
  override def isSatisfied(first: FileNodeTuple, second: FileNodeTuple)(implicit
      st: StackWrapper
  ): Boolean = first.node.child.length == second.node.child.length
}

object TextLabel extends ComparingCriteria {
  override def isSatisfied(first: FileNodeTuple, second: FileNodeTuple)(implicit
      st: StackWrapper
  ): Boolean = {
    (first.node, second.node) match {
      case (NodeToString(f), NodeToString(s)) => f.equalsIgnoreCase(s)
    }
  }
}

// Match the nodes recursively
object RecursiveMatch extends ComparingCriteria {
  override def apply(first: FileNodeTuple, second: FileNodeTuple)(implicit
      st: StackWrapper
  ): ComparatorResult = {
    st.push(first.node)
    val bool = isSatisfied(first, second)
    st.pop()
    if (bool)
      NodeFound
    else
      NodeNotFound("")
  }

  override def isSatisfied(first: FileNodeTuple, second: FileNodeTuple)(implicit
      st: StackWrapper
  ): Boolean = {
    first.node.child.forall(_ =>
      second.node.child.exists(_ =>
        Comparator(first, second) match {
          case NodeNotFound(_) => false
          case NodeFound => true
        }
      )
    )
  }
}
