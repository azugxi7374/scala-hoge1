package ixlib

import java.awt.image.BufferedImage
import java.io.File

import scala.annotation.tailrec
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

final case class UnionFind(size: Int) {
  val par = Array.tabulate(size) { i => i}

  @tailrec
  def root(i: Int): Int = if (par(i) == i) i else root(par(i))

  @tailrec
  def union(i1: Int, i2: Int): Unit = {
    val p1 = par(i1)
    par(i1) = root(i2)
    if (p1 != i1) union(p1, i2)
  }
}

object Eval {

  private def eval[A](string: String): A = {
    val toolbox = currentMirror.mkToolBox()
    val tree = toolbox.parse(string)
    toolbox.eval(tree).asInstanceOf[A]
  }

  def apply[A](string: String, args: String*): A = {
    val func = eval[Array[String] => A]( s"""(args:Array[String])=>{\n${string}\n}""")
    func(args.toArray)
  }

  def fromFile[A](file: File, args: String*): A = apply(scala.io.Source.fromFile(file, "UTF-8").mkString(""),
    args: _*)

  def fromFile[A](file: String, args: String*): A = fromFile(new File(file), args: _*)

}

/**
 * @see [[ixlib.printWrapper]]
 * @see [[ixlib.timeWrapper]]
 * @note (カリー化すると定義が重複する...
 */
class Wrapper[H, F, T] private(header: () => H, footer: (H, T) => F) {
  def apply[U <: T](f: => U): U = {
    val h = header()
    val ret = f
    footer(h, ret)
    ret
  }
}

/**
 * @see [[ixlib.printWrapper]]
 * @see [[ixlib.timeWrapper]]
 * @note (カリー化すると定義が重複する...
 */
object Wrapper {
  /** 名前渡し用 */
  def apply[H, F, T](header: => H, footer: (H, T) => F): Wrapper[H, F, T] =
    new Wrapper(() => header, footer)

  /** ヘッダの戻り値だけ渡す */
  def headerToFooter[H, F](header: => H, footer: H => F): Wrapper[H, F, Any] =
    new Wrapper(() => header, (a: H, _: Any) => footer(a))

  /** 中間の戻り値だけ渡す */
  def contentToFooter[H, T](header: => Any, footer: T => H): Wrapper[Any, H, T] =
    new Wrapper(() => header, (_: Any, t: T) => footer(t))

  /** どっちも渡さない */
  def apply(header: => Any, footer: => Any): Wrapper[Any, Any, Any] =
    new Wrapper(() => header, (_: Any, _: Any) => footer)

  /** ヘッダのみ */
  def withHeader[H](header: => H): Wrapper[H, Any, Any] = Wrapper(header, (_: H, _: Any) => {})

  /** フッタのみ */
  def withFooter[F, T](footer: T => F): Wrapper[Any, F, T] = contentToFooter((), footer)

  /** フッタのみ & フッタ引数なし */
  def withFooter[F](footer: => F): Wrapper[Any, F, Any] = contentToFooter((), (_: Any) => footer)

}


/** BufferedImage拡張
  *
  * @note '''BufferedImage.TYPE_INT_RGBのみ'''
  */
case class BI(bi: BufferedImage) {
  lazy val W = bi.getWidth
  lazy val H = bi.getHeight

  /** @return (x, y)のRGB
    * @example
		 {{{
       * bi(x, y).r // -> R of (x, y)

       * val i = 1
       * bi(x, y)(i) // -> B of (x, y)
       * }}}
    */
  def apply(x: Int, y: Int): RGB = RGB(bi.getRGB(x, y))

  def sub(x: Int, y: Int, w: Int, h: Int) = BI(bi.getSubimage(x, y, w, h))

  /** @example
	{{{
    * val (w, h) = bi.size
    * }}}
    */
  def size: (Int, Int) = (bi.getWidth, bi.getHeight)

  object RGB {
    def apply(r: Int, g: Int, b: Int) = {
      (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff)
    }
  }

  case class RGB(_rgb: Int) {
    //遅い
    //def rgb = Array.tabulate(3)(apply(_))

    lazy val r = apply(0)
    lazy val g = apply(1)
    lazy val b = apply(2)

    def gray: Int = (r + g + b) / 3

    def apply(i: Int) = (_rgb >> (2 - i) * 8) & 0xff
  }

}

////////////////////////////////////////////////////////////////////
case class Itrt1(N1: Int) extends Iterator[Int] {
  var i = 0

  override def hasNext: Boolean = i < N1

  override def next(): Int = {
    val ret = i
    i += 1
    ret
  }
}

object Itrt1 {
  def idxItrt[T](a: Array[T]) = Itrt1(a.size)
}

case class Itrt2(N1: Int, N2: Int) extends Iterator[(Int, Int)] {
  var i = 0

  override def hasNext: Boolean = i < N1 * N2

  override def next(): (Int, Int) = {
    val ret = (i / N2, i % N2)
    i += 1
    ret
  }
}

/**
 * @example
 {{{
  * Itrt2(n1, n2)
  * }}}
  *
 */
object Itrt2 {
  def idxItrt[T](a: Array[Array[T]]) = Itrt2(a.size, a(0).size)
}


/**
 * java.awt.Pointの拡張.
 *
 * apply, unapply, +, *
 */
class Point(xx: Int, yy: Int) extends java.awt.Point(xx, yy) {
  def +(p2: Point) = Point(x + p2.x, y + p2.y)

  def *(v: Int) = Point(x * v, y * v)
}

object Point {
  def apply(x: Int, y: Int) = new Point(x, y)

  def unapply(p: Point) = Some(p.x, p.y)
}
