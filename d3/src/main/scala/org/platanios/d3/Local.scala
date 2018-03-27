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

package org.platanios.d3

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|
import org.scalajs.dom

/** D3 locals allow you to define local state independent of data. For instance, when rendering small multiples of
  * time-series data, you might want to use the same x-scale for all charts but distinct y-scales to compare the
  * relative performance of each metric. D3 locals are scoped by DOM elements: on set, the value is stored on the given
  * element; on get, the value is retrieved from given element or the nearest ancestor that defines it.
  *
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-selection", JSImport.Namespace)
@js.native object Local extends js.Object {
  /** Declares a new local variable.
    *
    * For example:
    * {{{
    *   val local = D3.local[String]()
    * }}}
    * Like `val`, each local is a distinct symbolic reference; unlike `val`, the value of each local is also scoped by
    * the DOM.
    *
    * @tparam T Type of the local variable.
    * @return New local variable
    */
  def local[T](): Local[T] = js.native
}

/** Represents a local variable. */
@js.native trait Local[T] extends js.Object {
  /** Retrieves a local variable stored in the provided node (or one of its parents).
    *
    * @param  node Node element.
    * @return Local variable stored in `node`.
    */
  def get(node: dom.Element): T | Unit = js.native

  /** Deletes the value associated with the provided node. Values stored on ancestors are not affected, meaning that
    * child nodes will still be able to see their inherited values.
    *
    * @param  node Node element.
    * @return `true` if there was a value stored directly on the node, and `false` otherwise.
    */
  def remove(node: dom.Element): Boolean = js.native

  /** Stores a value for this local variable. Calling `Local.get()` on children of this node will also retrieve the
    * variable's value.
    *
    * @param  node  Node element.
    * @param  value Value to store.
    * @return
    */
  def set(node: dom.Element, value: T): dom.Element = js.native

  /** Returns a string with the internally assigned property name for the local variable which is used to store the
    * value on a node. */
  override def toString: String = js.native
}
