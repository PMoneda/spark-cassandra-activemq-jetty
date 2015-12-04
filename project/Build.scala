import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object TesteSparkBuild extends Build {
  lazy val buildSettings = Defaults.defaultSettings ++ Seq(
    version := "0.1-SNAPSHOT",
    organization := "org.moneda",
    scalaVersion := "2.10.4"
  )

  lazy val app = Project(
    "teste-spark",
    file("."),
    settings = buildSettings ++ assemblySettings ++ Seq(
      parallelExecution in Test := false,
      libraryDependencies ++= Seq(
        "com.datastax.spark" %% "spark-cassandra-connector" % "1.1.0-alpha3",
        //"org.apache.activemq" %% "activemq-core" % "5.0.0",
        // spark will already be on classpath when using spark-submit.
        // marked as provided, so that it isn't included in assembly.
        "org.apache.spark" %% "spark-catalyst" % "1.1.0" % "provided",
        "org.scalatest" %% "scalatest" % "2.1.5" % "test"
      ),
      libraryDependencies += "com.google.code.gson" % "gson" % "2.5"
    )
  )
}