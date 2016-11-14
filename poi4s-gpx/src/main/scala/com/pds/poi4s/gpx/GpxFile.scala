package com.pds.poi4s.gpx

import java.time.ZonedDateTime

case class GpxFile(version: GpxVersion,
                   creator: String,
                   name: Option[String],
                   created: Option[ZonedDateTime],
                   waypoints: Seq[WayPoint])
