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

import org.platanios.d3.{ArrayLike, D3AttrValue, D3Function, D3TextValue}
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/** A transition is a selection-like interface for animating changes to the DOM. Instead of applying changes
  * instantaneously, transitions smoothly interpolate the DOM from its current state to the desired target state over a
  * given duration.
  *
  * To apply a transition, select elements, call `Selection.transition()`, and then make the desired changes.
  *
  * For example:
  * {{{
  *   d3.select("body")
  *     .transition()
  *     .style("background-color", "red")
  * }}}
  *
  * Transitions support most selection methods (such as `Transition.attr()` and `Transition.style()` in place of
  * `Selection.attr()` and `Selection.style()`), but not all methods are supported. For example, you must append
  * elements or bind data before a transition starts. A `Transition.remove()` operator is provided for convenient
  * removal of elements when the transition ends.
  *
  * To compute intermediate state, transitions leverage a variety of built-in interpolators. Colors, numbers, and
  * transforms are automatically detected. Strings with embedded numbers are also detected, as is common with many
  * styles (such as padding or font sizes) and paths. To specify a custom interpolator, use `Transition.attrTween()`,
  * `Transition.styleTween()` or `Transition.tween()`.
  *
  * === The Life of a Transition ===
  *
  * Immediately after creating a transition, such as by Selection.transition()` or `Transition.transition()`, you may
  * configure the transition using methods such as `Transition.delay()`, `Transition.duration()`, `Transition.attr()`,
  * and `Transition.style()`. Methods that specify target values (such as `Transition.attr()`) are evaluated
  * synchronously. However, methods that require the starting value for interpolation, such as `Transition.attrTween()`
  * and `Transition.styleTween()`, must be deferred until the transition starts.
  *
  * Shortly after creation, either at the end of the current frame or during the next frame, the transition is
  * scheduled. At this point, the delay and start event listeners may no longer be changed. Attempting to do so throws
  * an error with the message `"too late: already scheduled"` (or if the transition has ended,
  * `"transition not found"`).
  *
  * When the transition subsequently starts, it interrupts the active transition of the same name on the same element,
  * if any, dispatching an interrupt event to registered listeners. Note that interrupts happen on start, not creation,
  * and thus even a zero-delay transition will not immediately interrupt the active transition; the old transition is
  * given a final frame. Use `Selection.interrupt()` to interrupt immediately. The starting transition also cancels any
  * pending transitions of the same name on the same element that were created before the starting transition. The
  * transition then dispatches a start event to registered listeners. This is the last moment at which the transition
  * may be modified; after starting, the transition's timing, tweens, and listeners may no longer be changed. Attempting
  * to do so throws an error with the message `"too late: already started"` (or if the transition has ended,
  * `"transition not found"`). The transition initializes its tweens immediately after starting.
  *
  * During the frame the transition starts, but after all transitions starting this frame have been started, the
  * transition invokes its tweens for the first time. Batching tween initialization, which typically involves reading
  * from the DOM, improves performance by avoiding interleaved DOM reads and writes.
  *
  * For each frame that a transition is active, it invokes its tweens with an eased `t`-value ranging from `0` to `1`.
  * Within each frame, the transition invokes its tweens in the order they were registered.
  *
  * When a transition ends, it invokes its tweens a final time with a (non-eased) `t`-value of `1`. It then dispatches
  * an end event to registered listeners. This is the last moment at which the transition may be inspected. After
  * ending, the transition is deleted from the element, and its configuration is destroyed. A transition's configuration
  * is also destroyed on interrupt or cancel. Attempting to inspect a transition after it is destroyed throws an error
  * with the message `"transition not found"`.
  *
  * @author Emmanouil Antonios Platanios
  */
