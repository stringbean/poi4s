package com.pds.poi4s.kml

import com.pds.poi4s.model.Waypoint
import com.pds.poi4s.util.XmlUtils._

import scala.xml.Node

object Placemark {
  private val Coordinates = "(\\-?[0-9]+\\.[0-9]+), *(\\-?[0-9]+\\.[0-9]+)".r
  private val CoordinatesWithElevation = "(\\-?[0-9]+\\.[0-9]+), *(\\-?[0-9]+\\.[0-9]+), *(\\-?[0-9]+\\.[0-9]+)".r

  def parseCoordinates(in: String): (Double, Double, Option[Double]) = {
    in match {
      case Coordinates(lon, lat) =>
        (lat.toDouble, lon.toDouble, None)

      case CoordinatesWithElevation(lon, lat, elevation) =>
        (lat.toDouble, lon.toDouble, Some(elevation.toDouble))

      case _ =>
        throw new KmlParseException(s"Invalid coordinate [$in]")
    }
  }

  private[kml] def parseVersion22(node: Node): Waypoint = {
    val coordinate = parseCoordinates((node \ "Point" \ "coordinates").text)
    Waypoint(
      coordinate._1,
      coordinate._2,
      coordinate._3,
      (node \ "name").textOption,
      None,
      (node \ "description").textOption.map(_.trim),
      None,
      None)
  }
}
