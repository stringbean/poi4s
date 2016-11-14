package com.pds.poi4s.gpx

import java.io.{ByteArrayOutputStream, StringReader}

import com.pds.poi4s.gpx.GpxVersion.Version11
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class GpxWriterSpec extends FlatSpec with Matchers {

  "GpxWriter" should "generate an empty GPX 1.1 file" in {
    val gpx = GpxFile(GpxVersion.Version11,
      "poi4s",
      Some("Empty GPX"),
      None,
      Nil)

    val xml = generateFile(gpx,Version11)

    xml \@ "version" shouldBe "1.1"
    xml \@ "creator" shouldBe "poi4s"

    (xml \ "metadata" \ "name").text shouldBe "Empty GPX"
    // TODO created time

    val waypoints = xml \\ "wpt"
    waypoints.isEmpty shouldBe true
  }

  it should "generate GPX 1.1 file with waypoints" in {
    val gpx = GpxFile(GpxVersion.Version11,
      "poi4s",
      Some("Waypoints GPX"),
      None,
      Seq(
        WayPoint(
          51.4994794d,
          -0.12480919999995876d,
          Some(2.134d),
          Some("Palace of Westminster"),
          Some("GPS coordinates taken from Google Maps"),
          Some("Seat of the UK parliament"),
          Some("https://en.wikipedia.org/wiki/Palace_of_Westminster"),
          Some("Google Maps")
        )
      ))

    val xml = generateFile(gpx,Version11)

    xml \@ "version" shouldBe "1.1"
    xml \@ "creator" shouldBe "poi4s"

    (xml \ "metadata" \ "name").text shouldBe "Waypoints GPX"
    // TODO created time

    val waypoints = xml \\ "wpt"
    waypoints.size shouldBe 1

    waypoints.head \@ "lat" shouldBe "51.4994794"
    waypoints.head \@ "lon" shouldBe "-0.12480919999995876"
    (waypoints.head \ "ele").text shouldBe "2.134"
    (waypoints.head \ "name").text shouldBe "Palace of Westminster"
    (waypoints.head \ "cmt").text shouldBe "GPS coordinates taken from Google Maps"
    (waypoints.head \ "desc").text shouldBe "Seat of the UK parliament"
    (waypoints.head \ "link").text shouldBe "https://en.wikipedia.org/wiki/Palace_of_Westminster"
    (waypoints.head \ "src").text shouldBe "Google Maps"

    waypoints.head \@ "lat" shouldBe "51.4994794"
    waypoints.head \@ "lon" shouldBe "-0.12480919999995876"
  }

  private def generateFile(gpxFile: GpxFile, version: GpxVersion): Elem = {
    val baos = new ByteArrayOutputStream()
    GpxWriter.write(gpxFile, baos, version)
//    println(baos.toString("UTF-8"))
    XML.load(new StringReader(baos.toString("UTF-8")))
  }
}
