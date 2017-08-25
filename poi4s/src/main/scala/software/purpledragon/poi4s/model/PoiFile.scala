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

import java.time.Instant

import software.purpledragon.poi4s.FileVersion

/**
  * Representation of a file containing GPS data, such as [[Waypoint]]s or [[Route]]s.
  *
  * @since 0.0.1
  * @param name Name of the file.
  * @param description Description of the file.
  * @param creator Software or device that created the file.
  * @param version Version of the file?
  * @param createdAt Timestamp of when the file was created.
  * @param waypoints List of waypoints.
  * @param routes List of routes to destinations.
  */
case class PoiFile(
    name: Option[String] = None,
    description: Option[String] = None,
    creator: Option[String] = None,
    version: Option[FileVersion] = None,
    createdAt: Option[Instant] = None,
    waypoints: Seq[Waypoint] = Nil,
    routes: Seq[Route] = Nil) {

  // TODO include meta about origin file

  /** A copy of this file with the specified name.
    *
    * @param name new name for the file.
    * @return a new file with the specified name.
    */
  def withName(name: String): PoiFile = copy(name = Some(name))

  /** A copy of this file with the specified description.
    *
    * @param description new description for the file.
    * @return a new file with the specified description.
    */
  def withDescription(description: String): PoiFile = copy(description = Some(description))

  /** A copy of this file with the specified creator.
    *
    * @param creator new creator for the file.
    * @return a new file with the specified creator.
    */
  def withCreator(creator: String): PoiFile = copy(creator = Some(creator))

  /** A copy of this file with the specified creation timestamp.
    *
    * @param created new timestamp for the file.
    * @return a new file with the specified creation timestamp.
    */
  def withCreatedAt(created: Instant): PoiFile = copy(createdAt = Some(created))

  /** A copy of this file with the specified waypoints. Any existing waypoints will be replaced.
    *
    * @param waypoints the waypoints to add to this file.
    * @return a new file with the waypoints.
    */
  def withWaypoints(waypoints: Seq[Waypoint]): PoiFile = copy(waypoints = waypoints)

  /** A copy of this file with the waypoint appended to the list of waypoints.
    *
    * @param waypoint the waypoint to add to this file.
    * @return a new file with the appended waypoint.
    */
  def +(waypoint: Waypoint): PoiFile = copy(waypoints = waypoints :+ waypoint)

  /** A copy of this file with the waypoints appended to the list of existing waypoints.
    *
    * @param wps the waypoints to add to this file.
    * @return a new file with the appended waypoints.
    */
  def ++(wps: Seq[Waypoint]): PoiFile = copy(waypoints = waypoints ++ wps)

  /** A copy of this file with the specified routes. Any existing routes will be replaced.
    *
    * @param routes the routes to add to this file.
    * @return a new file with the routes.
    */
  def withRoutes(routes: Seq[Route]): PoiFile = copy(routes = routes)

  /** A copy of this file with the route appended to the list of routes.
    *
    * @param route the route to add to this file.
    * @return a new file with the appended route.
    */
  def +(route: Route): PoiFile = copy(routes = routes :+ route)
}