class Transition[+E <: dom.EventTarget, +D, +PE <: dom.EventTarget, +PD] private[selection] (
    private[d3] val facade: Transition.Facade[E, D, PE, PD]
) {
  //region Selecting Elements

  // /** Returns the `index`-th group in this selection. */
  // def apply(index: Index): Selection.Group = facade(index)

  // /** Updated the `index`-th group in this selection. */
  // def update(index: Index, value: Selection.Group): Unit = facade.update(index, value)

  /** Returns the selection corresponding to this transition. */
  def selection(): Selection[E, D, PE, PD] = {
    new Selection(facade.selection())
  }

  /** Returns a new transition on the same selected elements as this transition, scheduled to start when this transition
    * ends. The new transition inherits a reference time equal to this transition's time plus its delay and duration.
    * The new transition also inherits this transition's name, duration, and easing. This method can be used to schedule
    * a sequence of chained transitions.
    *
    * For example:
    * {{{
    *   d3.select.all(".apple")
    *     .transition() // First fade to green.
    *       .style("fill", "green")
    *     .transition() // Then red.
    *       .style("fill", "red")
    *     .transition() // Wait one second. Then brown, and remove.
    *       .delay(1000)
    *       .style("fill", "brown")
    *       .remove()
    * }}}
    *
    * The delay for each transition is relative to the previous transition. Thus, in the above example, apples will stay
    * red for one second before the last transition to brown starts.
    */
  def transition(): Transition[E, D, PE, PD] = {
    new Transition(facade.transition())
  }

  /** Creates an empty transition. `Transition.select` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to the selected children. */
  def select[DE <: dom.EventTarget](selector: Null): Transition[Null, Unit, PE, PD] = {
    new Transition(facade.select(selector))
  }

  /** For each selected element, selects the first descendant element that matches the specified selector string. If no
    * element matches the specified selector for the current element, the element at the current index will be `null` in
    * the returned selection. If the selector is `null`, every element in the returned selection will be `null`,
    * resulting in an empty selection. If the current element has associated data, this data is propagated to the
    * corresponding selected element. If multiple elements match the selector, only the first matching element in
    * document order is selected.
    *
    * Unlike `Transition.selectAll()`, `Transition.select()` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to selected children, Grouping plays an important role in the
    * data join. See [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, creating a
    * subselection via `Selection.select()`, and then creating a new transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .select(selector)
    *     .transition(transition)
    * }}}
    *
    * @param  selector CSS selector string.
    * @tparam DE Type of the descendant element to the be selected.
    * @return Transition.
    */
  def select[DE <: dom.EventTarget](selector: String): Transition[DE, D, PE, PD] = {
    new Transition(facade.select(selector))
  }

  /** Selects the descendant element returned by the selector function, for each element in this selection. If no
    * element matches the specified selector for the current element, the element at the current index will be `null` in
    * the returned selection. If multiple elements match the selector, only the first matching element in document order
    * is selected. `Selection.select` does not affect grouping; it preserves the existing group structure and indexes,
    * and propagates data (if any) to the selected children.
    *
    * Unlike `Transition.selectAll()`, `Transition.select()` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to selected children, Grouping plays an important role in the
    * data join. See [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, creating a
    * subselection via `Selection.select()`, and then creating a new transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .select(selector)
    *     .transition(transition)
    * }}}
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return an element, or `null` if there is no
    *                  matching element.
    * @tparam DE Type of the descendant element to the be selected.
    * @return Transition.
    */
  def select[DE <: dom.EventTarget](selector: D3Function[E, D, DE]): Transition[DE, D, PE, PD] = {
    new Transition(facade.select(selector))
  }

  /** Creates an empty selection. `Transition.selectAll` does not affect grouping; the elements in the returned
    * selection are grouped by their corresponding parent node in this selection, and the group at the current index
    * will be empty.
    *
    * Unlike `Transition.select()`, `Transition.selectAll()` does affect grouping; each selected descendant is grouped
    * by the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    */
  def selectAll(): Transition[Null, Unit, E, D] = {
    new Transition(facade.selectAll())
  }

  /** Creates an empty selection. `Transition.selectAll` does not affect grouping; the elements in the returned
    * selection are grouped by their corresponding parent node in this selection, and the group at the current index
    * will be empty.
    *
    * Unlike `Transition.select()`, `Transition.selectAll()` does affect grouping; each selected descendant is grouped
    * by the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    */
  def selectAll(selector: Null): Transition[Null, Unit, E, D] = {
    new Transition(facade.selectAll(selector))
  }

  /** Creates an empty selection. `Transition.selectAll` does not affect grouping; the elements in the returned
    * selection are grouped by their corresponding parent node in this selection, and the group at the current index
    * will be empty.
    *
    * Unlike `Transition.select()`, `Transition.selectAll()` does affect grouping; each selected descendant is grouped
    * by the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    */
  def selectAll(selector: Unit): Transition[Null, Unit, E, D] = {
    new Transition(facade.selectAll(selector))
  }

  /** For each selected element, selects all descendant elements that match the specified selector string, if any, and
    * returns a transition on the resulting selection. The selector may be specified either as a selector string or a
    * function. If a function, it is evaluated for each selected element, in order, being passed the current datum
    * `d` and index `i`, with the this context as the current DOM element. The new transition has the same id, name, and
    * timing as this transition. However, if a transition with the same id already exists on a selected element, the
    * existing transition is returned for that element.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, creating a
    * subselection via `Selection.selectAll()`, and then creating a new transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .selectAll(selector)
    *     .transition(transition)
    * }}}
    *
    * Unlike `Transition.select()`, `Transition.selectAll()` does affect grouping; each selected descendant is grouped
    * by the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * @param  selector CSS selector string.
    * @tparam DE  Type of the descendant elements to the be selected.
    * @tparam DED Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *             previously set and known datum type.
    * @return Transition.
    */
  def selectAll[DE <: dom.EventTarget, DED](selector: String): Transition[DE, DED, E, D] = {
    new Transition(facade.selectAll(selector))
  }

  /** For each selected element, selects all descendant elements that match the specified selector string, if any, and
    * returns a transition on the resulting selection. The selector may be specified either as a selector string or a
    * function. If a function, it is evaluated for each selected element, in order, being passed the current datum
    * `d` and index `i`, with the this context as the current DOM element. The new transition has the same id, name, and
    * timing as this transition. However, if a transition with the same id already exists on a selected element, the
    * existing transition is returned for that element.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, creating a
    * subselection via `Selection.selectAll()`, and then creating a new transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .selectAll(selector)
    *     .transition(transition)
    * }}}
    *
    * Unlike `Transition.select()`, `Transition.selectAll()` does affect grouping; each selected descendant is grouped
    * by the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return an array of elements (or a pseudo-array,
    *                  such as a `NodeList`), or the empty array if there are no matching elements.
    * @tparam DE  Type of the descendant elements to the be selected.
    * @tparam DED Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *             previously set and known datum type.
    * @return Transition.
    */
  def selectAll[DE >: E <: dom.EventTarget, DED](
      selector: D3Function[E, D, js.Array[DE] | ArrayLike[DE]]
  ): Transition[DE, DED, E, D] = {
    new Transition(facade.selectAll(selector))
  }

  /** For each selected element, selects only the elements that match the specified filter, and returns a transition on
    * the resulting selection. The filter may be specified either as a selector string or a function. If a function, it
    * is evaluated for each selected element, in order, being passed the current datum `d` and index `i`, with the this
    * context as the current DOM element. The new transition has the same id, name and timing as this transition.
    * However, if a transition with the same id already exists on a selected element, the existing transition is
    * returned for that element.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, creating a
    * subselection via `Selection.filter()`, and then creating a new transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .filter(filter)
    *     .transition(transition)
    * }}}
    *
    * @param  selector CSS selector string.
    * @return Filtered transition.
    */
  def filter(selector: String): Transition[E, D, PE, PD] = {
    new Transition(facade.filter(selector))
  }

  /** For each selected element, selects only the elements that match the specified filter, and returns a transition on
    * the resulting selection. The filter may be specified either as a selector string or a function. If a function, it
    * is evaluated for each selected element, in order, being passed the current datum `d` and index `i`, with the this
    * context as the current DOM element. The new transition has the same id, name and timing as this transition.
    * However, if a transition with the same id already exists on a selected element, the existing transition is
    * returned for that element.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, creating a
    * subselection via `Selection.filter()`, and then creating a new transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .filter(filter)
    *     .transition(transition)
    * }}}
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return a boolean value indicating whether or not to
    *                  keep this element.
    * @return Filtered transition.
    */
  def filter(selector: D3Function[E, D, Boolean]): Transition[E, D, PE, PD] = {
    new Transition(facade.filter(selector))
  }

  /** Returns a new transition merging this transition with the specified other transition, which must have the same id
    * as this transition. The returned transition has the same number of groups, the same parents, the same name and the
    * same id as this transition. Any missing (`null`) elements in this transition are filled with the corresponding
    * element, if present (not `null`), from the other transition.
    *
    * This method is equivalent to deriving the selection for this transition via `Transition.selection()`, merging with
    * the selection likewise derived from the other transition via `Selection.merge()`, and then creating a new
    * transition via `Selection.transition()`:
    * {{{
    *   transition
    *     .selection()
    *     .merge(other.selection())
    *     .transition(transition)
    * }}}
    *
    * @param  other Selection to merge with.
    * @return Merged transition.
    */
  def merge[ME >: E <: dom.EventTarget, MD >: D, MPE >: PE <: dom.EventTarget, MPD >: PD](
      other: Transition[ME, MD, MPE, MPD]
  ): Transition[E, D, PE, PD] = {
    new Transition(facade.merge(other.facade))
  }

  //endregion Selecting Elements

  //region Modifying Elements

  /** Returns the current value of the specified attribute for the first (non-`null`) element in the selection. This is
    * generally useful only if you know that the selection contains exactly one element.
    *
    * @param  name Attribute name.
    * @return Attribute value.
    */
  def attr(name: String): String = {
    facade.attr(name)
  }

  /** For each selected element, assigns the attribute tween for the attribute with the specified name to the specified
    * target value. The starting value of the tween is the attribute's value when the transition starts. The target
    * value may be specified either as a constant or a function. If a function, it is immediately evaluated for each
    * selected element, in order, being passed the current datum `d` and index `i`, with the this context as the current
    * DOM element.
    *
    * If the target value is `null`, the attribute is removed when the transition starts. Otherwise, an interpolator is
    * chosen based on the type of the target value, using the following algorithm:
    *
    *   1. If `value` is a number, use `d3.interpolate.number`.
    *   2. If `value` is a color or a string coercible to a color, use `d3.interpolate.rgb`.
    *   3. Else, use `d3.interpolate.string`.
    *
    * To apply a different interpolator, use `Transition.attrTween()`.
    *
    * @param  name  Attribute name.
    * @param  value Attribute value.
    * @return Transition after the modification is applied.
    */
  def attr[T](name: String, value: D3Function[E, D, T])(implicit ev: T => D3AttrValue): Transition[E, D, PE, PD] = {
    new Transition(facade.attr(name, value)(ev))
  }

  /** Returns the current interpolator factory for attribute with the specified name, or undefined if no such tween
    * exists.
    *
    * @param  name Attribute name.
    * @return Interpolator factory.
    */
  def attrTween[SE >: E <: dom.EventTarget, SD >: D](
      name: String
  ): Option[D3Function[SE, SD, js.Function1[Double, String]]] = {
    facade.attrTween[SE, SD](name).toOption
  }

  /** If factory is specified and not `null`, this method assigns the attribute tween for the attribute with the
    * specified name to the specified interpolator factory. An interpolator factory is a function that returns an
    * interpolator. When the transition starts, the factory is evaluated for each selected element, in order, being
    * passed the current datum `d` and index `i`, with the this context as the current DOM element. The returned
    * interpolator will then be invoked for each frame of the transition, in order, being passed the eased time `t`,
    * typically in the range `[0, 1]`. Lastly, the return value of the interpolator will be used to set the attribute
    * value. The interpolator must return a string. To remove an attribute at the start of a transition, use
    * `Transition.attr()`. To remove an attribute at the end of a transition, use `Transition.on()` to listen for the
    * end event.
    *
    * If the specified factory is `null`, removes the previously-assigned attribute tween of the specified name, if any.
    *
    * For example, to interpolate the `fill` attribute from red to blue:
    * {{{
    *   transition.attrTween("fill", () => d3.interpolate.rgb("red", "blue"))
    * }}}
    * Or, to interpolate from the current fill to blue, like `Transition.attr()`:
    * {{{
    *   transition.attrTween("fill", (e: dom.Element) => d3.interpolate.rgb(e.getAttribute("fill"), "blue"))
    * }}}
    * Or, to apply a custom rainbow interpolator:
    * {{{
    *   transition.attrTween("fill", () => (t: Double) => "hsl(" + t * 360 + ", 100%, 50%)")
    * }}}
    *
    * This method is useful to specify a custom interpolator, such as one that understands SVG paths. A useful technique
    * is data interpolation, where `d3.interpolate.object` is used to interpolate two data values, and the resulting
    * value is then used (say, with a shape) to compute the new attribute value.
    *
    * @param  name    Attribute name.
    * @param  factory Interpolator factory.
    * @return Transition after the modification is applied.
    */
  def attrTween(
      name: String,
      factory: D3Function[E, D, js.Function1[Double, String]]
  ): Transition[E, D, PE, PD] = {
    new Transition(facade.attrTween(name, factory))
  }

  /** Returns the current value of the specified style property for the first (non-`null`) element in the selection.
    * The current value is defined as the element's inline value, if present, and otherwise its computed value.
    * Accessing the current style value is generally useful only if you know the selection contains exactly one element.
    *
    * @param  name Style property name.
    * @return Style property value.
    */
  def style(name: String): String = {
    facade.style(name)
  }

  /** For each selected element, assigns the style tween for the style with the specified name to the specified target
    * value with the specified priority. The starting value of the tween is the style’s inline value if present, and
    * otherwise its computed value, when the transition starts. The target value may be specified either as a constant
    * or a function. If a function, it is immediately evaluated for each selected element, in order, being passed the
    * current datum `d` and index `i`, with the this context as the current DOM element.
    *
    * If the target value is `null`, the style is removed when the transition starts. Otherwise, an interpolator is
    * chosen based on the type of the target value, using the following algorithm:
    *
    *   1. If `value` is a number, use `d3.interpolate.number`.
    *   2. If `value` is a color or a string coercible to a color, use `d3.interpolate.rgb`.
    *   3. Else, use `d3.interpolate.string`.
    *
    * To apply a different interpolator, use `Transition.styleTween()`.
    *
    * @param  name      Style property name.
    * @param  value     Style property value.
    * @param  important If `true`, `"important"` will be used as the priority value for the property.
    * @return Transition after the modification is applied.
    */
  def style[T](
      name: String,
      value: D3Function[E, D, T],
      important: Boolean = false
  )(implicit ev: T => D3AttrValue): Transition[E, D, PE, PD] = {
    if (important)
      new Transition(facade.style(name, value, priority = "important"))
    else
      new Transition(facade.style(name, value))
  }

  /** Returns the current interpolator factory for the style property with the specified name, or undefined if no such
    * tween exists.
    *
    * @param  name Style property name.
    * @return Interpolator factory.
    */
  def styleTween[SE >: E <: dom.EventTarget, SD >: D](
      name: String
  ): Option[D3Function[SE, SD, js.Function1[Double, String]]] = {
    facade.styleTween[SE, SD](name).toOption
  }

  /** If factory is specified and not `null`, this method assigns the style tween for the style property with the
    * specified name to the specified interpolator factory. An interpolator factory is a function that returns an
    * interpolator. When the transition starts, the factory is evaluated for each selected element, in order, being
    * passed the current datum `d` and index `i`, with the this context as the current DOM element. The returned
    * interpolator will then be invoked for each frame of the transition, in order, being passed the eased time `t`,
    * typically in the range `[0, 1]`. Lastly, the return value of the interpolator will be used to set the attribute
    * value. The interpolator must return a string. To remove a style at the start of a transition, use
    * `Transition.style()`. To remove a style at the end of a transition, use `Transition.on()` to listen for the
    * end event.
    *
    * If the specified factory is `null`, removes the previously-assigned style tween of the specified name, if any.
    *
    * For example, to interpolate the `fill` attribute from red to blue:
    * {{{
    *   transition.styleTween("fill", () => d3.interpolate.rgb("red", "blue"))
    * }}}
    * Or, to interpolate from the current fill to blue, like `Transition.style()`:
    * {{{
    *   transition.styleTween("fill", (e: dom.Element) => d3.interpolate.rgb(e.style("fill"), "blue"))
    * }}}
    * Or, to apply a custom rainbow interpolator:
    * {{{
    *   transition.styleTween("fill", () => (t: Double) => "hsl(" + t * 360 + ", 100%, 50%)")
    * }}}
    *
    * This method is useful to specify a custom interpolator, such as one that understands SVG paths. A useful technique
    * is data interpolation, where `d3.interpolate.object` is used to interpolate two data values, and the resulting
    * value is then used (say, with a shape) to compute the new attribute value.
    *
    * @param  name      Style property name.
    * @param  factory   Interpolator factory.
    * @param  important If `true`, `"important"` will be used as the priority value for the property.
    * @return Transition after the modification is applied.
    */
  def styleTween(
      name: String,
      factory: D3Function[E, D, js.Function1[Double, String]],
      important: Boolean = false
  ): Transition[E, D, PE, PD] = {
    if (important)
      new Transition(facade.styleTween(name, factory, priority = "important"))
    else
      new Transition(facade.styleTween(name, factory))
  }

  /** Returns the text content for the first (non-`null`) element in the selection. This is generally useful only if you
    * know the selection contains exactly one element.
    *
    * @return Text content for the first (non-`null`) element in the selection.
    */
  def text(): String = {
    facade.text()
  }

  /** Sets the text content to the specified value on all selected elements, replacing any existing child elements. If
    * the value is a constant, then all elements are given the same text content; otherwise, if the value is a function,
    * it is evaluated for each selected element, in order, being passed the current datum (`d`), the current index
    * (`i`), and the current group (`nodes`), with this as the current DOM element (`nodes(i)`). The function's return
    * value is then used to set each element's text content. A `null` value will clear the content.
    *
    * To interpolate text rather than to set it on start, use `Transition.tween()`
    * ([example](http://bl.ocks.org/mbostock/7004f92cac972edef365)) or append a replacement element and cross-fade
    * opacity ([example](http://bl.ocks.org/mbostock/f7dcecb19c4af317e464)). Text is not interpolated by default because
    * that is usually undesirable.
    *
    * @param  value Value function.
    * @return Transition after the modification is applied.
    */
  def text[T](value: D3Function[E, D, T])(implicit ev: T => D3TextValue): Transition[E, D, PE, PD] = {
    new Transition(facade.text(value))
  }

  /** For each selected element, removes the element when the transition ends, as long as the element has no other
    * active or pending transitions. If the element has other active or pending transitions, does nothing. */
  def remove(): Transition[E, D, PE, PD] = {
    new Transition(facade.remove())
  }

  /** Returns the current interpolator factory for the tween with the specified name, or undefined if no such tween
    * exists.
    *
    * @param  name Tween name.
    * @return Interpolator factory.
    */
  def tween[SE >: E <: dom.EventTarget, SD >: D](
      name: String
  ): Option[D3Function[SE, SD, js.Function1[Double, String]]] = {
    facade.tween[SE, SD](name).toOption
  }

  /** For each selected element, assigns the tween with the specified name with the specified value function. The value
    * must be specified as a function that returns a function. When the transition starts, the value function is
    * evaluated for each selected element, in order, being passed the current datum `d` and index `i`, with the this
    * context as the current DOM element. The returned function is then invoked for each frame of the transition, in
    * order, being passed the eased time `t`, typically in the range `[0, 1]`. If the specified value is `null`, this
    * method removes the previously-assigned tween of the specified name, if any.
    *
    * For example, to interpolate the fill attribute to blue, like `Transition.attr()`:
    * {{{
    *   transition.tween("attr.fill", (e: dom.Element) => {
    *     val i = d3.interpolate.rgb(e.getAttribute("fill"), "blue")
    *     (t: Double) => e.setAttribute("fill", i(t))
    *   })
    * }}}
    *
    * This method is useful to specify a custom interpolator, or to perform side-effects, say to animate the
    * [scroll offset](http://bl.ocks.org/mbostock/1649463).
    *
    * @param  name    Tween name.
    * @param  tweenFn Tween function.
    * @return Transition after the modification is applied.
    */
  def tween(name: String, tweenFn: D3Function[E, D, js.Function1[Double, Unit]]): Transition[E, D, PE, PD] = {
    new Transition(facade.tween(name, tweenFn))
  }

  //endregion Modifying Elements

  //region Timing

  /** Returns the current value of the delay for the first (non-`null`) element in the transition. This is generally
    * useful only if you know that the transition contains exactly one element. */
  def delay(): Int = {
    facade.delay()
  }

  /** For each selected element, sets the transition delay to the specified value in milliseconds. The value may be
    * specified either as a constant or a function. If a function, it is immediately evaluated for each selected
    * element, in order, being passed the current datum `d` and index `i`, with the this context as the current DOM
    * element. The function's return value is then used to set each element's transition delay. If a delay is not
    * specified, it defaults to zero.
    *
    * Setting the delay to a multiple of the index `i` is a convenient way to stagger transitions across a set of
    * elements. For example:
    * {{{
    *   transition.delay((_, i: Int) => i * 10)
    * }}}
    *
    * Of course, you can also compute the delay as a function of the data, or sort the selection before computing an
    * index-based delay.
    *
    * @param  milliseconds Delay value function.
    * @return Transition after the modification is applied.
    */
  def delay(milliseconds: D3Function[E, D, Int]): Transition[E, D, PE, PD] = {
    new Transition(facade.delay(milliseconds))
  }

  /** Returns the current value of the duration for the first (non-`null`) element in the transition. This is generally
    * useful only if you know that the transition contains exactly one element. */
  def duration(): Int = {
    facade.duration()
  }

  /** For each selected element, sets the transition duration to the specified value in milliseconds. The value may be
    * specified either as a constant or a function. If a function, it is immediately evaluated for each selected
    * element, in order, being passed the current datum `d` and index `i`, with the this context as the current DOM
    * element. The function's return value is then used to set each element's transition duration. If a duration is not
    * specified, it defaults to 250ms.
    *
    * @param  milliseconds Duration value function.
    * @return Transition after the modification is applied.
    */
  def duration(milliseconds: D3Function[E, D, Int]): Transition[E, D, PE, PD] = {
    new Transition(facade.duration(milliseconds))
  }

  /** Returns the current easing function for the first (non-`null`) element in the transition. This is generally
    * useful only if you know that the transition contains exactly one element. */
  def ease(): (Double) => Double = {
    facade.ease()
  }

  /** Specifies the transition [easing function](https://github.com/d3/d3-ease) for all selected elements. The value
    * must be specified as a function. The easing function is invoked for each frame of the animation, being passed the
    * normalized time `t` in the range `[0, 1]`. It must then return the eased time `t'` which is typically also in the
    * range `[0, 1]`. A good easing function should return `0` if `t = 0` and `1` if `t = 1`. If an easing function is
    * not specified, it defaults to `d3.ease.cubic`.
    *
    * @param  milliseconds Easing value function.
    * @return Transition after the modification is applied.
    */
  def ease(milliseconds: D3Function[E, D, js.Function1[Double, Double]]): Transition[E, D, PE, PD] = {
    new Transition(facade.ease(milliseconds))
  }

  //endregion Timing

  //region Handling Events

  // TODO: Type the `domTypeNames` for `on`, given that they can only be one of three types.

  /** Returns the currently-assigned listener for the specified event typename on the first (non-`null`) selected
    * element, if any. If multiple typenames are specified, the first matching listener is returned.
    *
    * @param  domTypeNames Event type, such as `"click"`, `"mouseover"`, or `"submit"`. Any DOM event type supported by
    *                      your browser may be used. The type may be optionally followed by a period (`.`) and a name.
    *                      The optional name allows multiple callbacks to be registered to receive events of the same
    *                      type, such as `"click.foo"` and `"click.bar"`. To specify multiple typenames, separate
    *                      typenames with spaces, such as `"input change"` or `"click.foo click.bar"`.
    * @return Currently assigned listener for the specified event typename on the first (non-`null`) selected element,
    *         if any.
    */
  def on[SE >: E <: dom.EventTarget, SD >: D](domTypeNames: String): D3Function[SE, SD, Unit] = {
    facade.on(domTypeNames)
  }

  /** Adds or removes a listener to each selected element for the specified event typenames.
    *
    * The typenames is one of the following string event types:
    *
    *   - `"start"`: when the transition starts.
    *   - `"end"`: when the transition ends.
    *   - `"interrupt"`: when the transition is interrupted.
    *
    * See [The Life of a Transition](https://github.com/d3/d3-transition#the-life-of-a-transition) for more. Note that
    * these are not native DOM events as implemented by `Selection.on()` and `Selection.dispatch()`, but transition
    * events!
    *
    * When a specified event is dispatched on a selected element, the specified listener will be evaluated for the
    * element, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`), with
    * this as the current DOM element (`nodes(i)`). Listeners always see the latest datum for their element, but the
    * index is a property of the selection and is fixed when the listener is assigned. To update the index, re-assign
    * the listener. To access the current event within a listener, use `d3.event`.
    *
    * If an event listener was previously registered for the same typename on a selected element, the old listener is
    * removed before the new listener is added. To remove a listener, pass `null` as the listener. To remove all
    * listeners for a given name, pass `null` as the listener and `".foo"` as the typename, where `"foo"` is the name.
    * To remove all listeners with no name, specify `"."` as the typename.
    *
    * @param  domTypeNames Event type, such as `"start"`, `"end"`, or `"interrupt"`. Any DOM event type supported by
    *                      your browser may be used. The type may be optionally followed by a period (`.`) and a name.
    *                      The optional name allows multiple callbacks to be registered to receive events of the same
    *                      type, such as `"click.foo"` and `"click.bar"`. To specify multiple typenames, separate
    *                      typenames with spaces, such as `"input change"` or `"click.foo click.bar"`.
    * @param  listener     Listener function.
    * @return Transition after the modification.
    */
  def on(domTypeNames: String, listener: D3Function[E, D, Unit]): Transition[E, D, PE, PD] = {
    new Transition(facade.on(domTypeNames, listener))
  }

  //endregion Handling Events

  //region Control Flow

  /** Invokes the specified function for each selected element, passing in the current datum (`d`), the current index
    * (`i`), and the current group (`nodes`), with this of the current DOM element (`nodes(i)`). This method can be used
    * to invoke arbitrary code for each selected element, and is useful for creating a context to access parent and
    * child data simultaneously.
    *
    * Equivalent to `Selection.each()`.
    *
    * @param  function Function which is invoked for each selected element.
    * @return This transition.
    */
  def each(function: D3Function[E, D, Unit]): Transition[E, D, PE, PD] = {
    new Transition(facade.each(function))
  }

  /** Invokes the specified function exactly once, passing in this transition and returns this transition. This is
    * equivalent to invoking the function by hand but facilitates method chaining. For example, to set several styles
    * in a reusable function:
    * {{{
    *   def color(
    *     transition: Transition[dom.Element, js.Any, dom.Element, js.Any],
    *     fill: String,
    *     stroke: String
    *   ): Unit = {
    *     transition
    *       .attr("fill", fill)
    *       .attr("stroke", stroke)
    *   }
    * }}}
    * Now say:
    * {{{
    *   d3.select.all("div").transition().call(color(_, "red", "blue"))
    * }}}
    * This is roughly equivalent to:
    * {{{
    *   color(d3.select.all("div").transition(), "red", "blue")
    * }}}
    *
    * Equivalent to `Selection.call()`.
    *
    * @param  function Function to call.
    * @return This transition.
    */
  def call[SE >: E <: dom.EventTarget, SD >: E, SPE >: PE <: dom.EventTarget, SPD >: PD](
      function: (Transition[SE, SD, SPE, SPD]) => _
  ): Transition[E, D, PE, PD] = {
    new Transition(facade.call((f: Transition.Facade[SE, SD, SPE, SPD]) => function(new Transition[SE, SD, SPE, SPD](f))))
  }

  /** Returns `true` if this transition contains no (non-null) elements. */
  def empty(): Boolean = facade.empty()

  /** Returns an array of all (non-null) elements in this transition. */
  def nodes[SE >: E <: dom.EventTarget](): Seq[SE] = facade.nodes().toSeq

  /** Returns the first (non-null) element in this transition. If the transition is empty, returns `null`. */
  def node(): E = facade.node()

  /** Returns the total number of elements in this transition. */
  def size(): Int = facade.size()

  //endregion Control Flow
}

