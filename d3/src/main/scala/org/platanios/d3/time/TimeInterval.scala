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
import scala.scalajs.js.|

/** Represents a time interval.
  *
  * @author Emmanouil Antonios Platanios
  */
@js.native trait TimeInterval extends js.Object {
  /** Returns a new date representing the latest interval boundary date before or equal to date. This function is an
    * alias for `TimeInterval.floor(date)`. For example, `d3.time.year(date)` and `d3.time.year.floor(date)` are
    * equivalent.
    *
    * This function is idempotent: if the specified date is already floored to the current interval, a new date with an
    * identical time is returned. Furthermore, the returned date is the minimum expressible value of the associated
    * interval, such that `interval.floor(interval.floor(date) - 1)` returns the preceding interval boundary date.
    *
    * Note that the `==` and `===` operators do not compare by value with date objects, and thus you cannot use them to
    * tell whether the specified date has already been floored. Instead, coerce to a number and then compare.
    *
    * This is more reliable than testing whether the time is `12:00 AM`, as in some time zones midnight may not exist
    * due to daylight savings.
    *
    * @param  date Date to floor.
    * @return Floored date.
    */
  def apply(date: js.Date): js.Date = js.native

  /** Returns a new date representing the latest interval boundary date before or equal to date. For example,
    * `d3.time.day.floor(date)` typically returns 12:00 AM local time on the given date.
    *
    * This function is idempotent: if the specified date is already floored to the current interval, a new date with an
    * identical time is returned. Furthermore, the returned date is the minimum expressible value of the associated
    * interval, such that `interval.floor(interval.floor(date) - 1)` returns the preceding interval boundary date.
    *
    * Note that the `==` and `===` operators do not compare by value with date objects, and thus you cannot use them to
    * tell whether the specified date has already been floored. Instead, coerce to a number and then compare.
    *
    * This is more reliable than testing whether the time is `12:00 AM`, as in some time zones midnight may not exist
    * due to daylight savings.
    *
    * @param  date Date to floor.
    * @return Floored date.
    */
  def floor(date: js.Date): js.Date = js.native

  /** Returns a new date representing the closest interval boundary date to date. For example, `d3.time.day.round(date)`
    * typically returns 12:00 AM local time on the given date if it is on or before noon, and 12:00 AM of the following
    * day if it is after noon.
    *
    * This method is idempotent: if the specified date is already rounded to the current interval, a new date with an
    * identical time is returned.
    *
    * @param  date Date to round.
    * @return Round date.
    */
  def round(date: js.Date): js.Date = js.native

  /** Returns a new date representing the earliest interval boundary date after or equal to date. For example,
    * `d3.time.day.ceil(date)` typically returns 12:00 AM local time on the date following the given date.
    *
    * This method is idempotent: if the specified date is already "ceiled" to the current interval, a new date with
    * an identical time is returned. Furthermore, the returned date is the maximum expressible value of the associated
    * interval, such that `interval.ceil(interval.ceil(date) + 1)` returns the following interval boundary date.
    *
    * @param  date Date to ceil.
    * @return "Ceiled" date.
    */
  def ceil(date: js.Date): js.Date = js.native

  /** Returns a new date equal to date plus step intervals. If `step` is not specified it defaults to `1`. If `step` is
    * negative, then the returned date will be before the specified date. If step is zero, then a copy of the specified
    * date is returned. This method does not round the specified date to the interval. For example, if date is today at
    * 5:34 PM, then `d3.time.day.offset(date, 1)` returns 5:34 PM tomorrow (even if daylight savings changes!).
    *
    * @param  date Date to offset.
    * @return Date with the offset applied to it.
    */
  def offset(date: js.Date, step: Int = 1): js.Date = js.native

  /** Returns an array of dates representing every interval boundary after or equal to start (inclusive) and before stop
    * (exclusive). If `step` is specified, then every step-th boundary will be returned. For example, for the
    * `d3.time.day` interval, a step of `2` will return every other day.
    *
    * The first date in the returned array is the earliest boundary after or equal to start. Subsequent dates are offset
    * by `step` intervals and floored. Thus, two overlapping ranges may be consistent. For example, this range contains
    * odd days:
    * {{{
    *   d3.time.day.range(new js.Date(2015, 0, 1), new js.Date(2015, 0, 7), 2)
    *   // [Thu Jan 01 2015 00:00:00 GMT-0800 (PST),
    *   //  Sat Jan 03 2015 00:00:00 GMT-0800 (PST),
    *   //  Mon Jan 05 2015 00:00:00 GMT-0800 (PST)]
    * }}}
    * While this contains even days:
    * {{{
    *   d3.time.day.range(new Date(2015, 0, 2), new Date(2015, 0, 8), 2)
    *   // [Fri Jan 02 2015 00:00:00 GMT-0800 (PST),
    *   //  Sun Jan 04 2015 00:00:00 GMT-0800 (PST),
    *   //  Tue Jan 06 2015 00:00:00 GMT-0800 (PST)]
    * }}}
    * To make ranges consistent when a step is specified, use `every()`, instead.
    *
    * @param  start Start date (inclusive).
    * @param  end   End date (exclusive).
    * @param  step  Step to use.
    * @return Array containing the dates in the range.
    */
  def range(start: js.Date, end: js.Date, step: Int = 1): js.Array[js.Date] = js.native

  /** Returns a new interval that is a filtered subset of this interval using the specified test function. The test
    * function is passed a date and should return `true` if and only if the specified date should be considered part of
    * the interval. For example, to create an interval that returns the 1st, 11th, 21th and 31th (if it exists) of each
    * month:
    * {{{
    *   val i = d3.time.day.filter((d: js.Date) => (d.getDate() - 1) % 10 == 0)
    * }}}
    * The returned filtered interval interval is not countable.
    *
    * @param  condition Condition function for the filter.
    * @return Filtered time interval.
    */
  def filter(condition: js.Function1[js.Date, Boolean]): TimeInterval = js.native
}

