package com.pds.poi4s.gpx

import java.io.{ByteArrayOutputStream, StringReader}

import com.pds.poi4s.gpx.GpxVersion.{Version10, Version11}
import com.pds.poi4s.model.{PoiFile, Waypoint}
import org.scalatest.StreamlinedXmlEquality._
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class GpxWriterSpec extends FlatSpec with Matchers {

  "GpxWriter" should "generate an empty GPX 1.1 file" in {
    val poiFile = PoiFile(name = Some("Empty GPX"), creator = Some("poi4s"))

    val xml = generateFile(poiFile, Version11)

    val expected = <gpx version="1.1" creator="poi4s">
      <metadata>
        <name>Empty GPX</name>
      </metadata>
    </gpx>

    xml should ===(expected)
  }

  it should "generate GPX 1.1 file with waypoints" in {
    val poiFile = PoiFile(
      name = Some("Waypoints GPX"),
      creator = Some("poi4s"),
      waypoints = Seq(
        Waypoint(
          51.4994794d,
          -0.12480919999995876d,
          Some(2.134d),
          Some("Palace of Westminster"),
          Some("GPS coordinates taken from Google Maps"),
          Some("Seat of the UK parliament"),
          Some("https://en.wikipedia.org/wiki/Palace_of_Westminster"),
          Some("Google Maps")
        )
      )
    )

    val xml = generateFile(poiFile, Version11)
    val expected = <gpx version="1.1" creator="poi4s">
      <metadata>
        <name>Waypoints GPX</name>
      </metadata>

      <wpt lat="51.4994794" lon="-0.12480919999995876">
        <name>Palace of Westminster</name>
        <ele>2.134</ele>
        <cmt>GPS coordinates taken from Google Maps</cmt>
        <desc>Seat of the UK parliament</desc>
        <link>https://en.wikipedia.org/wiki/Palace_of_Westminster</link>
        <src>Google Maps</src>
      </wpt>
    </gpx>

    xml should ===(expected)
  }

  it should "generate an empty GPX 1.0 file" in {
    val poiFile = PoiFile(name = Some("Empty GPX"), creator = Some("poi4s"))

    val xml = generateFile(poiFile, Version10)

    val expected = <gpx version="1.0" creator="poi4s">
      <name>Empty GPX</name>
    </gpx>

    xml should ===(expected)
  }

  it should "generate GPX 1.0 file with waypoints" in {
    val poiFile = PoiFile(
      name = Some("Waypoints GPX"),
      creator = Some("poi4s"),
      waypoints = Seq(
        Waypoint(
          51.4994794d,
          -0.12480919999995876d,
          Some(2.134d),
          Some("Palace of Westminster"),
          Some("GPS coordinates taken from Google Maps"),
          Some("Seat of the UK parliament"),
          Some("https://en.wikipedia.org/wiki/Palace_of_Westminster"),
          Some("Google Maps")
        )
      )
    )

    val xml = generateFile(poiFile, Version10)
    val expected = <gpx version="1.0" creator="poi4s">
      <name>Waypoints GPX</name>

      <wpt lat="51.4994794" lon="-0.12480919999995876">
        <name>Palace of Westminster</name>
        <ele>2.134</ele>
        <cmt>GPS coordinates taken from Google Maps</cmt>
        <desc>Seat of the UK parliament</desc>
        <url>https://en.wikipedia.org/wiki/Palace_of_Westminster</url>
        <src>Google Maps</src>
      </wpt>
    </gpx>

    xml should ===(expected)
  }

  private def generateFile(poiFile: PoiFile, version: GpxVersion): Elem = {
    val baos = new ByteArrayOutputStream()
    GpxWriter.write(poiFile, baos, version)
    XML.load(new StringReader(baos.toString("UTF-8")))
  }
}
