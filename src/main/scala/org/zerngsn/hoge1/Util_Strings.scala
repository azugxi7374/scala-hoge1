package org.zerngsn.hoge1

import java.net.{URLDecoder, URLEncoder}
import java.util.Date

trait Util_Strings {
	def decode(str: String) = URLDecoder.decode(str, "UTF-8")
	def encode(str: String) = URLEncoder.encode(str, "UTF-8")
	def date2String(date: Long): String = date2String(new Date(date))
	def date2String(date: Date, formatString: String = "yyyy/MM/dd HH:mm:ss"): String =
		new java.text.SimpleDateFormat(formatString).format(date)
}
