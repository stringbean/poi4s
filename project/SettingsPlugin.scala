import com.lucidchart.sbt.scalafmt.ScalafmtCorePlugin.autoImport._
import sbt._
import sbt.Keys._
import sbt.plugins.JvmPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin
import de.heikoseeberger.sbtheader.HeaderPlugin.autoImport._
import sbtunidoc.BaseUnidocPlugin.autoImport._
import sbtunidoc.ScalaUnidocPlugin.autoImport._

object SettingsPlugin extends AutoPlugin {
  override def trigger: PluginTrigger = AllRequirements
  override def requires: Plugins = JvmPlugin && HeaderPlugin

  object autoImport {
    val scalaXml = Seq(
      "org.scala-lang.modules"  %% "scala-xml"      % "1.0.6",
      "com.github.andyglow"     %% "scala-xml-diff" % "2.0.3-SNAPSHOT"  % "test"
    )
    val enumeratum = Seq(
      "com.beachape"            %% "enumeratum" % "1.5.12"
    )
  }

  val javaVersion = "1.8"

  override def projectSettings: Seq[Setting[_]] = Seq(
    organization := (organization in LocalRootProject).value,
    version := (version in LocalRootProject).value,
    scalaVersion := (scalaVersion in LocalRootProject).value,
    crossScalaVersions := (crossScalaVersions in LocalRootProject).value,
    javacOptions ++= Seq("-source", javaVersion,
      "-target", javaVersion,
      "-Xlint"),
    scalacOptions ++= Seq(s"-target:jvm-$javaVersion",
      "-deprecation",
      "-feature",
      "-unchecked"),
    scalacOptions in (ScalaUnidoc, unidoc) ++= Seq("-diagrams", "-diagrams-debug"),
    initialize := {
      if (sys.props("java.specification.version") != "1.8")
        sys.error("Java 8 is required for this project.")
    },
    libraryDependencies ++= Seq(
      "org.scalatest"           %% "scalatest"  % "3.0.4"   % "test",
      "commons-io"              %  "commons-io" % "2.5"     % "test"
    ),
    scalafmtVersion := "1.2.0",
    autoAPIMappings := true,
    apiMappings +=
      file("/Library/Java/JavaVirtualMachines/jdk1.8.0_131.jdk/Contents/Home/jre/lib/rt.jar") ->
        url("http://docs.oracle.com/javase/8/docs/api"),
    headerLicense := Some(HeaderLicense.ALv2("2017", "Michael Stringer")),
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
    developers := List(
      Developer("stringbean", "Michael Stringer", "@the_stringbean", url("https://github.com/stringbean"))
    ),
    organizationName := "Purple Dragon Software",
    organizationHomepage := Some(url("https://purpledragon.software")),
    homepage := Some(url("https://stringbean.github.io/poi4s")),
    scmInfo := Some(ScmInfo(url("https://github.com/stringbean/poi4s"), "https://github.com/stringbean/poi4s.git"))
  )
}
