package com.prateek.xmlcompare.yaml

import scala.beans.BeanProperty

import java.io.{ File, FileInputStream }

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

object YamlBeanTest2 extends App {
  val input = {
    val filename = getClass.getResource("/yaml/config2.yaml").getFile
    println(filename)
    new FileInputStream(new File(filename))
  }
  val yaml: Yaml = new Yaml(new Constructor(classOf[AllConfig]))
  val e = yaml.load(input).asInstanceOf[AllConfig]
  println(e)
}

class Config {
  @BeanProperty var req: EmailAccount = new EmailAccount
  override def toString =
    s"req= $req"
}

class EmailAccount {
  @BeanProperty var accountName = ""
  @BeanProperty var username = ""
  @BeanProperty var usersOfInterest = new java.util.ArrayList[String]()

  override def toString =
    s"acct: $accountName, user: $username, usersOfInterest: $usersOfInterest"
}
