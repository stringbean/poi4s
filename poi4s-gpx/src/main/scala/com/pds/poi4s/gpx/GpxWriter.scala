package com.pds.poi4s.gpx

import java.io.OutputStream

import com.pds.poi4s.gpx.GpxVersion._
import com.pds.poi4s.model.PoiFile

import scala.xml.{Elem, PrettyPrinter}

object GpxWriter {
  private val prettyPrinter = new PrettyPrinter(80, 4)

  def write(poiFile: PoiFile, os: OutputStream, version: GpxVersion = Version11): Unit = {
    val xml = version match {
      case Version10 => generateVersion10(poiFile)
      case Version11 => generateVersion11(poiFile)
    }

    os.write(prettyPrinter.format(xml).getBytes("UTF-8"))
  }

  private def generateVersion10(poiFile: PoiFile): Elem = {
    <gpx version="1.0" creator="poi4s">
      {poiFile.name.map(n => <name>{n}</name>).orNull}

      {
        poiFile.waypoints map { wpt =>
          <wpt lat={wpt.lat.toString} lon={wpt.lon.toString}>
            {wpt.name.map(n => <name>{n}</name>).orNull}
            {wpt.elevation.map(e => <ele>{e}</ele>).orNull}
            {wpt.comment.map(c => <cmt>{c}</cmt>).orNull}
            {wpt.description.map(d => <desc>{d}</desc>).orNull}
            {wpt.link.map(l => <url>{l}</url>).orNull}
            {wpt.source.map(s => <src>{s}</src>).orNull}
          </wpt>
        }
      }
    </gpx>
  }

  private def generateVersion11(poiFile: PoiFile): Elem = {
    <gpx version="1.1" creator="poi4s">
      <metadata>
        {poiFile.name.map(n => <name>{n}</name>).orNull}
      </metadata>

      {
      poiFile.waypoints map { wpt =>
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