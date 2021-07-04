package com.prateek.xmlcompare.yaml

import scala.jdk.CollectionConverters.{CollectionHasAsScala, SetHasAsScala}
import scala.collection.mutable
import scala.jdk.CollectionConverters.MapHasAsScala
import org.yaml.snakeyaml.constructor.Constructor
import org.yaml.snakeyaml.Yaml
import java.io.{File, FileInputStream}
import java.util
import scala.beans.BeanProperty


/** Reads [[ComparingCriteria]] from a yaml file.
  */
object ComparingCriteriaYamlReader {
  def apply(file: File): java.util.Map[String, util.Map[String, util.List[String]]] = {
    val contents = {
      val input = new FileInputStream(file)
      val yaml = new Yaml(new Constructor(classOf[util.Map[String, util.List[String]]]))
      val config: util.Map[String, util.Map[String, Object]] = yaml.load(input)
      config
    }
    contents.asScala foreach {
      case ("request", v: util.Map[String, Object])  => Criteria(v.asScala)
      case ("response", v: util.Map[String, Object]) => println(s"$v")
    }
    contents
  }
}

class Criteria {
  var exclude = new java.util.ArrayList()
  var defaultInclude = new java.util.ArrayList()
  var nodeConfig: java.util.Map[String, java.util.List[String]] =
    new java.util.HashMap[String, java.util.List[String]] {}

  override def toString =
    s"exclude: $exclude, defaultInclude: $defaultInclude, nodeConfig: $nodeConfig"
}

object Criteria {

  def apply(m: mutable.Map[String, Object]): Criteria = {
    val exclude: collection.Set[String] = toSet(m.get("exclude"))
    val defaultInclude: collection.Set[String] = toSet(m.get("defaultInclude"))

    println(exclude)
    println(defaultInclude)
    ???
  }

  private def toSet(m: Option[Object]): Set[String] = {
    m.map(v => v.asInstanceOf[util.List[String]].asScala.toSet).
      getOrElse(Set.empty)
  }
}
