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
    "spark-cassandra-activemq-jetty",
    file("."),
    settings = buildSettings ++ assemblySettings ++ Seq(
      parallelExecution in Test := false,
      libraryDependencies ++= Seq(
        "com.datastax.spark" %% "spark-cassandra-connector" % "1.1.0-alpha3",
        "org.apache.spark" %% "spark-catalyst" % "1.1.0" % "provided",
        "org.scalatest" %% "scalatest" % "2.1.5" % "test"
      ),
      libraryDependencies += "com.google.code.gson" % "gson" % "2.5",
      libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "8.1.14.v20131031"
      //libraryDependencies += "org.eclipse.jetty" % "jetty-plus" % "8.1.14.v20131031"
    )
  )
}