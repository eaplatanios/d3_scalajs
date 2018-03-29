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

import org.platanios.d3.interpolate.InterpolatorFactory

import scala.scalajs.js

/** Continuous scales map a continuous, quantitative input domain to a continuous output range. If the range is also
  * numeric, the mapping may be inverted. A continuous scale is not constructed directly; instead, try a linear, power,
  * log, identity, time or sequential color scale.
  *
  * Given a value from the domain, this scale returns the corresponding value from the range. If the given value is
  * outside the domain, and clamping is not enabled, the mapping may be extrapolated such that the returned value is
  * outside the range. For example, to apply a position encoding:
  * {{{
  *   val x = d3.scale.linear(
  *     domain = Seq(10.0, 130.0),
  *     range = Seq(0, 960))
  *
  *   x(20) // 80
  *   x(50) // 320
  * }}}
  * Or to apply a color encoding:
  * {{{
  *   val x = d3.scale.linear(
  *     domain = Seq(10.0, 100.0),
  *     range = Seq("brown", "steelblue"))
  *
  *   color(20) // "#9a3439"
  *   color(50) // "#7b5167"
  * }}}
  *
  * @author Emmanouil Antonios Platanios
  */
trait ContinuousNumeric[Range, Output, F <: ContinuousNumeric.Facade[Range, Output, F]]
    extends TickScale[Double, Range, Output, Int, F] {
  /** Returns a boolean indicating whether clamping is enabled for this scale. */
  def clamped(): Boolean = facade.clamp()

  /** Returns the interpolator used by this scale. */
  def interpolator(): InterpolatorFactory[Range, Output] = facade.interpolate()

  /** Given a value from the range, returns the corresponding value from the domain. Inversion is useful for
    * interaction, say to determine the data value corresponding to the position of the mouse. For example, to invert a
    * position encoding:
    * {{{
    *   val x = d3.scale.linear(
    *     domain = Seq(10.0, 130.0),
    *     range = Seq(0, 960))
    *
    *   x.invert(80)  // 20
    *   x.invert(320) // 50
    * }}}
    * If the given value is outside the range, and clamping is not enabled, the mapping may be extrapolated such that
    * the returned value is outside the domain. This method is only supported if the range is numeric. If the range is
    * not numeric, this method returns `NaN`.
    *
    * For a valid value `y` in the range, `continuous(continuous.invert(y))` approximately equals `y`. Similarly, for a
    * valid value `x` in the domain, `continuous.invert(continuous(x)` approximately equals `x`. The scale and its
    * inverse may not be exact due to the limitations of floating point precision.
    *
    * @param  value Value in the range to invert.
    * @return Inverted value in the domain of this scale.
    */
  def invert(value: Output): Double = facade.invert(value)
}

object ContinuousNumeric {
  @js.native private[d3] trait Facade[Range, Output, F <: Facade[Range, Output, F]]
      extends TickScale.Facade[Double, Range, Output, Int, F] {
    def domain(domain: js.Array[Double]): this.type = js.native
    def range(range: js.Array[Range]): this.type = js.native
    def rangeRound(range: js.Array[Double]): this.type = js.native
    def clamp(clamp: Boolean): this.type = js.native
    def nice(count: Int = -1): this.type = js.native

    def clamp(): Boolean = js.native
    def interpolate(): InterpolatorFactory[Range, Output] = js.native
    def invert(value: Output): Double = js.native
  }
}
