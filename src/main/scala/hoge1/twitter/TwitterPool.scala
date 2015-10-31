package hoge1.twitter

import hoge1.Pool
import twitter4j.{TwitterFactory, Twitter}
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

object TwitterPool {

	object CreateTwitter extends Pool[(ConsumerKeys, String, String), Twitter] {
		override protected def get(k: (ConsumerKeys, String, String)): Twitter = {
			val (ck, atk, ats) = k
			val conf = new ConfigurationBuilder()
				.setOAuthConsumerKey(ck.key).setOAuthConsumerSecret(ck.secret)
				.setOAuthAccessToken(atk).setOAuthAccessTokenSecret(ats)
			new TwitterFactory(conf.build).getInstance
		}
		def apply(ck: ConsumerKeys, at: AccessToken): Twitter = apply((ck, at.getToken, at.getTokenSecret))
	}

	object CreateConsumerTwitter extends Pool[ConsumerKeys, Twitter] {
		override protected def get(ck: ConsumerKeys): Twitter = {
			val conf = new ConfigurationBuilder()
				.setOAuthConsumerKey(ck.key).setOAuthConsumerSecret(ck.secret)
			new TwitterFactory(conf.build).getInstance
		}
	}
}


