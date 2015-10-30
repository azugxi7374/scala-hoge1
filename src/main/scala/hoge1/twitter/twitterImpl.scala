package hoge1.twitter

import java.text.SimpleDateFormat

import twitter4j.{ResponseList, Status, User, Twitter}
import java.util._
import collection.JavaConverters._

// TODO htmlダンプしてopenするやつ
trait TwitterImpl {
	implicit def twitter2Ex(tw: Twitter): ExTwitter = ExTwitter(tw)
	implicit def twitterResponseList2Ex[T <% TH](rs: ResponseList[T]): ExResponse[T] = ExResponse[T](rs)
	implicit def twitterStatus2Ex(status: Status): ExStatus = ExStatus(status)
	implicit def twitterUser2Ex(user: User): ExUser = ExUser(user)
}

case class ExTwitter(tw: Twitter) {
	import TwitterUtils._
	def switch: Twitter = switchUser(tw)
	def user: User = getUser(tw)

	def tl = tw.getHomeTimeline
}
case class ExResponse[T <% TH](rs: ResponseList[T]) {
	def print = println(rs.asScala.map(_.text).mkString("\n-----\n"))
}

case class ExStatus(st: Status) extends TH {

	val user = st.getUser
	def text =
		"""%s <%s@%s %s %s>
			| """.stripMargin.format(
			st.getText, user.getName, user.getScreenName, hoge1.dateFormat(st.getCreatedAt),
			createStatusURL(user.getScreenName, st.getId))

}

case class ExUser(user: User)


trait TH {
	def text: String
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