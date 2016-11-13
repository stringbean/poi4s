package com.pds.poi4s.gpx

import com.pds.poi4s.util.XmlUtils._

import scala.xml.Node

object WayPoint {
  private[gpx] def parseVersion10(node: Node): WayPoint = {
    WayPoint((node \@ "lat").toDouble,
      (node \@ "lon").toDouble,
      (node \ "ele").doubleOption,
      (node \ "name").textOption,
      (node \ "cmt").textOption,
      (node \ "desc").textOption,
      (node \ "url").textOption,
      (node \ "src").textOption)
  }

  private[gpx] def parseVersion11(node: Node): WayPoint = {
    WayPoint((node \@ "lat").toDouble,
      (node \@ "lon").toDouble,
      (node \ "ele").doubleOption,
      (node \ "name").textOption,
      (node \ "cmt").textOption,
      (node \ "desc").textOption,
      (node \ "link").textOption,
      (node \ "src").textOption)
  }
}

case class WayPoint(lat: Double,
                    lon: Double,
                    elevation: Option[Double],
                    name: Option[String],
                    comment: Option[String],
                    description: Option[String],
                    link: Option[String],
                    source: Option[String])