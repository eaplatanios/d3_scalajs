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

package org.platanios.d3.drag

import org.platanios.d3.ContainerElement
import org.platanios.d3.selection.{D3VOrFn, Selection}
import org.scalajs.dom

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait DragBehavior[E <: dom.Element, Datum, Subject] extends js.Function {
  def apply(selection: Selection[E, Datum, dom.Element, js.Any], args: js.Any*): Unit = js.native
  def container[C: ContainerElement, T: D3VOrFn[C]#CB](): T = js.native
  def container[C: ContainerElement, T: D3VOrFn[C]#CB](accessor: T): this.type = js.native
  def filter[T: D3VOrFn[Boolean]#CB](): T = js.native
  def filter[T: D3VOrFn[Boolean]#CB](filterFn: T): this.type = js.native
  def touchable[T: D3VOrFn[Boolean]#CB](): T = js.native
  def touchable[T: D3VOrFn[Boolean]#CB](touchable: T): this.type = js.native
  def subject[T: D3VOrFn[Subject]#CB](): T = js.native
  def subject[T: D3VOrFn[Subject]#CB](accessor: T): this.type = js.native
  def clickDistance(): Double = js.native
  def clickDistance(distance: Double): this.type = js.native
  def on[T: D3VOrFn[Unit]#CB](typenames: String): T = js.native
  def on[T: D3VOrFn[Unit]#CB](typenames: String, listener: T): this.type = js.native
  def on(typenames: String, listener: Null): this.type = js.native
}
