package hoge1.twitter

import config.SessionFile
import twitter4j.{User, TwitterFactory, Twitter}
import twitter4j.auth.AccessToken
import TwitterPool._

case class TwitterConf(sessionFile: SessionFile)

case class ConsumerKeys(key: String, secret: String)

object TwitterUtils {

	def at2User(ck: ConsumerKeys, at: AccessToken): User = {
		CreateTwitter(ck, at).users.verifyCredentials()
	}

	def authByPIN(ck: ConsumerKeys, requestPin: String => String): AccessToken = {
		val ctw = CreateConsumerTwitter(ck)
		val requestToken = ctw.getOAuthRequestToken
		val pin = requestPin(requestToken.getAuthorizationURL)
		val accessToken = if (pin.length > 0) ctw.getOAuthAccessToken(requestToken, pin) else ctw.getOAuthAccessToken
		accessToken
	}
}