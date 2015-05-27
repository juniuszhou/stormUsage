name := "stormUsage"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "typesafe-repository" at "http://repo.typesafe.com/typesafe/releases/",
  "clojars-repository" at "https://clojars.org/repo"
)

libraryDependencies += "org.apache.storm" % "storm-core" % "0.9.4"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.12"
