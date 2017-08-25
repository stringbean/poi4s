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

package software.purpledragon.poi4s.model

/** Representation of a route comprising a ordered sequence of [[Waypoint]]s leading to a destination.
  *
  * @param name Name of the route.
  * @param comment Comment about the route.
  * @param description Description of the route.
  * @param link Link to information about this route.
  * @param source Original source of this route.
  * @param waypoints List of waypoints that make up this route.
  * @since 0.0.3
  */
case class Route(
    name: Option[String] = None,
    comment: Option[String] = None,
    description: Option[String] = None,
    link: Option[String] = None,
    source: Option[String] = None,
    waypoints: Seq[Waypoint] = Nil) {

  /** A copy of this route with the specified name.
    *
    * @param name new name for the route.
    * @return a new route with the specified name.
    */
  def withName(name: String): Route = copy(name = Some(name))

  /** A copy of this route with the specified comment.
    *
    * @param comment new comment for the route.
    * @return a new route with the specified comment.
    */
  def withComment(comment: String): Route = copy(comment = Some(comment))

  /** A copy of this route with the specified description.
    *
    * @param description new description for the route.
    * @return a new route with the specified description.
    */
  def withDescription(description: String): Route = copy(description = Some(description))

  /** A copy of this route with the specified link to further information.
    *
    * @param link new link for the route.
    * @return a new route with the specified link.
    */
  def withLink(link: String): Route = copy(link = Some(link))

  /** A copy of this route with the specified source.
    *
    * @param source new source for the route.
    * @return a new route with the specified source.
    */
  def withSource(source: String): Route = copy(source = Some(source))

  /** A copy of this route with the specified waypoints. Any existing waypoints will be replaced.
    *
    * @param waypoints the waypoints to add to this route.
    * @return a new route with the waypoints.
    */
  def withWaypoints(waypoints: Seq[Waypoint]): Route = copy(waypoints = waypoints)

  /** A copy of this route with the waypoint appended to the list of waypoints.
    *
    * @param waypoint the waypoint to add to this route.
    * @return a new route with the appended waypoint.
    */
  def +(waypoint: Waypoint): Route = copy(waypoints = waypoints :+ waypoint)

  /** A copy of this route with the waypoints appended to the list of existing waypoints.
    *
    * @param wps the waypoints to add to this route.
    * @return a new route with the appended waypoints.
    */
  def ++(wps: Seq[Waypoint]): Route = copy(waypoints = waypoints ++ wps)
}
