package hoge1
import java.io.File

import scala.annotation.tailrec
import scala.language.implicitConversions
import scala.reflect.runtime.currentMirror
import scala.tools.reflect.ToolBox

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