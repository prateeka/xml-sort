package com.prateek.xmlcompare.yaml

import scala.collection.mutable
import scala.jdk.CollectionConverters.MapHasAsScala
import java.io.File

import org.scalatest.funspec.AnyFunSpec
import scala.jdk.CollectionConverters._

import com.prateek.xmlcompare.compare.{FileNotFound, NodeNotFound, Subset}

class ComparingCriteriaYamlReaderTest extends AnyFunSpec {
  describe("Yaml reader behavior") {
    describe(
      "when all elements of both request and response files are provided"
    ) {
      it("should return expected ComparingCriteria") {
        import java.util

        val criteriaMap = {
          val f = getClass.getResource("/yaml/criteria-config.yaml").getFile
          ComparingCriteriaYamlReader(new File(f))
        }
        //        println(criteriaMap.toString)
      }
    }
  }
}
