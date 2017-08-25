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

package software.purpledragon.poi4s.gpx

import java.io.{IOException, InputStream, OutputStream}

import software.purpledragon.poi4s.PoiParser
import software.purpledragon.poi4s.exception.{PoiParseException, PoiWriteException}
import software.purpledragon.poi4s.gpx.GpxVersion.{Version10, Version11}
import software.purpledragon.poi4s.model.PoiFile
import software.purpledragon.poi4s.util.XmlUtils._

import scala.xml.{Elem, PrettyPrinter, SAXException, XML}

/** Parse/writer for GPX files. Supports GPX 1.0 & 1.1.
  */
class GpxParser extends PoiParser[GpxVersion] {
  private val prettyPrinter = new PrettyPrinter(80, 4)

  @throws[PoiParseException]
  override def parseFile(is: InputStream): PoiFile = {
    try {
      val xml = XML.load(is)

      if (xml.label != "gpx") {
        throw new PoiParseException("Invalid GPX file")
      }

      xml \@ "version" match {
        case "1.0" =>
          parseVersion10(xml)

        case "1.1" =>
          parseVersion11(xml)

        case v if v.isEmpty =>
          throw new PoiParseException("Missing GPX version")

        case v =>
          throw new PoiParseException(s"Unsupported GPX version $v")
      }
    } catch {
      case se: SAXException =>
        throw new PoiParseException("Invalid GPX file", se)
    }
  }

  override def writeFile(poiFile: PoiFile, os: OutputStream): Unit = writeFile(poiFile, os, GpxVersion.Version11)

  override def writeFile(poiFile: PoiFile, os: OutputStream, version: GpxVersion): Unit = {
    val xml = version match {
      case Version10 => generateVersion10(poiFile)
      case Version11 => generateVersion11(poiFile)
    }

    try {
      os.write(prettyPrinter.format(xml).getBytes("UTF-8"))
      os.close()
    } catch {
      case ioe: IOException =>
        throw new PoiWriteException("Error occurred while writing to file", ioe)
    }
  }

  @throws[PoiParseException]
  private def parseVersion10(xml: Elem): PoiFile = {
    val creator = xml \@ "creator"
    val name = (xml \ "name").textOption
    val created = (xml \ "time").instantOption

    val waypoints = (xml \\ "wpt").map(GpxWaypoint.parseVersion10)
    val routes = (xml \\ "rte").map(GpxRoute.parseVersion10)

    PoiFile(
      name = name,
      creator = Some(creator),
      createdAt = created,
      version = Some(GpxVersion.Version10),
      waypoints = waypoints,
      routes = routes
    )
  }

  @throws[PoiParseException]
  private def parseVersion11(xml: Elem): PoiFile = {
    val creator = xml \@ "creator"
    val name = (xml \ "metadata" \ "name").textOption
    val created = (xml \ "metadata" \ "time").instantOption

    val waypoints = (xml \\ "wpt").map(GpxWaypoint.parseVersion11)
    val routes = (xml \\ "rte").map(GpxRoute.parseVersion11)

    PoiFile(
      name = name,
      creator = Some(creator),
      createdAt = created,
      version = Some(GpxVersion.Version11),
      waypoints = waypoints,
      routes = routes
    )
  }

  private def generateVersion10(poiFile: PoiFile): Elem = {
    <gpx version="1.0" creator="poi4s">
      {poiFile.name.map(n => <name>{n}</name>).orNull}

      {
      poiFile.waypoints map { wpt =>
        <wpt lat={wpt.lat.toString} lon={wpt.lon.toString}>
          {wpt.name.map(n => <name>{n}</name>).orNull}
          {wpt.elevation.map(e => <ele>{e}</ele>).orNull}
          {wpt.comment.map(c => <cmt>{c}</cmt>).orNull}
          {wpt.description.map(d => <desc>{d}</desc>).orNull}
          {wpt.link.map(l => <url>{l}</url>).orNull}
          {wpt.source.map(s => <src>{s}</src>).orNull}
        </wpt>
      }
      }
    </gpx>
  }

  private def generateVersion11(poiFile: PoiFile): Elem = {
    <gpx version="1.1" creator="poi4s">
      <metadata>
        {poiFile.name.map(n => <name>{n}</name>).orNull}
      </metadata>

      {
      poiFile.waypoints map { wpt =>
        <wpt lat={wpt.lat.toString} lon={wpt.lon.toString}>
          {wpt.name.map(n => <name>{n}</name>).orNull}
          {wpt.elevation.map(e => <ele>{e}</ele>).orNull}
          {wpt.comment.map(c => <cmt>{c}</cmt>).orNull}
          {wpt.description.map(d => <desc>{d}</desc>).orNull}
          {wpt.link.map(l => <link>{l}</link>).orNull}
          {wpt.source.map(s => <src>{s}</src>).orNull}
        </wpt>
      }
      }
    </gpx>
  }
}
