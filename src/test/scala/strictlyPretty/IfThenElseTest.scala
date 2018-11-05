package strictlyPretty

import utest._

object IfThenElseTest extends TestSuite {
  import Combinators._

  def binop(left: String, op: String, right: String): Doc = {
    group(
      nest(
        2,
        group(text(left) ^|: text(op)) ^|: text(right)
      )
    )
  }

  def ifthen(c: Doc, e1: Doc, e2: Doc): Doc = {
    group(
      group(nest(2, text("if") ^|: c))
        ^|: group(nest(2, text("then") ^|: e1))
        ^|: group(nest(2, text("else") ^|: e2))
    )
  }

  def cleanupExample(example: String): String = {
    example.stripMargin.trim.replace("\r", "")
  }

  val cond  = binop("a", "==", "b")
  val expr1 = binop("a", "<<", "2")
  val expr2 = binop("a", "+", "b")
  val doc   = ifthen(cond, expr1, expr2)

  val tests = Tests {
    'width_32 - {
      val pretty = Pretty.print(32, doc)

      assert(pretty == "if a == b then a << 2 else a + b")
    }
    'width_15 - {
      val pretty = Pretty.print(15, doc)
      val expected = cleanupExample(
        """if a == b
          |then a << 2
          |else a + b
        """
      )

      assert(pretty == expected)
    }
    'width_10 - {
      val pretty = Pretty.print(10, doc)
      val expected = cleanupExample(
        """if a == b
          |then
          |  a << 2
          |else a + b
        """
      )

      assert(pretty == expected)
    }
    'width_8 - {
      val pretty = Pretty.print(8, doc)
      val expected = cleanupExample(
        """if
          |  a == b
          |then
          |  a << 2
          |else
          |  a + b
        """
      )

      assert(pretty == expected)
    }
    'width_7 - {
      val pretty = Pretty.print(7, doc)
      val expected = cleanupExample(
        """if
          |  a ==
          |    b
          |then
          |  a <<
          |    2
          |else
          |  a + b
        """
      )

      assert(pretty == expected)
    }
    'width_6 - {
      val pretty = Pretty.print(6, doc)
      val expected = cleanupExample(
        """if
          |  a ==
          |    b
          |then
          |  a <<
          |    2
          |else
          |  a +
          |    b
        """
      )

      assert(pretty == expected)
    }
  }
}
