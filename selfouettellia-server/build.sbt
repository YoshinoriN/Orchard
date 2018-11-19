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
  "org.mariadb.jdbc" % "mariadb-java-client" % "2.3.0",
  "io.getquill" %% "quill-jdbc" % "2.6.0",
  "org.flywaydb" % "flyway-core" % "5.2.1",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)
