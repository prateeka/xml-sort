package com.prateek.xmlcompare
import scala.xml.Node

import java.io.File

object Main extends App {

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  private val microDiscoverReqs = Discover(
    "atscale-micro"
  )
  private val smallDiscoverReqs = Discover(
    "atscale-small"
  )

  microDiscoverReqs.foreach(x => {
    x.node.head.child.foreach(d => logger.debug(d.toString()))
  })

  private val crs =
    Comparator(microDiscoverReqs, smallDiscoverReqs)
  crs.foreach(cr=>logger.info(cr.toString))

}
case class XmlFile(file: File, node: Node)
