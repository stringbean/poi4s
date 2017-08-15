package com.pds.poi4s.model

import java.time.Instant

// TODO include meta about origin file
case class PoiFile(
    name: Option[String] = None,
    description: Option[String] = None,
    creator: Option[String] = None,
    version: Option[String] = None,
    createdAt: Option[Instant] = None,
    waypoints: Seq[Waypoint] = Nil) {

  def withName(name: String): PoiFile = copy(name = Some(name))
  def withDescription(description: String): PoiFile = copy(description = Some(description))
  def withCreator(creator: String): PoiFile = copy(creator = Some(creator))
  def withCreatedAt(created: Instant): PoiFile = copy(createdAt = Some(created))
  def withWaypoints(waypoints: Seq[Waypoint]): PoiFile = copy(waypoints = waypoints)

  def +(waypoint: Waypoint): PoiFile = copy(waypoints = waypoints :+ waypoint)
}
