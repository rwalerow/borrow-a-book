import java.nio.file.Paths

import sbt.project
import com.typesafe.config._
import sbt.Keys.baseDirectory

name := "bororow-a-book"
version := "1.0-SNAPSHOT"

lazy val configFile = scala.util.Properties.propOrElse("config.file", "conf/application.conf")

lazy val conf = ConfigFactory.parseFile(new File(configFile)).resolve()

lazy val root = (project in file(".")).
  enablePlugins(PlayScala)

scalaVersion := "2.11.7"

//Load backend dependencies
libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test",
  "joda-time" % "joda-time" % "2.9.2",
  "org.postgresql" % "postgresql" % "9.4.1207",
  "org.typelevel" %% "scalaz-scalatest" % "0.3.0" % "test",
  "org.scalaz" %% "scalaz-core" % "7.1.4"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator


/**
  *
  * Flyway db Configuration
  *
  * We want to configuration to be the same as slick
  *
  */
seq(flywaySettings: _*)

flywayBaselineOnMigrate := true

flywayUrl       := conf.getString("slick.dbs.default.db.url")
flywayUser      := conf.getString("slick.dbs.default.db.user")
flywayPassword  := conf.getString("slick.dbs.default.db.password")
flywayLocations := Seq("filesystem:" + Paths.get(baseDirectory.value.absolutePath,"db","migrate").toString)
flywaySchemas   := Seq("borrowabook")

TwirlKeys.templateImports ++= Seq("models.users.UserForms._", "play.api.libs.json.Json")

testOptions in Test += Tests.Argument("-Dconfig.file=conf/test.conf")

fork in run := false
