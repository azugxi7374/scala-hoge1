package hoge1.twitter

import config.SessionFile
import hoge1.Cached
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{User, TwitterFactory, Twitter}
import twitter4j.auth.AccessToken

case class TwitterConf(sessionFile: SessionFile)

case class ConsumerKeys(key: String, secret: String)

object TwitterUtils {
	protected case class AT(atk: String, ats: String) {
		def at = new AccessToken(atk, ats)
	}
	protected object AT {
		def apply(at: AccessToken) = new AT(at.getToken, at.getTokenSecret)
	}

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