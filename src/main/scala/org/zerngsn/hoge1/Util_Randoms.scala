package org.zerngsn.hoge1

import scala.util.Random

trait Util_Randoms {
	//////////////////////////////////////
	// random
	// [0,1]をn個にわける
	// Seq(0, 0.2, 0.4, 0.8)
	def randPartitionDouble(sp: Int)(implicit rand: Random): Vector[Double] = {
		require(sp >= 1)
		val width = Vector.fill(sp)(rand.nextDouble())
		val sum = width.sum
		width.map(_ / sum).scanLeft(0d)(_ + _).dropRight(1)
	}
	def randPartition(n: Int, sp: Int)(implicit rand: Random): Vector[(Int, Int)] = {
		val s = 0 +: rand.shuffle(Vector.range(1, n)).take(sp - 1).sorted
		s zip (s.tail :+ n)
	}
	def randPick[T](seq: Seq[T])(implicit rand: Random): T = {
		val i = rand.nextInt(seq.size)
		seq(i)
	}
	def randPick[T](seq: Seq[T], k: Int)(implicit rand: Random): Vector[T] = {
		val is = Stream.continually(rand.nextInt(seq.size)).distinct.take(k)
		val vec = seq.toVector
		is.map { i =>
			vec(i)
		}.toVector
	}
}