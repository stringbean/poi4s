package software.purpledragon.poi4s.model

/** Representation of a waypoint on a GPS track.
  *
  * @since 0.0.1
  * @param lat Latitude of this waypoint in decimal degrees.
  * @param lon Logitude of this waypoint in decimal degrees.
  * @param elevation Elevation of this waypoint in metres.
  * @param name Name of this waypoint.
  * @param comment Comment about this waypoint.
  * @param description Description of this waypoint.
  * @param link Link to information about this waypoint.
  * @param source Original source of this waypoint.
  */
case class Waypoint(
    lat: Double,
    lon: Double,
    elevation: Option[Double] = None,
    name: Option[String] = None,
    comment: Option[String] = None,
    description: Option[String] = None,
    link: Option[String] = None,
    source: Option[String] = None) {

  /** A copy of this waypoint with the specified elevation.
    *
    * @param elevation new elevation for the waypoint (in metres).
    * @return a new waypoint with the specified elevation.
    */
  def withElevation(elevation: Double): Waypoint = copy(elevation = Some(elevation))

  /** A copy of this waypoint with the specified name.
    *
    * @param name new name for the waypoint.
    * @return a new waypoint with the specified name.
    */
  def withName(name: String): Waypoint = copy(name = Some(name))

  /** A copy of this waypoint with the specified comment.
    *
    * @param comment new comment for the waypoint.
    * @return a new waypoint with the specified comment.
    */
  def withComment(comment: String): Waypoint = copy(comment = Some(comment))

  /** A copy of this waypoint with the specified description.
    *
    * @param description new description for the waypoint.
    * @return a new waypoint with the specified description.
    */
  def withDescription(description: String): Waypoint = copy(description = Some(description))

  /** A copy of this waypoint with the specified link to further information.
    *
    * @param link new link for the waypoint.
    * @return a new waypoint with the specified link.
    */
  def withLink(link: String): Waypoint = copy(link = Some(link))

  /** A copy of this waypoint with the specified source.
    *
    * @param source new source for the waypoint.
    * @return a new waypoint with the specified source.
    */
  def withSource(source: String): Waypoint = copy(source = Some(source))
}
