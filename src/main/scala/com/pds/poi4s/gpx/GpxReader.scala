package com.pds.poi4s.gpx

import java.io.InputStream
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

import com.pds.poi4s.util.XmlUtils._

import scala.xml.{InputSource, XML}

object GpxReader {
  private val saxParserFactory = {
    val schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
    val schema = schemaFactory.newSchema(new StreamSource(getClass.getResourceAsStream("/gpx.xsd")))

    val saxParserFactory = SAXParserFactory.newInstance()
    saxParserFactory.setValidating(true)
    saxParserFactory.setSchema(schema)

    saxParserFactory
  }

  def read(is: InputStream): GpxFile = {
    val xml = XML.loadXML(new InputSource(is), saxParserFactory.newSAXParser())

    val name = (xml \ "name").textOption
    val wayPoints = (xml \\ "wpt").map(WayPoint.apply)

    GpxFile(name, wayPoints)
  }
}
