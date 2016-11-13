package com.pds.poi4s.gpx

class GpxParseException(message: String, cause: Throwable) extends Exception(message, cause) {
  def this(message: String) = this(message, null)
}
