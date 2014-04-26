package ixxa

import java.io._

object FileIO {

	val DefaultCset = "UTF-8"

	def br(fname : String, cset : String = DefaultCset) = new BufferedReader(new InputStreamReader(new FileInputStream(fname), cset))

	def input[T](fname : String, cset : String = DefaultCset)(f : BufferedReader => T) : T = {
		val br = this.br(fname, cset)
		try {
			f(br)
		} finally {
			br.close
		}
	}

	def brToItrt(br : BufferedReader) : Iterator[String] =
		Iterator.continually(br.readLine).takeWhile(_ != null)

	def brToList(br : BufferedReader) : List[String] = brToItrt(br).toList

	def output[T](fname : String, cset : String = DefaultCset)(f : PrintWriter => T) : T = {
		val pw = new PrintWriter(fname, cset)
		try {
			f(pw)
		} finally {
			pw.close
		}
	}
}

