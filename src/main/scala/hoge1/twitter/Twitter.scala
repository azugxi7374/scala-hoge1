package hoge1

import config.SessionFile

package object twitter {
	import twitter4j._

	def createStatusURL(screenName: String, statusId: Long) =
		s"""https://twitter.com/$screenName/status/$statusId"""
	def createUserPageURL(screenName: String) =
		s"""https://twitter.com/$screenName"""
	val dumpHtmlFile = "dump.html"
	val sessionFile = hoge1.sessionFile

	case class TwitterConf(sessionFile: SessionFile)

	case class ConsumerKeys(key: String, secret: String)


	object Twitter4REPL extends Twitter4REPL

	trait Twitter4REPL extends TwitterI {
		val conf = TwitterConf(sessionFile)
		val tw = TwitterGetter(conf)

		object Tw4r {
			/** デフォルトユーザーでtwitter作成． */
			def apply() = default
			def default: Twitter = tw.createTwitterDefault

			/** ユーザーを選択してtwitter作成． */
			def select: Twitter = tw.createTwitterSelect

			/** ユーザー情報を消去してtwitter作成． */
			def clean: Twitter = tw.createTwitterClean
		}
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


