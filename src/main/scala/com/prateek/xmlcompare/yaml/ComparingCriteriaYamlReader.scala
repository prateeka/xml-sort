package com.prateek.xmlcompare.yaml

import scala.beans.BeanProperty

import java.io.{ File, FileInputStream }

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

/** Reads [[ComparingCriteria]] from a yaml file.
  */
object ComparingCriteriaYamlReader {
  def apply(file: File): Config = {
    val input = new FileInputStream(file)
    val yaml = new Yaml(new Constructor(classOf[Config]))
    val config: Config = yaml.load(input)
    config
  }
}

class Config {
  @BeanProperty var request = new Criteria
  override def toString = s"req= $request"
}

class Criteria {
  type JList = java.util.ArrayList[String]
  @BeanProperty var exclude = new JList()
  @BeanProperty var defaultInclude = new JList()
  @BeanProperty var nodeConfig: java.util.Map[String, java.util.List[String]] =
    new java.util.HashMap[String, java.util.List[String]] {}

  override def toString =
    s"exclude: $exclude, defaultInclude: $defaultInclude, nodeConfig: $nodeConfig"

}
