package com.pds.poi4s.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import scala.language.implicitConversions
import scala.xml.NodeSeq

object XmlUtils {
  implicit def wrapNodeSeq(node: NodeSeq): NodeSeqHelpers = new NodeSeqHelpers(node)
}

class NodeSeqHelpers(private val underlying: NodeSeq) {
  private val dateTimeParser = DateTimeFormatter.ISO_OFFSET_DATE_TIME

  def textOption: Option[String] = underlying.headOption.map(_.text)
  def doubleOption: Option[Double] = underlying.headOption.map(_.text.toDouble)

  def dateTimeOption: Option[ZonedDateTime] = textOption.map(ZonedDateTime.parse(_, dateTimeParser))
}
