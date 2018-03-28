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
import scala.scalajs.js.annotation.JSName

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
  * in the domain that corresponds to the pixel location under the mouse.
  *
  * @author Emmanouil Antonios Platanios
  */
@js.native trait Time[Range, Output] extends Scale[js.Date, Range, Output] {
  /** Sets the domain of this scale. */
  private[scale] def domain(domain: js.Array[js.Date]): this.type = js.native

  /** Sets the range of this scale. */
  private[scale] def range(range: js.Array[Range]): this.type = js.native

  /** Sets the range of this scale with rounding enabled. */
  private[scale] def rangeRound(range: js.Array[Double]): this.type = js.native

  /** Enables/disables clamping for this scale. */
  private[scale] def clamp(clamp: Boolean): this.type = js.native

  /** Makes this scale "nice". */
  private[scale] def nice(): this.type = js.native

  /** Sets the niceness of this scale. */
  private[scale] def nice(count: Int): this.type = js.native

  /** Sets the niceness of this scale. */
  private[scale] def nice(interval: CountableTimeInterval): this.type = js.native

  /** Sets the niceness of this scale. */
  private[scale] def nice(interval: CountableTimeInterval, step: Int): this.type = js.native

  private[scale] def interpolate[IO](interpolate: InterpolatorFactory[Range, IO]): Time[Range, IO] = js.native

  /** Returns a boolean indicating whether clamping is enabled for this scale. */
  @JSName("clamp") def clamped(): Boolean = js.native

  /** Returns the interpolator used by this scale. */
  @JSName("interpolate") def interpolator(): InterpolatorFactory[Range, Output] = js.native

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
  def invert(value: Output): Double = js.native

  /** Returns representative dates from the scale’s domain. The returned tick values are (mostly) uniformly-spaced, have
    * sensible values (such as every day at midnight), and are guaranteed to be within the extent of the domain. Ticks
    * are often used to display reference lines, or tick marks, in conjunction with the visualized data.
    *
    * An optional `count` may be specified to affect how many ticks are generated. If `count` is not specified, it
    * defaults to `10`. The specified count is only a hint; the scale may return more or fewer values depending on the
    * domain. For example, to create ten default ticks:
    * {{{
    *   d3.scale.Time().ticks(10)
    *   // [Sat Jan 01 2000 00:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 03:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 06:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 09:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 12:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 15:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 18:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 21:00:00 GMT-0800 (PST),
    *   //  Sun Jan 02 2000 00:00:00 GMT-0800 (PST)]
    * }}}
    *
    * The following time intervals are considered for automatic ticks:
    *
    *   - 1, 5, 15, and 30 seconds,
    *   - 1, 3, 6, and 12 hours,
    *   - 1 and 2 days,
    *   - 1 week,
    *   - 1 and 3 months,
    *   - 1 year.
    *
    * In lieu of a count, a time interval may be explicitly specified. To prune the generated ticks for a given time
    * interval, use `interval.every()`. For example, to generate ticks at 15-minute intervals:
    * {{{
    *   d3.scale.Time(Seq(new js.Date(2000, 0, 1, 0), new js.Date(2000, 0, 1, 2)))
    *     .ticks(d3.time.intervalEvery(15)) // TODO: Update when we fix the API.
    *   // [Sat Jan 01 2000 00:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 00:15:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 00:30:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 00:45:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 01:00:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 01:15:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 01:30:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 01:45:00 GMT-0800 (PST),
    *   //  Sat Jan 01 2000 02:00:00 GMT-0800 (PST)]
    * }}}
    *
    * Note that, in some cases, such as with day ticks, specifying a step can result in irregular spacing of ticks
    * because time intervals have varying length.
    *
    * @return Array containing the ticks.
    */
  def ticks[T: Time.TicksArgument](countsOrInternal: T = null): js.Array[js.Date] = js.native

  /** Returns a time format function suitable for displaying tick values. If a format specifier is provided, this method
    * is equivalent to `format`. If a specifier is not provided, the default time format is used. The default
    * multi-scale time format chooses a human-readable representation based on the specified date as follows:
    *
    *   - `%Y` - for year boundaries, such as `2011`,
    *   - `%B` - for month boundaries, such as `February`,
    *   - `%b %d` - for week boundaries, such as `Feb 06`,
    *   - `%a %d` - for day boundaries, such as `Mon 07`,
    *   - `%I %p` - for hour boundaries, such as `01 AM`,
    *   - `%I:%M` - for minute boundaries, such as `01:23`,
    *   - `:%S` - for second boundaries, such as `:45`,
    *   - `.%L` - milliseconds for all other times, such as `.012`.
    *
    * Although somewhat unusual, this default behavior has the benefit of providing both local and global context; for
    * example, formatting a sequence of ticks as `[11 PM, Mon 07, 01 AM]` reveals information about hours, dates, and
    * hours simultaneously, rather than just the hours `[11 PM, 12 AM, 01 AM]`.
    *
    * @param  specifier Format specifier to use.
    * @return Time format function suitable for displaying a tick value.
    */
  def tickFormat(specifier: String = null): js.Function1[js.Date, String] = js.native
}

object Time {
  trait TicksArgument[T]

  object TicksArgument {
    implicit val count   : TicksArgument[Double]       = new TicksArgument[Double] {}
    implicit val interval: TicksArgument[TimeInterval] = new TicksArgument[TimeInterval] {}
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
    val scale = (if (useUTC) Scale.scaleUTC[Range, Range]() else Scale.scaleTime[Range, Range]())
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
        .clamp(clamped)
    if (nice && niceCount > 0)
      scale.nice(niceCount)
    else if (nice && niceInterval != null)
      scale.nice(niceInterval._1, niceInterval._2)
    else if (nice)
      scale.nice()
    scale
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
    val scale = (if (useUTC) Scale.scaleUTC[Range, Output]() else Scale.scaleTime[Range, Output]())
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
        .clamp(clamped)
        .interpolate(interpolator)
    if (nice && niceCount > 0)
      scale.nice(niceCount)
    else if (nice && niceInterval != null)
      scale.nice(niceInterval._1, niceInterval._2)
    else if (nice)
      scale.nice()
    scale
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
    val scale = (if (useUTC) Scale.scaleUTC[Double, Double]() else Scale.scaleTime[Double, Double]())
        .domain(js.Array(domain: _*))
        .rangeRound(js.Array(range: _*))
        .clamp(clamped)
    if (nice && niceCount > 0)
      scale.nice(niceCount)
    else if (nice && niceInterval != null)
      scale.nice(niceInterval._1, niceInterval._2)
    else if (nice)
      scale.nice()
    scale
  }
}
