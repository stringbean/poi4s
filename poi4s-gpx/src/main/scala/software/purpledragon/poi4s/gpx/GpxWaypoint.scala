package software.purpledragon.poi4s.gpx

import software.purpledragon.poi4s.model.Waypoint
import software.purpledragon.poi4s.util.XmlUtils._

import scala.xml.Node

private[gpx] object GpxWaypoint {
  def parseVersion10(node: Node): Waypoint = {
    Waypoint(
      (node \@ "lat").toDouble,
      (node \@ "lon").toDouble,
      (node \ "ele").doubleOption,
      (node \ "name").textOption,
      (node \ "cmt").textOption,
      (node \ "desc").textOption,
      (node \ "url").textOption,
      (node \ "src").textOption
    )
  }

  def parseVersion11(node: Node): Waypoint = {
    Waypoint(
      (node \@ "lat").toDouble,
      (node \@ "lon").toDouble,
      (node \ "ele").doubleOption,
      (node \ "name").textOption,
      (node \ "cmt").textOption,
      (node \ "desc").textOption,
      (node \ "link").textOption,
      (node \ "src").textOption
    )
  }
}
