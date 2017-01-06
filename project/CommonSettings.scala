import sbt.Keys._
import sbt.LocalRootProject

object CommonSettings {
  val javaVersion = 8

  lazy val commonSettings = Seq(
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
    }
  )
}
