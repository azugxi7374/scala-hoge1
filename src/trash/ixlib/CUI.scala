package ixlib

import scala.util.matching._

/////////////////////////////////
// CUI
//case class CUI(comlist : List[Command]) {
//
//	def run {
//		while (true) {
//			print(">"); runCommand(readLine)
//		}
//	}
//
//	def runCommand(str : String) {
//		comlist.collectFirst(com => str match {
//			case com.reg(strlist @ _*) => com.run(strlist)
//		}).getOrElse {
//			runHelp
//		}
//	}
//
//	def runHelp = {
//		println("-- help --")
//		println(comlist.map(com => com.reg + " : " + com.help).mkString("\n"))
//	}
//}
//
/////////////////////////////////
//// Command
//case class Command(reg : Regex, run : Seq[String] => Any, help : String = "")








