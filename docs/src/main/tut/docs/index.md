---
layout: docs
title:  "Getting Started"
section: "docs"
---

# Getting Started

## Adding the Dependencies

poi4s is a published as a common library (`poi4s`) plus format specific libraries (`poi4s-gpx` etc). The common library
is pulled in as a dependency of the format libraries so you just need to add the formats you need to your `build.sbt`:

```scala
val poi4sVersion = "0.0.2"

libraryDependencies ++= Seq(
  "software.purpledragon" %% "poi4s-gpx"  % poi4sVersion,
  "software.purpledragon" %% "poi4s-kml"  % poi4sVersion
)
```

## Converting a File

```scala
import java.io._
import software.purpledragon.poi4s.gpx.Gpx
import software.purpledragon.poi4s.kml.Kml

// read in a KML file
val is = new FileInputStream("in.kml")
val parsed = Kml.Parser.parseFile(is)

// write out to a GPX 1.1 file
val os = new FileOutputStream("out.gpx")
GpxWriter.Parser.writeFile(parsed, os, GpxVersion.Version11)
```

## Parsing Files

```scala
import java.io._
import software.purpledragon.poi4s.gpx.Gpx

val is = new FileInputStream("in.gpx")
val parsed = Gpx.Parser.parseFile(is)

println(s"Name: ${parsed.name}")
parsed.waypoints foreach { wp =>
  println(s"(${wp.lat}, ${wp.lon})")
}
```

## Writing Files

```scala
import java.io._
import software.purpledragon.poi4s.kml.Kml
import software.purpledragon.poi4s.model.PoiFile

// generate the GPS data
val poi = PoiFile()
  .withName("Test File")
  .withWaypoints(Seq(Waypoint(1.1, 1.0), Waypoint(1.1, 1.1)))

// write out the file
val is = new FileOutputStream("out.kml")
Kml.Parser.writeFile(poi, os, KmlVersion.Version22)
```
