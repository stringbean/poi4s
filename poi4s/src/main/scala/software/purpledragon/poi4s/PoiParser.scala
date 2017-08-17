package software.purpledragon.poi4s

import java.io.{InputStream, OutputStream}

import software.purpledragon.poi4s.exception.{PoiParseException, PoiWriteException}
import software.purpledragon.poi4s.model.{FileVersion, PoiFile}

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
  def writeFile(os: OutputStream): Unit

  /** Writes GPS data to the supplied [[java.io.OutputStream]].
    *
    * @param os the stream to write to.
    * @param version Desired version of the file type to use when writing.
    * @throws exception.PoiWriteException if an error occurs while writing the file.
    */
  @throws[PoiWriteException]
  def writeFile(os: OutputStream, version: V): Unit
}
