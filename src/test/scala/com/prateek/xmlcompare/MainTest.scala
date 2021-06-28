package com.prateek.xmlcompare
import org.scalatest.funspec.AnyFunSpec

class MainTest extends AnyFunSpec {
  describe("Comparator behavior") {
    describe(
      "when one file is indented and corresponding match unindented"
    ) {
      it("should return matching request file names") {
        val f1 = getClass.getResource("/indented/1-req.xml").getFile
        val s1 = getClass.getResource("/nonindented/1-req.xml").getFile
        val args = s"-f $f1 -s $s1".split("\\s+")
        val crs = Main.execute(args)
        assert(crs.sizeIs == 1)
        crs.foreach({
          case Subset(fn, sn)
              if fn.equals(FileName(f1)) && sn.equals(FileName(s1)) =>
            succeed
          case _ => fail("expected match but found none")
        })
      }

      it("should return no match when matching request file not found") {
        val f1 = getClass.getResource("/indented/2-req.xml").getFile
        val s1 = getClass.getResource("/nonindented/1-req.xml").getFile
        val args = s"-f $f1 -s $s1".split("\\s+")
        val crs = Main.execute(args)
        assert(crs.sizeIs == 1)
        crs.foreach({
          case FileNotFound(f, s, _)
              if f.equals(FileName(f1)) && s.equals(FileName(s1)) =>
            succeed
          case Subset(fn, sn) =>
            fail(s"expected no match but found $fn matching $sn")
        })
      }
    }
  }

  object FileName {
    private val fnp = raw".+/(.+)".r
    def apply(ap: String): String = {
      ap match {
        case fnp(n) => n
        case _ => throw new Exception(s"failing to parse the $ap using $fnp")
      }
    }
  }
}
