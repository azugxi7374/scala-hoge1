//////////////////////////////////////
// plugins
resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

resolvers += "sbt-idea-repo" at "http://mpeltonen.github.com/maven/"

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.0")

// RevolverÇ™égÇ¶ÇÈÇÊÇ§Ç…Ç»ÇÈÇÁÇµÇ¢
addSbtPlugin("io.spray" % "sbt-revolver" % "0.7.2")



///////////sbt-native-packagerÇ∆Ç©ÅB
// https://github.com/jsuereth/sbt-in-action-examples/issues/11ÇÊÇË
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.4.0")

resolvers += "Typesafe Public Repo" at "http://repo.typesafe.com/typesafe/releases"

resolvers += "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.2.3")

//addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.11.2")

libraryDependencies ++= Seq(
  "org.apache.velocity" % "velocity" % "1.7"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.6.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.6.4")


