package com.prateek.xmlcompare

import scala.xml.NodeSeq

import java.io.File

object Main extends App {

  private val microDiscoverReqs: Seq[(File, NodeSeq)] = Discover(
    "atscale-micro"
  )
  private val smallDiscoverReqs: Seq[(File, NodeSeq)] = Discover(
    "atscale-small"
  )

  microDiscoverReqs.foreach(t => {
    println(t._1)
    t._2.head.child.foreach(d => println(d))
    println("----------------")
  })
  
}