object Transition {
  /** Interrupts the active transition of the specified name on the specified node, and cancels any pending transitions
    * with the specified name, if any. If a name is not specified, `null` is used. See also `Selection.interrupt()`.
    *
    * @param  node Node whose active transition to interrupt.
    * @param  name Optional transition name.
    */
  def interrupt(node: dom.EventTarget, name: String = null): Unit = {
    if (name != null)
      Facade.interrupt(node, name)
    else
      Facade.interrupt(node)
  }

  /** Returns a new transition on the root element, `dom.document.documentElement`, with the specified name. If a name
    * is not specified, `null` is used. The new transition is only exclusive with other transitions of the same name.
    * The name may also be a transition instance. See `Selection.transition()`.
    *
    * This method is equivalent to:
    * {{{
    *   d3.selection()
    *     .transition(name)
    * }}}
    *
    * @param  name Optional transition name.
    * @return Transition.
    */
  def transition[OldD](name: String = null): Transition[dom.html.Element, OldD, Null, Unit] = {
    if (name != null)
      new Transition(Facade.transition[OldD](name))
    else
      new Transition(Facade.transition[OldD]())
  }

  /** Returns a new transition on the root element, `dom.document.documentElement`, with the specified transition base.
    * The new transition is only exclusive with other transitions of the same name. See `Selection.transition()`.
    *
    * This method is equivalent to:
    * {{{
    *   d3.selection()
    *     .transition(transition)
    * }}}
    *
    * @param  transition Optional transition base.
    * @return Transition.
    */
  def transition[OldD](
      transition: Transition[dom.EventTarget, js.Any, dom.EventTarget, js.Any]
  ): Transition[dom.html.Element, OldD, Null, Unit] = {
    new Transition(Facade.transition[OldD](transition.facade))
  }

