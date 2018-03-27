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

import org.platanios.d3.time.{CountableTimeInterval, TimeInterval}

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native
trait ScaleTime[Range, Output] extends Scale[js.Date, Range, Output] {
  def invert(value: Double | js.Any): js.Date = js.native
  def domain(domain: js.Array[js.Date | Double | js.Any]): this.type = js.native
  def range(range: js.Array[Range]): this.type = js.native
  def rangeRound(range: js.Array[Double | js.Any]): this.type = js.native
  def clamp(): Boolean = js.native
  def clamp(clamp: Boolean): this.type = js.native
  def interpolate(): InterpolatorFactory[js.Any, js.Any] = js.native
  def interpolate[NO](interpolate: InterpolatorFactory[Range, NO]): ScaleTime[Range, NO] = js.native
  def ticks(): js.Array[js.Date] = js.native
  def ticks(count: Double): js.Array[js.Date] = js.native
  def ticks(interval: TimeInterval): js.Array[js.Date] = js.native
  def tickFormat(): js.Function1[js.Date, String] = js.native
  def tickFormat(count: Double): js.Function1[js.Date, String] = js.native
  def tickFormat(count: Double, specifier: String): js.Function1[js.Date, String] = js.native
  def tickFormat(interval: TimeInterval): js.Function1[js.Date, String] = js.native
  def tickFormat(interval: TimeInterval, specifier: String): js.Function1[js.Date, String] = js.native
  def nice(): this.type = js.native
  def nice(count: Double): this.type = js.native
  def nice(interval: CountableTimeInterval, step: Double = ???): this.type = js.native
}
