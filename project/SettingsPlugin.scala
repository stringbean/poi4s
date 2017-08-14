import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin

object SettingsPlugin extends AutoPlugin {
  val javaVersion = 8

  override def trigger: PluginTrigger = AllRequirements
  override def requires: Plugins = JvmPlugin

  override def projectSettings: Seq[Setting[_]] = Seq(
    organization := (organization in LocalRootProject).value,
    version := (version in LocalRootProject).value,
    scalaVersion := (scalaVersion in LocalRootProject).value,
    crossScalaVersions := (crossScalaVersions in LocalRootProject).value,
    javacOptions ++= Seq("-source", s"1.$javaVersion",
      "-target", s"1.$javaVersion",
      "-Xlint"),
    scalacOptions ++= Seq(s"-target:jvm-1.$javaVersion",
      "-deprecation",
      "-feature",
      "-unchecked"),
    initialize := {
      if (sys.props("java.specification.version") != "1.8")
        sys.error("Java 8 is required for this project.")
    },
    libraryDependencies ++= Seq(
      "org.scalatest"           %% "scalatest"  % "3.0.3" % "test",
      "commons-io"              %  "commons-io" % "2.5"     % "test"
    )
  )

  object autoImport {
    val scalaXml = Seq(
      "org.scala-lang.modules"  %% "scala-xml"  % "1.0.6"
    )
    val enumeratum = Seq(
      "com.beachape"            %% "enumeratum" % "1.5.12"
    )
  }
}
