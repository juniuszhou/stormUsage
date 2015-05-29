name := "stormUsage"

version := "1.0"

scalaVersion := "2.10.4"

scalacOptions += "-Yresolve-term-conflict:package"

resolvers ++= Seq(
  "typesafe-repository" at "http://repo.typesafe.com/typesafe/releases/",
  "clojars-repository" at "https://clojars.org/repo"
)

libraryDependencies += "org.apache.storm" % "storm-core" % "0.9.4"