/** Represents a countable time interval. */
@js.native trait CountableTimeInterval extends TimeInterval {
  /** Returns a filtered view of this interval representing every step-th date. The meaning of step is dependent on this
    * interval's parent interval as defined by the field function. For example, `d3.time.minute.every(15)` returns an
    * interval representing every fifteen minutes, starting on the hour: :00, :15, :30, :45, etc. Note that for some
    * intervals, the resulting dates may not be uniformly-spaced. `d3.time.day`'s parent interval is `d3.time.month`,
    * and thus the interval number resets at the start of each month. If `step` is not valid, then this method returns
    * `null`. If step is one, it returns this interval.
    *
    * This method can be used in conjunction with `TimeInterval.range()` to ensure that two overlapping ranges are
    * consistent. For example, this range contains odd days:
    * {{{
    *   d3.time.day.every(2).range(new js.Date(2015, 0, 1), new js.Date(2015, 0, 7))
    *   // [Thu Jan 01 2015 00:00:00 GMT-0800 (PST),
    *   //  Sat Jan 03 2015 00:00:00 GMT-0800 (PST),
    *   //  Mon Jan 05 2015 00:00:00 GMT-0800 (PST)]
    * }}}
    * As does this one:
    * {{{
    *   d3.time.day.every(2).range(new js.Date(2015, 0, 2), new js.Date(2015, 0, 8))
    *   // [Sat Jan 03 2015 00:00:00 GMT-0800 (PST),
    *   //  Mon Jan 05 2015 00:00:00 GMT-0800 (PST),
    *   //  Wed Jan 07 2015 00:00:00 GMT-0800 (PST)]
    * }}}
    *
    * The returned filtered interval is not countable.
    *
    * @param  step Step to use.
    * @return Resulting interval (non-countable).
    */
  def every(step: Int): TimeInterval | Null = js.native

  /** Returns the number of interval boundaries after start (exclusive) and before or equal to end (inclusive). Note
    * that this behavior is slightly different than interval.range because its purpose is to return the zero-based
    * number of the specified end date relative to the specified start date. For example, to compute the current
    * zero-based day-of-year number:
    * {{{
    *   val now = new js.Date()
    *   d3.time.day.count(d3.time.year(now), now) // 177
    * }}}
    * Likewise, to compute the current zero-based week-of-year number for weeks that start on Sunday:
    * {{{
    *   d3.time.sunday.count(d3.time.year(now), now) // 25
    * }}}
    *
    * @param  start Start date (exclusive).
    * @param  end   End date (inclusive).
    * @return Number of interval boundaries after start (exclusive) and before or equal to end (inclusive).
    */
  def count(start: js.Date, end: js.Date): Int = js.native
}
