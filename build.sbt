import play.sbt.PlayImport.PlayKeys.playRunHooks
import sbt.project

name := "bororow-a-book"
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  ws,
  specs2 % Test,
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.ticketfly" %% "play-liquibase" % "1.0",
  "org.webjars" %% "webjars-play" % "2.4.0-1",
  "org.webjars.bower" % "materialize" % "0.97.3",
  "org.postgresql" % "postgresql" % "9.4.1207"
)

resolvers ++= Seq(
  "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
  "sonatype-snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

playRunHooks <+= baseDirectory.map(base => RunSubProcess("gulp", base))