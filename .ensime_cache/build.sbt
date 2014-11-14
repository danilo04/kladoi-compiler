
import sbt._
import java.io._

scalaVersion := "2.10.4"

resolvers += Resolver.sonatypeRepo("snapshots")

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Akka Repo" at "http://repo.akka.io/repository"

libraryDependencies += "org.ensime" %% "ensime" % "0.9.10-SNAPSHOT"

// guaranteed to exist when started from emacs
val JavaTools = new File(sys.env("JAVA_HOME"), "/lib/tools.jar")

unmanagedClasspath in Runtime += { Attributed.blank(JavaTools) }

mainClass in Compile := Some("org.ensime.server.Server")

fork := true

javaOptions ++= Seq (
  "-Dscala.usejavacp=true",
  "-Densime.config=/home/danilo04/workspace/cool-compiler/.ensime",
  "-Densime.cachedir=/home/danilo04/workspace/cool-compiler/.ensime_cache/",
  "-Densime.active=IGNORED"
)

javaOptions += "-Xms1024m"

javaOptions += "-Xmx1024m"

javaOptions += "-XX:ReservedCodeCacheSize=128m"

javaOptions += "-XX:MaxPermSize=256m"
