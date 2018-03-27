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

import org.platanios.d3._
import org.scalajs.dom

import scala.language.reflectiveCalls
import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.|

// TODO: Add CSS/D3 selector trait and helper constructors.

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-selection", JSImport.Namespace)
@js.native object Selection extends js.Object {
  //region Selecting Elements

  @js.native trait Group extends js.Array[dom.EventTarget] {
    var parentNode: dom.EventTarget = js.native
  }

  val selection: js.Function0[Selection[dom.raw.HTMLElement, js.Any, Null, Unit]] = js.native

  /** Selects the first element that matches the specified selector string. If no elements match the selector, then this
    * method returns an empty selection. If multiple elements match the selector, only the first matching element
    * (in document order) will be selected.
    *
    * @param  selector CSS selector string.
    * @tparam E Type of the element to the be selected.
    * @tparam D Type of the datum on the selected element. This is useful when re-selecting an element with a
    *           previously set and known datum type.
    * @return Selection.
    */
  def select[E <: dom.EventTarget, D](selector: String): Selection[E, D, dom.raw.HTMLElement, js.Any] = js.native

  /** Selects the specified node element.
    *
    * @param  node Element to be selected.
    * @tparam E Type of the element to the be selected.
    * @tparam D Type of the datum on the selected element. This is useful when re-selecting an element with a
    *           previously set and known datum type.
    * @return Selection.
    */
  def select[E <: dom.EventTarget, D](node: E): Selection[E, D, Null, Unit] = js.native

  /** Creates an empty selection. */
  def selectAll(): Selection[Null, Unit, Null, Unit] = js.native

  /** Creates an empty selection. */
  def selectAll(selector: Null): Selection[Null, Unit, Null, Unit] = js.native

  /** Creates an empty selection. */
  def selectAll(selector: Unit): Selection[Null, Unit, Null, Unit] = js.native

  /** Selects all elements that match the specified selector string. The elements will be selected in document order
    * (top-to-bottom). If no elements in the document match the selector, then this method returns an empty selection.
    *
    * @param  selector CSS selector string.
    * @tparam E Type of the elements to the be selected.
    * @tparam D Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *           previously set and known datum type.
    * @return Selection.
    */
  def selectAll[E <: dom.EventTarget, D](selector: String): Selection[E, D, dom.raw.HTMLElement, js.Any] = js.native

  /** Selects the specified array of node elements.
    *
    * @param  nodes Elements to be selected.
    * @tparam E Type of the elements to the be selected.
    * @tparam D Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *           previously set and known data type.
    * @return Selection.
    */
  def selectAll[E <: dom.EventTarget, D](nodes: js.Array[E]): Selection[E, D, Null, Unit] = js.native

  /** Selects the specified node elements. This signature allows for the selection of nodes contained in a `NodeList`,
    * `HTMLCollection`, or any other similar data structure.
    *
    * @param  nodes Elements to be selected.
    * @tparam E Type of the elements to the be selected.
    * @tparam D Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *           previously set and known data type.
    * @return Selection.
    */
  def selectAll[E <: dom.EventTarget, D](nodes: ArrayLike[E]): Selection[E, D, Null, Unit] = js.native

  // TODO: !!!
  def matcher(selector: String): js.Function1[dom.EventTarget, Boolean] = js.native
  def selector[DE <: dom.Node](selector: String): js.Function1[dom.EventTarget, DE] = js.native
  def selectorAll[DE <: dom.Node](selector: String): js.Function1[dom.EventTarget, dom.NodeListOf[DE]] = js.native

  /** Returns the owner window of the specified node. If `node` is a node, then this method returns the owner document's
    * default view; if it is a document, then returns its default view; otherwise, it returns the node.
    *
    * @param  node A DOM window.
    */
  def window(node: dom.Window): dom.Window = js.native

  /** Returns the owner window of the specified node. If `node` is a node, then this method returns the owner document's
    * default view; if it is a document, then returns its default view; otherwise, it returns the node.
    *
    * @param  node A DOM document.
    */
  def window(node: dom.Document): dom.Window = js.native

  /** Returns the owner window of the specified node. If `node` is a node, then this method returns the owner document's
    * default view; if it is a document, then returns its default view; otherwise, it returns the node.
    *
    * @param  node A DOM element.
    */
  def window(node: dom.Element): dom.Window = js.native

  /** Returns the value of the style property with the specified name for the specified node. If the node has an inline
    * style with the specified name, then its value is returned; otherwise, the computed property value is returned.
    * See also `Selection.style()`.
    *
    * @param  node A DOM node (e.g. `HTMLElement`, `SVGElement`) for which to retrieve the style property.
    * @param  name Name of the style property.
    * @return Value of the style property.
    */
  def style(node: dom.Element, name: String): String = js.native

  //endregion Selecting Elements

  //region Modifying Elements

  /** Given the specified element name, returns a single-element selection containing a detached element of the given
    * name in the current document.
    *
    * @param  name Tag name of the element to be added.
    * @tparam E Type of the element to be added.
    * @return Selection containing a detached element of the given name in the current document.
    */
  def create[E <: dom.EventTarget](name: String): Selection[E, Unit, Null, Unit] = js.native

  /** Given the specified element name, returns a function which creates an element of the given name, assuming that
    * `"this"` is the parent element.
    *
    * @param  name Tag name of the element to be added.
    * @tparam E Type of the element to be added.
    * @return Function which creates an element of the given name.
    */
  def creator[E <: dom.EventTarget](name: String): js.Function1[dom.EventTarget, E] = js.native

  //endregion Modifying Elements

  //region Handling Events

  val event: dom.Event with BaseEvent = js.native
  def customEvent[C, R](event: BaseEvent, listener: js.Function, that: C, args: js.Any*): R = js.native
  def mouse[C: ContainerElement](container: C): js.Tuple2[Double, Double] = js.native
  def touch[C: ContainerElement](container: C, identifier: Double): js.Tuple2[Double, Double] | Null = js.native
  def touch[C: ContainerElement](container: C, touches: dom.TouchList, identifier: Double): js.Tuple2[Double, Double] | Null = js.native
  def touches[C: ContainerElement](container: C, touches: dom.TouchList = ???): js.Array[js.Tuple2[Double, Double]] = js.native
  def clientPoint[C: ContainerElement](container: C, event: ClientPointEvent): js.Tuple2[Double, Double] = js.native

  //endregion Handling Events
}

