package com.pds.poi4s.model

import org.scalatest.{FlatSpec, Matchers}

class WaypointSpec extends FlatSpec with Matchers {
  val waypoint = Waypoint(-2.307139, 53.236250)

  "Waypoint.withName" should "return a new copy with a name" in {
    val updated = waypoint.withName("Jodrell Bank")

    updated.name shouldBe Some("Jodrell Bank")
    updated should not be theSameInstanceAs(waypoint)
  }

  "Waypoint.withComment" should "return a new copy with a comment" in {
    val updated = waypoint.withComment("Test comment")

    updated.comment shouldBe Some("Test comment")
    updated should not be theSameInstanceAs(waypoint)
  }

  "Waypoint.withDescription" should "return a new copy with a description" in {
    val updated = waypoint.withDescription("Test description")

    updated.description shouldBe Some("Test description")
    updated should not be theSameInstanceAs(waypoint)
  }

  "Waypoint.withElevation" should "return a new copy with an elevation" in {
    val updated = waypoint.withElevation(1.5)

    updated.elevation shouldBe Some(1.5d)
    updated should not be theSameInstanceAs(waypoint)
  }

  "Waypoint.withLink" should "return a new copy with a link" in {
    val updated = waypoint.withLink("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")

    updated.link shouldBe Some("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")
    updated should not be theSameInstanceAs(waypoint)
  }

  "Waypoint.withSource" should "return a new copy with a source" in {
    val updated = waypoint.withSource("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")

    updated.source shouldBe Some("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")
    updated should not be theSameInstanceAs(waypoint)
  }
}
