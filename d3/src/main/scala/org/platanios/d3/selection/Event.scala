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

package org.platanios.d3.selection

import org.platanios.d3.{ClientPointEvent, ContainerElement}

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Contains methods related to events and event-handling.
  *
  * @author Emmanouil Antonios Platanios
  */
object Event {
  @js.native trait Event extends dom.Event {
    var sourceEvent: dom.Event = js.native
  }

  /** The current [event](https://developer.mozilla.org/en-US/docs/DOM/event), if any. This is set during the invocation
    * of an event listener, and is reset after the listener terminates. Use this to access standard event fields such as
    * [`event.timeStamp`](https://www.w3.org/TR/dom/#dom-event-timestamp) and methods such as
    * [`event.preventDefault`](https://www.w3.org/TR/dom/#dom-event-preventdefault). While you can use the native
    * [`event.pageX`](https://developer.mozilla.org/en/DOM/event.pageX) and
    * [`event.pageY`](https://developer.mozilla.org/en/DOM/event.pageY), it is often more convenient to transform the
    * event position to the local coordinate system of the container that received the event using
    * `d3.event.mouse`, `d3.event.touch` or `d3.event.touches`.
    *
    * If you use Babel, Webpack, or another ES6-to-ES5 bundler, be aware that the value of `d3.event` changes during an
    * event! An import of `d3.event` must be a [live binding](http://www.2ality.com/2015/07/es6-module-exports.html), so
    * you may need to configure the bundler to import from D3’s ES6 modules rather than from the generated UMD bundle;
    * not all bundlers observe [`jsnext:main`](https://github.com/rollup/rollup/wiki/jsnext:main). Also beware of
    * conflicts with the [`window.event`](https://developer.mozilla.org/en-US/docs/Web/API/Window/event) global.
    *
    * @return Current event.
    */
  def apply(): Event = Facade.event

  /** Invokes the specified listener using the specified `that` as the `this` context. During the invocation, `d3.event`
    * is set to the specified event. After the listener returns (or throws an error), `d3.event` is restored to its
    * previous value. In addition, this method sets `event.sourceEvent` to the prior value of `d3.event`, allowing
    * custom events to retain a reference to the originating native event. Returns the value returned by the listener.
    *
    * @param  event    Event to listen for.
    * @param  listener Event listener function.
    * @param  that     Context to use as the `this` context when invoking the listener.
    * @return Value returned by the listener.
    */
  def customEvent[C, R](event: dom.Event, listener: (C) => R, that: C): R = {
    Facade.customEvent(event, listener, that)
  }

  /** Returns the x and y coordinates of the current event relative to the specified container. The container may be an
    * HTML or SVG container element, such as a [G element](http://www.w3.org/TR/SVG/struct.html#Groups) or an
    * [SVG element](http://www.w3.org/TR/SVG/struct.html#SVGElement).
    *
    * @param  container Container to use as reference when computing coordinates.
    * @return Tuple containing the x and y coordinates of the current event relative to `container`.
    */
  def mouse[C: ContainerElement](container: C): (Double, Double) = {
    Facade.mouse(container)
  }

  /** Returns the x and y coordinates of the touch with the specified identifier associated with the current event
    * relative to the specified container. The container may be an HTML or SVG container element, such as a
    * [G element](http://www.w3.org/TR/SVG/struct.html#Groups) or an
    * [SVG element](http://www.w3.org/TR/SVG/struct.html#SVGElement).
    *
    * If there is no touch with the specified identifier in touches, this method returns `null`. This can be useful for
    * ignoring `touchmove` events where only some touches have moved. If touches is not specified, it defaults to the
    * current event's [`changedTouches`](http://developer.apple.com/library/safari/documentation/UserExperience/Reference/TouchEventClassReference/TouchEvent/TouchEvent.html#//apple_ref/javascript/instp/TouchEvent/changedTouches)
    * property.
    *
    * @param  container  Container to use as reference when computing coordinates.
    * @param  identifier Touch identifier.
    * @return Tuple containing the x and y coordinates of the current event relative to `container`.
    */
  def touch[C: ContainerElement](container: C, identifier: Int): (Double, Double) = {
    Facade.touch(container, identifier)
  }

  /** Returns the x and y coordinates of the touch with the specified identifier associated with the current event
    * relative to the specified container. The container may be an HTML or SVG container element, such as a
    * [G element](http://www.w3.org/TR/SVG/struct.html#Groups) or an
    * [SVG element](http://www.w3.org/TR/SVG/struct.html#SVGElement).
    *
    * If there is no touch with the specified identifier in touches, this method returns `null`. This can be useful for
    * ignoring `touchmove` events where only some touches have moved.
    *
    * @param  container  Container to use as reference when computing coordinates.
    * @param  touches    Touches to use.
    * @param  identifier Touch identifier.
    * @return Tuple containing the x and y coordinates of the current event relative to `container`.
    */
  def touch[C: ContainerElement](container: C, touches: dom.TouchList, identifier: Int): (Double, Double) = {
    Facade.touch(container, touches, identifier)
  }

  /** Returns the x and y coordinates of the touches associated with the current event relative to the specified
    * container. The container may be an HTML or SVG container element, such as a
    * [G element](http://www.w3.org/TR/SVG/struct.html#Groups) or an
    * [SVG element](http://www.w3.org/TR/SVG/struct.html#SVGElement).
    *
    * @param  container Container to use as reference when computing coordinates.
    * @param  touches   Touches to use. If not specified, it defaults to the current event's `touches` property.
    * @return Array containing tuples with the x and y coordinates of the touch events.
    */
  def touches[C: ContainerElement](container: C, touches: dom.TouchList = null): Seq[(Double, Double)] = {
    if (touches != null)
      Facade.touches(container, touches).map(t => (t._1, t._2)).toSeq
    else
      Facade.touches(container).map(t => (t._1, t._2)).toSeq
  }

  /** Returns the x and y coordinates of the specified event relative to the specified container. The container may be an
    * HTML or SVG container element, such as a [G element](http://www.w3.org/TR/SVG/struct.html#Groups) or an
    * [SVG element](http://www.w3.org/TR/SVG/struct.html#SVGElement).
    *
    * @param  container Container to use as reference when computing coordinates.
    * @param  event     Event whose client coordinates to return.
    * @return Tuple containing the x and y coordinates of the current event relative to `container`.
    */
  def clientPoint[C: ContainerElement](container: C, event: ClientPointEvent): (Double, Double) = {
    Facade.clientPoint(container, event)
  }

  @JSImport("d3-selection", JSImport.Namespace)
  @js.native private[Event] object Facade extends js.Object {
    var event: Event = js.native
    def customEvent[C, R](event: dom.Event, listener: js.ThisFunction0[C, R], that: C): R = js.native
    def mouse[C: ContainerElement](container: C): js.Tuple2[Double, Double] = js.native
    def touch[C: ContainerElement](container: C, identifier: Int): js.Tuple2[Double, Double] = js.native
    def touch[C: ContainerElement](container: C, touches: dom.TouchList, identifier: Int): js.Tuple2[Double, Double] = js.native
    def touches[C: ContainerElement](container: C, touches: dom.TouchList = null): js.Array[js.Tuple2[Double, Double]] = js.native
    def clientPoint[C: ContainerElement](container: C, event: ClientPointEvent): js.Tuple2[Double, Double] = js.native
  }
}
