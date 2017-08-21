package software.purpledragon.poi4s.gpx

import java.io.{ByteArrayOutputStream, InputStream, StringReader}
import java.nio.charset.StandardCharsets
import java.time.{ZoneOffset, ZonedDateTime}

import org.apache.commons.io.IOUtils
import org.scalatest.xml.XmlMatchers
import org.scalatest.{FlatSpec, Matchers}
import software.purpledragon.poi4s.gpx.GpxVersion.{Version10, Version11}
import software.purpledragon.poi4s.model.{PoiFile, Waypoint}

import scala.xml.{Elem, XML}

class GpxParserSpec extends FlatSpec with Matchers with XmlMatchers {
  val parser = new GpxParser()

  "GpxParser.parseFile" should "parse a valid GPX 1.1 file" in {
    val parsed = parseAndCheckFile(getClass.getResourceAsStream("/gpx/1.1/observatories.gpx"))
    parsed.version shouldBe Some(GpxVersion.Version11)
  }

  it should "parse a valid GPX 1.0 file" in {
    val parsed = parseAndCheckFile(getClass.getResourceAsStream("/gpx/1.0/observatories.gpx"))
    parsed.version shouldBe Some(GpxVersion.Version10)
  }

  it should "reject an un-versioned GPX file" in {
    val e = the[GpxParseException] thrownBy {
      GpxReader.read(getClass.getResourceAsStream("/gpx/missing-version.gpx"))
    }

    e.getMessage shouldBe "Missing GPX version"
  }

  it should "reject an unsupported version GPX file" in {
    val e = the[GpxParseException] thrownBy {
      GpxReader.read(getClass.getResourceAsStream("/gpx/unsupported-version.gpx"))
    }

    e.getMessage shouldBe "Unsupported GPX version 2.0"
  }

  it should "reject non-XML file" in {
    val e = the[GpxParseException] thrownBy {
      GpxReader.read(IOUtils.toInputStream("Not XML", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid GPX file"
  }

  it should "reject broken XML file" in {
    val e = the[GpxParseException] thrownBy {
      GpxReader.read(IOUtils.toInputStream("<gpx>incorrect termination</foo>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid GPX file"
  }

  it should "reject non-GPX XML file" in {
    val e = the[GpxParseException] thrownBy {
      GpxReader.read(IOUtils.toInputStream("<wrong>xml</wrong>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid GPX file"
  }

  "GpxParser.writeFile" should "generate an empty GPX 1.1 file" in {
    val poiFile = PoiFile(name = Some("Empty GPX"), creator = Some("poi4s"))

    val xml = generateFile(poiFile, Version11)

    val expected = <gpx version="1.1" creator="poi4s">
      <metadata>
        <name>Empty GPX</name>
      </metadata>
    </gpx>

    xml should beXml(expected, ignoreWhitespace = true)
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

    xml should beXml(expected, ignoreWhitespace = true)
  }

  it should "generate an empty GPX 1.0 file" in {
    val poiFile = PoiFile(name = Some("Empty GPX"), creator = Some("poi4s"))

    val xml = generateFile(poiFile, Version10)

    val expected = <gpx version="1.0" creator="poi4s">
      <name>Empty GPX</name>
    </gpx>

    xml should beXml(expected, ignoreWhitespace = true)
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

    xml should beXml(expected, ignoreWhitespace = true)
  }

  private def generateFile(poiFile: PoiFile, version: GpxVersion): Elem = {
    val baos = new ByteArrayOutputStream()
    parser.writeFile(poiFile, baos, version)
    XML.load(new StringReader(baos.toString("UTF-8")))
  }

  private def parseAndCheckFile(is: InputStream): PoiFile = {
    val parsed = parser.parseFile(is)

    parsed.creator shouldBe Some("Unit tests")
    parsed.name shouldBe Some("Observatories")
    parsed.createdAt shouldBe Some(
      ZonedDateTime.of(2016, 11, 13, 12, 31, 43, 827000000, ZoneOffset.UTC).toInstant
    )

    parsed.waypoints shouldBe Seq(
      Waypoint(
        51.4778,
        -0.0014,
        Some(46.0),
        Some("Royal Observatory, Greenwich"),
        Some("also known as Royal Greenwich Observatory"),
        Some(
          "The Royal Observatory, Greenwich (known as the Royal Greenwich Observatory or RGO when the " +
            "working institution moved from Greenwich to Herstmonceux after World War II) is an observatory " +
            "situated on a hill in Greenwich Park, overlooking the River Thames. It played a major role in " +
            "the history of astronomy and navigation, and is best known as the location of the prime meridian."),
        Some("https://en.wikipedia.org/wiki/Royal_Observatory,_Greenwich"),
        Some("Wikipedia")
      ),
      Waypoint(53.23625, -2.307139, name = Some("Jodrell Bank"))
    )

    parsed
  }
}
