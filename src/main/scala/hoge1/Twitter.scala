package hoge1


import config.{SessionFile, Session}

import scalaz._, Scalaz._
import twitter4j._
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder
import Utils._

trait Twitter4REPL {
	import TwitterUtils._
	implicit def twitter2Ex(tw: Twitter): ExTwitter = ExTwitter(tw)
	def twitter: Twitter = {
		val tw = create
		println("current user : @%s".format(getUser(tw).getScreenName))
		tw
	}
}

case class ExTwitter(tw: Twitter) {
	import TwitterUtils._
	def switch: Twitter = switchUser(tw)
	// def user
}


object TwitterUtils {
	import TwitterSessions._
	def clear = TwitterSessions.clear

	//
	def createBase: Twitter = createBase(inputCK)
	def createBase(ck: (String, String)): Twitter = {
		val conf = new ConfigurationBuilder()
			.setOAuthConsumerKey(ck._1).setOAuthConsumerSecret(ck._2)
		new TwitterFactory(conf.build).getInstance
	}

	//
	def create: Twitter = {
		val ck = inputCK
		create(ck, inputCurrentUAT(createBase(ck)))
	}
	def create(user: UAccessToken): Twitter = create(inputCK, user)
	def create(ck: (String, String), user: UAccessToken): Twitter = {
		val tw = createBase(ck)
		tw.setOAuthAccessToken(user.accessToken)
		tw
	}

	def switchUser(tw: Twitter): Twitter = create(switchUAT(tw))

	def getUser(tw: Twitter): User = uat2User(inputCurrentUAT(tw), tw)

	/////////////////////
	private def inputCurrentUAT(tw: Twitter): UAccessToken = inputUsers(tw).currentUser

	private def switchUAT(tw: Twitter): UAccessToken = {
		val users = inputUsers(tw)
		val list = users.set.toList.sortBy(_.id)
		list.zipWithIndex.foreach { case (u, idx) =>
			printf("%2d : @%s\n", idx + 1, uat2User(u, tw).getScreenName)
		}
		printf("%2d : %s\n", 0, "add user")
		val num = read("switch to > ").toInt
		val uat = if (num == 0) {
			newUser(createBase(inputCK))
		} else if (list.indices.contains(num - 1)) {
			list(num - 1)
		} else {
			users.currentUser
		}
		SUsers.update(Users(uat.id, users.set + uat))
		uat
	}

	private def inputUsers(tw: Twitter): Users = SUsers.getOrElseUpdate({
		val u = newUser(tw)
		Users(u.id, Set(u))
	})

	private def inputCK: (String, String) = SConsumerKeys.getOrElseUpdate(
		read("Consumer Key (API Key) :"), read("Consumer Secret (API Secret) :"))

	private def uat2User(uat: UAccessToken, base: Twitter): User = base.users.showUser(uat.id)

	private def newUser(base: Twitter): UAccessToken = {
		val requestToken = base.getOAuthRequestToken
		println(requestToken.getAuthorizationURL)
		val pin = read("Enter the PIN(if aviailable) or just hit enter. [PIN]:")
		val accessToken = if (pin.length > 0) base.getOAuthAccessToken(requestToken, pin) else base.getOAuthAccessToken
		UAccessToken(accessToken)
	}
}


case class UAccessToken(token: String, tokenSecret: String) {
	lazy val accessToken = new AccessToken(token, tokenSecret)
	lazy val id = accessToken.getUserId
}
object UAccessToken {
	def apply(accessToken: AccessToken): UAccessToken = UAccessToken(accessToken.getToken, accessToken.getTokenSecret)
}
case class Users(currentId: Long, set: Set[UAccessToken]) {
	lazy val currentUser = set.find(_.id == currentId).get
}

protected object TwitterSessions extends TwitterSessions(sessionFile)

protected case class TwitterSessions(sessionFile: SessionFile) {
	def clear = List(SConsumerKeys, SUsers).foreach(_.clear)

	object SConsumerKeys extends Session[(String, String)]("TWITTER_CONSUMER_KEYS", sessionFile)

	object SUsers extends Session[Users]("TWITTER_USERS", sessionFile)
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