  /** Returns the active transition on the specified node with the specified name, if any. If no name is specified,
    * `null` is used. Returns `null` if there is no such active transition on the specified node. This method is useful
    * for creating chained transitions.
    *
    * For example, to initiate disco mode:
    * {{{
    *   def repeat(e: dom.EventTarget): Unit = {
    *     d3.transition.active(e)
    *         .style("fill", "red")
    *       .transition()
    *         .style("fill", "green")
    *       .transition()
    *         .style("fill", "blue")
    *       .transition()
    *         .on("start", repeat)
    *
    *   d3.select.all("circle").transition()
    *     .delay((_, i: Int) => i * 50)
    *     .on("start", repeat)
    * }}}
    * See [chained transitions](http://bl.ocks.org/mbostock/70d5541b547cc222aa02) for an example.
    *
    * @param  node Node for which to return the active transition.
    * @param  name Optional node name.
    * @return Active transition of the specified node.
    */
  def active[E <: dom.EventTarget, D, PE <: dom.EventTarget, PD](
      node: E,
      name: String = null
  ): Option[Transition[E, D, PE, PD]] = {
    if (name != null)
      Facade.active(node, name).toOption.map(new Transition(_))
    else
      Facade.active(node).toOption.map(new Transition(_))
  }

  @JSImport("d3-transition", JSImport.Namespace)
  @js.native private[Transition] object Facade extends js.Object {
    def interrupt(node: dom.EventTarget, name: String = null): Unit = js.native
    def transition[OldD](name: String = null): Facade[dom.html.Element, OldD, Null, Unit] = js.native
    def transition[OldD](transition: Facade[dom.EventTarget, js.Any, dom.EventTarget, js.Any]): Facade[dom.html.Element, OldD, Null, Unit] = js.native
    def active[E <: dom.EventTarget, D, PE <: dom.EventTarget, PD](node: E, name: String = null): js.UndefOr[Facade[E, D, PE, PD]] = js.native
  }

