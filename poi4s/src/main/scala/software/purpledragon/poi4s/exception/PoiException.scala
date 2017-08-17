package software.purpledragon.poi4s.exception

/** Base exception for errors thrown by poi4s.
  *
  * @since 0.0.2
  * @param message Message describing the error.
  * @param cause Cause of the error.
  */
class PoiException(message: String, cause: Throwable) extends Exception(message, cause) {
  def this(message: String) = this(message, null)
}
