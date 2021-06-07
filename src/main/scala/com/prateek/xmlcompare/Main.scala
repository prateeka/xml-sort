package com.prateek.xmlcompare

import scala.xml.NodeSeq

import java.io.File

object Main extends App {

  private val microDiscoverReqs = Discover(
    "atscale-micro"
  )
  private val smallDiscoverReqs = Discover(
    "atscale-small"
  )

  microDiscoverReqs.foreach(x => {
    println(x.file)
    x.nodeSeq.head.child.foreach(d => println(d))
    println("----------------")
  })

}
case class XmlFile(file: File, nodeSeq: NodeSeq)
