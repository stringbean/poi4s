package com.pds.poi4s.kml

import java.io.InputStream

import com.pds.poi4s.model.PoiFile

import scala.xml._
import com.pds.poi4s.util.XmlUtils._

object KmlReader {
  private[kml] val KmlNamespace = "http://www.opengis.net/kml/([0-9]+.[0-9]+)".r

  @throws[KmlParseException]
  def read(is: InputStream): PoiFile = {
    try {
      val xml = XML.load(is)

      if (xml.label != "kml") {
        throw new KmlParseException("Invalid KML file")
      }

      xml.namespace match {
        case KmlNamespace("2.2") =>
          parseVersion22(xml)

        case KmlNamespace(v) =>
          throw new KmlParseException(s"Unsupported KML version $v")

        case _ =>
          throw new KmlParseException("Missing KML namespace")
      }

    } catch {
      case se: SAXException =>
        throw new KmlParseException("Invalid KML file", se)
    }
  }

  private def parseVersion22(xml: Elem): PoiFile = {
    val name = (xml \ "Document" \ "name").headOption.map(_.text)
    val description = (xml \ "Document" \ "description").textOption

    val placemarks = (xml \\ "Placemark").map(Placemark.parseVersion22)

    PoiFile(
      name = name,
      description = description,
      waypoints = placemarks
    )
  }
}
