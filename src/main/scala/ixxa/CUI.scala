package ixxa

import scala.util.matching._

/////////////////////////////////
// CUI
case class CUI(comlist : List[Command]) {

	def run(str : String) {
		println("run")
		comlist.collectFirst(com => str match {
			case com.reg(strlist @ _*) => com.run(strlist)
		})
	}
}

///////////////////////////////
// Command
case class Command(reg : Regex, run : Seq[String] => Any, help : String)








