package com.pds.poi4s.kml

import java.io.InputStream
import java.nio.charset.StandardCharsets

import org.apache.commons.io.IOUtils
import org.scalatest.{FlatSpec, Matchers}

class KmlReaderSpec extends FlatSpec with Matchers {

  "KmlReader" should "parse valid file" in {
    val parsed = parseAndCheckFile(getClass.getResourceAsStream("/observatories.kml"))
  }

  it should "reject a KML file without namespace" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(getClass.getResourceAsStream("/missing-namespace.kml"))
    }

    e.getMessage shouldBe "Missing KML namespace"
  }

  it should "reject an unsupported version KML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(getClass.getResourceAsStream("/unsupported-version.kml"))
    }

    e.getMessage shouldBe "Unsupported KML version 20.0"
  }

  it should "reject non-XML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(IOUtils.toInputStream("Not XML", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject broken XML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(IOUtils.toInputStream("<kml>incorrect termination</foo>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  it should "reject non-KML XML file" in {
    val e = the[KmlParseException] thrownBy {
      KmlReader.read(IOUtils.toInputStream("<wrong>xml</wrong>", StandardCharsets.UTF_8))
    }

    e.getMessage shouldBe "Invalid KML file"
  }

  private def parseAndCheckFile(is: InputStream): KmlFile = {
    val parsed = KmlReader.read(is)

    parsed.placemarks shouldBe Seq(
      Placemark(51.4778,
        -0.0014,
        Some(46.0)),
      Placemark(53.23625,
        -2.307139,
        None)
    )

    parsed
  }
}
