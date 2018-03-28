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

/** Logarithmic scales are similar to linear scales, except that a logarithmic transform is applied to the input domain
  * value before the output range value is computed. The mapping to the range value `y` can be expressed as a function
  * of the domain value `x`: `y = m log(x) + b`.
  *
  * As `log(0) = -∞`, a log scale domain must be strictly-positive or strictly-negative; the domain must not include or
  * cross zero. A log scale with a positive domain has a well-defined behavior for positive values, and a log scale with
  * a negative domain has a well-defined behavior for negative values (for a negative domain, input and output values
  * are implicitly multiplied by `-1`). The behavior of the scale is undefined if you pass a negative value to a log
  * scale with a positive domain or vice versa.
  *
  * '''NOTE:''' `Logarithmic.ticks()` produces ticks customized for a log scale. If the base is an integer, the returned
  * ticks are uniformly spaced within each integer power of `base`; otherwise, one tick per power of `base` is returned.
  * The returned ticks are guaranteed to be within the extent of the domain. If the number of orders of magnitude in the
  * domain is greater than `count`, then at most one tick per power is returned. Otherwise, the tick values are
  * unfiltered. However, note that you can use `Logarithmic.tickFormat()` to filter the display of tick labels.
  *
  * '''NOTE:''' `Logarithmic.nice()` extends the domain to integer powers of `base`. For example, for a domain of
  * `[0.201479..., 0.996679...]`, and base `10`, the nice domain is `[0.1, 1]`. If the domain has more than two values,
  * "nicing" the domain only affects its first and last value.
  *
  * @author Emmanouil Antonios Platanios
  */
@js.native trait Logarithmic[Range, Output] extends ContinuousNumeric[Range, Output] {
  /** Sets the interpolator of this scale. */
  private[scale] def interpolate[IO](interpolate: InterpolatorFactory[Range, IO]): Logarithmic[Range, IO] = js.native

  /** Sets the logarithm base of this scale. */
  private[scale] def base(base: Double): this.type = js.native

  /** Returns the logarithm base of this scale. */
  def base(): Double = js.native
}

object Logarithmic {
  trait API {
    def log[Range](
        domain: Seq[Double] = Seq(1.0, 10.0),
        range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
        base: Double = 10.0,
        clamped: Boolean = false,
        nice: Boolean = false,
        niceCount: Int = -1
    ): Logarithmic[Range, Range] = Logarithmic(domain, range, base, clamped, nice, niceCount)

    def logInterpolated[Range, Output](
        domain: Seq[Double],
        range: Seq[Range],
        base: Double = 10.0,
        clamped: Boolean = false,
        interpolator: InterpolatorFactory[Range, Output],
        nice: Boolean = false,
        niceCount: Int = -1
    ): Logarithmic[Range, Output] = Logarithmic.interpolated(domain, range, base, clamped, interpolator, nice, niceCount)

    def logRounded(
        domain: Seq[Double] = Seq(1.0, 10.0),
        range: Seq[Double] = Seq(0.0, 1.0),
        base: Double = 10.0,
        clamped: Boolean = false,
        nice: Boolean = false,
        niceCount: Int = -1
    ): Logarithmic[Double, Double] = Logarithmic.rounded(domain, range, base, clamped, nice, niceCount)
  }

