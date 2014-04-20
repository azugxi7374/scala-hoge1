package ixxa

import java.io._

object FileIO {

	def input[T](fname : String)(f : BufferedReader => T) : T = {
		val br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)))
		try {
			f(br)
		} finally {
			br.close
		}
	}

	def brToItrt(br : BufferedReader) : Iterator[String] =
		Iterator.continually(br.readLine).takeWhile(_ != null)

	def brToList(br : BufferedReader) : List[String] = brToItrt(br).toList

	def output[T](fname : String)(f : PrintWriter => T) : T = {
		val pw = new PrintWriter(fname)
		try {
			f(pw)
		} finally {
			pw.close
		}
	}
}

