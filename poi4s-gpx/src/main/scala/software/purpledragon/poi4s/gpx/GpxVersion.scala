/*
 * Copyright 2017 Michael Stringer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
