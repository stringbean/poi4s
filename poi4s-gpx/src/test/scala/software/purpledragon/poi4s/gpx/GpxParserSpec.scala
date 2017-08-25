package software.purpledragon.poi4s.gpx

import java.io.{ByteArrayOutputStream, StringReader}
import java.nio.charset.StandardCharsets
import java.time.{ZoneOffset, ZonedDateTime}

import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}
import software.purpledragon.poi4s.exception.PoiParseException
import software.purpledragon.poi4s.gpx.GpxVersion.{Version10, Version11}
import software.purpledragon.poi4s.model.{PoiFile, Route, Waypoint}
import software.purpledragon.xml.scalatest.XmlMatchers

import scala.xml.{Elem, XML}

class GpxParserSpec extends FlatSpec with Matchers with XmlMatchers {
  val parser = new GpxParser()

  "GpxParser.parseFile" should "parse a valid GPX 1.1 file" in {
    val expected = observatoryFile.copy(version = Some(GpxVersion.Version11))
    parseAndCheckFile("/gpx/1.1/observatories.gpx", expected)
  }

  it should "parse a valid GPX 1.0 file" in {
    val expected = observatoryFile.copy(version = Some(GpxVersion.Version10))
    parseAndCheckFile("/gpx/1.0/observatories.gpx", expected)
  }

  it should "reject an un-versioned GPX file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(getClass.getResourceAsStream("/gpx/missing-version.gpx"))
    }

    e.getMessage shouldBe "Missing GPX version"
  }

  it should "reject an unsupported version GPX file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(getClass.getResourceAsStream("/gpx/unsupported-version.gpx"))
    }

    e.getMessage shouldBe "Unsupported GPX version 2.0"
  }

  it should "reject non-XML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(IOUtils.toInputStream("Not XML", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid GPX file"
  }

  it should "reject broken XML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(IOUtils.toInputStream("<gpx>incorrect termination</foo>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid GPX file"
  }

  it should "reject non-GPX XML file" in {
    val e = the[PoiParseException] thrownBy {
      parser.parseFile(IOUtils.toInputStream("<wrong>xml</wrong>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid GPX file"
  }

  it should "parse GPX 1.1 file with route" in {
    val expected = grizedaleFile.copy(version = Some(GpxVersion.Version11))
    parseAndCheckFile("/gpx/1.1/grizedale.gpx", expected)
  }

  it should "parse GPX 1.0 file with route" in {
    val expected = grizedaleFile.copy(version = Some(GpxVersion.Version10))
    parseAndCheckFile("/gpx/1.0/grizedale.gpx", expected)
  }

  "GpxParser.writeFile" should "generate an empty GPX 1.1 file" in {
    val poiFile = PoiFile(name = Some("Empty GPX"), creator = Some("poi4s"))

    val xml = generateFile(poiFile, Version11)

    val expected = <gpx version="1.1" creator="poi4s">
      <metadata>
        <name>Empty GPX</name>
      </metadata>
    </gpx>

    xml should beXml(expected)
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

    xml should beXml(expected)
  }

  it should "generate an empty GPX 1.0 file" in {
    val poiFile = PoiFile(name = Some("Empty GPX"), creator = Some("poi4s"))

    val xml = generateFile(poiFile, Version10)

    val expected = <gpx version="1.0" creator="poi4s">
      <name>Empty GPX</name>
    </gpx>

    xml should beXml(expected)
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

    xml should beXml(expected)
  }

  private def generateFile(poiFile: PoiFile, version: GpxVersion): Elem = {
    val baos = new ByteArrayOutputStream()
    parser.writeFile(poiFile, baos, version)
    XML.load(new StringReader(baos.toString("UTF-8")))
  }

  private def parseAndCheckFile(resource: String, expected: PoiFile): Unit = {
    val parsed = parser.parseFile(getClass.getResourceAsStream(resource))
    parsed shouldBe expected
  }

  private val observatoryFile = PoiFile()
    .withName("Observatories")
    .withCreator("Unit tests")
    .withCreatedAt(ZonedDateTime.of(2016, 11, 13, 12, 31, 43, 827000000, ZoneOffset.UTC).toInstant)
    .withWaypoints(Seq(
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
    ))

  private val grizedaleFile = PoiFile()
    .withCreator("Memory-Map 5.4.2.1089 http://www.memory-map.com") +
    Route()
      .withName("Grizedale")
      .withWaypoints(Seq(
        Waypoint(54.3401727421, -3.0233466401).withName("WP19601"),
        Waypoint(54.3392044031, -3.0262450573).withName("WP19602"),
        Waypoint(54.3380752863, -3.0269090760).withName("WP19603"),
        Waypoint(54.3346652586, -3.0262857948).withName("WP19604"),
        Waypoint(54.3338558808, -3.0263425325).withName("WP19605"),
        Waypoint(54.3327189029, -3.0279290091).withName("WP19606"),
        Waypoint(54.3302458376, -3.0280979922).withName("WP19607"),
        Waypoint(54.3269925750, -3.0301696014).withName("WP19608"),
        Waypoint(54.3257220759, -3.0315986049).withName("WP19609"),
        Waypoint(54.3248394450, -3.0349592515).withName("WP19610"),
        Waypoint(54.3237135804, -3.0352384961).withName("WP19611"),
        Waypoint(54.3222355398, -3.0346632482).withName("WP19612"),
        Waypoint(54.3218291998, -3.0348836737).withName("WP19613"),
        Waypoint(54.3222613240, -3.0368932875).withName("WP19614"),
        Waypoint(54.3217313478, -3.0410311867).withName("WP19615"),
        Waypoint(54.3219280837, -3.0442649064).withName("WP19616"),
        Waypoint(54.3226772216, -3.0459751663).withName("WP19617"),
        Waypoint(54.3226713487, -3.0518176294).withName("WP19618"),
        Waypoint(54.3224533487, -3.0561940054).withName("WP19619"),
        Waypoint(54.3236026307, -3.0583760466).withName("WP19620"),
        Waypoint(54.3258351114, -3.0548967812).withName("WP19621"),
        Waypoint(54.3274720217, -3.0527090005).withName("WP19622"),
        Waypoint(54.3278072975, -3.0503340957).withName("WP19623"),
        Waypoint(54.3290719952, -3.0495974443).withName("WP19624"),
        Waypoint(54.3312915506, -3.0475778788).withName("WP19625"),
        Waypoint(54.3321698697, -3.0447551229).withName("WP19626"),
        Waypoint(54.3319142699, -3.0431338746).withName("WP19627"),
        Waypoint(54.3344119159, -3.0401211731).withName("WP19628"),
        Waypoint(54.3346471624, -3.0388967315).withName("WP19629"),
        Waypoint(54.3347562047, -3.0366694112).withName("WP19630"),
        Waypoint(54.3357631261, -3.0345415237).withName("WP19631"),
        Waypoint(54.3380326283, -3.0319068379).withName("WP19632"),
        Waypoint(54.3389866567, -3.0307002261).withName("WP19633"),
        Waypoint(54.3391503093, -3.0273204087).withName("WP19634"),
        Waypoint(54.3392044031, -3.0262450573).withName("WP19602")
      ))

}
