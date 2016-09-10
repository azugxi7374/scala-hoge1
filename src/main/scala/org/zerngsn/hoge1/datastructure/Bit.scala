package org.zerngsn.hoge1.datastructure

import org.zerngsn.hoge1._

case class Bit(len: Int) {
	// val a = Array.fill(Integer.highestOneBit(len - 1 << 1))(0L)
	lazy val a = MMap[Int, Long]()

	// add to [i]
	def add(i: Int, v: Long): Unit = if (i >= len * 4) {
		//
	} else {
		a(i) = a.getOrElse(i, 0L) + v
		var ii = i + 1
		ii += ii & -ii
		add(ii - 1, v)
	}

	// sum of [0, n)
	def sum(n: Int): Long = if (n == 0) {
		0L
	} else {
		a.getOrElse(n - 1, 0L) + sum(n - (n & -n))
	}
}

case class Bit2(len1: Int, len2: Int) {
	lazy val a = MMap[Int, Bit]()
	// add to [i1, i2]
	def add(i1: Int, i2: Int, v: Long): Unit = if (i1 >= len1 * 4) {
		//
	} else {
		if (!a.contains(i1)) {
			a(i1) = new Bit(len2)
		}
		a(i1).add(i2, v)
		var ii = i1 + 1
		ii += ii & -ii
		add(ii - 1, i2, v)
	}

	// sum of [0, n)
	def sum(n1: Int, n2: Int): Long = if (n1 == 0) {
		0L
	} else {
		a.get(n1 - 1).map {_.sum(n2)}.getOrElse(0L) + sum(n1 - (n1 & -n1), n2)
	}
}
