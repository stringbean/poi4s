name := "poi4s"

// don't want to have a hard dependency on scala-xml
libraryDependencies ++= scalaXml.map(d => d.copy(configurations = Some("provided")))