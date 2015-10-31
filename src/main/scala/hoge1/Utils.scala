package hoge1

import java.io.File

/**
 * Created on 2015/10/30.
 */
trait Utils {
	// Zコンビネータ(?)
	def Z[X, Y](s: X)(F: X => (X => Y) => Y): Y = F(s)((x: X) => Z(x)(F))

	// 例外きたらNone
	def optEx[T](f: => T, printFlg: Boolean = false): Option[T] = {
		try {
			Some(f)
		} catch {
			case e: Exception =>
				if (printFlg) e.printStackTrace
				None
		}
	}

	// ランダム
	def rand(s: Int, e: Int): Int = ((e - s) * Math.random + s).toInt
	def rand(e: Int): Int = rand(0, e)


	// キー± → 速度
	def keyspd(x: Double) = Math.pow(2, x / 12)

	def read(str: String = "> ") = {
		val ret = scala.io.StdIn.readLine(str)
		println
		println(""" => [%s]""".format(ret))
		ret
	}


	////////////////////////////////////////////////
	// Env
	def getHome = {
		System.getenv("HOME")
	}
}

object Utils extends Utils
