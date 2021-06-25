package com.prateek.xmlcompare
import java.io.File

object Comparator {

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  // checking the "request" files to find matching "request" file
  def apply(
      first: Seq[FileNodeTuple],
      second: Seq[FileNodeTuple]
  ): Seq[ComparatorResult] = {
    first.map(f => {
      val maybeTuple = second.find(apply(f, _)(new StackWrapper) match {
        case NodeFound => true
        case _ => false
      })
      maybeTuple match {
        case Some(s) => Subset(f.file, s.file)
        case None => FileNotFound(f.file)
      }
    })
  }

  // checking if two files match
  def apply(fnt: FileNodeTuple, snt: FileNodeTuple)(implicit
      st: StackWrapper
  ): ComparatorResult = {
    val ccs = ComparingCriteria(fnt)
    logger.info(s"***comparing \n$fnt\n$snt")
    st.push(fnt.node)
    val cr = {
      val cr1 = ccs.foldLeft(NodeNotFound(""): ComparatorResult)({
        case (nf @ NodeFound, _) => nf
        case (b @ NodeNotFound(n1), a) =>
          a(fnt, snt) match {
            case nf @ NodeFound => nf
            case NodeNotFound(n2) if n1.length >= n2.length => b
            case nnf2 @ NodeNotFound(_) => nnf2
          }
      })
      cr1 match {
        case NodeNotFound(n) => NodeNotFound(s"${st.top()}.$n")
        case nf @ NodeFound => nf
      }
    }
    st.pop()
    cr
  }
}

sealed trait ComparatorResult {}
case class Subset(first: File, second: File) extends ComparatorResult
//TODO: make reason a stack of nodes and add reason for failing
case class ResponseFailure(first: File, second: File, reason: String)
    extends ComparatorResult

case class FileNotFound(file: File) extends ComparatorResult
case class NodeNotFound(node: String) extends ComparatorResult
case object NodeFound extends ComparatorResult
