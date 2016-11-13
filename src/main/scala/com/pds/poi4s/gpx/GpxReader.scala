package com.pds.poi4s.gpx

import java.io.InputStream

import com.pds.poi4s.util.XmlUtils._

import scala.xml.XML

object GpxReader {
  def read(is: InputStream): GpxFile = {
    val xml = XML.load(is)

    val name = (xml \ "metadata" \ "name").textOption
    val created = (xml \ "metadata" \ "time").dateTimeOption

    val wayPoints = (xml \\ "wpt").map(WayPoint.apply)

    GpxFile(name, created, wayPoints)
  }
}
