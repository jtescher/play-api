name := """play-api"""

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4.1208",
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.ticketfly" %% "play-liquibase" % "1.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Test coverage configuration
coverageMinimum := 100
coverageFailOnMinimum := true
coverageExcludedPackages := ".*Reverse.*;.*Routes.*;"
