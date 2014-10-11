package ixlib

import java.io.File
import scala.language.implicitConversions
import scala.annotation.tailrec
import sys.process._

// どうでもいいメソッド群
object Ixlib {

  // Zコンビネータ(?)
  def Z[X, Y](s: X)(F: X => (X => Y) => Y): Y = F(s)((x: X) => Z(x)(F))

  /** 例外ならNone, それ以外はSome */
  def optEx[T](f: => T): Option[T] = {
    try {
      Some(f)
    } catch {
      case e: Exception => None
    }
  }

  /** ランダム */
  def rand(s: Int, e: Int): Int = {
    ((e - s) * Math.random + s).toInt
  }

  /** キー± → 速度 */
  def keySpeed(x: Double) = Math.pow(2, x / 12.0)

  /** System.currentTimeMillis のエイリアス */
  def currentTime = System.currentTimeMillis

  /** currentTime == t までThread.sleepする */
  @tailrec
  final def sleepUntil(t: Long, stopJudge: => Boolean = false): Unit = {
    if (t <= currentTime) {
      // end
    } else if (!stopJudge) {
      Thread.sleep(Math.max(0, Math.min(1000, t - currentTime)))
      sleepUntil(t, stopJudge)
    }
  }

  /** 実行時間出力Wrapper
		@example
		{{{
      * timeWrapper{
      * hoge...
      * }
      * }}}
    */
  lazy val timeWrapper = Wrapper.headerToFooter(currentTime, printWrapper(currentTime - (_: Long)))

  /** 出力Wrapper */
  lazy val printWrapper = Wrapper.withFooter(println(_: Any))

  /** '''GetKeyState.exeがパスに通ってること！！''' */
  def getKeyState(keys: String*): Boolean = {
    val com = "GetKeyState.exe %s".format(keys.mkString(" "))
    (com !!).count(_ == 'D') == keys.size
  }

  /** 10**2 = 100, 10#**2 = 100 */
  implicit class MyLong(val x: Long) extends AnyVal {
    def apply = x

    /** x**y オーバーフロー考慮しない */
    def **(y: Long): Long = {
      @tailrec
      def rec(ret: Long, xx: Long, yy: Long): Long = {
        if (yy == 0) ret
        else {
          if ((yy & 1) == 1) {
            rec(ret * xx, xx * xx, yy >> 1)
          } else {
            rec(ret, xx * xx, yy >> 1)
          }
        }
      }
      rec(1, x, y)
    }

    // 優先順位用
    def #**(y: Long): Long = **(y)
  }

  /** MyString */
  implicit class MyString(str: String) {
    def apply = str

    /** クリップボードにコピー */
    def clip {
      val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard
      val sel = new java.awt.datatransfer.StringSelection(str.toString)
      clipboard.setContents(sel, sel)
    }

    /** ファイル出力 */
    def *>(fname: String) = FileIO.output(fname) {
      _.print(str)
    }

    /** ファイル出力(追加) */
    def +>(fname: String) = FileIO.output(new File(fname), append = true) {
      _.print(str)
    }

    /** ファイル入力 => List[String] */
    def -> : List[String] = FileIO.inputList(str.toString)
  }

  //  // Seg
  //  case class Seg(s: Int, e: Int) extends Seq[Int] {
  //    def iterator = Range(s, e).iterator
  //    def apply(i: Int) = Range(s, e)(i)
  //    def length = Range(s, e).length
  //    def ++(s2: Seg) = Seg(Math.min(s, s2.s), Math.max(e, s2.e))
  //    def **(s2: Seg) = Seg(Math.max(s, s2.s), Math.min(e, s2.e))
  //  }
  //
  //  // Rect
  //  case class Rect(seg1: Seg, seg2: Seg) extends Seq[(Int, Int)] {
  //    val (s1, e1, s2, e2) = (seg1.s, seg1.e, seg2.s, seg2.e)
  //    def ++(r2: Rect) = Rect(seg1 ++ r2.seg1, seg2 ++ r2.seg2)
  //    def **(r2: Rect) = Rect(seg1 ** r2.seg1, seg2 ** r2.seg2)
  //    def iterator = seg1.iterator.flatMap { i1 => seg2.map { i2 => (i1, i2)}}
  //
  //    // TODO
  //    def apply(i: Int) = iterator.toSeq(i)
  //
  //    // TODO
  //    def length = iterator.length
  //  }
  //
  //  object Rect {
  //    def apply(s1: Int, e1: Int, s2: Int, e2: Int): Rect = Rect(Seg(s1, e1), Seg(s2, e2))
  //  }
}


