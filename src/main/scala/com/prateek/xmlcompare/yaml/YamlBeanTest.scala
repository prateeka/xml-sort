package com.prateek.xmlcompare.yaml
import scala.beans.BeanProperty

import org.yaml.snakeyaml.Yaml

/** With the approach shown in the main method -- load() plus asInstanceOf --
  * this class must declare its properties in the constructor.
  */
// snakeyaml requires properties to be specified in the constructor
case class Person(
    @BeanProperty firstName: String,
    @BeanProperty lastName: String,
    @BeanProperty age: Int
)

object YamlBeanTest extends App {
  val data: String =
    "--- !!com.prateek.xmlcompare.yaml.Person [ David, Moore, 22 ]"
  val yaml: Yaml = new Yaml
  val obj: Person = yaml.load(data)
  println(obj)
}
