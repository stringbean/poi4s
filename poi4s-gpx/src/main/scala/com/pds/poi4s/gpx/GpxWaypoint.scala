package com.pds.poi4s.gpx

import com.pds.poi4s.model.Waypoint
import com.pds.poi4s.util.XmlUtils._

import scala.xml.Node

object GpxWaypoint {
  private[gpx] def parseVersion10(node: Node): Waypoint = {
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

  private[gpx] def parseVersion11(node: Node): Waypoint = {
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
