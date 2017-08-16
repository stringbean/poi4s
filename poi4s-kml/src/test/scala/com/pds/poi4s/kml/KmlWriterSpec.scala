package com.pds.poi4s.kml

import java.io.{ByteArrayOutputStream, StringReader}

import com.pds.poi4s.model.{PoiFile, Waypoint}
import org.scalatest.StreamlinedXmlEquality._
import org.scalatest.{FlatSpec, Matchers}

import scala.xml.{Elem, XML}

class KmlWriterSpec extends FlatSpec with Matchers {

  "KmlWriter" should "generate an empty KML file" in {
    val poiFile = PoiFile()
    val xml = generateFile(poiFile)

    val expected = <kml xmlns="http://www.opengis.net/kml/2.2">
      <Document/>
    </kml>

    xml should ===(expected)
  }

  it should "generate KML file with waypoints" in {
    val kml = PoiFile(
      waypoints = Seq(
        Waypoint(
          51.4994794d,
          -0.12480919999995876d,
          elevation = Some(2.134d),
          name = Some("Palace of Westminster"),
          description = Some("Seat of the UK parliament")
        ),
        Waypoint(
          51.504722,
          -0.1375,
          name = Some("St James's Palace")
        )
      )
    )

    val xml = generateFile(kml)
    val expected = <kml xmlns="http://www.opengis.net/kml/2.2">
      <Document>
        <Placemark>
          <name>Palace of Westminster</name>
          <description>Seat of the UK parliament</description>
          <Point>
            <coordinates>-0.12480919999995876,51.4994794,2.134</coordinates>
          </Point>
        </Placemark>
        <Placemark>
          <name>St James's Palace</name>
          <Point>
            <coordinates>-0.1375,51.504722</coordinates>
          </Point>
        </Placemark>
      </Document>
    </kml>

    xml should ===(expected)
  }

  it should "generate KML file with metadata" in {
    val poiFile = PoiFile(name = Some("Test File"), description = Some("This is a test file"))

    val xml = generateFile(poiFile)
    val expected =
      <kml xmlns="http://www.opengis.net/kml/2.2">
        <Document>
          <name>Test File</name>
          <description>This is a test file</description>
        </Document>
      </kml>

    xml should ===(expected)
  }

  private def generateFile(poiFile: PoiFile): Elem = {
    val baos = new ByteArrayOutputStream()
    KmlWriter.write(poiFile, baos)
    XML.load(new StringReader(baos.toString("UTF-8")))
  }
}