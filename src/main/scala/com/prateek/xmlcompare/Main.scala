package com.prateek.xmlcompare
import java.io.File

import org.rogach.scallop.{ ScallopConf, ScallopOption }

object Main extends App {
  private val logger = com.typesafe.scalalogging.Logger(getClass)
  private val crs: Seq[ComparatorResult] = execute(args)

  def execute(args: Array[String]) = {
    val conf = new Conf(args)
    val fFiles = XmlFile(conf.first())
    val sFiles = XmlFile(conf.second())
    val crs = Comparator(fFiles, sFiles)
    crs
  }
  crs.foreach(cr => logger.info(cr.toString))

  class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
    val first: ScallopOption[File] = opt[File](required = true)
    val second: ScallopOption[File] = opt[File](required = true)
    verify()
  }
}
