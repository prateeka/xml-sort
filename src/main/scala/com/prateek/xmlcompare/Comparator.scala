package com.prateek.xmlcompare
import java.io.File

object Comparator {

  import scala.xml.Node

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  // checking the "request" files to find matching "request" file
  def apply(
      first: Seq[FileNodeTuple],
      second: Seq[FileNodeTuple]
  ): Seq[ComparatorResult] = {
    first.map({ case FileNodeTuple(f, fn) =>
      val maybeTuple = second.find({ case FileNodeTuple(s, sn) =>
        apply(fn, sn)(Context(f, s)) match {
          case NodeFound => true
          case _ => false
        }
      })
      maybeTuple match {
        case Some(snt) => Subset(f, snt.file)
        case None => FileNotFound(f)
      }
    })
  }

  // checking if two files match
  def apply(fn: Node, sn: Node)(implicit ctx: Context): ComparatorResult = {
    val ccs = ComparingCriteria(fn)
    logger.info(s"***comparing \n$fn\n$sn")
    /*
        ctx.st.push(fn)
        val cr = {
          val cr1 = ccs.foldLeft(NodeFound: ComparatorResult)({
            case (nnf @ NodeNotFound(_), _) => nnf
            case (NodeFound, cc) => cc(fn, sn)
          })
          cr1 match {
            case NodeNotFound(n) => NodeNotFound(s"${ctx.st.pop()}.$n")
            case nf @ NodeFound => nf
          }
        }
        cr
     */
    /*    val cr = ccs.foldLeft(NodeFound: ComparatorResult)({
      case (nnf @ NodeNotFound(_), _) => nnf
      case (NodeFound, cc) => cc(fn, sn)
    })*/
    val cr = ccs.iterator
      .map(_(fn, sn))
      .find({
        case NodeNotFound(_) => true
        case NodeFound => false
      })
      .getOrElse(NodeFound)
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
