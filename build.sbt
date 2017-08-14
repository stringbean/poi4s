name := "poi4s"
organization := "com.pds"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.12.3"
crossScalaVersions := Seq("2.11.11", "2.12.3")

val javaVersion = "8"

javacOptions ++= Seq("-source", s"1.$javaVersion",
  "-target", s"1.$javaVersion",
  "-Xlint")

scalacOptions ++= Seq(s"-target:jvm-1.$javaVersion",
  "-deprecation",
  "-feature",
  "-unchecked")

initialize := {
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}

libraryDependencies ++= Seq(
  "org.scala-lang.modules"  %% "scala-xml"  % "1.0.6",
  "com.beachape"            %% "enumeratum" % "1.5.12",
  "org.scalatest"           %% "scalatest"  % "3.0.3"   % "test",
  "commons-io"              %  "commons-io" % "2.5"     % "test"
)
