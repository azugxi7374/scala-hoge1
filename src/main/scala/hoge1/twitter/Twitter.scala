package hoge1

package object twitter {
	import twitter4j._

	def createStatusURL(screenName: String, statusId: Long) =
		"https://twitter.com/%s/status/%d".format(screenName, statusId)

	object Twitter4REPL extends Twitter4REPL
	trait Twitter4REPL extends TwitterI{
		val conf = TwitterConf(hoge1.sessionFile)
		val tw = TwitterGetter(conf)

		object Tw4r {
			/** デフォルトユーザーでtwitter作成． */
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


