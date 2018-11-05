package strictlyPretty

import utest._
import strictlyPretty.Classy._
import strictlyPretty.Combinators._

object ClassyTest extends TestSuite {

  case class Binop(lhs: String, op: String, rhs: String)

  implicit val binopPrettyPrintable: PrettyPrintable[Binop] = {
    case Binop(lhs, op, rhs) => group(text(lhs) ^|: text(op) ^|: text(rhs))
  }

  val tests = Tests {
    'CustomClassPrinter - {
      val pretty = Classy.Pretty.print(40, Binop("a", "==", "b"))

      assert(pretty == "a == b")
    }
  }
}
