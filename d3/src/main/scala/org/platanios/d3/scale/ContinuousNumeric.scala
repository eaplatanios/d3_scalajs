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
    extends Scale[Double, Range, Output, F] {
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

  /** Returns approximately `count` representative values from the scale’s domain. If count is not specified, it
    * defaults to `10`. The returned tick values are uniformly spaced, have human-readable values (such as multiples of
    * powers of 10), and are guaranteed to be within the extent of the domain. Ticks are often used to display reference
    * lines, or tick marks, in conjunction with the visualized data. The specified count is only a hint; the scale may
    * return more or fewer values depending on the domain.
    *
    * @param  count Hint for the number of ticks to return.
    * @return Array containing the ticks.
    */
  def ticks(count: Int = 10): js.Array[Double] = facade.ticks(count)

  /** Returns a [number format](https://github.com/d3/d3-format) function suitable for displaying a tick value,
    * automatically computing the appropriate precision based on the fixed interval between tick values. The specified
    * count should have the same value as the count that is used to generate the
    * [tick values](https://github.com/d3/d3-scale#continuous_ticks).
    *
    * An optional specifier allows a [custom format](https://github.com/d3/d3-format#locale_format) where the precision
    * of the format is automatically set by the scale as appropriate for the tick interval. For example, to format
    * percentage change, you might say:
    * {{{
    *   val x = d3.scale.linear(
    *     domain = Seq(-1, 1),
    *     range = Seq(0, 960))
    *
    *   val ticks = x.ticks(5)
    *   val tickFormat = x.tickFormat(5, "+%")
    *
    *   ticks.map(tickFormat) // ["-100%", "-50%", "+0%", "+50%", "+100%"]
    * }}}
    *
    * If `specifier` uses the format type `s`, the scale will return an
    * [SI-prefix format](https://github.com/d3/d3-format#locale_formatPrefix) based on the largest value in the domain.
    * If the specifier already specifies a precision, this method is equivalent to
    * [`locale.format()`](https://github.com/d3/d3-format#locale_format).
    *
    * @param  count     Hint for the number of ticks to return.
    * @param  specifier Format specifier to use.
    * @return Number format function suitable for displaying a tick value.
    */
  def tickFormat(count: Int = 10, specifier: String = ",f"): js.Function1[Double, String] = {
    facade.tickFormat(count, specifier)
  }
}

object ContinuousNumeric {
  @js.native private[scale] trait Facade[Range, Output, F <: Facade[Range, Output, F]]
      extends Scale.Facade[Double, Range, Output, F] {
    def domain(domain: js.Array[Double]): this.type = js.native
    def range(range: js.Array[Range]): this.type = js.native
    def rangeRound(range: js.Array[Double]): this.type = js.native
    def clamp(clamp: Boolean): this.type = js.native
    def nice(count: Int = -1): this.type = js.native

    def clamp(): Boolean = js.native
    def interpolate(): InterpolatorFactory[Range, Output] = js.native
    def invert(value: Output): Double = js.native
    def ticks(count: Int = 10): js.Array[Double] = js.native
    def tickFormat(count: Int = 10, specifier: String = ",f"): js.Function1[Double, String] = js.native
  }
}
