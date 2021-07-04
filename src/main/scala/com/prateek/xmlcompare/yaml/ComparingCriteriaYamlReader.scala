package com.prateek.xmlcompare.yaml

import scala.jdk.CollectionConverters.{ListHasAsScala, MapHasAsScala}

import java.io.{File, FileInputStream}
import java.util

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

import com.prateek.xmlcompare.yaml.ComparingCriteriaYamlReader.{JList, JMap, StringSet}

/** Reads [[ComparingCriteria]] from a yaml file.
  */
object ComparingCriteriaYamlReader {

  import scala.language.implicitConversions

  type JList = util.ArrayList[String]
  type JMap[V] = util.Map[String, V]
  type StringSet = Set[String]

  def apply(file: File): Seq[Criteria] = {
    val contents = {
      val input = new FileInputStream(file)
      val yaml = new Yaml(new Constructor(classOf[JMap[JMap[JList]]]))
      val config: JMap[JMap[JList]] = yaml.load(input)
      config
    }
    val criterias = contents.asScala.
      map {
        case ("request", v: JMap[JList])  => Criteria(v)
        case ("response", v: JMap[JList]) => Criteria(v)
      }
    criterias.foreach(println)
    criterias.toSeq
  }
}

case class Criteria(
  exclude: StringSet,
  defaultInclude: StringSet,
  nodeConfig: Map[String, StringSet])

object Criteria {
  def apply(m: JMap[JList]): Criteria = {
    val exclude: StringSet = toSet(m.asScala.get("exclude"))
    val defaultInclude: StringSet = toSet(m.asScala.get("defaultInclude"))
    val nodeConfig: Map[String, StringSet] = m.asScala.toMap.flatMap({
      case ("exclude", _)        => Map.empty
      case ("defaultInclude", _) => Map.empty
      case (k, v)                => Map(k -> toSet(Option(v)))
    })
    Criteria(exclude, defaultInclude, nodeConfig)
  }

  private def toSet(m: Option[JList]): StringSet = {
    m.map(_.asScala.toSet).
      getOrElse(Set.empty)
  }
}

