package com.pds.poi4s.gpx

import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.time.{ZoneOffset, ZonedDateTime}

import com.pds.poi4s.model.{PoiFile, Waypoint}
import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

class GpxReaderSpec extends FlatSpec with Matchers {

  "GpxReader" should "parse a valid GPX 1.1 file" in {
    val parsed = parseAndCheckFile(getClass.getResourceAsStream("/gpx/1.1/observatories.gpx"))
    parsed.version shouldBe Some("1.1")
  }

  it should "parse a valid GPX 1.0 file" in {
    val parsed = parseAndCheckFile(getClass.getResourceAsStream("/gpx/1.0/observatories.gpx"))
    parsed.version shouldBe Some("1.0")
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

  private def parseAndCheckFile(is: InputStream): PoiFile = {
    val parsed = GpxReader.read(is)

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
