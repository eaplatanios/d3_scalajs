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

import org.platanios.d3.interpolate.Interpolate.InterpolatorFactory
import org.platanios.d3.time.{CountableTimeInterval, TimeInterval}

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/** Time scales are a variant of linear scales that have a temporal domain: domain values are coerced to
  * [dates](https://developer.mozilla.org/en/JavaScript/Reference/Global_Objects/Date) rather than numbers, and invert
  * likewise returns a date. Time scales implement ticks based on [calendar intervals](https://github.com/d3/d3-time),
  * taking the pain out of generating axes for temporal domains.
  *
  * For example, to create a position encoding:
  * {{{
  *   val x = d3.scale.time(
  *     domain = Seq(new js.Date(2000, 0, 1), new js.Date(2000, 0, 2)),
  *     range = Seq(0, 960))
  *
  *   x(new js.Date(2000, 0, 1,  5)) // 200
  *   x(new js.Date(2000, 0, 1, 16)) // 640
  *   x.invert(200)                  // Sat Jan 01 2000 05:00:00 GMT-0800 (PST)
  *   x.invert(640)                  // Sat Jan 01 2000 16:00:00 GMT-0800 (PST)
  * }}}
  *
  * For a valid value `y` in the range, `time(time.invert(y))` equals `y`, and similarly, for any valid value `x` in the
  * domain, `time.invert(time(x))` equals `x`. The `invert` method is useful for interaction, say to determine the value
  * in the domain that corresponds to the pixel location under tghe mouse.
  *
  * @author Emmanouil Antonios Platanios
  */
class Time[Range, Output] protected (
    override private[d3] val facade: Time.Facade[Range, Output]
) extends Scale[js.Date, Range, Output, Int | TimeInterval, String, Time.Facade[Range, Output]] {
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

  override protected def withFacade(facade: Time.Facade[Range, Output]): Time[Range, Output] = {
    new Time(facade)
  }
}

object Time {
  @js.native private[d3] trait Facade[Range, Output]
      extends Scale.Facade[js.Date, Range, Output, Facade[Range, Output]] {
    def domain(domain: js.Array[js.Date]): this.type = js.native
    def range(range: js.Array[Range]): this.type = js.native
    def rangeRound(range: js.Array[Double]): this.type = js.native
    def clamp(clamp: Boolean): this.type = js.native
    def nice(): this.type = js.native
    def nice(count: Int): this.type = js.native
    def nice(interval: CountableTimeInterval): this.type = js.native
    def nice(interval: CountableTimeInterval, step: Int): this.type = js.native
    def interpolate[IO](interpolate: InterpolatorFactory[Range, IO]): Time.Facade[Range, IO] = js.native

    def clamp(): Boolean = js.native
    def interpolate(): InterpolatorFactory[Range, Output] = js.native
    def invert(value: Output): Double = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Time] object Facade extends js.Object {
    def scaleTime[Range, Output](): Time.Facade[Range, Output] = js.native
    def scaleUTC[Range, Output](): Time.Facade[Range, Output] = js.native
  }

  trait API {
    def time[Range](
        domain: Seq[js.Date] = Seq(new js.Date(2000, 1, 1), new js.Date(2000, 1, 2)),
        range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
        clamped: Boolean = false,
        nice: Boolean = false,
        niceCount: Int = -1,
        niceInterval: (CountableTimeInterval, Int) = null,
        useUTC: Boolean = false
    ): Time[Range, Range] = Time(domain, range, clamped, nice, niceCount, niceInterval, useUTC)

    def timeInterpolated[Range, Output](
        domain: Seq[js.Date] = Seq(new js.Date(2000, 1, 1), new js.Date(2000, 1, 2)),
        range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
        clamped: Boolean = false,
        interpolator: InterpolatorFactory[Range, Output],
        nice: Boolean = false,
        niceCount: Int = -1,
        niceInterval: (CountableTimeInterval, Int) = null,
        useUTC: Boolean = false
    ): Time[Range, Output] = {
      Time.interpolated(domain, range, clamped, interpolator, nice, niceCount, niceInterval, useUTC)
    }

    def timeRounded(
        domain: Seq[js.Date] = Seq(new js.Date(2000, 1, 1), new js.Date(2000, 1, 2)),
        range: Seq[Double] = Seq(0.0, 1.0),
        clamped: Boolean = false,
        nice: Boolean = false,
        niceCount: Int = -1,
        niceInterval: (CountableTimeInterval, Int) = null,
        useUTC: Boolean = false
    ): Time[Double, Double] = Time.rounded(domain, range, clamped, nice, niceCount, niceInterval, useUTC)
  }

