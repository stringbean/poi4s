import PgpKeys.{publishLocalSigned, publishSigned}

organization := "software.purpledragon"

version := "0.0.2"

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
    // don't publish the empty root project
    publish := {},
    publishSigned := {},
    publishLocal := {},
    publishLocalSigned := {},
    bintrayUnpublish := {},
    bintraySyncMavenCentral := {},
    scalacOptions in Compile in doc ++= Seq(
      "-doc-root-content", baseDirectory.value + "/root-scaladoc.txt"
    )
  )
  .enablePlugins(ScalaUnidocPlugin)

useGpg := true
usePgpKeyHex("B19D7A14F6F8B3BFA9FF655A5216B5A5F723A92D")
