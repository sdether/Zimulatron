import _root_.sbt.ConflictManager
import _root_.sbt.Keys._
import _root_.sbtassembly.AssemblyPlugin.autoImport._

name := "Zimulatron"

version := "0.1"

scalaVersion := "2.11.5"

assemblyJarName in assembly := "zimulatron.jar"

mainClass in assembly := Some("net.claassen.zimulatron.Universe")

conflictManager := ConflictManager.latestRevision

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature")

libraryDependencies ++= {
  val akkaVersion = "2.3.11"
  Seq(
    "ch.qos.logback"               % "logback-classic"          % "1.1.3",
    "com.typesafe"                 % "config"                   % "1.3.0",
    "com.typesafe.akka"           %% "akka-cluster"             % akkaVersion,
    "com.typesafe.akka"           %% "akka-contrib"             % akkaVersion,
    "com.typesafe.akka"           %% "akka-slf4j"               % akkaVersion,
    "com.typesafe.scala-logging"  %% "scala-logging-slf4j"      % "2.1.2",
    "org.json4s"                  %% "json4s-jackson"           % "3.2.11",
    "org.scalatest"               %% "scalatest"                % "2.2.1"     % "test"

  )
}
