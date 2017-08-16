package software.purpledragon.poi4s.kml

class KmlParseException(message: String, cause: Throwable) extends Exception(message, cause) {
  def this(message: String) = this(message, null)
}
