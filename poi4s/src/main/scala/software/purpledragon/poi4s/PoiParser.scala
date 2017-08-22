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

package software.purpledragon.poi4s

import java.io.{InputStream, OutputStream}

import software.purpledragon.poi4s.exception.{PoiParseException, PoiWriteException}
import software.purpledragon.poi4s.model.PoiFile

/** Representation of a GPS file type. Provides interfaces for reading and write files of the given type.
  *
  * @since 0.0.2
  */
trait PoiParser[V <: FileVersion] {

  /** Parses a GPS data from the supplied [[java.io.InputStream]] into a [[model.PoiFile]].
    *
    * @param is the stream to read from.
    * @throws exception.PoiParseException if an error occurs while reading the file.
    * @return the contents of the parsed GPS file.
    */
  @throws[PoiParseException]
  def parseFile(is: InputStream): PoiFile

  /** Writes GPS data to the supplied [[java.io.OutputStream]].
    *
    * @param os the stream to write to.
    * @throws exception.PoiWriteException if an error occurs while writing the file.
    */
  @throws[PoiWriteException]
  def writeFile(poiFile: PoiFile, os: OutputStream): Unit

  /** Writes GPS data to the supplied [[java.io.OutputStream]].
    *
    * @param os the stream to write to.
    * @param version Desired version of the file type to use when writing.
    * @throws exception.PoiWriteException if an error occurs while writing the file.
    */
  @throws[PoiWriteException]
  def writeFile(poiFile: PoiFile, os: OutputStream, version: V): Unit
}
