package object strictlyPretty {

  sealed trait Doc
  case class DocNil() extends Doc
  case class DocCons(l: Doc, r: Doc) extends Doc
  case class DocText(text: String) extends Doc
  case class DocNest(depth: Int, doc: Doc) extends Doc
  case class DocBreak(sep: String) extends Doc
  case class DocGroup(doc: Doc) extends Doc

}
