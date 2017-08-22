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

package software.purpledragon.poi4s.util

import java.time.format.DateTimeFormatter
import java.time.{Instant, ZonedDateTime}

import scala.language.implicitConversions
import scala.xml.NodeSeq

private[poi4s] object XmlUtils {
  implicit def wrapNodeSeq(node: NodeSeq): NodeSeqHelpers = new NodeSeqHelpers(node)
}

private[poi4s] class NodeSeqHelpers(private val underlying: NodeSeq) {
  private val dateTimeParser = DateTimeFormatter.ISO_OFFSET_DATE_TIME

  def textOption: Option[String] = underlying.headOption.map(_.text)
  def doubleOption: Option[Double] = underlying.headOption.map(_.text.toDouble)

  def dateTimeOption: Option[ZonedDateTime] = textOption.map(ZonedDateTime.parse(_, dateTimeParser))
  def instantOption: Option[Instant] = textOption.map(ZonedDateTime.parse(_, dateTimeParser).toInstant)
}
