package com.pds.poi4s.kml

import java.io.InputStream
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils
import org.scalatest._
import org.scalatest.matchers._

class KmlReaderSpec extends FlatSpec with Matchers {

  "KmlReader" should "parse valid file" in {
    parseAndCheckFile(getClass.getResourceAsStream("/observatories.kml"))
  }

  it should "reject a KML file without namespace" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(getClass.getResourceAsStream("/missing-namespace.kml"))
    }

    e.getMessage shouldBe "Missing KML namespace"
  }

  it should "reject an unsupported version KML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(getClass.getResourceAsStream("/unsupported-version.kml"))
    }

    e.getMessage shouldBe "Unsupported KML version 20.0"
  }

  it should "reject non-XML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(IOUtils.toInputStream("Not XML", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject broken XML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(IOUtils.toInputStream("<kml>incorrect termination</foo>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject non-KML XML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(IOUtils.toInputStream("<wrong>xml</wrong>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject KML file with invalid coordinates" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(getClass.getResourceAsStream("/invalid-coordinates.kml"))
    }

    e.getMessage shouldBe "Invalid coordinate [invalid]"
  }

  private def parseAndCheckFile(is: InputStream): KmlFile = {
    val parsed = KmlReader.read(is)

    parsed.placemarks.head should have(
      'lat (51.4778),
      'lon (-0.001400),
      'elevation (Some(46.0)),
      'name (Some("Royal Observatory, Greenwich"))
    )

    parsed.placemarks.head.description should beNormalisedOption(
      """
        |The Royal Observatory, Greenwich (known as the Royal Greenwich Observatory or RGO when the working institution
        |moved from Greenwich to Herstmonceux after World War II) is an observatory situated on a hill in Greenwich Park,
        |overlooking the River Thames. It played a major role in the history of astronomy and navigation, and is best
        |known as the location of the prime meridian.<br/>
        |<a href="https://en.wikipedia.org/wiki/Royal_Observatory,_Greenwich">Wikipedia Article</a>
      """.stripMargin
    )

    parsed.placemarks(1) should have(
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
}
