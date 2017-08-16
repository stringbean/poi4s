import PgpKeys.{publishLocalSigned, publishSigned}

organization := "software.purpledragon"

version := "0.0.1"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

lazy val common = project
  .in(file("poi4s"))

lazy val gpx = project
  .in(file("poi4s-gpx"))
  .dependsOn(common)

lazy val kml = project
  .in(file("poi4s-kml"))
  .dependsOn(common)

lazy val root = project
  .in(file("."))
  .aggregate(common, gpx, kml)
  .settings(
    publish := {},
    publishSigned := {},
    publishLocal := {},
    publishLocalSigned := {}
  )
  .enablePlugins(ScalaUnidocPlugin)

useGpg := true
usePgpKeyHex("B19D7A14F6F8B3BFA9FF655A5216B5A5F723A92D")