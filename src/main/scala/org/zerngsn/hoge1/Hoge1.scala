package org.zerngsn.hoge1

trait Hoge1 extends Utils with Implicits {
	type MMap[A, B] = scala.collection.mutable.Map[A, B]
	lazy val MMap = scala.collection.mutable.Map
	lazy val MList = scala.collection.mutable.ListBuffer
	lazy val MSet = scala.collection.mutable.Set


	// datastructure
	lazy val UnionFind = datastructure.UnionFind
	lazy val Bit = datastructure.Bit
	lazy val Bit2 = datastructure.Bit2

}