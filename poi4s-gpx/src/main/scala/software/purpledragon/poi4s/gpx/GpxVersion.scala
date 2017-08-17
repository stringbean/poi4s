package software.purpledragon.poi4s.gpx

import enumeratum.EnumEntry
import software.purpledragon.poi4s.model.FileVersion

sealed abstract class GpxVersion(override val entryName: String) extends EnumEntry with FileVersion

object GpxVersion {
  case object Version10 extends GpxVersion("1.0")
  case object Version11 extends GpxVersion("1.1")
}
