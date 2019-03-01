val Organization = "net.yoshinorin"
val Name = "orchard"
val version = "0.0.1"

scalaVersion := "2.12.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

val circeVersion = "0.11.1"
libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-http" % "10.1.7",
  "com.typesafe.akka" %% "akka-actor" % "2.5.20",
  "com.typesafe.akka" %% "akka-stream" % "2.5.20",
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.7.1-akka-2.5.x",
  "com.typesafe" % "config" % "1.3.3",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.getquill" %% "quill-jdbc" % "3.0.1",
  "org.flywaydb" % "flyway-core" % "6.0.0-beta",
  "org.mariadb.jdbc" % "mariadb-java-client" % "2.4.0",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.mockito" % "mockito-core" % "2.23.4" % Test
)

scalafmtOnCompile := true
coverageExcludedPackages := ".*Plugin.*;.*applicationlogs.html.*"
org.scoverage.coveralls.Imports.CoverallsKeys.coverallsGitRepoLocation := Some("..")
