package strictlyPretty

import scala.annotation.tailrec

object Pretty {

  private object Internals {
    import SimplifiedDoc._

    sealed trait Mode
    case class Flat() extends Mode
    case class Break() extends Mode

    def fits(w: Int): List[(Int, Mode, Doc)] => Boolean = {
      case _ if w < 0                     => false
      case Nil                            => true
      case (i, m, DocNil()) :: z          => fits(w)(z)
      case (i, m, DocCons(x, y)) :: z     => fits(w)((i, m, x) :: (i, m, y) :: z)
      case (i, m, DocNest(j, x)) :: z     => fits(w)((i + j, m, x) :: z)
      case (i, m, DocText(s)) :: z        => fits(w - s.length)(z)
      case (i, Flat(), DocBreak(s)) :: z  => fits(w - s.length)(z)
      case (i, Break(), DocBreak(_)) :: _ => true /* impossible */
      case (i, m, DocGroup(x)) :: z       => fits(w)((i, Flat(), x) :: z)
    }

    def formatDoc(w: Int, k: Int): List[(Int, Mode, Doc)] => SDoc = {
      case Nil                            => SNil()
      case (i, m, DocNil()) :: z          => formatDoc(w, k)(z)
      case (i, m, DocCons(x, y)) :: z     => formatDoc(w, k)((i, m, x) :: (i, m, y) :: z)
      case (i, m, DocNest(j, x)) :: z     => formatDoc(w, k)((i + j, m, x) :: z)
      case (i, m, DocText(s)) :: z        => SText(s, formatDoc(w, k + s.length)(z))
      case (i, Flat(), DocBreak(s)) :: z  => SText(s, formatDoc(w, k + s.length)(z))
      case (i, Break(), DocBreak(_)) :: z => SLine(i, formatDoc(w, i)(z))
      case (i, m, DocGroup(x)) :: z =>
        if (fits(w - k)((i, Flat(), x) :: z)) {
          formatDoc(w, k)((i, Flat(), x) :: z)
        } else {
          formatDoc(w, k)((i, Break(), x) :: z)
        }
    }

    def formatSDoc: SDoc => String = {
      case SNil()            => ""
      case SText(text, doc)  => text + formatSDoc(doc)
      case SLine(depth, doc) => "\n" + (" " * depth) + formatSDoc(doc)
    }

  }

  def print(lineWidth: Int, doc: Doc): String = {
    Internals.formatSDoc(
      Internals.formatDoc(lineWidth, 0)(List((0, Internals.Flat(), DocGroup(doc))))
    )
  }

}
