package software.purpledragon.poi4s.kml

import java.io.{InputStream, OutputStream}

import software.purpledragon.poi4s.PoiParser
import software.purpledragon.poi4s.exception.PoiParseException
import software.purpledragon.poi4s.model.PoiFile

import scala.xml.{Elem, PrettyPrinter, SAXException, XML}
import software.purpledragon.poi4s.util.XmlUtils._

import KmlVersion._

class KmlParser extends PoiParser[KmlVersion] {
  private val KmlNamespace = "http://www.opengis.net/kml/([0-9]+.[0-9]+)".r
  private val prettyPrinter = new PrettyPrinter(80, 4)

  @throws[PoiParseException]
  override def parseFile(is: InputStream): PoiFile = {
    try {
      val xml = XML.load(is)

      if (xml.label != "kml") {
        throw new PoiParseException("Invalid KML file")
      }

      xml.namespace match {
        case KmlNamespace("2.2") =>
          parseVersion22(xml)

        case KmlNamespace(v) =>
          throw new PoiParseException(s"Unsupported KML version $v")

        case _ =>
          throw new PoiParseException("Missing KML namespace")
      }

    } catch {
      case se: SAXException =>
        throw new PoiParseException("Invalid KML file", se)
    }
  }

  private def parseVersion22(xml: Elem): PoiFile = {
    val name = (xml \ "Document" \ "name").headOption.map(_.text)
    val description = (xml \ "Document" \ "description").textOption

    val placemarks = (xml \\ "Placemark").map(Placemark.parseVersion22)

    PoiFile(
      name = name,
      description = description,
      waypoints = placemarks,
      version = Some(KmlVersion.Version22)
    )
  }

  override def writeFile(poiFile: PoiFile, os: OutputStream): Unit = writeFile(poiFile, os, Version22)

  override def writeFile(poiFile: PoiFile, os: OutputStream, version: KmlVersion): Unit = {
    val xml = version match {
      case Version22 => generateVersion22(poiFile)
    }

    os.write(prettyPrinter.format(xml).getBytes("UTF-8"))
  }

  private def generateVersion22(kmlFile: PoiFile): Elem = {
    <kml xmlns="http://www.opengis.net/kml/2.2">
      <Document>
        {kmlFile.name.map(n => <name>{n}</name>).orNull}
        {kmlFile.description.map(d => <description>{d}</description>).orNull}
        {
        kmlFile.waypoints map { waypoint =>
          <Placemark>
            {waypoint.name.map(n => <name>{n}</name>).orNull}
            {waypoint.description.map(p => <description>{p}</description>).orNull}
            <Point>
              <coordinates>
                {
                waypoint.elevation match {
                  case Some(e) =>
                    s"${waypoint.lon},${waypoint.lat},$e"
                  case None =>
                    s"${waypoint.lon},${waypoint.lat}"
                }
                }
              </coordinates>
            </Point>
          </Placemark>
        }
        }
      </Document>
    </kml>
  }
}
