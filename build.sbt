import java.nio.file.Paths

import play.sbt.PlayImport.PlayKeys.playRunHooks
import sbt.project
import com.typesafe.config._
import sbt.Keys.baseDirectory

name := "bororow-a-book"
version := "1.0-SNAPSHOT"

lazy val configFile = scala.util.Properties.propOrElse("config.file", "conf/application.conf")

lazy val conf = ConfigFactory.parseFile(new File(configFile)).resolve()

lazy val root = (project in file(".")).
  enablePlugins(PlayScala)

scalaVersion := "2.11.6"

//Load backend dependencies
libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "org.scalatest" %% "scalatest" % "2.2.1" % "test",
  "org.scalatestplus" %% "play" % "1.4.0-M3" % "test",
  "joda-time" % "joda-time" % "2.9.2",
  "org.postgresql" % "postgresql" % "9.4.1207"
)

// Load frontend libraries
libraryDependencies ++= Seq(
	"org.webjars" %% "webjars-play" % "2.4.0-1",
	"org.webjars.bower" % "materialize" % "0.97.3",

	//angular2 dependencies
	"org.webjars.npm" % "angular2" % "2.0.0-beta.8",
	"org.webjars.npm" % "systemjs" % "0.19.20",
	"org.webjars.npm" % "rxjs" % "5.0.0-beta.2",
	"org.webjars.npm" % "es6-promise" % "3.0.2",
	"org.webjars.npm" % "es6-shim" % "0.34.1",
	"org.webjars.npm" % "reflect-metadata" % "0.1.2",
	"org.webjars.npm" % "zone.js" % "0.5.15",
	"org.webjars.npm" % "typescript" % "1.8.2",

	//tslint dependency
	"org.webjars.npm" % "tslint-eslint-rules" % "1.0.1"
)
dependencyOverrides += "org.webjars.npm" % "minimatch" % "2.0.10"

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

incOptions := incOptions.value.withNameHashing(true)
updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true)


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

// the typescript typing information is by convention in the typings directory
// It provides ES6 implementations. This is required when compiling to ES5.
typingsFile := Some(baseDirectory.value / "typings" / "browser.d.ts")


fork in run := false
