package org.zerngsn.opt.twitter

import twitter4j.{ResponseList, Status, User, Twitter}
import collection.JavaConverters._
import TwitterUtils._

// TODO htmlダンプしてopenするやつ
trait TwitterI {
	val conf: TwitterConf
	lazy val ct = TwitterGetter(conf)

	implicit class ExTwitter(tw: Twitter) {
		implicit def to = tw
		def switch: Twitter = ct.createTwitterSelect
		def user: String = "@" + owner(tw).getScreenName

		def tl = tw.getHomeTimeline
	}

	implicit class ExResponse[T <% TH](rs: ResponseList[T]) {
		implicit def to = rs
		def print = println(rs.asScala.map(_.text).mkString("\n-----\n"))
	}

	implicit class ExStatus(st: Status) extends TH {
		implicit def to = st
		val user = st.getUser
		def text =
			"""%s <%s@%s %s %s>
				| """.stripMargin.format(
				st.getText, user.getName, user.getScreenName, dateFormat(st.getCreatedAt),
				createStatusURL(user.getScreenName, st.getId))

	}

	implicit class ExUser(user: User) {
		implicit def to = user
	}

	trait TH {
		def text: String
	}
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