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
@JSImport("d3-timer", JSImport.Namespace)
@js.native object Timer extends js.Object {
  def now(): Double = js.native
  def timer(callback: js.Function1[Double, Unit], delay: Double = ???, time: Double = ???): Timer = js.native
  def timerFlush(): Unit = js.native
  def timeout(callback: js.Function1[Double, Unit], delay: Double = ???, time: Double = ???): Timer = js.native
  def interval(callback: js.Function1[Double, Unit], delay: Double = ???, time: Double = ???): Timer = js.native
}

@js.native trait Timer extends js.Object {
  def restart(callbackFn: js.Function1[Double, Unit], delay: Double = ???, time: Double = ???): Unit = js.native
  def stop(): Unit = js.native
}
