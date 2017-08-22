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
