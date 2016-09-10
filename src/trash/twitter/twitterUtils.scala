package org.zerngsn.opt.twitter

import java.text.SimpleDateFormat
import java.util.Date

import org.zerngsn.hoge1.Cached
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{Status, Twitter, TwitterFactory, User}
import twitter4j.auth.AccessToken

object TwitterUtils {
	protected case class AT(atk: String, ats: String) {
		def at = new AccessToken(atk, ats)
	}
	protected object AT {
		def apply(at: AccessToken) = new AT(at.getToken, at.getTokenSecret)
	}

	val df = new SimpleDateFormat("yy/MM/dd HH:mm:ss")
	def dateFormat(date: Date) = df.format(date)

	def status2URL(status: Status): String = status2URL(status.getUser, status)
	def status2URL(user: User, status: Status): String = createStatusURL(user.getScreenName, status.getId)

	def user2UserPageURL(user: User): String = createUserPageURL(user.getScreenName)


	object at2User extends Cached[(ConsumerKeys, AT), User] {
		override protected def get(k: (ConsumerKeys, AT)): User = {
			val (ck, at) = k
			createTwitter(ck, at.at).users.verifyCredentials()
		}
		def apply(ck: ConsumerKeys, at: AccessToken): User = apply((ck, AT(at)))
	}

	def owner(tw: Twitter): User = {
		val conf = tw.getConfiguration
		at2User(ConsumerKeys(conf.getOAuthConsumerKey, conf.getOAuthConsumerSecret),
			new AccessToken(conf.getOAuthAccessToken, conf.getOAuthAccessTokenSecret))
	}

	def authByPIN(ck: ConsumerKeys, requestPin: String => String): AccessToken = {
		val ctw = createConsumerTwitter(ck)
		val requestToken = ctw.getOAuthRequestToken
		val pin = requestPin(requestToken.getAuthorizationURL)
		val accessToken = if (pin.length > 0) ctw.getOAuthAccessToken(requestToken, pin) else ctw.getOAuthAccessToken
		accessToken
	}

	def createTwitter(ck: ConsumerKeys, at: AccessToken): Twitter = {
		val conf = new ConfigurationBuilder()
			.setOAuthConsumerKey(ck.key).setOAuthConsumerSecret(ck.secret)
			.setOAuthAccessToken(at.getToken).setOAuthAccessTokenSecret(at.getTokenSecret)
		new TwitterFactory(conf.build).getInstance
	}

	def createConsumerTwitter(ck: ConsumerKeys): Twitter = {
		val conf = new ConfigurationBuilder()
			.setOAuthConsumerKey(ck.key).setOAuthConsumerSecret(ck.secret)
		new TwitterFactory(conf.build).getInstance
	}
}