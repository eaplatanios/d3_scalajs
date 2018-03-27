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

package org.platanios.d3

import scalajs.js
import scalajs.js.`|`
import scala.scalajs.js.annotation._

@JSImport("d3-polygon", JSImport.Namespace)
@js.native
object D3Polygon extends js.Object {
  type Point = js.Tuple2[Double, Double]
  type Polygon = js.Array[Point]

  def polygonArea(polygon: Polygon): Double = js.native
  def polygonCentroid(polygon: Polygon): Point = js.native
  def polygonHull(points: Polygon): Polygon | Null = js.native
  def polygonContains(polygon: Polygon, point: Point): Boolean = js.native
  def polygonLength(polygon: Polygon): Boolean = js.native
}
