package com.pds.poi4s.kml

import com.pds.poi4s.model.Waypoint

case class KmlFile(placemarks: Seq[Waypoint], name: Option[String], description: Option[String])
