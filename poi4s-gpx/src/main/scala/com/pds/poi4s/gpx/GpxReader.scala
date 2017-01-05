package com.pds.poi4s.gpx

import java.io.InputStream

import com.pds.poi4s.gpx.GpxVersion._
import com.pds.poi4s.util.XmlUtils._

import scala.xml.{Elem, SAXException, XML}

object GpxReader {
  @throws[GpxParseException]
  def read(is: InputStream): GpxFile = {
    try {
      val xml = XML.load(is)

      if (xml.label != "gpx") {
        throw new GpxParseException("Invalid GPX file")
      }

      xml \@ "version" match {
        case "1.0" =>
          parseVersion10(xml)

        case "1.1" =>
          parseVersion11(xml)

        case v if v.isEmpty =>
          throw new GpxParseException("Missing GPX version")

        case v =>
          throw new GpxParseException(s"Unsupported GPX version $v")
      }
    } catch {
      case se: SAXException =>
        throw new GpxParseException("Invalid GPX file", se)
    }
  }

  private def parseVersion10(xml: Elem): GpxFile = {
    val creator = xml \@ "creator"
    val name = (xml \ "name").textOption
    val created = (xml \ "time").dateTimeOption

    val wayPoints = (xml \\ "wpt").map(GpxWaypoint.parseVersion10)

    GpxFile(Version10, creator, name, created, wayPoints)
  }

  private def parseVersion11(xml: Elem): GpxFile = {
    val creator = xml \@ "creator"
    val name = (xml \ "metadata" \ "name").textOption
    val created = (xml \ "metadata" \ "time").dateTimeOption

    val wayPoints = (xml \\ "wpt").map(GpxWaypoint.parseVersion11)

    GpxFile(Version11, creator, name, created, wayPoints)
  }
}
