val prog_name = "hoge1"

name := prog_name

version := "2.00"

scalaVersion := "2.11.7"

//////////////////////////////////////
// libraries
unmanagedJars in Compile := Seq()

val scalazVersion = "7.0.6"

val dispatchVersion = "[0.11,)"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % scalaVersion.value,
  // "com.typesafe.akka" %% "akka-actor" % "2.3.6",
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3",
  "org.scalaz" %% "scalaz-core" % scalazVersion,
  "org.scalaz" %% "scalaz-effect" % scalazVersion,
  "org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test",
  "org.scalaz" %% "scalaz-typelevel" % scalazVersion,
  // "net.databinder.dispatch" %% "dispatch-core" % dispatchVersion,
  // "net.databinder.dispatch" %% "dispatch-json4s-native" % dispatchVersion,
  // "net.databinder.dispatch" %% "dispatch-jsoup" % dispatchVersion,
  // "org.apache.pdfbox" % "pdfbox" % "[1.8,)",
  "org.json4s" %% "json4s-native" % "3.2.11",
  // "org.jsoup" % "jsoup" % "[1.8,)",
  // "org.slf4j" % "slf4j-log4j12" % "1.7.6",
  "org.twitter4j" % "twitter4j-core" % "[4.0,)",
  // "org.specs2" %% "specs2" % "2.3.11" % "test",
  null
  ).dropRight(1)


//////////////////////////////////////
// options
scalacOptions ++= Seq(
	"-feature",
	"-language:postfixOps",
	"-language:implicitConversions"
	)

javacOptions ++= Seq(
  "-encoding", "UTF-8"
)

//////////////////////////////////////
// console commands
initialCommands in console := """
  import java.io._
  import sys.process._
  import scalax.io._, scalax.file._
  import scala.collection.mutable.{ListBuffer}
  import collection.JavaConverters._
  import scalaz._, Scalaz._
  // import dispatch._, Defaults._
  import org.json4s._, native.JsonMethods._, JsonDSL._
  import twitter4j._
  // import org.jsoup.Jsoup

  import hoge1._, Hoge1._
""".split("\n").filter(! _.contains("//")).mkString("\n")

/////////////////////////////////////////

assemblyOption in assembly ~= {_.copy(includeScala = false)}

assemblyOutputPath in assembly := file(s"""${System.getenv("DROPBOX")}/_lib/${name.value}.jar""")

