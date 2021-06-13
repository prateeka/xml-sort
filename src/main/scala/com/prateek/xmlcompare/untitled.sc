import scala.xml.XML

import java.io.File

import com.prateek.xmlcompare.Discover.getClass

val file = new File(
  getClass.getClassLoader.getResource("atscale-micro/0002-req.xml").getPath
)
val seq = (XML.loadFile(file) \ "Body" \ "Discover").head

val b = (<a><b>hello</b></a> \ "b").head
b
b.label
b.text
b.child.head match {
  case a:xml.Text=>a.text
}