package strictlyPretty

object Classy {

  trait PrettyPrintable[-T] {
    def prettify(input: T): Doc
  }

  object Pretty {
    def print[T](lineWidth: Int, input: T)(implicit printer: PrettyPrintable[T]): String = {
      strictlyPretty.Pretty.print(lineWidth, printer.prettify(input))
    }
  }

}