  /** Constructs a new logarithmic scale.
    *
    * @param  domain    Domain for the new scale.
    * @param  range     Range for the new scale.
    * @param  base      Logarithm base for the new scale.
    * @param  clamped   Boolean value that enables or disables clamping. If clamping is disabled and the scale is passed
    *                   a value outside its domain, the scale may return a value outside the range through
    *                   extrapolation. If clamping is enabled, the return value of the scale is always within the
    *                   scale's range. Clamping similarly applies to the `invert` method of the scale. For example:
    *                   {{{
    *                     val x = d3.scale.linear(
    *                       domain = Seq(10.0, 130.0),
    *                       range = Seq(0, 960))
    *
    *                     x(-10)         // -160, which is outside the range
    *                     x.invert(-160) // -10, which is outside the domain
    *
    *                     val x = d3.scale.linear(
    *                       domain = Seq(10.0, 130.0),
    *                       range = Seq(0, 960),
    *                       clamped = true)
    *
    *                     x(-10)         // 0, clamped to the range
    *                     x.invert(-160) // 10, clamped to the domain
    *                   }}}
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
  def apply[Range](
      domain: Seq[Double] = Seq(1.0, 10.0),
      range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
      base: Double = 10.0,
      clamped: Boolean = false,
      nice: Boolean = false,
      niceCount: Int = -1
  ): Logarithmic[Range, Range] = {
    val scale = Scale.scaleLog[Range, Range]()
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
        .base(base)
        .clamp(clamped)
    if (nice && niceCount > 0)
      scale.nice(niceCount)
    else if (nice)
      scale.nice()
    scale
  }

  /** Constructs a new logarithmic scale.
    *
    * @param  domain       Domain for the new scale.
    * @param  range        Range for the new scale.
    * @param  base      Logarithm base for the new scale.
    * @param  clamped      Boolean value that enables or disables clamping. If clamping is disabled and the scale is
    *                      passed a value outside its domain, the scale may return a value outside the range through
    *                      extrapolation. If clamping is enabled, the return value of the scale is always within the
    *                      scale's range. Clamping similarly applies to the `invert` method of the scale. For example:
    *                      {{{
    *                        val x = d3.scale.linear(
    *                          domain = Seq(10.0, 130.0),
    *                          range = Seq(0, 960))
    *
    *                        x(-10)         // -160, which is outside the range
    *                        x.invert(-160) // -10, which is outside the domain
    *
    *                        val x = d3.scale.linear(
    *                          domain = Seq(10.0, 130.0),
    *                          range = Seq(0, 960),
    *                          clamped = true)
    *
    *                        x(-10)         // 0, clamped to the range
    *                        x.invert(-160) // 10, clamped to the domain
    *                      }}}
    * @param  interpolator Interpolator factory used to create interpolators for each adjacent pair of values from the
    *                      range; these interpolators then map a normalized domain parameter `t` in `[0, 1]` to the
    *                      corresponding value in the range. If not specified, a default interpolator is used.
    *
    *                      For example, consider a diverging color scale with three colors in the range:
    *                      {{{
    *                        val color = d3.scale.linear(
    *                          domain = Seq(-100, 0, 100),
    *                          range = Seq("red", "white", "green"))
    *                      }}}
    *                      Two interpolators are created internally by the scale, equivalent to:
    *                      {{{
    *                        val i0 = d3.interpolate("red", "white")
    *                        val i1 = d3.interpolate("white", "green")
    *                      }}}
    *                      A common reason to specify a custom interpolator is to change the color space of
    *                      interpolation. For example, to use HCL:
    *                      {{{
    *                        val color = d3.scale.linear(
    *                          domain = Seq(10, 100),
    *                          range = Seq("brown", "steelblue"),
    *                          interpolator = d3.interpolateHCL) // TODO: Check API.
    *                      }}}
    *                      Note that the default interpolator may reuse return values. For example, if the range values
    *                      are objects, then the value interpolator always returns the same object, modifying it
    *                      in-place. If the scale is used to set an attribute or style, this is typically acceptable
    *                      (and desirable for performance). However, if you need to store the scale's return value, you
    *                      must specify your own interpolator or make a copy as appropriate.
    * @param  nice         If `true`, the domain will be extended so that it starts and ends on nice round values. This
    *                      method typically modifies the scale’s domain, and may only extend the bounds to the nearest
    *                      round value. An optional tick count argument. "Nicing" is useful if the domain is computed
    *                      from data, say using `extent`, and may be irregular. For example, for a domain of
    *                      `[0.201479..., 0.996679...]`, a nice domain might be `[0.2, 1.0]`. If the domain has more
    *                      than two values, "nicing" the domain only affects the first and last value.
    * @param  niceCount    Optional value that allows greater control over the step size used to extend the bounds, when
    *                      `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @return New scale.
    */
  def interpolated[Range, Output](
      domain: Seq[Double],
      range: Seq[Range],
      base: Double = 10.0,
      clamped: Boolean = false,
      interpolator: InterpolatorFactory[Range, Output],
      nice: Boolean = false,
      niceCount: Int = -1
  ): Logarithmic[Range, Output] = {
    val scale = Scale.scaleLog[Range, Output]()
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
        .base(base)
        .clamp(clamped)
        .interpolate(interpolator)
    if (nice && niceCount > 0)
      scale.nice(niceCount)
    else if (nice)
      scale.nice()
    scale
  }

  /** Constructs a new rounded logarithmic scale. This is done by using the round interpolator, by default.
    *
    * @param  domain    Domain for the new scale.
    * @param  range     Range for the new scale.
    * @param  base      Logarithm base for the new scale.
    * @param  clamped   Boolean value that enables or disables clamping. If clamping is disabled and the scale is passed
    *                   a value outside its domain, the scale may return a value outside the range through
    *                   extrapolation. If clamping is enabled, the return value of the scale is always within the
    *                   scale's range. Clamping similarly applies to the `invert` method of the scale. For example:
    *                   {{{
    *                     val x = d3.scale.linear(
    *                       domain = Seq(10.0, 130.0),
    *                       range = Seq(0, 960))
    *
    *                     x(-10)         // -160, which is outside the range
    *                     x.invert(-160) // -10, which is outside the domain
    *
    *                     val x = d3.scale.linear(
    *                       domain = Seq(10.0, 130.0),
    *                       range = Seq(0, 960),
    *                       clamped = true)
    *
    *                     x(-10)         // 0, clamped to the range
    *                     x.invert(-160) // 10, clamped to the domain
    *                   }}}
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
  def rounded(
      domain: Seq[Double] = Seq(1.0, 10.0),
      range: Seq[Double] = Seq(0.0, 1.0),
      base: Double = 10.0,
      clamped: Boolean = false,
      nice: Boolean = false,
      niceCount: Int = -1
  ): Logarithmic[Double, Double] = {
    val scale = Scale.scaleLog[Double, Double]()
        .domain(js.Array(domain: _*))
        .rangeRound(js.Array(range: _*))
        .base(base)
        .clamp(clamped)
    if (nice && niceCount > 0)
      scale.nice(niceCount)
    else if (nice)
      scale.nice()
    scale
  }
}
