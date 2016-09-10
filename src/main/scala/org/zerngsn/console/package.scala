package org.zerngsn

import org.zerngsn.hoge1.Hoge1

package object console extends Hoge1 {
	// キー± → 速度
	def keyspd(x: Double) = Math.pow(2, x / 12)

}
