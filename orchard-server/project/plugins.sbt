import sbt.util

logLevel := util.Level.Warn

addSbtPlugin("com.geirsson" % "sbt-scalafmt" % "1.5.1")
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")
addSbtPlugin("org.scoverage" % "sbt-coveralls" % "1.2.7")
addSbtPlugin("ch.epfl.scala" % "sbt-scalafix" % "0.9.4")
