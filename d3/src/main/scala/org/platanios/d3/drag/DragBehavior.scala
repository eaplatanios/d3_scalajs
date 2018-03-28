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

import org.platanios.d3.{ContainerElement, D3Function}
import org.platanios.d3.selection.Selection

import org.scalajs.dom

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait DragBehavior[E <: dom.Element, D, Subject] extends js.Function {
  def apply(selection: Selection[E, D, dom.Element, js.Any], args: js.Any*): Unit = js.native
  def container[C: ContainerElement](): D3Function[E, D, C] = js.native
  def container[C: ContainerElement](accessor: D3Function[E, D, C]): this.type = js.native
  def filter(): D3Function[E, D, Boolean] = js.native
  def filter(filterFn: D3Function[E, D, Boolean]): this.type = js.native
  def touchable(): D3Function[E, D, Boolean] = js.native
  def touchable(touchable: D3Function[E, D, Boolean]): this.type = js.native
  def subject(): D3Function[E, D, Subject] = js.native
  def subject(accessor: D3Function[E, D, Subject]): this.type = js.native
  def clickDistance(): Double = js.native
  def clickDistance(distance: Double): this.type = js.native

  def on(typenames: String): D3Function[E, D, Unit] = js.native
  def on(typenames: String, listener: D3Function[E, D, Unit]): this.type = js.native
  def on(typenames: String, listener: Null): this.type = js.native
}
