package com.pds.poi4s.gpx

import java.io.OutputStream

import com.pds.poi4s.gpx.GpxVersion._

import scala.xml.{Elem, PrettyPrinter}

object GpxWriter {
  private val prettyPrinter = new PrettyPrinter(80, 4)

  def write(gpxFile: GpxFile, os: OutputStream, version: GpxVersion = Version11): Unit = {
    val xml = version match {
      case Version10 => ???
      case Version11 => generateVersion11(gpxFile)
    }

    os.write(prettyPrinter.format(xml).getBytes("UTF-8"))
  }

  private def generateVersion11(gpxFile: GpxFile): Elem = {
    <gpx version="1.1" creator="poi4s">
      <metadata>
        {gpxFile.name.map(n => <name>{n}</name>).orNull}
      </metadata>

      {
        gpxFile.waypoints map { wpt =>
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
