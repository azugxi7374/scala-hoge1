package hoge1.twitter

import twitter4j._
import twitter4j.auth.AccessToken
import hoge1.twitter.TwitterUtils._


case class TwitterGetter(tc: TwitterConf) {
	val kg = TwitterKeysGetter(tc)

	def createTwitterDefault: Twitter = create(kg.getATDefault)

	def createTwitterSelect: Twitter = create(kg.getATSelect)

	def createTwitterClean: Twitter = create(kg.getATClean)

	private def create(atGetter: ConsumerKeys => AccessToken): Twitter = {
		val ck = kg.getCK
		val at = atGetter(ck)
		println("Create Twitter @%s".format(at2User(ck, at).getScreenName))
		createTwitter(ck, at)
	}
}

case class TwitterKeysGetter(tc: TwitterConf) {
	val sg = TwitterStateModel(tc)

	def getCK = sg.getCK

	def getATDefault(ck: ConsumerKeys): AccessToken = {
		val atList = sg.getATList
		sg.getCID match {
			case Some(cid) if atList.exists(_.getUserId == cid) =>
				atList.find(_.getUserId == cid).get
			case _ => getATSelect(ck)
		}
	}

	def getATSelect(ck: ConsumerKeys): AccessToken = {
		sg.getATList match {
			case Nil => getATNewCurrent(ck)
			case atList => sg.selectAndSetCurrentNum(
				atList.map(at2User(ck, _)).map { u => (u.getId, "@" + u.getScreenName) }
			) match {
				case Some(Some(idx)) => atList(idx)
				case Some(None) => getATNewCurrent(ck)
				case None => getATDefault(ck)
			}
		}
	}

	def getATNewCurrent(ck: ConsumerKeys): AccessToken = sg.getNewAndSetCurrentAT(authByPIN(ck, _))

	def getATClean(ck: ConsumerKeys): AccessToken = {
		sg.clearATs
		getATNewCurrent(ck)
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