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
    * alias for `TimeInterval.floor(date)`. For example, `timeYear(date)` and `timeYear.floor(date)` are equivalent.
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

  def floor(date: js.Date): js.Date = js.native
  def round(date: js.Date): js.Date = js.native
  def ceil(date: js.Date): js.Date = js.native
  def offset(date: js.Date): js.Date = js.native
  def offset(date: js.Date, step: Double): js.Date = js.native
  def range(start: js.Date, stop: js.Date): js.Array[js.Date] = js.native
  def range(start: js.Date, stop: js.Date, step: Double): js.Array[js.Date] = js.native
  def filter(test: js.Function1[js.Date, Boolean]): TimeInterval = js.native
}

@js.native trait CountableTimeInterval extends TimeInterval {
  def count(start: js.Date, end: js.Date): Double = js.native
  def every(step: Double): TimeInterval | Null = js.native
}
