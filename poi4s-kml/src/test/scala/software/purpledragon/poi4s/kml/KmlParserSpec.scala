package software.purpledragon.poi4s.kml

import java.io.{ByteArrayOutputStream, InputStream, StringReader}
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.xml.XmlMatchers
import software.purpledragon.poi4s.exception.PoiParseException
import software.purpledragon.poi4s.model.{PoiFile, Waypoint}

import scala.xml.{Elem, XML}

class KmlParserSpec extends FlatSpec with Matchers with XmlMatchers {
  val parser = new KmlParser()

  "KmlParser.parseFile" should "parse valid file" in {
    val parsed = parseAndCheckFile(getClass.getResourceAsStream("/observatories.kml"))
    parsed.version shouldBe Some(KmlVersion.Version22)
  }

  it should "reject a KML file without namespace" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(getClass.getResourceAsStream("/missing-namespace.kml"))
    }

    e.getMessage shouldBe "Missing KML namespace"
  }

  it should "reject an unsupported version KML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(getClass.getResourceAsStream("/unsupported-version.kml"))
    }

    e.getMessage shouldBe "Unsupported KML version 20.0"
  }

  it should "reject non-XML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(IOUtils.toInputStream("Not XML", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject broken XML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(IOUtils.toInputStream("<kml>incorrect termination</foo>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject non-KML XML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(IOUtils.toInputStream("<wrong>xml</wrong>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject KML file with invalid coordinates" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(getClass.getResourceAsStream("/invalid-coordinates.kml"))
    }

    e.getMessage shouldBe "Invalid coordinate [invalid]"
  }

  "KmlParser.writeFile" should "generate an empty KML file" in {
    val poiFile = PoiFile()
    val xml = generateFile(poiFile)

    val expected = <kml xmlns="http://www.opengis.net/kml/2.2">
      <Document/>
    </kml>

    xml should beXml(expected, ignoreWhitespace = true)
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

    xml should beXml(expected, ignoreWhitespace = true)
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

    xml should beXml(expected, ignoreWhitespace = true)
  }

  private def parseAndCheckFile(is: InputStream): PoiFile = {
    val parsed = parser.parseFile(is)

    parsed.name shouldBe Some("Observatories")
    parsed.description shouldBe Some("List of observatories")

    parsed.waypoints.head should have(
      'lat (51.4778),
      'lon (-0.001400),
      'elevation (Some(46.0)),
      'name (Some("Royal Observatory, Greenwich"))
    )

    parsed.waypoints.head.description should beNormalisedOption(
      """
        |The Royal Observatory, Greenwich (known as the Royal Greenwich Observatory or RGO when the working institution
        |moved from Greenwich to Herstmonceux after World War II) is an observatory situated on a hill in Greenwich Park,
        |overlooking the River Thames. It played a major role in the history of astronomy and navigation, and is best
        |known as the location of the prime meridian.<br/>
        |<a href="https://en.wikipedia.org/wiki/Royal_Observatory,_Greenwich">Wikipedia Article</a>
      """.stripMargin
    )

    parsed.waypoints(1) should have(
      'lat (53.23625),
      'lon (-2.307139),
      'elevation (None),
      'name (Some("Jodrell Bank")),
      'description (None)
    )

    parsed
  }

  def beNormalised(expected: String): Matcher[String] = new NormalisedStringMatcher(expected)

  def beNormalisedOption(expected: String): Matcher[Option[String]] =
    beNormalised(expected) compose { (o: Option[String]) =>
      o.get
    }

  class NormalisedStringMatcher(expected: String) extends Matcher[String] {
    override def apply(left: String): MatchResult = {
      MatchResult(
        normalise(left).trim == normalise(expected).trim,
        s"[$left] did not match [$expected]",
        s"[$left] matched [$expected]"
      )
    }

    private def normalise(in: String): String = {
      in.replaceAll("\\n", " ")
        .replaceAll("  +", " ")
    }
  }

  private def generateFile(poiFile: PoiFile): Elem = {
    val baos = new ByteArrayOutputStream()
    parser.writeFile(poiFile, baos)
    XML.load(new StringReader(baos.toString("UTF-8")))
  }
}
