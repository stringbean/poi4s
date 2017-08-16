package software.purpledragon.poi4s.gpx

import java.io.InputStream

import software.purpledragon.poi4s.model.PoiFile
import software.purpledragon.poi4s.util.XmlUtils._

import scala.xml.{Elem, SAXException, XML}

object GpxReader {
  @throws[GpxParseException]
  def read(is: InputStream): PoiFile = {
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

  private def parseVersion10(xml: Elem): PoiFile = {
    val creator = xml \@ "creator"
    val name = (xml \ "name").textOption
    val created = (xml \ "time").instantOption

    val waypoints = (xml \\ "wpt").map(GpxWaypoint.parseVersion10)

    PoiFile(
      name = name,
      creator = Some(creator),
      createdAt = created,
      version = Some("1.0"),
      waypoints = waypoints
    )
  }

  private def parseVersion11(xml: Elem): PoiFile = {
    val creator = xml \@ "creator"
    val name = (xml \ "metadata" \ "name").textOption
    val created = (xml \ "metadata" \ "time").instantOption

    val waypoints = (xml \\ "wpt").map(GpxWaypoint.parseVersion11)

    PoiFile(
      name = name,
      creator = Some(creator),
      createdAt = created,
      version = Some("1.1"),
      waypoints = waypoints
    )
  }
}