  @js.native trait Facade[+E <: dom.EventTarget, +D, +PE <: dom.EventTarget, +PD] extends js.Object {
    // @JSBracketAccess def apply(index: Index): Selection.Group = js.native
    // @JSBracketAccess def update(index: Index, value: Selection.Group): Unit = js.native

    def selection(): Selection.Facade[E, D, PE, PD] = js.native
    def transition(): Facade[E, D, PE, PD] = js.native

    def select[DE <: dom.EventTarget](selector: Null): Facade[Null, Unit, PE, PD] = js.native
    def select[DE <: dom.EventTarget](selector: String): Facade[DE, D, PE, PD] = js.native
    def select[DE <: dom.EventTarget](selector: D3Function[E, D, DE]): Facade[DE, D, PE, PD] = js.native
    def selectAll(): Facade[Null, Unit, E, D] = js.native
    def selectAll(selector: Null): Facade[Null, Unit, E, D] = js.native
    def selectAll(selector: Unit): Facade[Null, Unit, E, D] = js.native
    def selectAll[DE <: dom.EventTarget, DED](selector: String): Facade[DE, DED, E, D] = js.native
    def selectAll[DE >: E <: dom.EventTarget, DED](selector: D3Function[E, D, js.Array[DE] | ArrayLike[DE]]): Facade[DE, DED, E, D] = js.native
    def filter(selector: String): Facade[E, D, PE, PD] = js.native
    def filter(selector: D3Function[E, D, Boolean]): Facade[E, D, PE, PD] = js.native
    def merge[ME >: E <: dom.EventTarget, MD >: D, MPE >: PE <: dom.EventTarget, MPD >: PD](other: Facade[ME, MD, MPE, MPD]): Facade[E, D, PE, PD] = js.native

    def attr(name: String): String = js.native
    def attr[T](name: String, value: D3Function[E, D, T])(implicit ev: T => D3AttrValue): Facade[E, D, PE, PD] = js.native
    def attrTween[SE >: E <: dom.EventTarget, SD >: D](name: String): js.UndefOr[D3Function[SE, SD, js.Function1[Double, String]]] = js.native
    def attrTween(name: String, factory: D3Function[E, D, js.Function1[Double, String]]): Facade[E, D, PE, PD] = js.native
    def style(name: String): String = js.native
    def style[T](name: String, value: D3Function[E, D, T], priority: String = null)(implicit ev: T => D3AttrValue): Facade[E, D, PE, PD] = js.native
    def styleTween[SE >: E <: dom.EventTarget, SD >: D](name: String): js.UndefOr[D3Function[SE, SD, js.Function1[Double, String]]] = js.native
    def styleTween(name: String, factory: D3Function[E, D, js.Function1[Double, String]], priority: String = null): Facade[E, D, PE, PD] = js.native
    def text(): String = js.native
    def text[T](value: D3Function[E, D, T])(implicit ev: T => D3TextValue): Facade[E, D, PE, PD] = js.native
    def remove(): Facade[E, D, PE, PD] = js.native
    def tween[SE >: E <: dom.EventTarget, SD >: D](name: String): js.UndefOr[D3Function[SE, SD, js.Function1[Double, String]]] = js.native
    def tween(name: String, tweenFn: D3Function[E, D, js.Function1[Double, Unit]]): Facade[E, D, PE, PD] = js.native

    def delay(): Int = js.native
    def delay(milliseconds: D3Function[E, D, Int]): Facade[E, D, PE, PD] = js.native
    def duration(): Int = js.native
    def duration(milliseconds: D3Function[E, D, Int]): Facade[E, D, PE, PD] = js.native
    def ease(): js.Function1[Double, Double] = js.native
    def ease(milliseconds: D3Function[E, D, js.Function1[Double, Double]]): Facade[E, D, PE, PD] = js.native

    def on[SE >: E <: dom.EventTarget, SD >: D](domTypeNames: String): D3Function[SE, SD, Unit] = js.native
    def on(domTypeNames: String, listener: D3Function[E, D, Unit]): Facade[E, D, PE, PD] = js.native

    def each(func: D3Function[E, D, Unit]): Facade[E, D, PE, PD] = js.native
    def call[SE >: E <: dom.EventTarget, SD >: E, SPE >: PE <: dom.EventTarget, SPD >: PD](function: js.Function1[Facade[SE, SD, SPE, SPD], _]): Facade[E, D, PE, PD] = js.native
    def empty(): Boolean = js.native
    def nodes[SE >: E <: dom.EventTarget](): js.Array[SE] = js.native
    def node(): E = js.native
    def size(): Int = js.native
  }
}
