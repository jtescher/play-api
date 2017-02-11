resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

// Play Framework
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.10")

// Test Coverage
addSbtPlugin("org.scoverage" %% "sbt-scoverage" % "1.5.0")

// Style Guide
addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

// Code Formatting
addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

// Server Monitoring
addSbtPlugin("com.gilt.sbt" % "sbt-newrelic" % "0.1.15")

// UTC timezone
addSbtPlugin("com.timgroup" % "sbt-utc" % "0.0.14")

// SBT Dependency Graph
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")
