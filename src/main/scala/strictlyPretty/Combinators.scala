package strictlyPretty

object Combinators {
  implicit class DocOperators(lhs: Doc) {
    def ^^:(rhs: Doc): Doc = DocCons(rhs, lhs)
    def ^|:(rhs: Doc): Doc = {
      (lhs, rhs) match {
        case (DocNil(), _) => lhs
        case (_, DocNil()) => rhs
        case (_, _)        => rhs ^^: break ^^: lhs
      }
    }
  }
  val empty:     Doc = DocNil()
  val text:      String => Doc = DocText
  val break:     Doc = DocBreak(" ")
  val breakWith: String => Doc = DocBreak
  val group:     Doc => Doc = DocGroup
  def nest(depth: Int, doc: Doc): Doc = DocNest(depth, doc)
}
