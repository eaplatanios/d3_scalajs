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
import scala.scalajs.js.annotation.JSImport

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-time", JSImport.Namespace)
@js.native object Time extends js.Object {
  def timeInterval(floor: js.Function1[js.Date, Unit], offset: js.Function2[js.Date, Double, Unit]): TimeInterval = js.native
  def timeInterval(floor: js.Function1[js.Date, Unit], offset: js.Function2[js.Date, Double, Unit], count: js.Function2[js.Date, js.Date, Double], field: js.Function1[js.Date, Double] = ???): CountableTimeInterval = js.native
  val timeMillisecond: CountableTimeInterval = js.native
  def timeMilliseconds(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeSecond: CountableTimeInterval = js.native
  def timeSeconds(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeMinute: CountableTimeInterval = js.native
  def timeMinutes(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeHour: CountableTimeInterval = js.native
  def timeHours(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeDay: CountableTimeInterval = js.native
  def timeDays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeWeek: CountableTimeInterval = js.native
  def timeWeeks(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeSunday: CountableTimeInterval = js.native
  def timeSundays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeMonday: CountableTimeInterval = js.native
  def timeMondays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeTuesday: CountableTimeInterval = js.native
  def timeTuesdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeWednesday: CountableTimeInterval = js.native
  def timeWednesdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeThursday: CountableTimeInterval = js.native
  def timeThursdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeFriday: CountableTimeInterval = js.native
  def timeFridays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeSaturday: CountableTimeInterval = js.native
  def timeSaturdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeMonth: CountableTimeInterval = js.native
  def timeMonths(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val timeYear: CountableTimeInterval = js.native
  def timeYears(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcMillisecond: CountableTimeInterval = js.native
  def utcMilliseconds(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcSecond: CountableTimeInterval = js.native
  def utcSeconds(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcMinute: CountableTimeInterval = js.native
  def utcMinutes(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcHour: CountableTimeInterval = js.native
  def utcHours(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcDay: CountableTimeInterval = js.native
  def utcDays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcWeek: CountableTimeInterval = js.native
  def utcWeeks(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcSunday: CountableTimeInterval = js.native
  def utcSundays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcMonday: CountableTimeInterval = js.native
  def utcMondays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcTuesday: CountableTimeInterval = js.native
  def utcTuesdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcWednesday: CountableTimeInterval = js.native
  def utcWednesdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcThursday: CountableTimeInterval = js.native
  def utcThursdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcFriday: CountableTimeInterval = js.native
  def utcFridays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcSaturday: CountableTimeInterval = js.native
  def utcSaturdays(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcMonth: CountableTimeInterval = js.native
  def utcMonths(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
  val utcYear: CountableTimeInterval = js.native
  def utcYears(start: js.Date, stop: js.Date, step: Double = ???): js.Array[js.Date] = js.native
}
