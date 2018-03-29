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
import scala.scalajs.js.annotation.JSImport

/** Identity scales are a special case of linear scales where the domain and range are identical; the scale and its
  * invert method are thus the identity function. These scales are occasionally useful when working with pixel
  * coordinates, say in conjunction with an axis or a brush.
  *
  * @author Emmanouil Antonios Platanios
  */
class Identity private[scale] (
    override private[d3] val facade: Identity.Facade
) extends Scale[Double, Double, Double, Identity.Facade] {
  /** Given a value from the range, returns the corresponding value from the domain. Inversion is useful for
    * interaction, say to determine the data value corresponding to the position of the mouse. For example, to invert a
    * position encoding:
    * {{{
    *   val x = d3.scale.identity()
    *
    *   x.invert(0.4) // 0.4
    *   x.invert(0.6) // 0.6
    * }}}
    * If the given value is outside the range, and clamping is not enabled, the mapping may be extrapolated such that
    * the returned value is outside the domain. This method is only supported if the range is numeric. If the range is
    * not numeric, this method returns `NaN`.
    *
    * For a valid value `y` in the range, `identity(identity.invert(y))` approximately equals `y`. Similarly, for a
    * valid value `x` in the domain, `identity.invert(identity(x)` approximately equals `x`. The scale and its
    * inverse may not be exact due to the limitations of floating point precision.
    *
    * @param  value Value in the range to invert.
    * @return Inverted value in the domain of this scale.
    */
  def invert(value: Double): Double = facade.invert(value)

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

  override protected def copy(facade: Identity.Facade): Identity = new Identity(facade)
}

object Identity {
  @js.native private[scale] trait Facade
      extends Scale.Facade[Double, Double, Double, Facade] {
    def domain(domain: js.Array[Double]): this.type = js.native
    def range(range: js.Array[Double]): this.type = js.native
    def nice(count: Int = -1): this.type = js.native

    def invert(value: Double): Double = js.native
    def ticks(count: Int = 10): js.Array[Double] = js.native
    def tickFormat(count: Int = 10, specifier: String = ",f"): js.Function1[Double, String] = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[scale] object Facade extends js.Object {
    def scaleIdentity(): Identity.Facade = js.native
  }

  trait API {
    def identity(
        domain: Seq[Double] = Seq(0.0, 1.0),
        nice: Boolean = false,
        niceCount: Int = -1
    ): Identity = Identity(domain, nice, niceCount)
  }

  /** Constructs a new identity scale.
    *
    * @param  domain    Domain for the new scale, that also acts as its range.
    * @param  nice      If `true`, the domain will be extended so that it starts and ends on nice round values. This
    *                   method typically modifies the scale’s domain, and may only extend the bounds to the nearest
    *                   round value. An optional tick count argument. "Nicing" is useful if the domain is computed from
    *                   data, say using `extent`, and may be irregular. For example, for a domain of
    *                   `[0.201479..., 0.996679...]`, a nice domain might be `[0.2, 1.0]`. If the domain has more than
    *                   two values, "nicing" the domain only affects the first and last value.
    * @param  niceCount Optional value that allows greater control over the step size used to extend the bounds, when
    *                   `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @return New scale.
    */
  def apply(
      domain: Seq[Double] = Seq(0.0, 1.0),
      nice: Boolean = false,
      niceCount: Int = -1
  ): Identity = {
    val facade = Facade.scaleIdentity()
        .domain(js.Array(domain: _*))
        .range(js.Array(domain: _*))
    if (nice && niceCount > 0)
      facade.nice(niceCount)
    else if (nice)
      facade.nice()
    new Identity(facade)
  }
}
