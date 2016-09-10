package org.zerngsn.hoge1

import org.zerngsn.hoge1.ex._

trait Implicits {
	implicit def toExInt(x: Int): ExInt = ExInt(x)
	implicit def toExInts(x: Long): ExIntFamily = ExIntFamily(BigInt(x))
	implicit def toExInts(x: BigInt): ExIntFamily = ExIntFamily(x)

	implicit def toExString(s: String): ExString = new ExString(s)
	implicit def toExAnyRef[T <: AnyRef](x: T): ExAnyRef[T] = new ExAnyRef[T](x)

	implicit def toExIterable[T](it: Iterable[T]): ExIterable[T] = new ExIterable(it)
}

