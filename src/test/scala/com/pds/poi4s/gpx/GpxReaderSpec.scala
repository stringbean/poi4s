package com.pds.poi4s.gpx

import java.time.{ZoneOffset, ZonedDateTime}

import org.scalatest.{FlatSpec, Matchers}

import scala.language.higherKinds

class GpxReaderSpec extends FlatSpec with Matchers {

  "GpxReader" should "parse valid GPX" in {
    val parsed = GpxReader.read(GpxReader.getClass.getResourceAsStream("/gpx/observatories.gpx"))

    parsed.name shouldBe Some("Observatories")
    parsed.created shouldBe Some(
      ZonedDateTime.of(2016, 11, 13, 12, 31, 43, 827000000, ZoneOffset.UTC)
    )

    parsed.waypoints shouldBe Seq(
      WayPoint(51.4778,
        -0.0014,
        Some(46.0),
        Some("Royal Observatory, Greenwich"),
        Some("also known as Royal Greenwich Observatory"),
        Some("The Royal Observatory, Greenwich (known as the Royal Greenwich Observatory or RGO when the " +
          "working institution moved from Greenwich to Herstmonceux after World War II) is an observatory " +
          "situated on a hill in Greenwich Park, overlooking the River Thames. It played a major role in " +
          "the history of astronomy and navigation, and is best known as the location of the prime meridian."),
        Some("https://en.wikipedia.org/wiki/Royal_Observatory,_Greenwich"),
        Some("Wikipedia")),
      WayPoint(53.23625,
        -2.307139,
        None,
        Some("Jodrell Bank"),
        None,
        None,
        None,
        None)
    )
  }
}