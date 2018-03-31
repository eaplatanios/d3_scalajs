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

/** This module provides an efficient queue capable of managing thousands of concurrent animations, while guaranteeing
  * consistent, synchronized timing with concurrent or staged animations. Internally, it uses
  * [requestAnimationFrame](https://developer.mozilla.org/en-US/docs/Web/API/window/requestAnimationFrame) for fluid
  * animation (if available), switching to
  * [setTimeout](https://developer.mozilla.org/en-US/docs/Web/API/WindowTimers/setTimeout) for delays longer than 24ms.
  *
  * @author Emmanouil Antonios Platanios
  */
class Timer protected (
    private[d3] val facade: Timer.Facade
) {
  /** Restarts this timer with the specified callback and optional delay and time. This is equivalent to stopping this
    * timer and creating a new timer with the specified arguments, although this timer retains the original invocation
    * priority.
    *
    * @param  callback Timer callback function.
    * @param  delay    Invoke the given callback after `delay` milliseconds.
    * @param  time     Time to use (defaults to `Timer.now()`).
    */
  def restart(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = Timer.now()): Unit = {
    facade.restart(callback, delay, time)
  }

  /** Stops this timer, preventing subsequent callbacks. This method has no effect if the timer has already
    * been stopped. */
  def stop(): Unit = {
    facade.stop()
  }
}

object Timer {
  /** Returns the current time as defined by
    * [`performance.now`](https://developer.mozilla.org/en-US/docs/Web/API/Performance/now), if available, and
    * [`Date.now`](https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Date/now) if not. The
    * current time is updated at the start of a frame. It is thus consistent during the frame, and any timers scheduled
    * during the same frame will be synchronized. If this method is called outside of a frame, such as in response to a
    * user event, the current time is calculated and then fixed until the next frame, again ensuring consistent timing
    * during event handling.
    *
    * @return Time elapsed since the
    *         [time origin](https://developer.mozilla.org/en-US/docs/Web/API/DOMHighResTimeStamp#The_time_origin),
    *         in milliseconds.
    */
  def now(): Double = Facade.now()

  /** Schedules a new timer, invoking the specified callback repeatedly until the timer is stopped. An optional numeric
    * delay in milliseconds may be specified to invoke the given callback after a delay; if `delay` is not specified, it
    * defaults to zero. The delay is relative to the specified time in milliseconds. If `time` is not specified, it
    * defaults to `now()`.
    *
    * The callback is passed the (apparent) elapsed time since the timer became active. For example:
    * {{{
    *   val t = d3.timer((elapsed: Double) => {
    *     js.Dynamic.global.console.log(elapsed)
    *     if (elapsed > 200) t.stop()
    *   }, 150)
    * }}}
    * This produces roughly the following console output (the exact values may vary depending on your JavaScript runtime
    * and what else your computer is doing):
    * {{{
    *   3
    *   25
    *   48
    *   65
    *   85
    *   106
    *   125
    *   146
    *   167
    *   189
    *   209
    * }}}
    * Note that the first elapsed time is `3ms`. This is the elapsed time since the timer started; not since the timer
    * was scheduled. Here the timer started `150ms` after it was scheduled due to the specified delay. The apparent
    * elapsed time may be less than the true elapsed time if the page is backgrounded and
    * [`requestAnimationFrame`](https://developer.mozilla.org/en-US/docs/Web/API/window/requestAnimationFrame) is
    * paused; in the background, apparent time is frozen.
    *
    * If `timer()` is called within the callback of another timer, the new timer callback (if eligible as determined by
    * the specified delay and time) will be invoked immediately at the end of the current frame, rather than waiting
    * until the next frame. Within a frame, timer callbacks are guaranteed to be invoked in the order in which they were
    * scheduled, regardless of their start time.
    *
    * @param  callback Timer callback function.
    * @param  delay    Invoke the given callback after `delay` milliseconds.
    * @param  time     Time to use (defaults to `now()`).
    * @return New timer.
    */
  def apply(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Timer = {
    new Timer(Facade.timer(callback, delay, time))
  }

  /** Immediately invoke any eligible timer callbacks. Note that zero-delay timers are normally first executed after one
    * frame (~17ms). This can cause a brief flicker because the browser renders the page twice: once at the end of the
    * first event loop, then again immediately on the first timer callback. By flushing the timer queue at the end of
    * the first event loop, you can run any zero-delay timers immediately and avoid the flicker. */
  def timerFlush(): Unit = {
    Facade.timerFlush()
  }

  /** Like `timer()`, except that the timer automatically stops on its first callback. A suitable replacement for
    * [setTimeout](https://developer.mozilla.org/en-US/docs/Web/API/WindowTimers/setTimeout) that is guaranteed to not
    * run in the background. The callback is passed the elapsed time.
    *
    * @param  callback Timer callback function.
    * @param  delay    Invoke the given callback after `delay` milliseconds.
    * @param  time     Time to use (defaults to `now()`).
    * @return New timer.
    */
  def timeout(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Timer = {
    new Timer(Facade.timeout(callback, delay, time))
  }

  /** Like `timer()`, except that the timer automatically stops on its first callback. A suitable replacement for
    * [setTimeout](https://developer.mozilla.org/en-US/docs/Web/API/WindowTimers/setTimeout) that is guaranteed to not
    * run in the background. The callback is passed the elapsed time.
    *
    * Like `timer()`, except the callback is invoked only every `delay` milliseconds. If `delay` is not specified, this
    * is equivalent to `timer()`. A suitable replacement for
    * [setInterval](https://developer.mozilla.org/en-US/docs/Web/API/WindowTimers/setInterval) that is guaranteed to not
    * run in the background. The callback is passed the elapsed time.
    *
    * @param  callback Timer callback function.
    * @param  delay    Invoke the given callback after `delay` milliseconds.
    * @param  time     Time to use (defaults to `now()`).
    * @return New timer.
    */
  def interval(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Timer = {
    new Timer(Facade.interval(callback, delay, time))
  }

  @JSImport("d3-timer", JSImport.Namespace)
  @js.native object Facade extends js.Object {
    def now(): Double = js.native
    def timer(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Facade = js.native
    def timerFlush(): Unit = js.native
    def timeout(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Facade = js.native
    def interval(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Facade = js.native
  }

  @js.native trait Facade extends js.Object {
    def restart(callback: js.Function1[Double, Unit], delay: Double = 0.0, time: Double = now()): Unit = js.native
    def stop(): Unit = js.native
  }
}
