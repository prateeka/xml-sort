package com.prateek.xmlcompare.yaml

import org.scalatest.funspec.AnyFunSpec

import com.prateek.xmlcompare.compare.{FileNotFound, NodeNotFound, Subset}

class ComparingCriteriaYamlReaderTest extends AnyFunSpec {
  describe("Yaml reader behavior") {
    describe(
      "when all elements of both request and response files are provided"
    ) {
      it("should return expected ComparingCriteria") {
        val f = getClass.getResource("/yaml/all-provided.yaml").getFile

/*
        val crs = Main.execute(args)
        assert(crs.sizeIs == 1)
        crs.foreach({
          case Subset(first, second)
              if first.getAbsolutePath.equals(f1) && second.getAbsolutePath
                .equals(s1) =>
            succeed
          case _ => fail("expected match but found none")

        )
*/
      }
    }
  }
}
