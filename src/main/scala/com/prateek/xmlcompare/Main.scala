package com.prateek.xmlcompare

import scala.xml.Node

import java.io.File

object Main extends App {

  private val microDiscoverReqs = Discover(
    "atscale-micro"
  )
  private val smallDiscoverReqs = Discover(
    "atscale-small"
  )

/*  microDiscoverReqs.foreach(x => {
    println(x.file)
    x.node.head.child.foreach(d => println(d))
    println("----------------")
  })*/

  private val crs: Seq[Comparator.ComparatorResult] = Comparator(microDiscoverReqs, smallDiscoverReqs)
  crs.foreach(println)

}
case class XmlFile(file: File, node: Node)
