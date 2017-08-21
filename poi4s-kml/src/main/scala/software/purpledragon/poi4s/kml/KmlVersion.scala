package software.purpledragon.poi4s.kml

import enumeratum.EnumEntry
import software.purpledragon.poi4s.model.FileVersion

sealed abstract class KmlVersion(override val entryName: String) extends EnumEntry with FileVersion

object KmlVersion {
  case object Version22 extends KmlVersion("2.2")
}
