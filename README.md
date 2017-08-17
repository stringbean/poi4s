# poi4s - Scala library for parsing and converting GPS files

[![Build Status](https://img.shields.io/travis/stringbean/poi4s.svg)](https://travis-ci.org/stringbean/poi4s)
[![Test Coverage](https://img.shields.io/codecov/c/github/stringbean/poi4s.svg)](https://codecov.io/gh/stringbean/poi4s)
[![Maven Central - Scala 2.11](https://img.shields.io/maven-central/v/software.purpledragon/poi4s_2.11.svg?label=scala 2.11)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22poi4s_2.11%22)
[![Maven Central - Scala 2.12](https://img.shields.io/maven-central/v/software.purpledragon/poi4s_2.12.svg?label=scala 2.12)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22poi4s_2.12%22)

This is currently very much a work in progress.

More information can found in the [documentation](https://stringbean.github.io/poi4s/docs).

## SBT Configuration

```scala
val poi4sVersion = "0.0.1"

libraryDependencies ++= Seq(
  "software.purpledragon" %% "poi4s"      % poi4sVersion,
  "software.purpledragon" %% "poi4s-gpx"  % poi4sVersion,
  "software.purpledragon" %% "poi4s-kml"  % poi4sVersion
)
```