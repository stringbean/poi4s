name := "poi4s"
organization := "com.pds"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"
crossScalaVersions := Seq("2.11.8", "2.12.0")

libraryDependencies ++= Seq(
  "org.scala-lang.modules"  %% "scala-xml"  % "1.0.6",
  "org.scalatest"           %% "scalatest"  % "3.0.1"   % "test"
)
