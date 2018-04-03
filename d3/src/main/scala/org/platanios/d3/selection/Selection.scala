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

import scala.scalajs.js
import scala.scalajs.js.annotation._
import scala.scalajs.js.|

/** Selections allow powerful data-driven transformation of the document object model (DOM): set attributes, styles,
  * properties, HTML or text content, and more. Using the data join’s enter and exit selections, you can also add or
  * remove elements to correspond to data.
  *
  * Selection methods typically return the current selection, or a new selection, allowing the concise application of
  * multiple operations on a given selection via method chaining.
  *
  * For example, to set the class and color style of all paragraph elements in the current document:
  * {{{
  *   d3.select.all("p")
  *     .attr("class", "graf")
  *     .style("color", "red")
  * }}}
  * This is equivalent to:
  * {{{
  *   val p = d3.select.all("p")
  *   p.attr("class", "graf")
  *   p.style("color", "red")
  * }}}
  *
  * By convention, selection methods that return the current selection use four spaces of indent, while methods that
  * return a new selection use only two. This helps reveal changes of context by making them stick out of the chain:
  * {{{
  *   d3.select("body")
  *     .append("svg")
  *       .attr("width", 960)
  *       .attr("height", 500)
  *     .append("g")
  *       .attr("transform", "translate(20,20)")
  *     .append("rect")
  *       .attr("width", 920)
  *       .attr("height", 460)
  * }}}
  *
  * Selections are immutable. All selection methods that affect which elements are selected (or their order) return a
  * new selection rather than modifying the current selection. However, note that elements are necessarily mutable, as
  * selections drive transformations of the document!
  *
  * @tparam E  Type of the selected element(s).
  * @tparam D  Datum type of the selected element(s).
  * @tparam PE Type of the parent element(s) in the selection.
  * @tparam PD Datum type of the parent element(s) in the selection.
  *
  * @author Emmanouil Antonios Platanios
  */
