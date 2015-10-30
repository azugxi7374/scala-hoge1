package object hoge1 {

	import scala.language.implicitConversions
	import scalax.file.Path
	import scalax.io.Codec
	import scalax.io.JavaConverters._
	import scalax.io.Resource

	lazy val sessionFile = new config.SessionFile(
		Path.fromString(Utils.getHome) / ".scala_hoge1" createFile (failIfExists = false)
	)

	object Hoge1 extends Utils with Wrapped with Impls with Twitter4REPL

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
