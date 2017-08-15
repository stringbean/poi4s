package com.pds.poi4s.model

case class Waypoint(
    lat: Double,
    lon: Double,
    elevation: Option[Double] = None,
    name: Option[String] = None,
    comment: Option[String] = None,
    description: Option[String] = None,
    link: Option[String] = None,
    source: Option[String] = None) {

  def withElevation(elevation: Double): Waypoint = copy(elevation = Some(elevation))
  def withName(name: String): Waypoint = copy(name = Some(name))
  def withComment(comment: String): Waypoint = copy(comment = Some(comment))
  def withDescription(description: String): Waypoint = copy(description = Some(description))
  def withLink(link: String): Waypoint = copy(link = Some(link))
  def withSource(source: String): Waypoint = copy(source = Some(source))
}
