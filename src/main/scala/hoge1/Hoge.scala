import java.text.SimpleDateFormat
import java.util.Date
package object hoge1 {

	import scala.language.implicitConversions
	import scalax.file.Path
	import scalax.io.Codec
	import scalax.io.JavaConverters._
	import scalax.io.Resource

	lazy val sessionFile = new config.SessionFile(
		Path.fromString(Utils.getHome) / ".scala_hoge1" createFile (failIfExists = false)
	)

	val df = new SimpleDateFormat("yy/MM/dd HH:mm:ss")
	def dateFormat(date: Date) = df.format(date)


	object Hoge1 extends Utils with Wrapped with Impls with twitter.Twitter4REPL

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
//
//
//
//
//
//
//
//
