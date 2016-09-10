package org.zerngsn.hoge1

import java.net.{URLDecoder, URLEncoder}
import java.util.Date

import scala.reflect.ClassTag
import scala.util.Random

trait Utils extends Util_Randoms with Util_Strings {
	// Zコンビネータ(?)
	def Z[X, Y](s: X)(F: X => (X => Y) => Y): Y = F(s)((x: X) => Z(x)(F))

	def exopt[T](f: => T): Option[T] = exfold(Some(f), (_: Exception) => None)
	def exfold[T](f: => T, orElse: => T): T = exfold(f, (_: Exception) => orElse)
	def exfold[T, E <: Exception : ClassTag](f: => T, orElse: E => T): T = try {
		f
	} catch {
		case e: E => orElse(e)
		case e: Throwable => throw e
	}

	def uid = new java.rmi.server.UID().toString

	def read(str: String = "> ") = {
		val ret = scala.io.StdIn.readLine(str)
		println
		println( """ => [%s]""".format(ret))
		ret
	}

	def printed[T](f: => T) = {
		val ret = f
		println(ret)
		ret
	}

	def timed[T](f: => T) = {
		val cur = System.currentTimeMillis
		val ret = f
		println("Time : " + (System.currentTimeMillis - cur) + " ms")
		ret
	}
}
