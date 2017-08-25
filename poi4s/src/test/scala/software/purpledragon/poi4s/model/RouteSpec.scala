package software.purpledragon.poi4s.model

import org.scalatest.{FlatSpec, Matchers}

class RouteSpec extends FlatSpec with Matchers {
  val route = Route()

  "Route.withName" should "return a new copy with a name" in {
    val updated = route.withName("Fell Walk")

    updated.name shouldBe Some("Fell Walk")
    updated should not be theSameInstanceAs(route)
  }

  "Route.withComment" should "return a new copy with a comment" in {
    val updated = route.withComment("Test comment")

    updated.comment shouldBe Some("Test comment")
    updated should not be theSameInstanceAs(route)
  }

  "Route.withDescription" should "return a new copy with a description" in {
    val updated = route.withDescription("Test description")

    updated.description shouldBe Some("Test description")
    updated should not be theSameInstanceAs(route)
  }

  "Route.withLink" should "return a new copy with a link" in {
    val updated = route.withLink("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")

    updated.link shouldBe Some("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")
    updated should not be theSameInstanceAs(route)
  }

  "Route.withSource" should "return a new copy with a source" in {
    val updated = route.withSource("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")

    updated.source shouldBe Some("https://en.wikipedia.org/wiki/Jodrell_Bank_Observatory")
    updated should not be theSameInstanceAs(route)
  }

  "Route.withWaypoints" should "return a new copy with waypoints" in {
    val waypoints = Seq(
      Waypoint(1.0, 1.0),
      Waypoint(1.0, -1.0)
    )
    val updated = route.withWaypoints(waypoints)
    updated should not be theSameInstanceAs(route)
  }

  "Route + waypoint" should "return a new copy with the additional waypoint" in {
    val waypoint = Waypoint(1.0, 1.0)
    val updated = route + waypoint
    updated should not be theSameInstanceAs(route)
  }

  "Route ++ waypoint" should "return a new copy with the additional waypoints" in {
    val waypoints = Seq(
      Waypoint(1.0, 1.0),
      Waypoint(1.0, -1.0)
    )
    val updated = route ++ waypoints
    updated should not be theSameInstanceAs(route)
  }
}
