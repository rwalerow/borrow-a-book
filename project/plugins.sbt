resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.0")

// web plugins

addSbtPlugin("org.flywaydb" % "flyway-sbt" % "3.2.1")

resolvers += "Flyway" at "http://flywaydb.org/repo"
