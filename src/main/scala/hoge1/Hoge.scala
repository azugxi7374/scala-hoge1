package hoge1

import java.io.File

import scala.annotation.tailrec
import scala.language.implicitConversions
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox
import sys.process._

object Hoge1 extends Wrapped {

	// Zコンビネータ(?)
	def Z[X, Y](s: X)(F: X => (X => Y) => Y): Y = F(s)((x: X) => Z(x)(F))

	// 例外きたらNone
	def optEx[T](f: => T): Option[T] = {
		try {
			Some(f)
		} catch {
			case e: Exception => None
		}
	}

	// ランダム
	def rand(s: Int, e: Int): Int = {
		((e - s) * Math.random + s).toInt
	}

	// キー± → 速度
	def keyspd(x: Double) = Math.pow(2, x / 12)


	///////////////////////////////////////
	implicit def toExInt(x: Long): ExInt = ExInt(BigInt(x))
	implicit def toExInt(x: BigInt): ExInt = ExInt(x)

	implicit def toExString(s: String): ExString = new ExString(s)

	implicit def toExAnyRef[T <: AnyRef](x: T): ExAnyRef[T] = new ExAnyRef[T](x)
}

case class ExString(str: String) {
	// クリップボードにコピー
	def clip: String = {
		val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard
		val sel = new java.awt.datatransfer.StringSelection(str.toString)
		clipboard.setContents(sel, sel)
		str
	}

	def open: String = {
		val open = if (System.getProperty("os.name").startsWith("Windows")) {
			"cmd /k start"
		} else {
			"open"
		}
		val cmd = s"$open $str"
		println(cmd)
		cmd !!
	}
	def google: String = ExString( s"""http://google.com?#q=${str.split(" ").mkString("+")}""").open
	def weblio: String = ExString( s"""http://ejje.weblio.jp/content/${str.split(" ").mkString("+")}""").open
}

case class ExAnyRef[T <: AnyRef](obj: T) {
	def clip: String = ExString(obj.toString).clip

	def api: String = ExString(("api" +:
		obj.getClass.getPackage.getName.split('.') :+
		obj.getClass.getSimpleName
		).mkString(" ")
	).google
}


case class ExInt(x: BigInt) {
	def clip: String = ExString(x.toString).clip

	// x^y
	def **(y: BigInt): BigInt = {

		@tailrec
		def rec(ret: BigInt, xx: BigInt, yy: BigInt): BigInt = {
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

	def **(y: Long): BigInt = **(BigInt(y))

	// 優先順位用
	def #**(y: BigInt): BigInt = **(y)

	def #**(y: Long): BigInt = **(y)

}


final case class UnionFind(size: Int) {
	val par = Array.tabulate(size) { i => i }

	@tailrec
	def root(i: Int): Int = if (par(i) == i) i else root(par(i))

	@tailrec
	def union(i1: Int, i2: Int): Unit = {
		val p1 = par(i1)
		par(i1) = root(i2)
		if (p1 != i1) union(p1, i2)
	}
}


/**
 * Eval[Int]("val a = 5; a + 3") // => 8
 *
 */
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

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//