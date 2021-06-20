package com.prateek.xmlcompare
import org.scalatest.funspec.AnyFunSpec

class MainTest extends AnyFunSpec {
  describe("Comparator behavior") {
    describe(
      "when one file is indented and corresponding match unindented"
    ) {
      it("should return matching request file names") {
        val f1 = getClass.getResource("/f/1a-req.xml").getFile
        val s1 = getClass.getResource("/s/1b-req.xml").getFile
        val args = s"-f $f1 -s $s1".split("\\s+")
        val crs = Main.execute(args)
        assert(crs.sizeIs == 1)
        crs.foreach({
          case Subset(first, second) if  first.getAbsolutePath.equals(f1) && second.getAbsolutePath.equals(s1) => succeed
          case _ => fail("expected match but found none")
        })
      }

      it("should return no match when matching request file not found") {
        val f1 = getClass.getResource("/f/2a-req.xml").getFile
        val s1 = getClass.getResource("/s/1b-req.xml").getFile
        val args = s"-f $f1 -s $s1".split("\\s+")
        val crs = Main.execute(args)
        assert(crs.sizeIs == 1)
        crs.foreach({
          case NoMatch(f, _) if f.getAbsolutePath.equals(f1) => succeed
          case _ => fail("expected no match but found some")
        })
      }
    }
  }
}
