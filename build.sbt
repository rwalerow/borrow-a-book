import java.nio.file.Paths

import play.sbt.PlayImport.PlayKeys.playRunHooks
import sbt.project
import com.typesafe.config._
import sbt.Keys.baseDirectory

name := "bororow-a-book"
version := "1.0-SNAPSHOT"

val conf = ConfigFactory.parseFile(new File("conf/application.conf")).resolve()

lazy val root = (project in file(".")).
  enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars.bower" % "materialize" % "0.97.3",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test",
  "joda-time" % "joda-time" % "2.9.2",
  "org.postgresql" % "postgresql" % "9.4.1207"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"


// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

playRunHooks <+= baseDirectory.map(base => RunSubProcess("gulp", base))

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

fork in run := true