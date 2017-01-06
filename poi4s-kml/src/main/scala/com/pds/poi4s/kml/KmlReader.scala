package com.pds.poi4s.kml

import java.io.InputStream

import scala.xml._

object KmlReader {
  private[kml] val KmlNamespace = "http://www.opengis.net/kml/([0-9]+.[0-9]+)".r

  @throws[KmlParseException]
  def read(is: InputStream): KmlFile = {
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

  private def parseVersion22(xml: Elem): KmlFile = {
    val placemarks = (xml \\ "Placemark").map(Placemark.parseVersion22)

    KmlFile(placemarks)
  }
}
