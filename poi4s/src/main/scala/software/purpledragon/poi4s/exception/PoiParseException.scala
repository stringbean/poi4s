package software.purpledragon.poi4s.exception

/** Indicates an error during the parsing of a GPS file.
  *
  * @since 0.0.2
  * @param message Message describing the error.
  * @param cause Cause of the error.
  */
class PoiParseException(message: String, cause: Throwable) extends PoiException(message, cause) {
  def this(message: String) = this(message, null)
}
