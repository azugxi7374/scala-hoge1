package ixxa

import java.io._
import java.awt.image._
import javax.imageio._

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

	def output[T](fname : String, cset : String = DefaultCset, append : Boolean = false)(f : PrintWriter => T) : T = {
		val pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(
			new File(fname), append)))

		try {
			f(pw)
		} finally {
			pw.close
		}
	}

	// image output
	def imout(bi : BufferedImage, fname : String, format : String = "PNG") {
		import java.awt.image.BufferedImage;
		import javax.imageio.{ ImageIO => JImageIO }

		if (!ImageIO.write(bi, format, new File(fname))) {
			throw new IOException("フォーマットが対象外")
		}
	}
}
