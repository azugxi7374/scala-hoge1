import AssemblyKeys._

val prog_name = "sixlib"

name := prog_name

organization := "ixxa"

version := "1.00"

scalaVersion := "2.11.2"

//val scalazVersion = "7.0.6"

//val dispatchVersion = "0.11.0"

//jarName in assembly := prog_name + ".jar"

//val db = System.getenv("DROPBOX").filterNot(_=='"')

//outputPath in assembly := file(db+"/Public/library/"+prog_name+".jar")

unmanagedJars in Compile := Seq()

libraryDependencies ++= Seq(
	"org.apache.pdfbox" % "pdfbox" % "[1.8,)",
	"org.scala-lang" % "scala-compiler" % scalaVersion.value,	
  "com.typesafe.akka" %% "akka-actor" % "2.3.6",
	"org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
)