/** D3 selection of elements.
  * 
  * @tparam E  Type of the selected element(s).
  * @tparam D  Datum type of the selected element(s).
  * @tparam PE Type of the parent element(s) in the selection.
  * @tparam PD Datum type of the parent element(s) in the selection.
  */
@js.native trait Selection[E <: dom.EventTarget, D, PE <: dom.EventTarget, PD] extends js.Object {
  //region Selecting Elements

  @JSBracketAccess def apply(index: Index): Group = js.native

  @JSBracketAccess def update(index: Index, value: Group): Unit = js.native

  /** Selects the first descendant element that matches the specified selector string, for each element in this
    * selection. If no element matches the specified selector for the current element, the element at the current index
    * will be `null` in the returned selection. If multiple elements match the selector, only the first matching element
    * in document order is selected. `Selection.select` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to the selected children.
    *
    * If the current element has any associated data, then this data is also propagated to the corresponding selected
    * element.
    *
    * @param  selector CSS selector string.
    * @tparam DE Type of the descendant element to the be selected.
    * @return Selection.
    */
  def select[DE <: dom.EventTarget](selector: String): Selection[DE, D, PE, PD] = js.native

  /** Creates an empty selection. `Selection.select` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to the selected children. */
  def select[DE <: dom.EventTarget](selector: Null): Selection[Null, Unit, PE, PD] = js.native

  /** Selects the descendant element returned by the selector function, for each element in this selection. If no
    * element matches the specified selector for the current element, the element at the current index will be `null` in
    * the returned selection. If multiple elements match the selector, only the first matching element in document order
    * is selected. `Selection.select` does not affect grouping; it preserves the existing group structure and indexes,
    * and propagates data (if any) to the selected children.
    *
    * If the current element has any associated data, then this data is also propagated to the corresponding selected
    * * element.
    *
    * The generic represents the type of the descendant element to be selected.
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return an element, or `null` if there is no
    *                  matching element.
    * @tparam DE Type of the descendant element to the be selected.
    * @return Selection.
    */
  def select[DE <: dom.EventTarget, S: D3VOrFn[DE]#CB](selector: S): Selection[DE, D, PE, PD] = js.native

  /** Creates an empty selection. `Selection.selectAll` does not affect grouping; the elements in the returned selection
    * are grouped by their corresponding parent node in this selection, and the group at the current index will be
    * empty. */
  def selectAll(): Selection[Null, Unit, E, D] = js.native

  /** Creates an empty selection. `Selection.selectAll` does not affect grouping; the elements in the returned selection
    * are grouped by their corresponding parent node in this selection, and the group at the current index will be
    * empty. */
  def selectAll(selector: Null): Selection[Null, Unit, E, D] = js.native

  /** Creates an empty selection. `Selection.selectAll` does not affect grouping; the elements in the returned selection
    * are grouped by their corresponding parent node in this selection, and the group at the current index will be
    * empty. */
  def selectAll(selector: Unit): Selection[Null, Unit, E, D] = js.native

  /** Selects the descendant elements of each element in this selection that match the specified selector string. The
    * elements in the returned selection are grouped by their corresponding parent node in this selection. If no element
    * matches the specified selector for the current element, the group at the current index will be empty.
    * `Selection.selectAll` does not affect grouping; the elements in the returned selection are grouped by their
    * corresponding parent node in this selection, and the group at the current index will be empty.
    *
    * The selected elements do not inherit data from this selection; use `Selection.data()` to propagate data to
    * children.
    *
    * @param  selector CSS selector string.
    * @tparam DE  Type of the descendant elements to the be selected.
    * @tparam DED Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *             previously set and known datum type.
    * @return Selection.
    */
  def selectAll[DE <: dom.EventTarget, DED](selector: String): Selection[DE, DED, E, D] = js.native

  /** Selects the descendant elements of each element in this selection, returned by the selector function. The
    * elements in the returned selection are grouped by their corresponding parent node in this selection. If no element
    * matches the specified selector for the current element, the group at the current index will be empty.
    * `Selection.selectAll` does not affect grouping; the elements in the returned selection are grouped by their
    * corresponding parent node in this selection, and the group at the current index will be empty.
    *
    * The selected elements do not inherit data from this selection; use `Selection.data()` to propagate data to
    * children.
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return an array of elements (or a pseudo-array,
    *                  such as a `NodeList`), or the empty array if there are no matching elements.
    * @tparam DE  Type of the descendant elements to the be selected.
    * @tparam DED Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *             previously set and known datum type.
    * @return Selection.
    */
  // TODO: !!! May need to change to a subtype relationship.
  def selectAll[DE <: dom.EventTarget, DED, S: D3VOrFn[js.Array[DE] | ArrayLike[DE]]#CB](
      selector: S
  ): Selection[DE, DED, E, D] = js.native

  // TODO: !!!
  def filter(selector: String): Selection[E, D, PE, PD] = js.native
  def filter[T: D3VOrFn[Boolean]#CB](selector: T): Selection[E, D, PE, PD] = js.native

  def merge[ME <: E, MD <: D, MPE <: PE, MPD <: PD](
      other: Selection[ME, MD, MPE, MPD]
  ): Selection[E, D, PE, PD] = js.native

  //endregion Selecting Elements

  //region Modifying Elements

  def attr(name: String): String = js.native
  def attr[T: D3AttrValueOrFn](name: String, value: T): this.type = js.native

  def classed(names: String): Boolean = js.native
  def classed[T: D3VOrFn[Boolean]#CB](names: String, value: T): this.type = js.native

  def style(name: String): String = js.native
  def style[T: D3AttrValueOrFn](name: String, value: T, priority: String = ""): this.type = js.native

  def property(name: String): js.Dynamic = js.native
  def property[T: D3PropertyOrFn](name: String, value: T): this.type = js.native

  def property[T](name: Local[T]): T | Unit = js.native

  def property[T, TOrFn: D3VOrFn[T]#CB](name: Local[T], value: TOrFn): this.type = js.native

  def text(): String = js.native
  def text[T: D3TextOrFn](value: T): this.type = js.native

  def html(): String = js.native
  def html[T: D3TextOrFn](value: T): this.type = js.native

  def append(value: String): Selection[E, D, PE, PD] = js.native

  def append[ChildE <: dom.EventTarget, ChildEOrFn: D3VOrFn[ChildE]#CB](
      value: ChildEOrFn
  ): Selection[ChildE, D, PE, PD] = js.native

  def insert(value: String): Selection[E, D, PE, PD] = js.native

  def insert(value: String, before: String): Selection[E, D, PE, PD] = js.native

  def insert[ChildE <: dom.EventTarget, ChildEOrFn: D3VOrFn[ChildE]#CB](
      value: ChildEOrFn
  ): Selection[ChildE, D, PE, PD] = js.native

  def insert[EOrFn: D3VOrFn[E]#CB](value: String, before: EOrFn): Selection[E, D, PE, PD] = js.native

  def insert[ChildE <: dom.EventTarget, ChildEOrFn: D3VOrFn[ChildE]#CB](
      value: ChildEOrFn,
      before: String
  ): Selection[ChildE, D, PE, PD] = js.native

  def insert[ChildE <: dom.EventTarget, ChildEOrFn: D3VOrFn[ChildE]#CB, EOrFn: D3VOrFn[E]#CB](
      value: ChildEOrFn,
      before: EOrFn
  ): Selection[ChildE, D, PE, PD] = js.native

  def remove(): this.type = js.native

  def clone(deep: Boolean = false): Selection[E, D, PE, PD] = js.native

  def sort(comparator: js.Function2[D, D, Double] = null): this.type = js.native

  /** Re-inserts elements into the document such that the document order of each group matches the selection order.
    * This is equivalent to calling `Selection.sort()` if the data is already sorted, but much faster. Returns this
    * selection. */
  def order(): this.type = js.native

  /** Re-inserts each selected element, in order, as the last child of its parent and returns this selection. */
  def raise(): this.type = js.native

  /** Re-inserts each selected element, in order, as the first child of its parent and returns this selection. */
  def lower(): this.type = js.native

  //endregion Modifying Elements

  //region Joining Data

  /** Returns the bound data for the first (non-null) element in this selection. Unlike `Selection.data()`, this method
    * does not compute a join and does not affect indexes or the enter and exit selections. This is generally useful
    * only if you know the selection contains exactly one element.
    *
    * This method is useful for accessing HTML5
    * [custom data attributes](http://www.w3.org/TR/html5/dom.html#custom-data-attribute). For example, given the
    * following elements:
    * {{{
    *   <ul id="list">
    *     <li data-username="shawnbot">Shawn Allen</li>
    *     <li data-username="mbostock">Mike Bostock</li>
    *   </ul>
    * }}}
    * You can expose the custom data attributes by setting each element’s data as the built-in dataset property:
    * {{{
    *   selection.datum(f(n => n.dataset))
    * }}}
    *
    * @return Bound data for the first (non-null) element in this selection.
    */
  def datum(): D = js.native

  /** Sets the element’s bound data to the specified value on all selected elements. If the value is a constant, all
    * elements are given the same datum; otherwise, if the value is a function, it is evaluated for each selected
    * element, in order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`),
    * with this as the current DOM element (`nodes(i)`). The function is then used to set each element’s new data.
    *
    * A `null` value will delete the bound data.
    *
    * @param  value Value to set the elements' bound data to.
    * @return Selection after setting the elements' bound data.
    */
  def datum[ND, NDOrFn: D3VOrFn[ND]#CB](value: NDOrFn): Selection[E, ND, PE, PD] = js.native

  def data(): js.Array[D] = js.native

  def data[ND](data: js.Array[ND]): Selection[E, ND, PE, PD] = js.native

  def data[ND, KeyOrFn: D3VOrFn[String]#CB](data: js.Array[ND], key: KeyOrFn): Selection[E, ND, PE, PD] = js.native

  def data[ND](data: D3ThisFunction1[E, D, js.Array[ND]]): Selection[E, ND, PE, PD] = js.native

  // def data[ND, NDOrFn: D3VOrFn[js.Array[ND]]#CB](data: NDOrFn): Selection[E, ND, PE, PD] = js.native

  // def data[ND, NDOrFn: D3VOrFn[js.Array[ND]]#CB, KeyOrFn: D3VOrFn[String]#CB](data: NDOrFn, key: KeyOrFn): Selection[E, ND, PE, PD] = js.native

  /** Returns the enter selection: placeholder nodes for each datum that had no corresponding DOM element in the
    * selection. Note that the enter selection is empty for selections not returned by `Selection.data()`. */
  def enter(): Selection[E, D, PE, PD] = js.native

  /** Returns the exit selection: existing DOM elements in the selection for which no new datum was found. Note that the
    * exit selection is empty for selections not returned by `Selection.data()`.
    *
    * @tparam OldD Refers to the type of the old datum associated with the exit selection elements. Ensure that you set
    *              the generic to the correct type, if you need to access the data on the exit selection in follow-up
    *              steps (e.g., to set styles as part of an exit transition before removing them).
    */
  def exit[OldD](): Selection[E, OldD, PE, PD] = js.native

  //endregion Joining Data

  //region Handling Events

  def on(domTypeNames: String): D3ThisFunction3[E, D, Unit] = js.native

  def on(domTypeNames: String, listener: Null): this.type = js.native

  def on[T: D3VOrFn[Unit]#CB](domTypeNames: String, listener: T, capture: Boolean = false): this.type = js.native

  def dispatch(value: String): this.type = js.native
  def dispatch(value: String, parameters: CustomEventParameters): this.type = js.native
  def dispatch[T: D3VOrFn[CustomEventParameters]#CB](value: String, parameters: T): this.type = js.native

  //endregion Handling Events

  //region Control Flow

  /** Invokes the specified function for each selected element, passing in the current datum (`d`), the current index
    * (`i`), and the current group (`nodes`), with this of the current DOM element (`nodes(i)`). This method can be used
    * to invoke arbitrary code for each selected element, and is useful for creating a context to access parent and
    * child data simultaneously.
    *
    * @param  func Function which is invoked for each selected element, being passed the current datum (`d`), the
    *              current index (`i`), and the current group (`nodes`), with this of the current DOM element
    *              (`nodes(i)`).
    * @return This selection.
    */
  def each[F: D3VOrFn[Unit]#CB](func: F): this.type = js.native

  /** Invokes the specified function exactly once for each element in this selection, passing in the selection element
    * along with any optional arguments. This is equivalent to invoking the function by hand but facilitates method
    * chaining.
    *
    * @param  func Function which is passed this selection as the first argument along with any optional arguments.
    * @param  args List of optional arguments to be passed to the callback function.
    * @return This selection.
    */
  def call(func: js.Function, args: js.Any*): this.type = js.native

  /** Returns `true` if this selection contains no (non-null) elements. */
  def empty(): Boolean = js.native

  /** Returns the first (non-null) element in this selection. If the selection is empty, returns `null`. */
  def node(): E | Null = js.native

  /** Returns an array of all (non-null) elements in this selection. */
  def nodes(): js.Array[E] = js.native

  /** Returns the total number of elements in this selection. */
  def size(): Double = js.native

  //endregion Control Flow

  //region Transitions

  def interrupt(name: String = ???): Transition[E, D, PE, PD] = js.native
  def transition(name: String = ???): Transition[E, D, PE, PD] = js.native
  def transition(transition: Transition[dom.EventTarget, js.Any, dom.EventTarget, js.Any]): Transition[E, D, PE, PD] = js.native

  //endregion Transitions
}
