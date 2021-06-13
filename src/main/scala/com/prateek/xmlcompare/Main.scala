package com.prateek.xmlcompare
import java.io.File

import org.rogach.scallop.ScallopConf

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  val first = opt[File](required = true)
  val second = opt[File](required = true)
  verify()
}

object Main extends App {

  private val logger = com.typesafe.scalalogging.Logger(getClass)
  private val conf = new Conf(args)
  private val fFiles = XmlFile(conf.first())
  private val sFiles = XmlFile(conf.second())

  fFiles.foreach(x => {
    x.node.head.child.foreach(d => logger.debug(d.toString()))
  })

  private val crs = Comparator(fFiles, sFiles)
  crs.foreach(cr => logger.info(cr.toString))
}
