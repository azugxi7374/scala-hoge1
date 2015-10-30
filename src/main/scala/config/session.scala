package config

import scala.reflect.Manifest
import scalaz._, Scalaz._
import scalax.file.Path
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization

import hoge1.Utils._

case class SessionFile(file: Path) {
	file.createFile(failIfExists = false)

	implicit val sFormats = Serialization.formats(NoTypeHints)
	implicit val eFormats = DefaultFormats

	private def fRead: String = optEx(file.string).getOrElse("")
	private def fWrite(str: String) = file.write(str)

	private def getMap: Map[String, String] = {
		parse(fRead).extractOpt[Map[String, String]].getOrElse(Map())
	}

	def clearAll: Unit = updateFromMap(Map[String,String]())
	protected[config] def clear(key: String): Unit = updateFromMap(getMap - key)

	protected[config] def apply[T](key: String)(implicit mf: Manifest[T]) = get[T](key).get

	protected[config] def get[T](key: String)(implicit mf: Manifest[T]) =
		getMap.get(key).map(Serialization.read[SessionWrapper[T]](_).v)

	protected[config] def getOrElseUpdate[T](key: String, op: => T)(implicit mf: Manifest[T]) = get[T](key).getOrElse {
		val value = op
		update(key, value)
		value
	}

	protected[config] def update[T](key: String, value: T) = {
		val valueJson = Serialization.write(SessionWrapper(value))
		updateFromMap(getMap.updated(key, valueJson))
	}
	private def updateFromMap(map: Map[String, String]) = {
		fWrite(Serialization.write(map))
	}
}
case class SessionWrapper[T](v: T)

abstract class Session[T](val key: String, val sessionFile: SessionFile) {
	def clear: Unit = sessionFile.clear(key)
	def apply(implicit mf: Manifest[T]): T = sessionFile.apply[T](key)
	def get(implicit mf: Manifest[T]): Option[T] = sessionFile.get[T](key)
	def update(value: T) = sessionFile.update(key, value)
	def getOrElseUpdate(op: => T)(implicit mf: Manifest[T]) = sessionFile.getOrElseUpdate(key, op)
}

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
