/* Copyright 2017-18, Emmanouil Antonios Platanios. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.platanios.d3.shape

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** This module provides a few basic geometric operations for two-dimensional polygons. Each polygon is represented as
  * an array of two-element arrays `[​[x1, y1], [x2, y2], ...]`, and may either be closed (wherein the first and last
  * point are the same) or open (wherein they are not). Typically polygons are in counterclockwise order, assuming a
  * coordinate system where the origin `(0, 0)` is in the top-left corner.
  *
  * @author Emmanouil Antonios Platanios
  */
object Polygon {
  type Point = (Double, Double)
  type Polygon = Seq[Point]

  /** Returns the signed area of the specified polygon. If the vertices of the polygon are in counterclockwise order
    * (assuming a coordinate system where the origin `(0, 0)` is in the top-left corner), the returned area is positive.
    * Otherwise, it is negative, or zero.
    *
    * @param  polygon Polygon vertices.
    * @return Polygon area.
    */
  def area(polygon: Polygon): Double = {
    Facade.polygonArea(js.Array(polygon.map(p => p: js.Tuple2[Double, Double]): _*))
  }

  /** Returns the [centroid](https://en.wikipedia.org/wiki/Centroid) of the specified polygon.
    *
    * @param  polygon Polygon vertices.
    * @return Polygon centroid.
    */
  def centroid(polygon: Polygon): Point = {
    Facade.polygonCentroid(js.Array(polygon.map(p => p: js.Tuple2[Double, Double]): _*))
  }

  /** Returns the [convex hull](https://en.wikipedia.org/wiki/Convex_hull) of the specified points using
    * [Andrew's monotone chain](http://en.wikibooks.org/wiki/Algorithm_Implementation/Geometry/Convex_hull/Monotone_chain)
    * algorithm. The returned hull is represented as an array containing a subset of the input points arranged in
    * counterclockwise order. Returns `null` if `polygon` has fewer than three elements.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-polygon/master/img/hull.png" width="250" height="250" style="max-width:100%;"/>
    *
    * @param  polygon Polygon vertices.
    * @return Polygon hull.
    */
  def hull(polygon: Polygon): Polygon = {
    Facade.polygonHull(js.Array(polygon.map(p => p: js.Tuple2[Double, Double]): _*)).map(p => (p._1, p._2))
  }

  /** Returns `true` if and only if the specified point is
    * [inside the specified polygon](https://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html).
    *
    * @param  polygon Polygon vertices.
    * @param  point   Point to check if inside the provided polygon.
    * @return `true` if the provided polygon contains `point`.
    */
  def contains(polygon: Polygon, point: Point): Boolean = {
    Facade.polygonContains(js.Array(polygon.map(p => p: js.Tuple2[Double, Double]): _*), point)
  }

  /** Returns the length of the perimeter of the specified polygon.
    *
    * @param  polygon Polygon vertices.
    * @return Polygon perimeter.
    */
  def perimeter(polygon: Polygon): Double = {
    Facade.polygonLength(js.Array(polygon.map(p => p: js.Tuple2[Double, Double]): _*))
  }

  @JSImport("d3-polygon", JSImport.Namespace)
  @js.native private[Polygon] object Facade extends js.Object {
    def polygonArea(polygon: js.Array[js.Tuple2[Double, Double]]): Double = js.native
    def polygonCentroid(polygon: js.Array[js.Tuple2[Double, Double]]): js.Tuple2[Double, Double] = js.native
    def polygonHull(points: js.Array[js.Tuple2[Double, Double]]): js.Array[js.Tuple2[Double, Double]] = js.native
    def polygonContains(polygon: js.Array[js.Tuple2[Double, Double]], point: js.Tuple2[Double, Double]): Boolean = js.native
    def polygonLength(polygon: js.Array[js.Tuple2[Double, Double]]): Double = js.native
  }
}
