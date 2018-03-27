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

package org.platanios.d3.zoom

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait ZoomTransform extends js.Object {
  def x: Double = js.native
  def y: Double = js.native
  def k: Double = js.native
  def apply(point: js.Tuple2[Double, Double]): js.Tuple2[Double, Double] = js.native
  def applyX(x: Double): Double = js.native
  def applyY(y: Double): Double = js.native
  def invert(point: js.Tuple2[Double, Double]): js.Tuple2[Double, Double] = js.native
  def invertX(x: Double): Double = js.native
  def invertY(y: Double): Double = js.native
  def rescaleX[S <: ZoomScale](xScale: S): S = js.native
  def rescaleY[S <: ZoomScale](yScale: S): S = js.native
  def scale(k: Double): ZoomTransform = js.native
  def translate(x: Double, y: Double): ZoomTransform = js.native

  override def toString(): String = js.native
}
