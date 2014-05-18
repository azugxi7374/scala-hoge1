import AssemblyKeys._

val prog_name = "ixxascalalib"

name := prog_name

version := "0.01"

val scalazVersion = "7.0.6"

val dispatchVersion = "0.11.0"

//jarName in assembly := prog_name + ".jar"

val db = System.getenv("DROPBOX").filterNot(_=='"')

outputPath in assembly := file(db+"/Public/library/"+prog_name+".jar")

unmanagedJars in Compile := Seq()

libraryDependencies := Seq(
  //"ixxascalalib" % "ixxascalalib" % "0.017" from "https://dl.dropboxusercontent.com/u/30758682/liblary/ixxascalalib_2.10-0.01.jar",
  //"org.specs2" %% "specs2" % "2.3.11" % "test",
  //"org.scalaz" %% "scalaz-core" % scalazVersion,
  //"org.scalaz" %% "scalaz-effect" % scalazVersion,
  //"org.scalaz" %% "scalaz-typelevel" % scalazVersion,
  //"org.scalaz" %% "scalaz-scalacheck-binding" % scalazVersion % "test",
  "net.databinder.dispatch" %% "dispatch-core" % dispatchVersion
  //"net.databinder.dispatch" %% "dispatch-jsoup" % dispatchVersion,
  //"net.databinder.dispatch" %% "dispatch-json4s-native" % dispatchVersion
  //"org.slf4j" % "slf4j-log4j12" % "1.7.6",
  //"org.twitter4j" % "twitter4j-core" % "[4.0,)"
  //"org.jsoup" % "jsoup" % "1.7.3"
)
