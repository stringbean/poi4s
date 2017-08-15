package com.pds.poi4s.model

import java.time.Instant

import org.scalatest.{FlatSpec, Matchers}

class PoiFileSpec extends FlatSpec with Matchers {
  val poiFile = PoiFile()

  "PoiFile()" should "create an empty file" in {
    poiFile should have(
      'name (None),
      'description (None),
      'creator (None),
      'createdAt (None),
      'waypoints (Nil)
    )
  }

  "PoiFile.withName" should "return a new copy with a name" in {
    val updated = poiFile.withName("Observatories")
    updated should not be theSameInstanceAs(poiFile)

    updated should have(
      'name (Some("Observatories")),
      'description (None),
      'creator (None),
      'createdAt (None),
      'waypoints (Nil)
    )
  }

  "PoiFile.withDescription" should "return a new copy with a description" in {
    val updated = poiFile.withDescription("List of observatories")
    updated should not be theSameInstanceAs(poiFile)

    updated should have(
      'name (None),
      'description (Some("List of observatories")),
      'creator (None),
      'createdAt (None),
      'waypoints (Nil)
    )
  }

  "PoiFile.withCreator" should "return a new copy with a creator" in {
    val updated = poiFile.withCreator("poi4s")
    updated should not be theSameInstanceAs(poiFile)

    updated should have(
      'name (None),
      'description (None),
      'creator (Some("poi4s")),
      'createdAt (None),
      'waypoints (Nil)
    )
  }

  "PoiFile.withCreatedAt" should "return a new copy with a createdAt" in {
    val ts = Instant.now()
    val updated = poiFile.withCreatedAt(ts)
    updated should not be theSameInstanceAs(poiFile)

    updated should have(
      'name (None),
      'description (None),
      'creator (None),
      'createdAt (Some(ts)),
      'waypoints (Nil)
    )
  }

  "PoiFile.withWaypoints" should "return a new copy with waypoints" in {
    val waypoints = Seq(
      Waypoint(1.0, 1.0),
      Waypoint(1.0, -1.0)
    )
    val updated = poiFile.withWaypoints(waypoints)
    updated should not be theSameInstanceAs(poiFile)

    updated should have(
      'name (None),
      'description (None),
      'creator (None),
      'createdAt (None),
      'waypoints (waypoints)
    )
  }

  "PoiFile + waypoint" should "return a new copy with the additional waypoint" in {
    val waypoint = Waypoint(1.0, 1.0)
    val updated = poiFile + waypoint
    updated should not be theSameInstanceAs(poiFile)

    updated should have(
      'name (None),
      'description (None),
      'creator (None),
      'createdAt (None),
      'waypoints (Seq(waypoint))
    )
  }
}
