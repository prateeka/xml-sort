package com.prateek.xmlcompare.yaml

import scala.beans.BeanProperty
import scala.collection.mutable

import java.io.{ File, FileInputStream }

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

object ComparingCriteriaReader extends App {
  val input = {
    val filename = getClass.getResource("/yaml/criteria-config.yaml").getFile
    new FileInputStream(new File(filename))
  }
  val yaml: Yaml = new Yaml(new Constructor(classOf[AllConfig]))
  val e = yaml.load(input).asInstanceOf[AllConfig]
  println(e)
}

class AllConfig {
  @BeanProperty var request = new CriteriaConfig
  override def toString = s"req= $request"
}

class CriteriaConfig {
  type JList = java.util.ArrayList[String]
  @BeanProperty var exclude = new JList()
  @BeanProperty var defaultInclude = new JList()
  @BeanProperty var nodeConfig: java.util.Map[String, JList] =
    new java.util.HashMap[String, JList] {}

  override def toString =
    s"exclude: $exclude, defaultInclude: $defaultInclude, nodeConfig: $nodeConfig"
}
