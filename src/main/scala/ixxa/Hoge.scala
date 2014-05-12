package ixxa

import scala.language.implicitConversions
import scala.annotation.tailrec
import scala.util.Random

// どうでもいいメソッド群
object Hoge {

	// 例外きたらfalse
	def successEx(f : => Any) : Boolean = {
		try {
			f
			true
		} catch {
			case e : Exception => false
		}
	}

	// ランダム
	def rand(s : Int, e : Int) : Int = {
		((e - s) * Math.random + s).toInt
	}

	// キー± → 速度
	def keyspd(x : Double) = Math.pow(2, x / 12)

	implicit def toMyString[T](x : T) : MyString[T] = new MyString(x)

	class MyString[T](t : T) {
		def apply = t

		// クリップボードにコピー
		def clip {
			val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard
			val sel = new java.awt.datatransfer.StringSelection(t.toString)
			clipboard.setContents(sel, sel)
		}
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


