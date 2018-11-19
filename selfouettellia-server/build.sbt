val Organization = "net.yoshinorin"
val Name = "selfouettellia"
val version = "0.0.1"

scalaVersion := "2.12.7"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.5",
  "com.typesafe.akka" %% "akka-actor" % "2.5.18",
  "com.typesafe.akka" %% "akka-stream" % "2.5.18",
  "com.typesafe" % "config" % "1.3.3",
  "io.getquill" %% "quill-jdbc" % "2.6.0",
  "org.flywaydb" % "flyway-core" % "5.2.1",
  "org.mariadb.jdbc" % "mariadb-java-client" % "2.3.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

scalafmtOnCompile := true
