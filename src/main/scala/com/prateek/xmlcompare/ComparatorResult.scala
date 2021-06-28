package com.prateek.xmlcompare

sealed trait ComparatorResult {}
case class Subset(first: String, second: String) extends ComparatorResult
//TODO: make reason a stack of nodes and add reason for failing
case class ResponseFailure(first: String, second: String, reason: String)
    extends ComparatorResult

case class FileNotFound(ff: String, sf: String, nnf: NodeNotFound)
    extends ComparatorResult
case class NodeNotFound(node: String) extends ComparatorResult
case object NodeFound extends ComparatorResult
