package com.prateek.xmlcompare
import org.scalatest.funspec.AnyFunSpec

class MainTest extends AnyFunSpec {
  describe("Comparator behavior") {
    describe(
      "when one request file is indented and corresponding match unindented"
    ) {
      describe("when matching files do not have similar names") {
        it("should return matching request file names") {
          val f1 = getClass.getResource("/f/0001a-req.xml").getFile
          val s1 = getClass.getResource("/s/0001b-req.xml").getFile
          val args = s"-f $f1 -s $s1".split("\\s+")
          Main.execute(args)
          args.foreach(println)
        }
      }
    }
  }
}
