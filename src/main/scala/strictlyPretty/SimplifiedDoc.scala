package strictlyPretty

private[strictlyPretty] object SimplifiedDoc {

  sealed trait SDoc
  case class SNil() extends SDoc
  case class SText(text: String, doc: SDoc) extends SDoc
  case class SLine(depth: Int, doc: SDoc) extends SDoc

}
