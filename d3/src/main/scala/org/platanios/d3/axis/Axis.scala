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

package org.platanios.d3.axis

import org.platanios.d3.selection.{Selection, TransitionLike}

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-axis", JSImport.Namespace)
@js.native object Axis extends js.Object {
  def axisTop[Domain](scale: AxisScale[Domain]): Axis[Domain] = js.native
  def axisRight[Domain](scale: AxisScale[Domain]): Axis[Domain] = js.native
  def axisBottom[Domain](scale: AxisScale[Domain]): Axis[Domain] = js.native
  def axisLeft[Domain](scale: AxisScale[Domain]): Axis[Domain] = js.native
}

@js.native trait Axis[Domain] extends js.Object {
  def apply[C <: dom.Element](context: Selection[C, js.Any, dom.Element, js.Any]): Unit = js.native
  // def apply[C <: dom.Element](context: TransitionLike[C, js.Any]): Unit = js.native
  def scale[A <: AxisScale[Domain]](): A = js.native
  def scale(scale: AxisScale[Domain]): this.type = js.native

  // TODO: The argument type depends on the scale being used.
  def ticks(arg0: js.Any, args: js.Any*): this.type = js.native

  def tickArguments(): js.Array[js.Any] = js.native
  def tickArguments[T: TickArgumentsArgs](args: T): this.type = js.native
  def tickValues(): js.Array[Domain] | Null = js.native
  def tickValues(values: js.Array[Domain]): this.type = js.native
  def tickValues(values: Null): this.type = js.native
  def tickFormat(): js.Function2[Domain, Double, String] | Null = js.native
  def tickFormat(format: js.Function2[Domain, Double, String]): this.type = js.native
  def tickFormat(format: Null): this.type = js.native
  def tickSize(): Double = js.native
  def tickSize(size: Double): this.type = js.native
  def tickSizeInner(): Double = js.native
  def tickSizeInner(size: Double): this.type = js.native
  def tickSizeOuter(): Double = js.native
  def tickSizeOuter(size: Double): this.type = js.native
  def tickPadding(): Double = js.native
  def tickPadding(padding: Double): this.type = js.native
}
