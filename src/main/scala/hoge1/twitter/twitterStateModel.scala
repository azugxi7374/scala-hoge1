package hoge1.twitter

import config.{Session, SessionFile}
import twitter4j.auth.AccessToken
import scalaz._, Scalaz._

case class TwitterStateModel(tc: TwitterConf) {
	val session = TwitterStateFromSessions(tc)
	val console = TwitterStateFromConsole

	def getCK: ConsumerKeys = session.getCK.getOrElse {
		val ck = console.getCK.get
		session.setCK(ck)
		ck
	}

	def getATList: List[AccessToken] = session.getATs.toList.sortBy(_.getUserId)

	def getNewAndSetCurrentAT(atGetter: (String => String) => AccessToken): AccessToken = {
		val at = atGetter(console.getPIN)
		session.addAT(at)
		session.setCID(at.getUserId)
		at
	}

	/** @return none: failed, some(some(idx)): selected, some(none): add */
	def selectAndSetCurrentNum(idNameList: List[(Long, String)]): Option[Option[Int]] = {
		val ret = console.selectUserName(idNameList.unzip._2)
		ret match {
			case Some(Some(idx)) => session.setCID(idNameList(idx)._1)
			case _ =>
		}
		ret
	}

	def getCID: Option[Long] = session.getCID

	def clearATs: Unit = session.clearATS
}


protected object TwitterStateFromConsole {
	import hoge1.Utils._
	def getCK: Option[ConsumerKeys] = {
		val k = read("Consumer Key :")
		val s = read("Consumer Secret :")
		(k.nonEmpty && s.nonEmpty) option ConsumerKeys(k, s)
	}

	def getPIN(url: String): String = {
		println("Open the URL : " + url)
		read("Enter the PIN :")
	}

	/** @return none: failed, some(some(idx)): selected, some(none): add */
	def selectUserName(nameList: List[String]): Option[Option[Int]] = {
		nameList.zipWithIndex.foreach { case (name, idx) =>
			printf("%2d : %s\n", idx + 1, name)
		}
		printf("%2d : %s\n", 0, "Add new user")
		val str = read("select > ")

		optEx(str.toInt) match {
			case Some(0) => Some(None)
			case Some(i) if nameList.indices contains (i - 1) => Some(Some(i - 1))
			case _ => None
		}
	}
}


protected case class TwitterStateFromSessions(tc: TwitterConf) {
	val _ts = TwitterSessionModel(tc.sessionFile)
	import _ts._

	def getCK: Option[ConsumerKeys] = SConsumerKeys.get.map { ck => ConsumerKeys(ck._1, ck._2) }
	def getATs: Set[AccessToken] = SUsers.get.getOrElse(Set()).map { ak => new AccessToken(ak._1, ak._2) }

	def getCID: Option[Long] = SCurrentUserId.get

	def setCK(ck: ConsumerKeys) = SConsumerKeys.update((ck.key, ck.secret))

	def setCID(cid: Long) = SCurrentUserId.update(cid)
	def setATs(ats: Set[AccessToken]) = SUsers.update(
		ats.map(at => (at.getToken, at.getTokenSecret))
	)
	def addAT(at: AccessToken) = setATs(getATs + at)
	def clearATS = setATs(Set.empty)
}
protected case class TwitterSessionModel(sessionFile: SessionFile) {
	object SConsumerKeys extends Session[(String, String)]("TWITTER_CONSUMER_KEYS", sessionFile)
	object SUsers extends Session[Set[(String, String)]]("TWITTER_USERS", sessionFile)
	object SCurrentUserId extends Session[Long]("TWITTER_CURRENT_USERS", sessionFile)
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