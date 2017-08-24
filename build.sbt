import PgpKeys.{publishLocalSigned, publishSigned}
import microsites.{ExtraMdFileConfig,MicrositeFavicon}

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
      "brand-primary"     -> "#337ab7",
      "brand-secondary"   -> "#34495e",
      "brand-tertiary"    -> "#2c3e50",
      "gray-dark"         -> "#495454",
      "gray"              -> "#6c7677",
      "gray-light"        -> "#bdc3c7",
      "gray-lighter"      -> "#ecf0f1",
      "white-color"       -> "#FFFFFF"
    ),
    micrositeHighlightTheme := "atom-one-light",
    micrositeFavicons := Seq(
      MicrositeFavicon("favicon16.png", "16x16"),
      MicrositeFavicon("favicon24.png", "24x24"),
      MicrositeFavicon("favicon32.png", "32x32"),
      MicrositeFavicon("favicon64.png", "64x64"),
      MicrositeFavicon("favicon96.png", "96x96"),
      MicrositeFavicon("favicon128.png", "128x128"),
      MicrositeFavicon("favicon512.png", "512x512")
    ),
    docsMappingsAPIDir := "api",
    addMappingsToSiteDir(mappings in (ScalaUnidoc, packageDoc), docsMappingsAPIDir)
  )

useGpg := true
usePgpKeyHex("B19D7A14F6F8B3BFA9FF655A5216B5A5F723A92D")
