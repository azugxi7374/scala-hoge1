package ixxa

import scala.language.implicitConversions

// どうでもいいメソッド群
object Hoge {

	// キー± → 速度
	def keyspd (x : Double) = Math.pow(2, x / 12)

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
}

