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

package org.platanios.d3.scale

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait ScaleLogarithmic[Range, Output] extends ScaleContinuousNumeric[Range, Output] {
  override def domain(): js.Array[Double] = js.native
  override def domain(domain: js.Array[Double | js.Any]): this.type = js.native
  def interpolate(): InterpolatorFactory[js.Any, js.Any] = js.native
  def interpolate[NO](interpolate: InterpolatorFactory[Range, NO]): ScaleLogarithmic[Range, NO] = js.native
  override def ticks(count: Double = ???): js.Array[Double] = js.native
  override def tickFormat(count: Double = ???, specifier: String = ???): js.Function1[Double | js.Any, String] = js.native
  def nice(): this.type = js.native
  def base(): Double = js.native
  def base(base: Double): this.type = js.native
}
