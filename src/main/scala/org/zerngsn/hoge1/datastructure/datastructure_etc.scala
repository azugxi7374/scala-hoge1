package org.zerngsn.hoge1.datastructure

import scala.annotation.tailrec

final case class UnionFind(size: Int) {
	lazy val par = Array.tabulate(size) { i => i }

	@tailrec
	def root(i: Int): Int = if (par(i) == i) i else root(par(i))

	@tailrec
	def union(i1: Int, i2: Int): Unit = {
		val p1 = par(i1)
		par(i1) = root(i2)
		if (p1 != i1) union(p1, i2)
	}
}