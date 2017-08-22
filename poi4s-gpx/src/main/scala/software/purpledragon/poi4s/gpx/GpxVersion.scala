package software.purpledragon.poi4s.gpx

import enumeratum.EnumEntry
import software.purpledragon.poi4s.FileVersion

/** Representation of a supported version of GPX files. See the companion object for possible values.
  */
sealed abstract class GpxVersion(override val entryName: String) extends EnumEntry with FileVersion

/** Supported versions of GPX files.
  */
object GpxVersion {
  /** GPX Version 1.0 */
  case object Version10 extends GpxVersion("1.0")

  /** GPX Version 1.1 ''(default version)''.
    *
    * This is the default version used for GPX files.
    */
  case object Version11 extends GpxVersion("1.1")
}
