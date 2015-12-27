name := """play-api"""

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4.1207",
  "com.typesafe.play" %% "play-slick" % "1.1.1",
  "com.ticketfly" %% "play-liquibase" % "1.0",
  "org.scalatest" %% "scalatest" % "2.2.5" % Test,
  "org.scalatestplus" %% "play" % "1.4.0-M4" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

// Excluded test coverage
coverageExcludedPackages := ".*Reverse.*;.*Routes.*;"
