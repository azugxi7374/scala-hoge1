package lib.beta

/**
 * Created on 2014/09/23.
 */

import akka.actor._
import scala.sys.process._


/**
 * StopReaderがきたら中断するプロセス
 */
object ProcessWatchActor {

	val pb = "ls" ### "sleep 3" ### "ls"

	def main0(args: Array[String]) {
		val system = ActorSystem()
		val master: ActorRef = system.actorOf(Props[Master])

		master ! START

		println("main ok")
	}

	class Master extends Actor {

		var pi: Process = _

		val pw: ActorRef = context.actorOf(Props[ProcessWatcher])
		val rd: ActorRef = context.actorOf(Props[StopReader])

		override def receive: Actor.Receive = {
			case START =>
				println("master : start")
				pi = pb.run
				pw ! pi
				self ! DO

			case DO =>
				rd ! DO

			case CANCEL =>
				println("master : cancel")
				pi.destroy
				stop

			case FIN =>
				println("master : fin")
				stop


		}

		def stop = {
			context.stop(pw)
			context.stop(rd)
			context.system.shutdown
		}
	}

	class ProcessWatcher extends Actor {
		override def receive: Receive = {
			case p: Process =>
				println("pw: received process")
				p.exitValue
				sender ! FIN
				println("pw: fin")
		}
	}

	class StopReader extends Actor {

		// TODO
		def stopjudge = {
			false
		}

		override def receive: Receive = {
			case DO =>
				if (stopjudge) {
					sender ! CANCEL
				} else {
					sender ! DO
				}
		}
	}

	object START
	object DO
	object FIN
	object CANCEL
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
//
//
//