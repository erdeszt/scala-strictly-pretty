## Scala implementation of "Strictly Pretty" by Christian Lindig

[Original Paper](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.34.2200&rep=rep1&type=pdf)

### Usage

```scala
import strictlyPretty._
import strictlyPretty.Combinators._

val doc: Doc = group(text("a") ^|: text("==") ^|: text("b"))
val pretty: String = Pretty.print(40, doc) // Line width = 40
// pretty = "a == b"
```

For a more complex example check out [strictlyPretty.IfThenElseTest](https://github.com/erdeszt/scala-strictly-pretty/blob/master/src/test/scala/strictlyPretty/IfThenElseTest.scala)

#### Classy interface

```scala
import strictlyPretty.Classy._
import strictlyPretty.Combinators._

case class Binop(lhs: String, op: String, rhs: String)

implicit val binopPrettyPrintable: PrettyPrintable[Binop] = {
    case Binop(lhs, op, rhs) => group(text(lhs) ^|: text(op) ^|: text(rhs))
}

val pretty: String = Pretty.print(40, Binop("a", "==", "b"))
// pretty = "a == b"
```