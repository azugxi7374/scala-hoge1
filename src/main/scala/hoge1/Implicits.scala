package hoge1

import scala.annotation.tailrec
import scala.sys.process._

trait Impls {
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