  /** Constructs a new time scale.
    *
    * @param  domain       Domain for the new scale.
    * @param  range        Range for the new scale.
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
    * @param  nice         If `true`, the domain will be extended so that it starts and ends on nice round values. This
    *                      method typically modifies the scale’s domain, and may only extend the bounds to the nearest
    *                      round value. An optional tick count argument. "Nicing" is useful if the domain is computed
    *                      from data, say using `extent`, and may be irregular. For example, for a domain of
    *                      `[2009-07-13T00:02, 2009-07-13T23:48]`, a nice domain might be `[2009-07-13, 2009-07-14]`.
    *                      If the domain has more than two values, "nicing" the domain only affects the first and last
    *                      value.
    * @param  niceCount    Optional value that allows greater control over the step size used to extend the bounds, when
    *                      `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @param  niceInterval Alternatively to `niceCount`, a time interval may be specified to explicitly set the ticks.
    *                      If an interval is specified, an optional step may also be specified to skip some ticks. For
    *                      example, `niceInterval = (d3.timeSecond, 10)` will extend the domain to an even ten seconds
    *                      (`0`, `10`, `20`, etc.). // TODO: Check API.
    * @param  useUTC       Create a time scale that operates in
    *                      [Coordinated Universal Time](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    *                      rather than local time.
    * @return New scale.
    */
  def apply[Range](
      domain: Seq[js.Date] = Seq(new js.Date(2000, 1, 1), new js.Date(2000, 1, 2)),
      range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
      clamped: Boolean = false,
      nice: Boolean = false,
      niceCount: Int = -1,
      niceInterval: (CountableTimeInterval, Int) = null,
      useUTC: Boolean = false
  ): Time[Range, Range] = {
    val facade = (if (useUTC) Facade.scaleUTC[Range, Range]() else Facade.scaleTime[Range, Range]())
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
        .clamp(clamped)
    if (nice && niceCount > 0)
      facade.nice(niceCount)
    else if (nice && niceInterval != null)
      facade.nice(niceInterval._1, niceInterval._2)
    else if (nice)
      facade.nice()
    new Time(facade)
  }

  /** Constructs a new time scale.
    *
    * @param  domain       Domain for the new scale.
    * @param  range        Range for the new scale.
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
    *                      `[2009-07-13T00:02, 2009-07-13T23:48]`, a nice domain might be `[2009-07-13, 2009-07-14]`.
    *                      If the domain has more than two values, "nicing" the domain only affects the first and last
    *                      value.
    * @param  niceCount    Optional value that allows greater control over the step size used to extend the bounds, when
    *                      `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @param  niceInterval Alternatively to `niceCount`, a time interval may be specified to explicitly set the ticks.
    *                      If an interval is specified, an optional step may also be specified to skip some ticks. For
    *                      example, `niceInterval = (d3.timeSecond, 10)` will extend the domain to an even ten seconds
    *                      (`0`, `10`, `20`, etc.). // TODO: Check API.
    * @param  useUTC       Create a time scale that operates in
    *                      [Coordinated Universal Time](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    *                      rather than local time.
    * @return New scale.
    */
  def interpolated[Range, Output](
      domain: Seq[js.Date] = Seq(new js.Date(2000, 1, 1), new js.Date(2000, 1, 2)),
      range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
      clamped: Boolean = false,
      interpolator: InterpolatorFactory[Range, Output],
      nice: Boolean = false,
      niceCount: Int = -1,
      niceInterval: (CountableTimeInterval, Int) = null,
      useUTC: Boolean = false
  ): Time[Range, Output] = {
    val facade = (if (useUTC) Facade.scaleUTC[Range, Output]() else Facade.scaleTime[Range, Output]())
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
        .clamp(clamped)
        .interpolate(interpolator)
    if (nice && niceCount > 0)
      facade.nice(niceCount)
    else if (nice && niceInterval != null)
      facade.nice(niceInterval._1, niceInterval._2)
    else if (nice)
      facade.nice()
    new Time(facade)
  }

  /** Constructs a new rounded time scale.
    *
    * @param  domain       Domain for the new scale.
    * @param  range        Range for the new scale.
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
    * @param  nice         If `true`, the domain will be extended so that it starts and ends on nice round values. This
    *                      method typically modifies the scale’s domain, and may only extend the bounds to the nearest
    *                      round value. An optional tick count argument. "Nicing" is useful if the domain is computed
    *                      from data, say using `extent`, and may be irregular. For example, for a domain of
    *                      `[2009-07-13T00:02, 2009-07-13T23:48]`, a nice domain might be `[2009-07-13, 2009-07-14]`.
    *                      If the domain has more than two values, "nicing" the domain only affects the first and last
    *                      value.
    * @param  niceCount    Optional value that allows greater control over the step size used to extend the bounds, when
    *                      `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @param  niceInterval Alternatively to `niceCount`, a time interval may be specified to explicitly set the ticks.
    *                      If an interval is specified, an optional step may also be specified to skip some ticks. For
    *                      example, `niceInterval = (d3.timeSecond, 10)` will extend the domain to an even ten seconds
    *                      (`0`, `10`, `20`, etc.). // TODO: Check API.
    * @param  useUTC       Create a time scale that operates in
    *                      [Coordinated Universal Time](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    *                      rather than local time.
    * @return New scale.
    */
  def rounded(
      domain: Seq[js.Date] = Seq(new js.Date(2000, 1, 1), new js.Date(2000, 1, 2)),
      range: Seq[Double] = Seq(0.0, 1.0),
      clamped: Boolean = false,
      nice: Boolean = false,
      niceCount: Int = -1,
      niceInterval: (CountableTimeInterval, Int) = null,
      useUTC: Boolean = false
  ): Time[Double, Double] = {
    val facade = (if (useUTC) Facade.scaleUTC[Double, Double]() else Facade.scaleTime[Double, Double]())
        .domain(js.Array(domain: _*))
        .rangeRound(js.Array(range: _*))
        .clamp(clamped)
    if (nice && niceCount > 0)
      facade.nice(niceCount)
    else if (nice && niceInterval != null)
      facade.nice(niceInterval._1, niceInterval._2)
    else if (nice)
      facade.nice()
    new Time(facade)
  }
}
