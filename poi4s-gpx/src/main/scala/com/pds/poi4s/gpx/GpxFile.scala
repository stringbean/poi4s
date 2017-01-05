package com.pds.poi4s.gpx

import java.time.ZonedDateTime

import com.pds.poi4s.model.Waypoint

case class GpxFile(
    version: GpxVersion,
    creator: String,
    name: Option[String],
    created: Option[ZonedDateTime],
    waypoints: Seq[Waypoint])
