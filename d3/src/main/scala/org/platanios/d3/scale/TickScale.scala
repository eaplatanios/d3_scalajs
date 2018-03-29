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

/** Represents scales that support generating ticks and can be used for creating axes.
  *
  * @author Emmanouil Antonios Platanios
  */
trait TickScale[Domain, Range, Output, Tick, F <: TickScale.Facade[Domain, Range, Output, Tick, F]]
    extends Scale[Domain, Range, Output, F] {
  /** Returns approximately `count` representative values from the scale’s domain. If count is not specified, it
    * defaults to `10`. The returned tick values are uniformly spaced, have human-readable values (such as multiples of
    * powers of 10, or every day at midnight), and are guaranteed to be within the extent of the domain. Ticks are often
    * used to display reference lines, or tick marks, in conjunction with the visualized data. The specified count is
    * only a hint; the scale may return more or fewer values depending on the domain.
    *
    * An optional `count` may be specified to affect how many ticks are generated. If `count` is not specified, it
    * defaults to `10`. The specified count is only a hint; the scale may return more or fewer values depending on the
    * domain. For example, to create ten default ticks:
    * {{{
    *   d3.scale.time().ticks(10)
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
    *   d3.scale.time(Seq(new js.Date(2000, 0, 1, 0), new js.Date(2000, 0, 1, 2)))
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
    * @param  tick Hint for the number of ticks to return.
    * @return Array containing the ticks.
    */
  def ticks(tick: Tick = ???): js.Array[Domain] = facade.ticks(tick)

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
    * For time scales, this method returns a time format function suitable for displaying tick values. If a format
    * specifier is provided, this method is equivalent to `format`. If a specifier is not provided, the default time
    * format is used. The default multi-scale time format chooses a human-readable representation based on the
    * specified date as follows:
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
    * @param  tick      Hint for the number of ticks to return, or the time intervals to use.
    * @param  specifier Format specifier to use.
    * @return Number format function suitable for displaying a tick value.
    */
  def tickFormat(tick: Tick = ???, specifier: String = ???): js.Function1[Domain, String] = {
    facade.tickFormat(tick, specifier)
  }
}

object TickScale {
  @js.native private[d3] trait Facade[Domain, Range, Output, Tick, F <: Facade[Domain, Range, Output, Tick, F]]
      extends Scale.Facade[Domain, Range, Output, F] {
    def ticks(count: Tick = ???): js.Array[Domain] = js.native
    def tickFormat(count: Tick = ???, specifier: String = ???): js.Function1[Domain, String] = js.native
  }
}