class Selection[+E <: dom.EventTarget, +D, +PE <: dom.EventTarget, +PD] private[selection] (
    private[d3] val facade: Selection.Facade[E, D, PE, PD]
) {
  //region Selecting Elements

  // /** Returns the `index`-th group in this selection. */
  // def apply(index: Index): Selection.Group = facade(index)

  // /** Updated the `index`-th group in this selection. */
  // def update(index: Index, value: Selection.Group): Unit = facade.update(index, value)

  /** Creates an empty selection. `Selection.select` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to the selected children. */
  def select[DE <: dom.EventTarget](selector: Null): Selection[Null, Unit, PE, PD] = {
    new Selection(facade.select(selector))
  }

  /** For each selected element, selects the first descendant element that matches the specified selector string. If no
    * element matches the specified selector for the current element, the element at the current index will be `null` in
    * the returned selection. If the selector is `null`, every element in the returned selection will be `null`,
    * resulting in an empty selection. If the current element has associated data, this data is propagated to the
    * corresponding selected element. If multiple elements match the selector, only the first matching element in
    * document order is selected.
    *
    * For example, to select the first bold element in every paragraph:
    * {{{
    *   val b = d3.select.all("p").select("p")
    * }}}
    *
    * Unlike `Selection.selectAll()`, `Selection.select()` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to selected children, Grouping plays an important role in the
    * data join. See [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * @param  selector CSS selector string.
    * @tparam DE Type of the descendant element to the be selected.
    * @return Selection.
    */
  def select[DE <: dom.EventTarget](selector: String): Selection[DE, D, PE, PD] = {
    new Selection(facade.select(selector))
  }

  /** Selects the descendant element returned by the selector function, for each element in this selection. If no
    * element matches the specified selector for the current element, the element at the current index will be `null` in
    * the returned selection. If multiple elements match the selector, only the first matching element in document order
    * is selected. `Selection.select` does not affect grouping; it preserves the existing group structure and indexes,
    * and propagates data (if any) to the selected children.
    *
    * For example, to select the previous sibling of each paragraph:
    * {{{
    *   val previous = d3.select.all("p").select((e: dom.Element) => e.previousElementSibling)
    * }}}
    *
    * Unlike `Selection.selectAll()`, `Selection.select()` does not affect grouping; it preserves the existing group
    * structure and indexes, and propagates data (if any) to selected children, Grouping plays an important role in the
    * data join. See [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return an element, or `null` if there is no
    *                  matching element.
    * @tparam DE Type of the descendant element to the be selected.
    * @return Selection.
    */
  def select[DE <: dom.EventTarget](selector: D3Function[E, D, DE]): Selection[DE, D, PE, PD] = {
    new Selection(facade.select(selector))
  }

  /** Creates an empty selection. `Selection.selectAll` does not affect grouping; the elements in the returned selection
    * are grouped by their corresponding parent node in this selection, and the group at the current index will be
    * empty.
    *
    * Unlike `Selection.select()`, `Selection.selectAll()` does affect grouping; each selected descendant is grouped by
    * the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    */
  def selectAll(): Selection[Null, Unit, E, D] = {
    new Selection(facade.selectAll())
  }

  /** Creates an empty selection. `Selection.selectAll` does not affect grouping; the elements in the returned selection
    * are grouped by their corresponding parent node in this selection, and the group at the current index will be
    * empty.
    *
    * Unlike `Selection.select()`, `Selection.selectAll()` does affect grouping; each selected descendant is grouped by
    * the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    */
  def selectAll(selector: Null): Selection[Null, Unit, E, D] = {
    new Selection(facade.selectAll(selector))
  }

  /** Creates an empty selection. `Selection.selectAll` does not affect grouping; the elements in the returned selection
    * are grouped by their corresponding parent node in this selection, and the group at the current index will be
    * empty.
    *
    * Unlike `Selection.select()`, `Selection.selectAll()` does affect grouping; each selected descendant is grouped by
    * the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    */
  def selectAll(selector: Unit): Selection[Null, Unit, E, D] = {
    new Selection(facade.selectAll(selector))
  }

  /** For each selected element, selects the descendant elements that match the specified selector string. The elements
    * in the returned selection are grouped by their corresponding parent node in this selection. If no element matches
    * the specified selector for the current element, or if the selector is `null`, the group at the current index will
    * be empty. The selected elements do not inherit data from this selection; use `Selection.data()` to propagate data
    * to children.
    *
    * For example, to select the bold elements in every paragraph:
    * {{{
    *   val b = d3.select.all("p").selectAll("b")
    * }}}
    *
    * Unlike `Selection.select()`, `Selection.selectAll()` does affect grouping; each selected descendant is grouped by
    * the parent element in the originating selection. Grouping plays an important role in the data join. See
    * [Nested Selections](http://bost.ocks.org/mike/nest/) and
    * [How Selections Work](http://bost.ocks.org/mike/selection/) for more on this topic.
    *
    * @param  selector CSS selector string.
    * @tparam DE  Type of the descendant elements to the be selected.
    * @tparam DED Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *             previously set and known datum type.
    * @return Selection.
    */
  def selectAll[DE <: dom.EventTarget, DED](selector: String): Selection[DE, DED, E, D] = {
    new Selection(facade.selectAll(selector))
  }

  /** Selects the descendant elements of each element in this selection, returned by the selector function. The
    * elements in the returned selection are grouped by their corresponding parent node in this selection. If no element
    * matches the specified selector for the current element, the group at the current index will be empty.
    * `Selection.selectAll` does not affect grouping; the elements in the returned selection are grouped by their
    * corresponding parent node in this selection, and the group at the current index will be empty.
    *
    * For example, to select the previous and next siblings of each paragraph:
    * {{{
    *   val previous = d3.select.all("p").select((e: dom.Element) => js.Array(e.previousElementSibling, e.nextElementSibling))
    * }}}
    *
    * Unlike `Selection.select()`, `Selection.selectAll()` does affect grouping; each selected descendant is grouped by
    * the parent element in the originating selection. Grouping plays an important role in the data join. See
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
    * @return Selection.
    */
  def selectAll[DE >: E <: dom.EventTarget, DED](
      selector: D3Function[E, D, js.Array[DE] | ArrayLike[DE]]
  ): Selection[DE, DED, E, D] = {
    new Selection(facade.selectAll(selector))
  }

  /** Filters the selection, returning a new selection that contains only the elements for which the specified filter is
    * `true`. The filter may be specified either as a selector string or a function.
    *
    * For example, to filter a selection of table rows to contain only even rows:
    * {{{
    *   val even = d3.select.all("tr").filter(":nth-child(even)")
    * }}}
    * This is approximately equivalent to using `d3.select.all()` directly, although the indexes may be different:
    * {{{
    *   val even = d3.select.all("tr:nth-child(even)")
    * }}}
    *
    * Note that the `":nth-child"` pseudo-class is a one-based index rather than a zero-based index.
    *
    * The returned filtered selection preserves the parents of this selection, but it does not preserve indexes as some
    * elements may be removed. Use `Selection.select()` to preserve the index, if needed.
    *
    * @param  selector CSS selector string.
    * @return Filtered selection.
    */
  def filter(selector: String): Selection[E, D, PE, PD] = {
    new Selection(facade.filter(selector))
  }

  /** Filters the selection, returning a new selection that contains only the elements for which the specified filter is
    * `true`. The filter may be specified either as a selector string or a function.
    *
    * For example, to filter a selection of table rows to contain only even rows:
    * {{{
    *   val even = d3.select.all("tr").filter((_, i: Int) => i % 2 == 0)
    * }}}
    * Or using `Selection.select()`:
    * {{{
    *   val even = d3.select.all("tr").select((e: dom.Element, _, i: Int) => if (i % 2 == 0) e else null)
    * }}}
    *
    * Note that the `":nth-child"` pseudo-class is a one-based index rather than a zero-based index. Also, the above
    * filter functions do not have precisely the same meaning as `":nth-child"`. They rely on the selection index rather
    * than the number of preceding sibling elements in the DOM.
    *
    * The returned filtered selection preserves the parents of this selection, but it does not preserve indexes as some
    * elements may be removed. Use `Selection.select()` to preserve the index, if needed.
    *
    * @param  selector Selector function, which is evaluated for each selected element, in order, and is being passed
    *                  the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as
    *                  the current DOM element (`nodes(i)`). It must return a boolean value indicating whether or not to
    *                  keep this element.
    * @return Filtered selection.
    */
  def filter(selector: D3Function[E, D, Boolean]): Selection[E, D, PE, PD] = {
    new Selection(facade.filter(selector))
  }

  /** Returns a new selection merging this selection with the specified `other` selection. The returned selection has
    * the same number of groups and the same parents as this selection. Any missing (`null`) elements in this selection
    * are filled with the corresponding element, if present (i.e., not `null`), from the specified selection. If the
    * other selection has additional groups or parents, they are ignored.
    *
    * This method is commonly used to merge the enter and update selections after a data-join. After modifying the
    * entering and updating elements separately, you can merge the two selections and perform operations on both without
    * duplicate code. For example:
    * {{{
    *   val circle = svg.select.all("circle").data(data) // UPDATE
    *     .style("fill", "blue")
    *
    *   circle.exit().remove() // EXIT
    *
    *   circle = circle.enter().append("circle") // ENTER
    *     .style("fill", "green")
    *     .merge(circle) // ENTER + UPDATE
    *     .style("stroke", "black")
    * }}}
    * See `Selection.data()` for a more complete explanation of this code, which is known as the general update pattern.
    *
    * This method is not intended for concatenating arbitrary selections. However, if both this selection and the
    * specified other selection have (non-`null`) elements at the same index, this selection's element is returned in
    * the merge and the other selection's element is ignored.
    *
    * @param  other Selection to merge with.
    * @return Merged selection.
    */
  def merge[ME >: E <: dom.EventTarget, MD >: D, MPE >: PE <: dom.EventTarget, MPD >: PD](
      other: Selection[ME, MD, MPE, MPD]
  ): Selection[E, D, PE, PD] = {
    new Selection(facade.merge(other.facade))
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

  /** Sets the attribute with the specified name to the specified value on the selected elements and returns this
    * selection. If the value is a constant, all elements are given the same attribute value; otherwise, if the value is
    * a function, it is evaluated for each selected element, in order, being passed the current datum (`d`), the current
    * index (`i`), and the current group (`nodes`), with this as the current DOM element (`nodes(i)`). The function's
    * return value is then used to set each element's attribute. A `null` value will remove the specified attribute.
    *
    * The specified name may have a namespace prefix, such as `xlink:href` to specify the href attribute in the XLink
    * namespace.
    *
    * @param  name  Attribute name.
    * @param  value Attribute value.
    * @return Selection after the modification is applied.
    */
  def attr[T](name: String, value: D3Function[E, D, T])(implicit ev: T => D3AttrValue): Selection[E, D, PE, PD] = {
    new Selection(facade.attr(name, value)(ev))
  }

  /** Returns `true` if and only if the first (non-`null`) selected element has the specified classes. This is generally
    * useful only if you know the selection contains exactly one element.
    *
    * @param  names Space-separated class names.
    * @return `true` if and only if the first (non-`null`) selected element has the specified classes.
    */
  def classed(names: String): Boolean = {
    facade.classed(names)
  }

  /** Assigns or unassigns the specified CSS class names on the selected elements by setting the class attribute or
    * modifying the classList property and returns this selection.
    *
    * For example, to assign the classes `foo` and `bar` to the selected elements:
    * {{{
    *   selection.classed("foo bar", true)
    * }}}
    *
    * If the value is truthy, then all elements are assigned the specified classes. Otherwise, the classes are
    * unassigned. If the value is a function, it is evaluated for each selected element, in order, being passed the
    * current datum (`d`), the current index (`i`), and the current group (`nodes`), with this as the current DOM
    * element (`nodes(i)`). The function's return value is then used to assign or unassign classes on each element.
    *
    * For example, to randomly associate the class foo with on average half the selected elements:
    * {{{
    *   selection.classed("foo", () => Math.random() > 0.5)
    * }}}
    *
    * @param  names Space-separated class names.
    * @param  value Value to use for the class assignment decision.
    * @return Selection after the modification is applied.
    */
  def classed(names: String, value: D3Function[E, D, Boolean]): Selection[E, D, PE, PD] = {
    new Selection(facade.classed(names, value))
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

  /** Sets the style property with the specified name to the specified value on the selected elements and returns this
    * selection. If the value is a constant, then all elements are given the same style property value; otherwise, if
    * the value is a function, it is evaluated for each selected element, in order, being passed the current datum
    * (`d`), the current index (`i`), and the current group (`nodes`), with this as the current DOM element
    * (`nodes(i)`). The function’s return value is then used to set each element's style property. A `null` value will
    * remove the style property.
    *
    * '''Caution:''' Unlike many SVG attributes, CSS styles typically have associated units. For example, `3px` is a
    * valid stroke-width property value, while `3` is not. Some browsers implicitly assign the `px` (pixel) unit to
    * numeric values, but not all browsers do (IE, for example, throws an "invalid arguments" error).
    *
    * @param  name      Style property name.
    * @param  value     Style property value.
    * @param  important If `true`, `"important"` will be used as the priority value for the property.
    * @return Selection after the modification is applied.
    */
  def style[T](
      name: String,
      value: D3Function[E, D, T],
      important: Boolean = false
  )(implicit ev: T => D3AttrValue): Selection[E, D, PE, PD] = {
    if (important)
      new Selection(facade.style(name, value, priority = "important"))
    else
      new Selection(facade.style(name, value))
  }

  /** Returns the value of the specified property for the first (non-`null`) element in the selection. This is generally
    * useful only if you know the selection contains exactly one element.
    *
    * This is useful for HTML elements that have special properties that are not addressable using attributes or styles,
    * such as a form field's text `value` and a checkbox's `checked` boolean. Use this method to get these properties.
    *
    * @param  name Property name.
    * @return Property value.
    */
  def property(name: String): js.Dynamic = facade.property(name)

  /** Sets the property with the specified name to the specified value on selected elements. If the value is a constant,
    * then all elements are given the same property value; otherwise, if the value is a function, it is evaluated for
    * each selected element, in order, being passed the current datum (`d`), the current index (`i`), and the current
    * group (`nodes`), with this as the current DOM element (`nodes(i)`). The function's return value is then used to
    * set each element's property. A `null` value will delete the specified property.
    *
    * This is useful for HTML elements that have special properties that are not addressable using attributes or styles,
    * such as a form field's text `value` and a checkbox's `checked` boolean. Use this method to set these properties.
    *
    * @param  name  Property name.
    * @param  value Property value.
    * @return Selection after the modification is applied.
    */
  def property[T](
      name: String,
      value: D3Function[E, D, T]
  )(implicit ev: T => D3PropertyValue): Selection[E, D, PE, PD] = {
    new Selection(facade.property(name, value)(ev))
  }

  /** Returns the value of the specified property for the first (non-`null`) element in the selection. This is generally
    * useful only if you know the selection contains exactly one element.
    *
    * This is useful for HTML elements that have special properties that are not addressable using attributes or styles,
    * such as a form field's text `value` and a checkbox's `checked` boolean. Use this method to get these properties.
    *
    * @param  name Property name.
    * @return Property value.
    */
  def property[T](name: Local[T]): T = facade.property(name)

  /** Sets the property with the specified name to the specified value on selected elements. If the value is a constant,
    * then all elements are given the same property value; otherwise, if the value is a function, it is evaluated for
    * each selected element, in order, being passed the current datum (`d`), the current index (`i`), and the current
    * group (`nodes`), with this as the current DOM element (`nodes(i)`). The function's return value is then used to
    * set each element's property. A `null` value will delete the specified property.
    *
    * This is useful for HTML elements that have special properties that are not addressable using attributes or styles,
    * such as a form field's text `value` and a checkbox's `checked` boolean. Use this method to set these properties.
    *
    * @param  name  Property name.
    * @param  value Property value.
    * @return Selection after the modification is applied.
    */
  def property[T](name: Local[T], value: D3Function[E, D, T]): Selection[E, D, PE, PD] = {
    new Selection(facade.property(name, value))
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
    * @param  value Value function.
    * @return Selection after the modification is applied.
    */
  def text[T](value: D3Function[E, D, T])(implicit ev: T => D3TextValue): Selection[E, D, PE, PD] = {
    new Selection(facade.text(value))
  }

  /** Returns the inner HTML for the first (non-`null`) element in the selection. This is generally useful only if you
    * know the selection contains exactly one element.
    *
    * @return Inner HTML for the first (non-`null`) element in the selection.
    */
  def html(): String = {
    facade.html()
  }

  /** Sets the [inner HTML](http://dev.w3.org/html5/spec-LC/apis-in-html-documents.html#innerhtml) to the specified
    * value on all selected elements, replacing any existing child elements. If the value is a constant, then all
    * elements are given the same inner HTML; otherwise, if the value is a function, it is evaluated for each selected
    * element, in order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`),
    * with this as the current DOM element (`nodes(i)`). The function's return value is then used to set each element's
    * inner HTML. A `null` value will clear the content.
    *
    * Use `Selection.append()` or `Selection.insert()` instead to create data-driven content. This method is intended
    * for when you want a little bit of HTML, say for rich formatting. Also, `Selection.html()` is only supported on
    * HTML elements. SVG elements and other non-HTML elements do not support the `innerHTML` property, and thus are
    * incompatible with `Selection.html()`. Consider using
    * [XMLSerializer](https://developer.mozilla.org/en-US/docs/XMLSerializer) to convert a DOM subtree to text. See also
    * the [innersvg polyfill](https://code.google.com/p/innersvg/), which provides a shim to support the `innerHTML`
    * property on SVG elements.
    *
    * @param  value Value function.
    * @return Selection after the modification is applied.
    */
  def html[T](value: D3Function[E, D, T])(implicit ev: T => D3TextValue): Selection[E, D, PE, PD] = {
    new Selection(facade.html(value)(ev))
  }

  /** Appends a new element of this type (tag name) as the last child of each selected element, or before the next
    * following sibling in the update selection if this is an enter selection. The latter behavior for enter selections
    * allows you to insert elements into the DOM in an order consistent with the new bound data. However, note that
    * selection.order may still be required if updating elements change order (i.e., if the order of new data is
    * inconsistent with old data).
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").append("div")
    * }}}
    *
    * This method returns a new selection containing the appended elements. Each new element inherits the data of the
    * current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value Value to append.
    * @return Selection after the modification is applied.
    */
  def append(value: String): Selection[E, D, PE, PD] = {
    new Selection(facade.append(value))
  }

  /** Evaluates `value` for each selected element, in order, being passed the current datum (`d`), the current index
    * (`i`), and the current group (`nodes`), with this as the current DOM element (`nodes(i)`), and appends the
    * returned elements. The `value` function should return an element to be appended. The function typically creates a
    * new element, but it may instead return an existing element.
    * ß
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").append(() => dom.document.createElement("div"))
    * }}}
    *
    * This method returns a new selection containing the appended elements. Each new element inherits the data of the
    * current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value Value function returning elements to append.
    * @return Selection after the modification is applied.
    */
  def append[ChildE <: dom.EventTarget](value: D3Function[E, D, ChildE]): Selection[ChildE, D, PE, PD] = {
    new Selection(facade.append(value))
  }

  /** If the specified type is a string, inserts a new element of this type (tag name) before the first element matching
    * the specified before selector for each selected element. For example, a before selector `":first-child"` will
    * prepend nodes before the first child. If `before` is not specified, it defaults to `null`. To append elements in
    * an order consistent with bound data, use `Selection.append()`.
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").insert("div")
    * }}}
    * This is equivalent to:
    * {{{
    *   d3.select.all("p").insert(() => dom.document.createElement("div"))
    * }}}
    * Which is equivalent to:
    * {{{
    *   d3.select.all("p").select((e: dom.Element) => e.insertBefore(dom.document.createElement("div"), null))
    * }}}
    *
    * In both cases, this method returns a new selection containing the appended elements. Each new element inherits the
    * data of the current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value Value to insert.
    * @return Selection after the modification is applied.
    */
  def insert(value: String): Selection[E, D, PE, PD] = {
    new Selection(facade.insert(value))
  }

  /** If the specified type is a string, inserts a new element of this type (tag name) before the first element matching
    * the specified before selector for each selected element. For example, a before selector `":first-child"` will
    * prepend nodes before the first child. If `before` is not specified, it defaults to `null`. To append elements in
    * an order consistent with bound data, use `Selection.append()`.
    *
    * Both `value` and `before` may instead be specified as functions which are evaluated for each selected element, in
    * order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this
    * as the current DOM element (`nodes(i)`). The `value` function should return an element to be inserted. The
    * `before` function should return the child element before which the element should be inserted.
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").insert("div")
    * }}}
    * This is equivalent to:
    * {{{
    *   d3.select.all("p").insert(() => dom.document.createElement("div"))
    * }}}
    * Which is equivalent to:
    * {{{
    *   d3.select.all("p").select((e: dom.Element) => e.insertBefore(dom.document.createElement("div"), null))
    * }}}
    *
    * In both cases, this method returns a new selection containing the appended elements. Each new element inherits the
    * data of the current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value Value function returning elements to insert.
    * @return Selection after the modification is applied.
    */
  def insert[ChildE <: dom.EventTarget](value: D3Function[E, D, ChildE]): Selection[ChildE, D, PE, PD] = {
    new Selection(facade.insert(value))
  }

  /** If the specified type is a string, inserts a new element of this type (tag name) before the first element matching
    * the specified before selector for each selected element. For example, a before selector `":first-child"` will
    * prepend nodes before the first child. If `before` is not specified, it defaults to `null`. To append elements in
    * an order consistent with bound data, use `Selection.append()`.
    *
    * Both `value` and `before` may instead be specified as functions which are evaluated for each selected element, in
    * order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this
    * as the current DOM element (`nodes(i)`). The `value` function should return an element to be inserted. The
    * `before` function should return the child element before which the element should be inserted.
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").insert("div")
    * }}}
    * This is equivalent to:
    * {{{
    *   d3.select.all("p").insert(() => dom.document.createElement("div"))
    * }}}
    * Which is equivalent to:
    * {{{
    *   d3.select.all("p").select((e: dom.Element) => e.insertBefore(dom.document.createElement("div"), null))
    * }}}
    *
    * In both cases, this method returns a new selection containing the appended elements. Each new element inherits the
    * data of the current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value  Value to insert.
    * @param  before Selector specifying before which element to insert `value`.
    * @return Selection after the modification is applied.
    */
  def insert(value: String, before: String): Selection[E, D, PE, PD] = {
    new Selection(facade.insert(value, before))
  }

  /** If the specified type is a string, inserts a new element of this type (tag name) before the first element matching
    * the specified before selector for each selected element. For example, a before selector `":first-child"` will
    * prepend nodes before the first child. If `before` is not specified, it defaults to `null`. To append elements in
    * an order consistent with bound data, use `Selection.append()`.
    *
    * Both `value` and `before` may instead be specified as functions which are evaluated for each selected element, in
    * order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this
    * as the current DOM element (`nodes(i)`). The `value` function should return an element to be inserted. The
    * `before` function should return the child element before which the element should be inserted.
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").insert("div")
    * }}}
    * This is equivalent to:
    * {{{
    *   d3.select.all("p").insert(() => dom.document.createElement("div"))
    * }}}
    * Which is equivalent to:
    * {{{
    *   d3.select.all("p").select((e: dom.Element) => e.insertBefore(dom.document.createElement("div"), null))
    * }}}
    *
    * In both cases, this method returns a new selection containing the appended elements. Each new element inherits the
    * data of the current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value  Value to insert.
    * @param  before Selector function returning a selector specifying before which element to insert `value`.
    * @return Selection after the modification is applied.
    */
  def insert[SE >: E <: dom.EventTarget](value: String, before: D3Function[E, D, SE]): Selection[E, D, PE, PD] = {
    new Selection(facade.insert(value, before))
  }

  /** If the specified type is a string, inserts a new element of this type (tag name) before the first element matching
    * the specified before selector for each selected element. For example, a before selector `":first-child"` will
    * prepend nodes before the first child. If `before` is not specified, it defaults to `null`. To append elements in
    * an order consistent with bound data, use `Selection.append()`.
    *
    * Both `value` and `before` may instead be specified as functions which are evaluated for each selected element, in
    * order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this
    * as the current DOM element (`nodes(i)`). The `value` function should return an element to be inserted. The
    * `before` function should return the child element before which the element should be inserted.
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").insert("div")
    * }}}
    * This is equivalent to:
    * {{{
    *   d3.select.all("p").insert(() => dom.document.createElement("div"))
    * }}}
    * Which is equivalent to:
    * {{{
    *   d3.select.all("p").select((e: dom.Element) => e.insertBefore(dom.document.createElement("div"), null))
    * }}}
    *
    * In both cases, this method returns a new selection containing the appended elements. Each new element inherits the
    * data of the current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value  Value function returning elements to insert.
    * @param  before Selector specifying before which element to insert `value`.
    * @return Selection after the modification is applied.
    */
  def insert[ChildE <: dom.EventTarget](
      value: D3Function[E, D, ChildE],
      before: String
  ): Selection[ChildE, D, PE, PD] = {
    new Selection(facade.insert(value, before))
  }

  /** If the specified type is a string, inserts a new element of this type (tag name) before the first element matching
    * the specified before selector for each selected element. For example, a before selector `":first-child"` will
    * prepend nodes before the first child. If `before` is not specified, it defaults to `null`. To append elements in
    * an order consistent with bound data, use `Selection.append()`.
    *
    * Both `value` and `before` may instead be specified as functions which are evaluated for each selected element, in
    * order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`), with this
    * as the current DOM element (`nodes(i)`). The `value` function should return an element to be inserted. The
    * `before` function should return the child element before which the element should be inserted.
    *
    * For example, to append a DIV element to each paragraph:
    * {{{
    *   d3.select.all("p").insert("div")
    * }}}
    * This is equivalent to:
    * {{{
    *   d3.select.all("p").insert(() => dom.document.createElement("div"))
    * }}}
    * Which is equivalent to:
    * {{{
    *   d3.select.all("p").select((e: dom.Element) => e.insertBefore(dom.document.createElement("div"), null))
    * }}}
    *
    * In both cases, this method returns a new selection containing the appended elements. Each new element inherits the
    * data of the current elements, if any, in the same manner as `Selection.select()`.
    *
    * The specified name may have a namespace prefix, such as `svg:text` to specify a text attribute in the SVG
    * namespace. If no namespace is specified, the namespace will be inherited from the parent element, or, if the name
    * is one of the known prefixes, the corresponding namespace will be used (for example, `svg` implies `svg:svg`).
    *
    * @param  value  Value function returning elements to insert.
    * @param  before Selector function returning a selector specifying before which element to insert `value`.
    * @return Selection after the modification is applied.
    */
  def insert[SE >: E <: dom.EventTarget, ChildE <: dom.EventTarget](
      value: D3Function[E, D, ChildE],
      before: D3Function[E, D, SE]
  ): Selection[ChildE, D, PE, PD] = {
    new Selection(facade.insert(value, before))
  }

  /** Removes the selected elements from the document. Returns this selection (the removed elements) which are now
    * detached from the DOM. There is not currently a dedicated API to add removed elements back to the document.
    * However, you can pass a function to `Selection.append()` or `Selection.insert()` to re-add elements. */
  def remove(): Selection[E, D, PE, PD] = {
    new Selection(facade.remove())
  }

  /** Inserts clones of the selected elements immediately following the selected elements and returns a selection of the
    * newly added clones. Equivalent to:
    * {{{
    *   selection.select((e: dom.Element) => e.parentNode.insertBefore(e.cloneNode(deep), e.nextSibling))
    * }}}
    *
    * @param  deep If `true`, the descendant nodes of the selected elements will be cloned as well.  Otherwise, only the
    *              elements themselves will be cloned.
    * @return Selection containing the newly added clones.
    */
  def clone(deep: Boolean = false): Selection[E, D, PE, PD] = {
    new Selection(facade.clone(deep))
  }

  /** Returns a new selection that contains a copy of each group in this selection sorted according to the compare
    * function. After sorting, re-inserts elements to match the resulting order (per `Selection.order()`).
    *
    * The `compare` function, which defaults to inducing ascending order, is passed two elements' data `a` and `b` to
    * compare. It should return either a negative, positive, or zero value. If negative, then `a` should be before `b`,
    * if positive, then `a` should be after `b`, otherwise, `a` and `b` are considered equal and the order is arbitrary.
    *
    * Note that sorting is not guaranteed to be stable. However, it is guaranteed to have the same behavior as your
    * browser's built-in sort method on arrays.
    *
    * @param  comparator Optional comparator function.
    * @return Selection after the sorting.
    */
  def sort(comparator: (D, D) => Double = null): Selection[E, D, PE, PD] = {
    if (comparator != null)
      new Selection(facade.sort(comparator))
    else
      new Selection(facade.sort())
  }

  /** Re-inserts elements into the document such that the document order of each group matches the selection order.
    * This is equivalent to calling `Selection.sort()` if the data is already sorted, but much faster. Returns this
    * selection. */
  def order(): Selection[E, D, PE, PD] = new Selection(facade.order())

  /** Re-inserts each selected element, in order, as the last child of its parent and returns this selection.
    *
    * Equivalent to:
    * {{{
    *   selection.each((e: dom.Element) => e.parentNode.appendChild(e))
    * }}}
    */
  def raise(): Selection[E, D, PE, PD] = new Selection(facade.raise())

  /** Re-inserts each selected element, in order, as the first child of its parent and returns this selection.
    *
    * Equivalent to:
    * {{{
    *   selection.each((e: dom.Element) => e.parentNode.insertBefore(e, e.parentNode.firstChild))
    * }}}
    */
  def lower(): Selection[E, D, PE, PD] = new Selection(facade.lower())

  //endregion Modifying Elements

  //region Joining Data

  /** Returns the data for the selected elements. */
  def data(): Seq[D] = facade.data()

  /** Joins the specified array of data with the selected elements, returning a new selection that represents the update
    * selection: the elements successfully bound to data. Also defines the enter and exit selections on the returned
    * selection, which can be used to add or remove elements to correspond to the new data. The specified data is an
    * array of arbitrary values (e.g., numbers or objects), or a function that returns an array of values for each
    * group. When data is assigned to an element, it is stored in the property `__data__`, thus making the data
    * "sticky" and available on re-selection.
    *
    * The data is specified for each group in the selection. If the selection has multiple groups (such as
    * `d3.select.all()` followed by `Selection.selectAll()`), then data should typically be specified as a function.
    * This function will be evaluated for each group in order, being passed the group's parent datum (`d`, which may be
    * undefined), the group index (`i`), and the selection's parent nodes (`nodes`), with this as the group's parent
    * element. For example, to create an HTML table from a matrix of numbers:
    * {{{
    *   val matrix = Array(
    *     Array(11975,  5871, 8916, 2868),
    *     Array( 1951, 10048, 2060, 6171),
    *     Array( 8010, 16145, 8090, 8045),
    *     Array( 1013,   990,  940, 6907))
    *
    *   val tr = d3.select("body")
    *     .append("table")
    *     .selectAll("tr")
    *     .data(matrix)
    *     .enter().append("tr")
    *
    *   val td = tr.selectAll("td")
    *     .data((d: Int) => d)
    *     .enter().append("td")
    *     .text((d: Int) => d)
    * }}}
    * In this example the data function is the identity function: for each table row, it returns the corresponding row
    * from the data matrix.
    *
    * If a key function is not specified, then the first datum in data is assigned to the first selected element, the
    * second datum to the second selected element, and so on. A key function may be specified to control which datum is
    * assigned to which element, replacing the default join-by-index, by computing a string identifier for each datum
    * and element. This key function is evaluated for each selected element, in order, being passed the current datum
    * (`d`), the current index (`i`), and the current group (`nodes`), with this as the current DOM element
    * (`nodes(i)`). The returned string is the element's key. The key function is then also evaluated for each new datum
    * in `data`, being passed the current datum (`d`), the current index (`i`), and the group's new data, with this as
    * the group's parent DOM element. The returned string is the datum's key. The datum for a given key is assigned to
    * the element with the matching key. If multiple elements have the same key, the duplicate elements are put into the
    * exit selection. If multiple data have the same key, the duplicate data are put into the enter selection.
    *
    * For example, given this document:
    * {{{
    *   <div id="Ford"></div>
    *   <div id="Jarrah"></div>
    *   <div id="Kwon"></div>
    *   <div id="Locke"></div>
    *   <div id="Reyes"></div>
    *   <div id="Shephard"></div>
    * }}}
    * You could join data by key as follows:
    * {{{
    *   val data = js.Array(
    *     js.Dictionary("name" -> "Locke", "number" -> "4"),
    *     js.Dictionary("name" -> "Reyes", "number" -> "8"),
    *     js.Dictionary("name" -> "Ford", "number" -> "15"),
    *     js.Dictionary("name" -> "Jarrah", "number" -> "16"),
    *     js.Dictionary("name" -> "Shephard", "number" -> "31"),
    *     js.Dictionary("name" -> "Kwon", "number" -> "34"))
    *
    *   d3.select.all("div")
    *     .data(data, (e: dom.Element, d: js.Dictionary[String]) => if (d != null) d("name") else e.id)
    *     .text((d: js.Dictionary[String]) => d("number"))
    * }}}
    * This example key function uses the datum `d` if present, and otherwise falls back to the element's `id` property.
    * Since these elements were not previously bound to data, the datum `d` is `null` when the key function is evaluated
    * on selected elements, and non-`null` when the key function is evaluated on the new data.
    *
    * The update and enter selections are returned in data order, while the exit selection preserves the selection order
    * prior to the join. If a key function is specified, the order of elements in the selection may not match their
    * order in the document. Use `Selection.order()` or `Selection.sort()` as needed. For more on how the key function
    * affects the join, see [A Bar Chart, Part 2](http://bost.ocks.org/mike/bar/2/) and
    * [Object Constancy](http://bost.ocks.org/mike/constancy/).
    *
    * Although the data-join can be used simply to create (to enter) a set of elements corresponding to data, more
    * generally the data-join is designed to let you create, destroy or update elements as needed so that the resulting
    * DOM corresponds to the new data. The data-join lets you do this efficiently by executing only the minimum
    * necessary operations on each state of element (entering, updating, or exiting), and allows you to declare concise
    * animated transitions between states as well.
    *
    * Here is a simple example of the [General Update Pattern](http://bl.ocks.org/mbostock/3808218):
    * {{{
    *   val circle = svg.selectAll("circle")     // 1
    *     .data(data)                            // 2
    *     .style("fill", "blue")                 // 3
    *
    *   circle.exit().remove()                   // 4
    *
    *   circle = circle.enter().append("circle") // 5, 9
    *     .style("fill", "green")                // 6
    *     .merge(circle)                         // 7
    *     .style("stroke", "black")              // 8
    * }}}
    *
    * Breaking this down into discrete steps:
    *
    *   1. Any existing circles (that are descendants of the `svg` selection) are selected.
    *   2. These circles are joined to new `data`, returning the matching circles: the update selection.
    *   3. These updating circles are given a blue fill.
    *   4. Any existing circles that do not match new data — the exit selection — are removed.
    *   5. New circles are appended for any new data that do not match any existing circle: the enter selection.
    *   6. These entering circles are given a green fill.
    *   7. A new selection representing the union of entering and updating circles is created.
    *   8. These entering and updating circles are given a black stroke.
    *   9. These circles are stored in the variable circle.
    *
    * As described in the preceding paragraphs, the "matching" logic is determined by the key function passed to
    * `Selection.data()`. Since no key function is used in the above code sample, the elements and data are joined by
    * index.
    *
    * This method cannot be used to clear bound data; use `Selection.datum()` instead.
    *
    * For an introduction to D3’s data joins, see [Thinking With Joins](http://bost.ocks.org/mike/join/). Also see the
    * [General Update Pattern](http://bl.ocks.org/mbostock/3808218) examples.
    *
    * @param  data Data to bind.
    * @param  key  Optional key function.
    * @return Selection after the data binding is complete.
    */
  def data[ND](
      data: D3Function[E, D, js.Array[ND]],
      key: D3Function[E, ND, String] = null
  ): Selection[E, ND, PE, PD] = {
    if (key != null)
      new Selection(facade.data(data, key))
    else
      new Selection(facade.data(data))
  }

  /** Returns the enter selection: placeholder nodes for each datum that had no corresponding DOM element in the
    * selection. Note that the enter selection is empty for selections not returned by `Selection.data()`.
    *
    * The enter selection is typically used to create "missing" elements corresponding to new data. For example, to
    * create DIV elements from an array of numbers:
    * {{{
    *   val div = d3.select("body")
    *     .selectAll("div")
    *     .data(Array(4, 8, 15, 16, 23, 42))
    *     .enter().append("div")
    *     .text((d: Int) => d)
    * }}}
    * If the body is initially empty, the above code will create six new DIV elements, append them to the body in-order,
    * and assign their text content as the associated (string-coerced) number:
    * {{{
    *   <div>4</div>
    *   <div>8</div>
    *   <div>15</div>
    *   <div>16</div>
    *   <div>23</div>
    *   <div>42</div>
    * }}}
    * Conceptually, the enter selection's placeholders are pointers to the parent element (in this example, the document
    * body). The enter selection is typically only used transiently to append elements, and is often merged with the
    * update selection after appending, such that modifications can be applied to both entering and updating elements.
    *
    * @return Selection after the enter is complete.
    */
  def enter(): Selection[E, D, PE, PD] = {
    new Selection(facade.enter())
  }

  /** Returns the exit selection: existing DOM elements in the selection for which no new datum was found. Note that the
    * exit selection is empty for selections not returned by `Selection.data()`.
    *
    * The exit selection is typically used to remove "superfluous" elements corresponding to old data. For example, to
    * update the DIV elements created previously with a new array of numbers:
    * {{{
    *   div = div.data(Array(1, 2, 4, 8, 16, 32), (d: Int) => d)
    * }}}
    * Since a key function was specified (as the identity function), and the new data contains the numbers `[4, 8, 16]`
    * which match existing elements in the document, the update selection contains three DIV elements. Leaving those
    * elements as-is, we can append new elements for `[1, 2, 32]` using the enter selection:
    * {{{
    *   div.enter().append("div").text((d: Int) => d)
    * }}}
    * Likewise, to remove the exiting elements `[15, 23, 42]`:
    * {{{
    *   div.exit().remove()
    * }}}
    * Now the document body looks like this:
    * {{{
    *   <div>1</div>
    *   <div>2</div>
    *   <div>4</div>
    *   <div>8</div>
    *   <div>16</div>
    *   <div>32</div>
    * }}}
    * The order of the DOM elements matches the order of the data because the old data's order and the new data's order
    * were consistent. If the new data's order is different, use `Selection.order()` to reorder the elements in the DOM.
    * See the [General Update Pattern](http://bl.ocks.org/mbostock/3808218) example thread for more on data joins.
    *
    * @tparam OldD Refers to the type of the old datum associated with the exit selection elements. Ensure that you set
    *              the generic to the correct type, if you need to access the data on the exit selection in follow-up
    *              steps (e.g., to set styles as part of an exit transition before removing them).
    * @return Selection after the exit is complete.
    */
  def exit[OldD](): Selection[E, OldD, PE, PD] = {
    new Selection(facade.exit[OldD]())
  }

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
  def datum(): D = facade.datum()

  /** Sets the element's bound data to the specified value on all selected elements. If the value is a constant, all
    * elements are given the same datum; otherwise, if the value is a function, it is evaluated for each selected
    * element, in order, being passed the current datum (`d`), the current index (`i`), and the current group (`nodes`),
    * with this as the current DOM element (`nodes(i)`). The function is then used to set each element’s new data.
    *
    * A `null` value will delete the bound data.
    *
    * @param  value Value to set the elements' bound data to.
    * @return Selection after setting the elements' bound data.
    */
  def datum[ND](value: D3Function[E, D, ND]): Selection[E, ND, PE, PD] = {
    new Selection(facade.datum(value))
  }

  //endregion Joining Data

  //region Handling Events

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
    * An optional capture flag may be specified which corresponds to the W3C
    * [`useCapture` flag](http://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-registration): "After initiating
    * capture, all events of the specified type will be dispatched to the registered EventListener before being
    * dispatched to any EventTargets beneath them in the tree. Events which are bubbling upward through the tree will
    * not trigger an EventListener designated to use capture."
    *
    * @param  domTypeNames Event type, such as `"click"`, `"mouseover"`, or `"submit"`. Any DOM event type supported by
    *                      your browser may be used. The type may be optionally followed by a period (`.`) and a name.
    *                      The optional name allows multiple callbacks to be registered to receive events of the same
    *                      type, such as `"click.foo"` and `"click.bar"`. To specify multiple typenames, separate
    *                      typenames with spaces, such as `"input change"` or `"click.foo click.bar"`.
    * @param  listener     Listener function.
    * @param  capture      Optional capture flag may be specified which corresponds to the W3C
    *                      [`useCapture` flag](http://www.w3.org/TR/DOM-Level-2-Events/events.html#Events-registration):
    *                      "After initiating capture, all events of the specified type will be dispatched to the
    *                      registered EventListener before being dispatched to any EventTargets beneath them in the
    *                      tree. Events which are bubbling upward through the tree will not trigger an EventListener
    *                      designated to use capture."
    * @return Selection after the modification.
    */
  def on(domTypeNames: String, listener: D3Function[E, D, Unit], capture: Boolean = false): Selection[E, D, PE, PD] = {
    new Selection(facade.on(domTypeNames, listener, capture))
  }

  /** Dispatches a [custom event](http://www.w3.org/TR/dom/#interface-customevent) of the specified type to each
    * selected element, in order. An optional parameters map may be specified to set additional properties of the event.
    * It may contain the following fields:
    *
    *   - [`bubbles`](https://www.w3.org/TR/dom/#dom-event-bubbles_: If `true`, the event is dispatched to ancestors in
    *     reverse tree order.
    *   - [`cancelable`](https://www.w3.org/TR/dom/#dom-event-cancelable): If `true`, `event.preventDefault` is allowed.
    *   - [`detail`](https://www.w3.org/TR/dom/#dom-customevent-detail): Any custom data associated with the event.
    *
    * If `parameters` is a function, it is evaluated for each selected element, in order, being passed the current
    * datum (`d`), the current index (`i`), and the current group (`nodes`), with this as the current DOM element
    * (`nodes(i)`). It must return the parameters map for the current element.
    *
    * @param  value      Custom event type.
    * @param  parameters Optional parameters function.
    * @return Selection after the modification.
    */
  def dispatch(value: String, parameters: D3Function[E, D, CustomEventParameters] = null): Selection[E, D, PE, PD] = {
    if (parameters != null)
      new Selection(facade.dispatch(value, parameters))
    else
      new Selection(facade.dispatch(value))
  }

  //endregion Handling Events

  //region Control Flow

  /** Invokes the specified function for each selected element, passing in the current datum (`d`), the current index
    * (`i`), and the current group (`nodes`), with this of the current DOM element (`nodes(i)`). This method can be used
    * to invoke arbitrary code for each selected element, and is useful for creating a context to access parent and
    * child data simultaneously.
    *
    * For example:
    * {{{
    *   parent.each((e: dom.Element, p: dom.Element) => {
    *     d3.select(e)
    *       .selectAll(".child")
    *       .text((d: dom.Element) => "child ${d.name} of ${p.name}")
    *   })
    * }}}
    *
    * See [Sized Donut Multiples](http://bl.ocks.org/mbostock/4c5fad723c87d2fd8273) for an example.
    *
    * @param  function Function which is invoked for each selected element.
    * @return This selection.
    */
  def each(function: D3Function[E, D, Unit]): Selection[E, D, PE, PD] = {
    new Selection(facade.each(function))
  }

  /** Invokes the specified function exactly once, passing in this selection and returns this selection. This is
    * equivalent to invoking the function by hand but facilitates method chaining. For example, to set several styles
    * in a reusable function:
    * {{{
    *   def name(
    *     selection: Selection[dom.Element, js.Any, dom.Element, js.Any],
    *     first: String,
    *     last: String
    *   ): Unit = {
    *     selection
    *       .attr("first-name", first)
    *       .attr("last-name", last)
    *   }
    * }}}
    * Now say:
    * {{{
    *   d3.select.all("div").call(name(_, "John", "Snow"))
    * }}}
    * This is roughly equivalent to:
    * {{{
    *   name(d3.select.all("div"), "John", "Snow")
    * }}}
    * The only difference is that `Selection.call()` always returns the selection and not the return value of the
    * called function.
    *
    * @param  function Function to call.
    * @return This selection.
    */
   def call[SE >: E <: dom.EventTarget, SD >: E, SPE >: PE <: dom.EventTarget, SPD >: PD](
       function: (Selection[SE, SD, SPE, SPD]) => _
   ): Selection[E, D, PE, PD] = {
     new Selection(facade.call((f: Selection.Facade[SE, SD, SPE, SPD]) => function(new Selection[SE, SD, SPE, SPD](f))))
   }

  /** Returns `true` if this selection contains no (non-null) elements. */
  def empty(): Boolean = facade.empty()

  /** Returns an array of all (non-null) elements in this selection. */
  def nodes[SE >: E <: dom.EventTarget](): Seq[SE] = facade.nodes().toSeq

  /** Returns the first (non-null) element in this selection. If the selection is empty, returns `null`. */
  def node(): E = facade.node()

  /** Returns the total number of elements in this selection. */
  def size(): Int = facade.size()

  //endregion Control Flow

  //region Transitions

  /** Returns a new transition on the given selection with the specified name. If a name is not specified, `null` is
    * used. The new transition is only exclusive with other transitions of the same name.
    *
    * If the name is a transition instance, the returned transition has the same id and name as the specified
    * transition. If a transition with the same id already exists on a selected element, the existing transition is
    * returned for that element. Otherwise, the timing of the returned transition is inherited from the existing
    * transition of the same id on the nearest ancestor of each selected element. Thus, this method can be used to
    * synchronize a transition across multiple selections, or to re-select a transition for specific elements and modify
    * its configuration.
    *
    * For example:
    * {{{
    *   val t = d3.transition()
    *     .duration(750)
    *     .ease(d3.ease.linear)
    *
    *   d3.select.all(".apple").transition(t)
    *     .style("fill", "red")
    *
    *   d3.select.all(".orange").transition(t)
    *     .style("fill", "orange")
    * }}}
    *
    * If the specified transition is not found on a selected node or its ancestors (such as if the transition
    * [already ended](https://github.com/d3/d3-transition#the-life-of-a-transition)), the default timing parameters are
    * used. However, in a future release, this will likely be changed to throw an error.
    * See [#59](https://github.com/d3/d3-transition/issues/59).
    *
    * @param  name Transition name to use.
    * @return Transition.
    */
  def transition[SE >: E <: dom.EventTarget, SD >: D, SPE >: PE <: dom.EventTarget, SPD >: PD](
      name: String = null
  ): Transition[SE, SD, SPE, SPD] = {
    if (name != null)
      new Transition(facade.transition(name))
    else
      new Transition(facade.transition())
  }

  /** Returns a new transition on the given selection with the specified name. If a name is not specified, `null` is
    * used. The new transition is only exclusive with other transitions of the same name.
    *
    * If a transition instance is provided, the returned transition has the same id and name as the specified
    * transition. If a transition with the same id already exists on a selected element, the existing transition is
    * returned for that element. Otherwise, the timing of the returned transition is inherited from the existing
    * transition of the same id on the nearest ancestor of each selected element. Thus, this method can be used to
    * synchronize a transition across multiple selections, or to re-select a transition for specific elements and modify
    * its configuration.
    *
    * For example:
    * {{{
    *   val t = d3.transition()
    *     .duration(750)
    *     .ease(d3.ease.linear)
    *
    *   d3.select.all(".apple").transition(t)
    *     .style("fill", "red")
    *
    *   d3.select.all(".orange").transition(t)
    *     .style("fill", "orange")
    * }}}
    *
    * If the specified transition is not found on a selected node or its ancestors (such as if the transition
    * [already ended](https://github.com/d3/d3-transition#the-life-of-a-transition)), the default timing parameters are
    * used. However, in a future release, this will likely be changed to throw an error.
    * See [#59](https://github.com/d3/d3-transition/issues/59).
    *
    * @param  transition Transition from which to derive the id and name of the new transition.
    * @return Transition.
    */
  def transition[SE >: E <: dom.EventTarget, SD >: D, SPE >: PE <: dom.EventTarget, SPD >: PD](
      transition: Transition[dom.EventTarget, js.Any, dom.EventTarget, js.Any]
  ): Transition[SE, SD, SPE, SPD] = {
    new Transition(facade.transition(transition.facade))
  }

  /** Interrupts the active transition of the specified name on the selected elements, and cancels any pending
    * transitions with the specified name, if any. If a name is not specified, `null` is used.
    *
    * Interrupting a transition on an element has no effect on any transitions on any descendant elements. For example,
    * an axis transition consists of multiple independent, synchronized transitions on the descendants of the axis G
    * element (the tick lines, the tick labels, the domain path, etc.). To interrupt the axis transition, you must
    * therefore interrupt the descendants:
    * {{{
    *   selection.select.all("*").interrupt()
    * }}}
    * The universal selector, `*`, selects all descendant elements. If you also want to interrupt the G element itself:
    * {{{
    *   selection.interrupt().select.all("*").interrupt()
    * }}}
    *
    * @param  name Optional name for the transition to interrupt.
    * @return Transition after the interruption.
    */
  def interrupt[SE >: E <: dom.EventTarget, SD >: D, SPE >: PE <: dom.EventTarget, SPD >: PD](
      name: String = null
  ): Transition[SE, SD, SPE, SPD] = {
    if (name != null)
      new Transition(facade.interrupt(name))
    else
      new Transition(facade.interrupt())
  }

  //endregion Transitions
}

object Selection {
  //region Selecting Elements

  /** Selects the first element that matches the specified selector string. If no elements match the selector, then this
    * method returns an empty selection. If multiple elements match the selector, only the first matching element
    * (in document order) will be selected.
    *
    * For example, to select the first anchor element:
    * {{{
    *   val anchor = d3.select("a")
    * }}}
    *
    * @param  selector CSS selector string.
    * @tparam E Type of the element to the be selected.
    * @tparam D Type of the datum on the selected element. This is useful when re-selecting an element with a
    *           previously set and known datum type.
    * @return Selection.
    */
  def apply[E <: dom.EventTarget, D](selector: String): Selection[E, D, dom.raw.HTMLElement, js.Any] = {
    new Selection(Facade.select(selector))
  }

  /** Selects the specified node element. This is useful if you already have a reference to a node, such as this within
    * an event listener.
    *
    * For example, to make a clicked paragraph red:
    * {{{
    *   d3.select.all("p").on("click", (p: dom.Element) => {
    *     d3.select(p).style("color", "red")
    *   })
    * }}}
    *
    * @param  node Element to be selected.
    * @tparam E Type of the element to the be selected.
    * @tparam D Type of the datum on the selected element. This is useful when re-selecting an element with a
    *           previously set and known datum type.
    * @return Selection.
    */
  def apply[E <: dom.EventTarget, D](node: E): Selection[E, D, Null, Unit] = {
    new Selection(Facade.select(node))
  }

  /** Creates an empty selection. */
  def all(): Selection[Null, Unit, Null, Unit] = {
    new Selection(Facade.selectAll())
  }

  /** Creates an empty selection. */
  def all(selector: Null): Selection[Null, Unit, Null, Unit] = {
    new Selection(Facade.selectAll(selector))
  }

  /** Creates an empty selection. */
  def all(selector: Unit): Selection[Null, Unit, Null, Unit] = {
    new Selection(Facade.selectAll(selector))
  }

  /** Selects all elements that match the specified selector string. The elements will be selected in document order
    * (top-to-bottom). If no elements in the document match the selector, then this method returns an empty selection.
    *
    * For example, to select all paragraphs:
    * {{{
    *   val paragraph = d3.select.all("p")
    * }}}
    *
    * @param  selector CSS selector string.
    * @tparam E Type of the elements to the be selected.
    * @tparam D Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *           previously set and known datum type.
    * @return Selection.
    */
  def all[E <: dom.EventTarget, D](selector: String): Selection[E, D, dom.html.Element, js.Any] = {
    new Selection(Facade.selectAll(selector))
  }

  /** Selects the specified array of node elements.
    *
    * @param  nodes Elements to be selected.
    * @tparam E Type of the elements to the be selected.
    * @tparam D Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *           previously set and known data type.
    * @return Selection.
    */
  def all[E <: dom.EventTarget, D](nodes: js.Array[E]): Selection[E, D, Null, Unit] = {
    new Selection(Facade.selectAll(nodes))
  }

  /** Selects the specified node elements. This signature allows for the selection of nodes contained in a `NodeList`,
    * `HTMLCollection`, or any other similar data structure.
    *
    * For example, to color all links red:
    * {{{
    *   d3.select.all(dom.document.links).style("color", "red")
    * }}}
    *
    * @param  nodes Elements to be selected.
    * @tparam E Type of the elements to the be selected.
    * @tparam D Type of the datum on the selected elements. This is useful when re-selecting elements with a
    *           previously set and known data type.
    * @return Selection.
    */
  def all[E <: dom.EventTarget, D](nodes: ArrayLike[E]): Selection[E, D, Null, Unit] = {
    new Selection(Facade.selectAll(nodes))
  }

  /** Given the specified selector, returns a function which returns `true` if the provided element matches the
    * specified selector. This method is used internally by `Selection.filter()`.
    *
    * For example, this:
    * {{{
    *   val div = selection.filter("div")
    * }}}
    * is equivalent to this:
    * {{{
    *   val div = selection.filter(d3.select.matcher("div"))
    * }}}
    *
    * Although D3 is not a compatibility layer, this implementation does support vendor-prefixed implementations due to
    * the recent standardization of `element.matches()`.
    *
    * @param  selector Selector string to use.
    * @return Function that returns `true` if the provided element matches `selector`.
    */
  def matcher(selector: String): js.Function1[dom.EventTarget, Boolean] = {
    Facade.matcher(selector)
  }

  /** Given the specified selector, returns a function which returns the first descendant of `this` element that matches
    * the specified selector. This method is used internally by `Selection.select()`.
    *
    * For example, this:
    * {{{
    *   val div = selection.select("div")
    * }}}
    * is equivalent to this:
    * {{{
    *   val div = selection.select(d3.select.selector("div"))
    * }}}
    *
    * @param  selector Selector string to use.
    * @return Function which returns the first descendant of `this` element that matches the specified selector.
    */
  def selector[DE](selector: String): js.Function1[dom.EventTarget, DE] = {
    Facade.selector(selector)
  }

  /** Given the specified selector, returns a function which returns all descendants of `this` element that match
    * the specified selector. This method is used internally by `Selection.selectAll()`.
    *
    * For example, this:
    * {{{
    *   val div = selection.selectAll("div")
    * }}}
    * is equivalent to this:
    * {{{
    *   val div = selection.selectAll(d3.select.selectorAll("div"))
    * }}}
    *
    * @param  selector Selector string to use.
    * @return Function which returns all descendants of `this` element that match the specified selector.
    */
  def selectorAll[DE <: dom.Node](selector: String): js.Function1[dom.EventTarget, dom.NodeListOf[DE]] = {
    Facade.selectorAll(selector)
  }

  /** Returns the owner window of the specified node. If `node` is a node, then this method returns the owner document's
    * default view; if it is a document, then returns its default view; otherwise, it returns the node.
    *
    * @param  node A DOM window.
    */
  def window(node: dom.Window): dom.Window = Facade.window(node)

  /** Returns the owner window of the specified node. If `node` is a node, then this method returns the owner document's
    * default view; if it is a document, then returns its default view; otherwise, it returns the node.
    *
    * @param  node A DOM document.
    */
  def window(node: dom.Document): dom.Window = Facade.window(node)

  /** Returns the owner window of the specified node. If `node` is a node, then this method returns the owner document's
    * default view; if it is a document, then returns its default view; otherwise, it returns the node.
    *
    * @param  node A DOM element.
    */
  def window(node: dom.Element): dom.Window = Facade.window(node)

  /** Returns the value of the style property with the specified name for the specified node. If the node has an inline
    * style with the specified name, then its value is returned; otherwise, the computed property value is returned.
    * See also `Selection.style()`.
    *
    * @param  node A DOM node (e.g. `HTMLElement`, `SVGElement`) for which to retrieve the style property.
    * @param  name Name of the style property.
    * @return Value of the style property.
    */
  def style(node: dom.Element, name: String): String = Facade.style(node, name)

  //endregion Selecting Elements

  //region Modifying Elements

  /** Given the specified element name, returns a single-element selection containing a detached element of the given
    * name in the current document.
    *
    * @param  name Tag name of the element to be added.
    * @tparam E Type of the element to be added.
    * @return Selection containing a detached element of the given name in the current document.
    */
  def create[E <: dom.EventTarget](name: String): Selection[E, Unit, Null, Unit] = {
    new Selection(Facade.create[E](name))
  }

  /** Given the specified element name, returns a function which creates an element of the given name, assuming that
    * `this` is the parent element. This method is used internally by `Selection.append()` and `Selection.insert()` to
    * create new elements.
    *
    * For example, this:
    * {{{
    *   val div = selection.append("div")
    * }}}
    * is equivalent to this:
    * {{{
    *   val div = selection.append(d3.select.creator("div"))
    * }}}
    *
    * @param  name Tag name of the element to be added.
    * @tparam E Type of the element to be added.
    * @return Function which creates an element of the given name.
    */
  def creator[E <: dom.EventTarget](name: String): js.Function1[dom.EventTarget, E] = Facade.creator[E](name)

  //endregion Modifying Elements

  @js.native trait Group extends js.Array[dom.EventTarget] {
    val parentNode: dom.EventTarget = js.native
  }

  @JSImport("d3-selection", JSImport.Namespace)
  @js.native private[Selection] object Facade extends js.Object {
    def selection: Facade[dom.html.Element, js.Any, Null, Unit] = js.native
    def select[E <: dom.EventTarget, D](selector: String): Facade[E, D, dom.raw.HTMLElement, js.Any] = js.native
    def select[E <: dom.EventTarget, D](node: E): Facade[E, D, Null, Unit] = js.native
    def selectAll(): Facade[Null, Unit, Null, Unit] = js.native
    def selectAll(selector: Null): Facade[Null, Unit, Null, Unit] = js.native
    def selectAll(selector: Unit): Facade[Null, Unit, Null, Unit] = js.native
    def selectAll[E <: dom.EventTarget, D](selector: String): Facade[E, D, dom.html.Element, js.Any] = js.native
    def selectAll[E <: dom.EventTarget, D](nodes: js.Array[E]): Facade[E, D, Null, Unit] = js.native
    def selectAll[E <: dom.EventTarget, D](nodes: ArrayLike[E]): Facade[E, D, Null, Unit] = js.native
    def matcher(selector: String): js.Function1[dom.EventTarget, Boolean] = js.native
    def selector[DE](selector: String): js.Function1[dom.EventTarget, DE] = js.native
    def selectorAll[DE <: dom.Node](selector: String): js.Function1[dom.EventTarget, dom.NodeListOf[DE]] = js.native
    def window(node: dom.Window): dom.Window = js.native
    def window(node: dom.Document): dom.Window = js.native
    def window(node: dom.Element): dom.Window = js.native
    def style(node: dom.Element, name: String): String = js.native
    def create[E <: dom.EventTarget](name: String): Facade[E, Unit, Null, Unit] = js.native
    def creator[E <: dom.EventTarget](name: String): js.Function1[dom.EventTarget, E] = js.native
  }

  @js.native private[d3] trait Facade[+E <: dom.EventTarget, +D, +PE <: dom.EventTarget, +PD] extends js.Object {
    // @JSBracketAccess def apply(index: Index): Group = js.native
    // @JSBracketAccess def update(index: Index, value: Group): Unit = js.native

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
    def classed(names: String): Boolean = js.native
    def classed(names: String, value: D3Function[E, D, Boolean]): Facade[E, D, PE, PD] = js.native
    def style(name: String): String = js.native
    def style[T](name: String, value: D3Function[E, D, T], priority: String = null)(implicit ev: T => D3AttrValue): Facade[E, D, PE, PD] = js.native
    def property(name: String): js.Dynamic = js.native
    def property[T](name: String, value: D3Function[E, D, T])(implicit ev: T => D3PropertyValue): Facade[E, D, PE, PD] = js.native
    def property[T](name: Local[T]): T = js.native
    def property[T](name: Local[T], value: D3Function[E, D, T]): Facade[E, D, PE, PD] = js.native
    def text(): String = js.native
    def text[T](value: D3Function[E, D, T])(implicit ev: T => D3TextValue): Facade[E, D, PE, PD] = js.native
    def html(): String = js.native
    def html[T](value: D3Function[E, D, T])(implicit ev: T => D3TextValue): Facade[E, D, PE, PD] = js.native
    def append(value: String): Facade[E, D, PE, PD] = js.native
    def append[ChildE <: dom.EventTarget](value: D3Function[E, D, ChildE]): Facade[ChildE, D, PE, PD] = js.native
    def insert(value: String): Facade[E, D, PE, PD] = js.native
    def insert[ChildE <: dom.EventTarget](value: D3Function[E, D, ChildE]): Facade[ChildE, D, PE, PD] = js.native
    def insert(value: String, before: String): Facade[E, D, PE, PD] = js.native
    def insert[SE >: E <: dom.EventTarget](value: String, before: D3Function[E, D, SE]): Facade[E, D, PE, PD] = js.native
    def insert[ChildE <: dom.EventTarget](value: D3Function[E, D, ChildE], before: String): Facade[ChildE, D, PE, PD] = js.native
    def insert[SE >: E <: dom.EventTarget, ChildE <: dom.EventTarget](value: D3Function[E, D, ChildE], before: D3Function[E, D, SE]): Facade[ChildE, D, PE, PD] = js.native
    def remove(): Facade[E, D, PE, PD] = js.native
    def clone(deep: Boolean = false): Facade[E, D, PE, PD] = js.native
    def sort(comparator: js.Function2[D, D, Double] = null): Facade[E, D, PE, PD] = js.native
    def order(): Facade[E, D, PE, PD] = js.native
    def raise(): Facade[E, D, PE, PD] = js.native
    def lower(): Facade[E, D, PE, PD] = js.native

    def data[SD >: D](): js.Array[SD] = js.native
    def data[ND](data: D3Function[E, D, js.Array[ND]], key: D3Function[E, ND, String] = null): Facade[E, ND, PE, PD] = js.native
    def enter(): Facade[E, D, PE, PD] = js.native
    def exit[OldD](): Facade[E, OldD, PE, PD] = js.native
    def datum(): D = js.native
    def datum[ND](value: D3Function[E, D, ND]): Facade[E, ND, PE, PD] = js.native

    def on[SE >: E <: dom.EventTarget, SD >: D](domTypeNames: String): D3Function[SE, SD, Unit] = js.native
    def on(domTypeNames: String, listener: D3Function[E, D, Unit], capture: Boolean = false): Facade[E, D, PE, PD] = js.native
    def dispatch(value: String, parameters: D3Function[E, D, CustomEventParameters] = null): Facade[E, D, PE, PD] = js.native

    def each(func: D3Function[E, D, Unit]): Facade[E, D, PE, PD] = js.native
    def call[SE >: E <: dom.EventTarget, SD >: E, SPE >: PE <: dom.EventTarget, SPD >: PD](function: js.Function1[Facade[SE, SD, SPE, SPD], _]): Facade[E, D, PE, PD] = js.native
    def empty(): Boolean = js.native
    def nodes[SE >: E <: dom.EventTarget](): js.Array[SE] = js.native
    def node(): E = js.native
    def size(): Int = js.native

    def transition[SE >: E <: dom.EventTarget, SD >: D, SPE >: PE <: dom.EventTarget, SPD >: PD](name: String = null): Transition.Facade[SE, SD, SPE, SPD] = js.native
    def transition[SE >: E <: dom.EventTarget, SD >: D, SPE >: PE <: dom.EventTarget, SPD >: PD](transition: Transition.Facade[dom.EventTarget, js.Any, dom.EventTarget, js.Any]): Transition.Facade[SE, SD, SPE, SPD] = js.native
    def interrupt[SE >: E <: dom.EventTarget, SD >: D, SPE >: PE <: dom.EventTarget, SPD >: PD](name: String = null): Transition.Facade[SE, SD, SPE, SPD] = js.native
  }
}
