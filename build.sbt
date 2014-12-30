organization := "com.kotancode"

name := "scalamud"

version := "1.0"

scalaVersion := "2.11.4"


libraryDependencies ++= Seq(
                    "edu.stanford.nlp" % "stanford-corenlp" % "1.3.0" % "compile",
                    "com.typesafe.akka" %% "akka-actor" % "2.3.8" % "compile"
                    )

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Nexus Releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
