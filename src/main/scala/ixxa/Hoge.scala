package ixxa

import scala.language.implicitConversions
import scala.annotation.tailrec
import scala.util.Random

import FileIO._

// どうでもいいメソッド群
object Hoge {

	// Zコンビネータ(?)
	def Z[X, Y](s : X)(F : X => (X => Y) => Y) : Y = F(s)((x : X) => Z(x)(F))

	// 例外きたらNone
	def optEx[T](f : => T) : Option[T] = {
		try {
			Some(f)
		} catch {
			case e : Exception => None
		}
	}

	// ランダム
	def rand(s : Int, e : Int) : Int = {
		((e - s) * Math.random + s).toInt
	}

	// キー± → 速度
	def keyspd(x : Double) = Math.pow(2, x / 12)

	///////////////////////////////////////
	implicit def toMyLong(x : Long) = MyLong(x)

	case class MyLong(x : Long) {
		def apply = x

		// x^y オーバーフロー考慮しない
		def **(y : Long) : Long = {
			@tailrec
			def rec(ret : Long, xx : Long, yy : Long) : Long = {
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
		def #**(y : Long) : Long = **(y)

	}

	//////////////////////////////////////
	// String Impl
	implicit def toMyString(s : String) : MyString = new MyString(s)
	implicit def toMyString(x : AnyVal) : MyString = new MyString(x.toString)

	class MyString(str : String) {
		def apply = str

		// クリップボードにコピー
		def clip {
			val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard
			val sel = new java.awt.datatransfer.StringSelection(str.toString)
			clipboard.setContents(sel, sel)
		}

		// +>
		def *>(fname : String) = FileIO.output(fname) { _.print(str) }

		def +>(fname : String) = FileIO.output(fname, append = true) { _.print(str) }

		def -> = FileIO.inputList(str.toString)
	}

	// Seg
	case class Seg(s : Int, e : Int) extends Seq[Int] {
		def iterator = Range(s, e).iterator
		def apply(i : Int) = Range(s, e)(i)
		def length = Range(s, e).length
		def ++(s2 : Seg) = Seg(Math.min(s, s2.s), Math.max(e, s2.e))
		def **(s2 : Seg) = Seg(Math.max(s, s2.s), Math.min(e, s2.e))
	}

	// Rect
	case class Rect(seg1 : Seg, seg2 : Seg) extends Seq[(Int, Int)] {
		val (s1, e1, s2, e2) = (seg1.s, seg1.e, seg2.s, seg2.e)
		def ++(r2 : Rect) = Rect(seg1 ++ r2.seg1, seg2 ++ r2.seg2)
		def **(r2 : Rect) = Rect(seg1 ** r2.seg1, seg2 ** r2.seg2)
		def iterator = seg1.iterator.flatMap { i1 => seg2.map { i2 => (i1, i2) } }
		// TODO
		def apply(i : Int) = iterator.toSeq(i)
		// TODO
		def length = iterator.length
	}
	object Rect {
		def apply(s1 : Int, e1 : Int, s2 : Int, e2 : Int) : Rect = Rect(Seg(s1, e1), Seg(s2, e2))
	}
}

final case class UnionFind(size : Int) {
	val par = Array.tabulate(size) { i => i }

	@tailrec
	def root(i : Int) : Int = if (par(i) == i) i else root(par(i))

	@tailrec
	def union(i1 : Int, i2 : Int) : Unit = {
		val p1 = par(i1)
		par(i1) = root(i2)
		if (p1 != i1) union(p1, i2)
	}
}


