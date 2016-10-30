package com.pds.poi4s.gpx

import org.scalatest.{FlatSpec, Matchers}

import scala.language.higherKinds

class GpxReaderSpec extends FlatSpec with Matchers {

  "GpxReader" should "parse valid GPX" in {
    val parsed = GpxReader.read(GpxReader.getClass.getResourceAsStream("/gpx/observatories.gpx"))

    parsed.name shouldBe Some("Observatories")

    parsed.waypoints shouldBe Seq(
      WayPoint(51.4778,
        -0.0014,
        Some(46.0),
        Some("Royal Observatory, Greenwich"),
        Some("Now a museum"),
        Some("https://en.wikipedia.org/wiki/Royal_Observatory,_Greenwich")),
      WayPoint(53.23625,
        -2.307139,
        None,
        Some("Jodrell Bank"),
        None,
        None)
    )
  }
}
