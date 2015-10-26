package hoge1

trait Wrapped {
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
