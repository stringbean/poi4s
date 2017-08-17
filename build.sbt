import PgpKeys.{publishLocalSigned, publishSigned}
import microsites.ExtraMdFileConfig

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
    publish := {},
    publishSigned := {},
    publishLocal := {},
    publishLocalSigned := {},
    scalacOptions in Compile in doc ++= Seq(
      "-doc-root-content", baseDirectory.value + "/root-scaladoc.txt"
    )
  )


lazy val docsMappingsAPIDir = settingKey[String]("Name of subdirectory in site target directory for api docs")


lazy val docs = project
  .in(file("docs"))
  .enablePlugins(MicrositesPlugin, ScalaUnidocPlugin, GhpagesPlugin)
  .settings(
    micrositeName := "poi4s",
    micrositeDescription := "GPS manipulation library for Scala",
    micrositeAuthor := "Michael Stringer",
    micrositeHomepage := "https://stringbean.github.io/poi4s",
    micrositeGithubOwner := "stringbean",
    micrositeGithubRepo := "poi4s",
    micrositeBaseUrl := "/poi4s",
    micrositeDocumentationUrl := "docs",
    micrositeExtraMdFiles := Map(file("README.md") -> ExtraMdFileConfig("index.md", "home")),
    micrositeGitterChannel := false,
    micrositePushSiteWith := GHPagesPlugin,
    micrositePalette := Map(
      "brand-primary"     -> "#d35400",
      "brand-secondary"   -> "#34495e",
      "brand-tertiary"    -> "#2c3e50",
      "gray-dark"         -> "#667374",
      "gray"              -> "#7f8c8d",
      "gray-light"        -> "#bdc3c7",
      "gray-lighter"      -> "#ecf0f1",
      "white-color"       -> "#FFFFFF"
    ),
    docsMappingsAPIDir := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), docsMappingsAPIDir)
  )

useGpg := true
usePgpKeyHex("B19D7A14F6F8B3BFA9FF655A5216B5A5F723A92D")
