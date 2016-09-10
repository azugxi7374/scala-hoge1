package org.zerngsn.hoge1.ex

import org.zerngsn.hoge1._

class ExIterable[T](it: Iterable[T]) {
	def toMMap[A, B](implicit ev: T <:< (A, B)): MMap[A, B] = MMap(it.asInstanceOf[Iterable[(A, B)]].toSeq: _*)
	def unzip4[A, B, C, D](implicit as4: T => (A, B, C, D)): (Vector[A], Vector[B], Vector[C], Vector[D]) = {
		val (a, b, c, d) = (MList[A](), MList[B](), MList[C](), MList[D]())
		for (tt <- it) {
			val (aa, bb, cc, dd) = as4(tt)
			a += aa; b += bb; c += cc; d += dd
		}
		(a.toVector, b.toVector, c.toVector, d.toVector)
	}
}
