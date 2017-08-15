package com.pds.poi4s.kml

import java.io.OutputStream

import scala.xml.{Elem, PrettyPrinter}

object KmlWriter {
  private val prettyPrinter = new PrettyPrinter(80, 4)

  def write(kmlFile: KmlFile, os: OutputStream): Unit = {
    val xml = generateKml(kmlFile)
    os.write(prettyPrinter.format(xml).getBytes("UTF-8"))
  }

  private def generateKml(kmlFile: KmlFile): Elem = {
    <kml xmlns="http://www.opengis.net/kml/2.2">
      <Document>
        {kmlFile.name.map(n => <name>{n}</name>).orNull}
        {kmlFile.description.map(d => <description>{d}</description>).orNull}
        {
          kmlFile.placemarks map { placemark =>
            <Placemark>
              {placemark.name.map(n => <name>{n}</name>).orNull}
              {placemark.description.map(p => <description>{p}</description>).orNull}
              <Point>
                <coordinates>
                  {
                    placemark.elevation match {
                      case Some(e) =>
                        s"${placemark.lon},${placemark.lat},$e"
                      case None =>
                        s"${placemark.lon},${placemark.lat}"
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
