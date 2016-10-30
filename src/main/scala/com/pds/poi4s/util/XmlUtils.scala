package com.pds.poi4s.util

import scala.language.implicitConversions
import scala.xml.NodeSeq

object XmlUtils {
  implicit def wrapNodeSeq(node: NodeSeq): NodeSeqHelpers = new NodeSeqHelpers(node)
}

class NodeSeqHelpers(private val underlying: NodeSeq) {
  def textOption: Option[String] = underlying.headOption.map(_.text)
  def doubleOption: Option[Double] = underlying.headOption.map(_.text.toDouble)
}
