package com.pds.poi4s.model

case class Waypoint(
    lat: Double,
    lon: Double,
    elevation: Option[Double],
    name: Option[String],
    comment: Option[String],
    description: Option[String],
    link: Option[String],
    source: Option[String])
