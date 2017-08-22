package software.purpledragon.poi4s.kml

import enumeratum.EnumEntry
import software.purpledragon.poi4s.FileVersion

/** Representation of a supported version of KML files. See the companion object for possible values.
  */
sealed abstract class KmlVersion(override val entryName: String) extends EnumEntry with FileVersion

/** Supported versions of KML files.
  */
object KmlVersion {

  /** KML Version 2.2 ''(default version)''.
    *
    * This is the default version used for KML files.
    */
  case object Version22 extends KmlVersion("2.2")
}
