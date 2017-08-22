/*
 * Copyright 2017 Michael Stringer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package software.purpledragon.poi4s.kml

import software.purpledragon.poi4s.exception.PoiParseException
import software.purpledragon.poi4s.model.Waypoint
import software.purpledragon.poi4s.util.XmlUtils._

import scala.xml.Node

private[kml] object Placemark {
  private val Coordinates = "(\\-?[0-9]+\\.[0-9]+), *(\\-?[0-9]+\\.[0-9]+)".r
  private val CoordinatesWithElevation = "(\\-?[0-9]+\\.[0-9]+), *(\\-?[0-9]+\\.[0-9]+), *(\\-?[0-9]+\\.[0-9]+)".r

  @throws[PoiParseException]
  def parseCoordinates(in: String): (Double, Double, Option[Double]) = {
    in match {
      case Coordinates(lon, lat) =>
        (lat.toDouble, lon.toDouble, None)

      case CoordinatesWithElevation(lon, lat, elevation) =>
        (lat.toDouble, lon.toDouble, Some(elevation.toDouble))

      case _ =>
        throw new PoiParseException(s"Invalid coordinate [$in]")
    }
  }

  @throws[PoiParseException]
  def parseVersion22(node: Node): Waypoint = {
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
