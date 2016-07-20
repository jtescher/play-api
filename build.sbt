name := """play-api"""

version := "1.0.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.postgresql" % "postgresql" % "9.4.1209",
  "com.typesafe.play" %% "play-slick" % "2.0.2",
  "com.ticketfly" %% "play-liquibase" % "1.2",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Test coverage configuration
coverageMinimum := 100
coverageFailOnMinimum := true
coverageExcludedPackages := ".*Reverse.*;.*Routes.*;"
