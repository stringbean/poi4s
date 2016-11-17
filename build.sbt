import CommonSettings._

organization := "com.pds"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

lazy val common = project.in(file("poi4s"))
  .settings(commonSettings)

lazy val gpx = project.in(file("poi4s-gpx"))
  .settings(commonSettings)
  .dependsOn(common)

lazy val kml = project.in(file("poi4s-kml"))
  .settings(commonSettings)
  .dependsOn(common)

lazy val root = project.in(file("."))
  .aggregate(common, gpx, kml)
