val Organization = "net.yoshinorin"
val Name = "selfouettie"
val version = "0.0.1"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

val circeVersion = "0.10.0"
libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.typesafe.akka" %% "akka-http" % "10.1.5",
  "com.typesafe.akka" %% "akka-actor" % "2.5.18",
  "com.typesafe.akka" %% "akka-stream" % "2.5.18",
  "com.typesafe" % "config" % "1.3.3",
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.getquill" %% "quill-jdbc" % "2.6.0",
  "org.flywaydb" % "flyway-core" % "5.2.1",
  "org.mariadb.jdbc" % "mariadb-java-client" % "2.3.0",
  "org.slf4j" % "slf4j-api" % "1.7.25",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
)

scalafmtOnCompile := true
coverageExcludedPackages := ".*Plugin.*;.*applicationlogs.html.*"
