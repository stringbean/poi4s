package com.pds.poi4s.kml

import java.io.OutputStream

import com.pds.poi4s.model.PoiFile

import scala.xml.{Elem, PrettyPrinter}

object KmlWriter {
  private val prettyPrinter = new PrettyPrinter(80, 4)

  def write(poiFile: PoiFile, os: OutputStream): Unit = {
    val xml = generateKml(poiFile)
    os.write(prettyPrinter.format(xml).getBytes("UTF-8"))
  }

  private def generateKml(kmlFile: PoiFile): Elem = {
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
