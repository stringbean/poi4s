package com.pds.poi4s.gpx

import scala.xml.Node

object WayPoint {
  def apply(node: Node): WayPoint = {
    val name = (node \ "name").headOption.map(_.text)
    val elevation = (node \ "ele").headOption.map(_.text.toDouble)
    WayPoint((node \@ "lat").toDouble, (node \@ "lon").toDouble, name, elevation)
  }
}

case class WayPoint(lat: Double, lon: Double, name: Option[String], elevation: Option[Double])