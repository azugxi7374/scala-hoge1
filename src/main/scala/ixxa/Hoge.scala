package ixxa

// どうでもいいメソッド群
object Hoge{

	// キー± → 速度
	def keySpd(x:Double) = Math.pow(2,x/12)

	// string to clipboard
	def strClip(str:String){
		val clipboard = java.awt.Toolkit.getDefaultToolkit.getSystemClipboard
		val sel = new java.awt.datatransfer.StringSelection(str)
		clipboard.setContents(sel, sel)
	}
}

