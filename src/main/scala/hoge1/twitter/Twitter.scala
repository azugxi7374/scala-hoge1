package hoge1

package object twitter {
	import config.{Session, SessionFile}
	import twitter4j._
	import twitter4j.auth.AccessToken
	import twitter4j.conf.ConfigurationBuilder

	def createStatusURL(screenName: String, statusId: Long) =
		"https://twitter.com/%s/status/%d".format(screenName, statusId)


	trait Twitter4REPL extends twitter.TwitterImpl {
		import TwitterUtils._

		def twitter: Twitter = {
			val tw = create
			println("current user : @%s".format(getUser(tw).getScreenName))
			tw
		}
		def clearTwitter = TwitterUtils.clear
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


