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

package org.platanios.d3.time

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

/** When visualizing time series data, analyzing temporal patterns, or working with time in general, the irregularities
  * of conventional time units quickly become apparent. In the
  * [Gregorian calendar](https://en.wikipedia.org/wiki/Gregorian_calendar), for example, most months have 31 days but
  * some have 28, 29 or 30; most years have 365 days but [leap years](https://en.wikipedia.org/wiki/Leap_year) have 366;
  * and with daylight saving, most days have 24 hours but some have 23 or 25. Adding to complexity, daylight saving
  * conventions vary around the world.
  *
  * As a result of these temporal peculiarities, it can be difficult to perform seemingly-trivial tasks. For example,
  * if you want to compute the number of days that have passed between two dates, you can't simply subtract and divide
  * by 24 hours (86,400,000 ms):
  * {{{
  *   val start = new Date(2015, 02, 01) // Sun Mar 01 2015 00:00:00 GMT-0800 (PST)
  *   val end = new Date(2015, 03, 01)   // Wed Apr 01 2015 00:00:00 GMT-0700 (PDT)
  *   (end - start) / 864e5              // 30.958333333333332, oops!
  * }}}
  * You can, however, use `d3.time.day.count()`:
  * {{{
  *   d3.time.day.count(start, end) // 31
  * }}}
  *
  * The day interval is one of several provided by `d3.time`. Each interval represents a conventional unit of
  * time-hours, weeks, months, etc., and has methods to calculate boundary dates. For example, `d3.time.day` computes
  * midnight (typically 12:00 AM local time) of the corresponding day. In addition to rounding and counting, intervals
  * can also be used to generate arrays of boundary dates. For example, to compute each Sunday in the current month:
  * {{{
  *   val now = new js.Date()
  *   d3.time.week.range(d3.time.month.floor(now), d3.time.month.ceil(now))
  *   // [Sun Jun 07 2015 00:00:00 GMT-0700 (PDT),
  *   //  Sun Jun 14 2015 00:00:00 GMT-0700 (PDT),
  *   //  Sun Jun 21 2015 00:00:00 GMT-0700 (PDT),
  *   //  Sun Jun 28 2015 00:00:00 GMT-0700 (PDT)]
  * }}}
  *
  * The `d3.time` module does not implement its own calendar system. It merely implements a convenient API for calendar
  * math on top of ECMAScript
  * [Date](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Date). Thus, it ignores leap
  * seconds and can only work with the local time zone and
  * [Coordinated Universal Time (UTC)](https://en.wikipedia.org/wiki/Coordinated_Universal_Time).
  *
  * This module is used by D3's time scales to generate sensible ticks, by D3's time format, and can also be used
  * directly to do things like [calendar layouts](http://bl.ocks.org/mbostock/4063318).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-time", JSImport.Namespace)
@js.native object Time extends js.Object {
  /** Constructs a new custom (non-countable) interval given the specified floor and offset functions.
    *
    * @param  floor  Takes a single date as an argument and rounds it down to the nearest interval boundary.
    * @param  offset Takes a date and an integer step as arguments and advances the specified date by the specified
    *                number of boundaries. The step may be positive, negative, or zero.
    * @return Constructed countable time interval.
    */
  @JSName("timeInterval") def interval(
      floor: js.Function1[js.Date, Unit],
      offset: js.Function2[js.Date, Int, Unit]
  ): TimeInterval = js.native

  /** Constructs a new custom (countable) interval given the specified floor, offset, and count functions.
    *
    * @param  floor  Takes a single date as an argument and rounds it down to the nearest interval boundary.
    * @param  offset Takes a date and an integer step as arguments and advances the specified date by the specified
    *                number of boundaries. The step may be positive, negative, or zero.
    * @param  count  Takes a start date and an end date, already floored to the current interval, and returns the number
    *                of boundaries between the start (exclusive) and end (inclusive). If a count function is not
    *                specified, the returned interval is not countable. Note that due to an internal optimization, the
    *                specified count function must not invoke the `count()` method of other time intervals.
    * @param  field  Takes a date, already floored to the current interval, and returns the field value of the specified
    *                date, corresponding to the number of boundaries between this date (exclusive) and the latest
    *                previous parent boundary. For example, for the `d3.time.day` interval, this returns the number of
    *                days since the start of the month. If a field function is not specified, it defaults to counting
    *                the number of interval boundaries since the UNIX epoch of January 1, 1970 UTC. The field function
    *                defines the behavior of the `every()` method of countable time intervals.
    * @return Constructed countable time interval.
    */
  @JSName("timeInterval") def interval(
      floor: js.Function1[js.Date, Unit],
      offset: js.Function2[js.Date, Int, Unit],
      count: js.Function2[js.Date, js.Date, Int],
      field: js.Function1[js.Date, Int] = ???
  ): CountableTimeInterval = js.native

  /** Milliseconds. The shortest available time unit. */
  @JSName("Millisecond") val millisecond   : CountableTimeInterval = js.native
                         val utcMillisecond: CountableTimeInterval = js.native

  /** Seconds (e.g., 01:23:45.0000 AM); 1,000 milliseconds. */
  @JSName("Second") val second   : CountableTimeInterval = js.native
                    val utcSecond: CountableTimeInterval = js.native

  /** Minutes (e.g., 01:02:00 AM); 60 seconds. Note that ECMAScript
    * [ignores leap seconds](http://www.ecma-international.org/ecma-262/5.1/#sec-15.9.1.1). */
  @JSName("Minute") val minute   : CountableTimeInterval = js.native
                    val utcMinute: CountableTimeInterval = js.native

  /** Hours (e.g., 01:00 AM); 60 minutes. Note that advancing time by one hour in local time can return the same hour
    * or skip an hour due to daylight saving. */
  @JSName("Hour") val hour   : CountableTimeInterval = js.native
                  val utcHour: CountableTimeInterval = js.native

  /** Days (e.g., February 7, 2012 at 12:00 AM); typically 24 hours. Days in local time may range from 23 to 25 hours
    * due to daylight saving. */
  @JSName("Day") val day   : CountableTimeInterval = js.native
                 val utcDay: CountableTimeInterval = js.native

  /** Alias for `d3.time.sunday`; 7 days and typically 168 hours. Weeks in local time may range from 167 to 169 hours
    * due on daylight saving. */
  @JSName("Week") val week   : CountableTimeInterval = js.native
                  val utcWeek: CountableTimeInterval = js.native

  /** Sunday-based weeks (e.g., February 5, 2012 at 12:00 AM). */
  @JSName("Sunday") val sunday   : CountableTimeInterval = js.native
                    val utcSunday: CountableTimeInterval = js.native

  /** Monday-based weeks (e.g., February 6, 2012 at 12:00 AM). */
  @JSName("Monday") val monday   : CountableTimeInterval = js.native
                    val utcMonday: CountableTimeInterval = js.native

  /** Tuesday-based weeks (e.g., February 7, 2012 at 12:00 AM). */
  @JSName("Tuesday") val tuesday   : CountableTimeInterval = js.native
                     val utcTuesday: CountableTimeInterval = js.native

  /** Wednesday-based weeks (e.g., February 8, 2012 at 12:00 AM). */
  @JSName("Wednesday") val wednesday   : CountableTimeInterval = js.native
                       val utcWednesday: CountableTimeInterval = js.native

  /** Thursday-based weeks (e.g., February 9, 2012 at 12:00 AM). */
  @JSName("Thursday") val thursday   : CountableTimeInterval = js.native
                      val utcThursday: CountableTimeInterval = js.native

  /** Friday-based weeks (e.g., February 10, 2012 at 12:00 AM). */
  @JSName("Friday") val friday   : CountableTimeInterval = js.native
                    val utcFriday: CountableTimeInterval = js.native

  /** Saturday-based weeks (e.g., February 11, 2012 at 12:00 AM). */
  @JSName("Saturday") val saturday   : CountableTimeInterval = js.native
                      val utcSaturday: CountableTimeInterval = js.native

  /** Months (e.g., February 1, 2012 at 12:00 AM); ranges from 28 to 31 days. */
  @JSName("Month") val month   : CountableTimeInterval = js.native
                   val utcMonth: CountableTimeInterval = js.native

  /** Years (e.g., January 1, 2012 at 12:00 AM); ranges from 365 to 366 days. */
  @JSName("Year") val year   : CountableTimeInterval = js.native
                  val utcYear: CountableTimeInterval = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeMilliseconds(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeSeconds(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeMinutes(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeHours(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeDays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeWeeks(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeSundays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeMondays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeTuesdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeWednesdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeThursdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeFridays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeSaturdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeMonths(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def timeYears(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcMilliseconds(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcSeconds(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcMinutes(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcHours(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcDays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcWeeks(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcSundays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcMondays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcTuesdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcWednesdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcThursdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcFridays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcSaturdays(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcMonths(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Alias for the corresponding `Interval.range()` method. */
  def utcYears(start: js.Date, stop: js.Date, step: Int = 1): js.Array[js.Date] = js.native
}
