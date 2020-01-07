package com.prateek.xmlsort

object Main extends App {

  import scala.xml.{Elem, XML}

  val doc = XML.loadFile("src/main/resources/ssas-response.xml")
  private val seq = doc \\ "row"
  seq
    .map(r => {
      val name = r.descendant
      name.foreach {
        case elem: Elem =>
          import scala.xml.Text
          println(s"${elem.label}")
          elem.child foreach {
            case Text(data) => println(data)
            case other      => println("other")
          }
        case _ =>
      }
//      println(s"${name.text}")
    })
    .toList
//  println(seq)
}
