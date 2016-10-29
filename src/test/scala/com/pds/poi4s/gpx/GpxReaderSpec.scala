package com.pds.poi4s.gpx

import org.scalatest.{FlatSpec, Matchers}

import scala.language.higherKinds

class GpxReaderSpec extends FlatSpec with Matchers {

  "GpxReader" should "parse valid GPX" in {
    val parsed = GpxReader.read(GpxReader.getClass.getResourceAsStream("/gpx/observatories.gpx"))

    parsed.name shouldBe Some("Observatories")

    parsed.waypoints should contain (
      WayPoint(51.4778,-0.0014, Some("Royal Observatory, Greenwich"), Some(46.0))
    )
  }
}
