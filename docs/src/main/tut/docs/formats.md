---
layout: docs
title:  "Supported File Formats"
section: "docs"
---

# Supported File Formats

Format                                                    | Versions | Read | Write | Waypoints | Tracks | Routes
----------------------------------------------------------|---------:|:----:|:-----:|:---------:|:------:|:-------:
[GPS Exchange Format](#gps-exchange-format) (GPX)         | 1.0, 1.1 | ✔︎    | ✔︎    | ✔︎         | ✘      | ✘
[Keyhole Markup Language](#keyhole-markup-language) (KML) | 2.2      | ✔︎    | ✔︎    | ✔︎         | ✘      | ✘

On the TODO list:

* TomTom POI (`.asc` & `.ov2`).
* Garmin MapSource.
* CSV.

## GPS Exchange Format

* Parser: `software.purpledragon.poi4s.gpx.GpxParser`
* Artifact: `poi4s-gpx`
* Format Documentation:
  * [Official Site](http://www.topografix.com/gpx.asp)
  * [Wikipedia](https://en.wikipedia.org/wiki/GPS_Exchange_Format)

## Keyhole Markup Language

* Parser: `software.purpledragon.poi4s.kml.KmlParser`
* Artifact: `poi4s-kml`
* Format Documentation:
  * [KML Standard](http://www.opengeospatial.org/standards/kml/)
  * [Google KML Docs](https://developers.google.com/kml/documentation/)
  * [Wikipedia](https://en.wikipedia.org/wiki/Keyhole_Markup_Language)
