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

import org.platanios.d3.{D3AttrValue, D3Function}

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait Transition[E <: dom.EventTarget, D, PE <: dom.EventTarget, PD] extends js.Object {
  def select[DescElement <: dom.EventTarget](selector: String): Transition[DescElement, D, PE, PD] = js.native
//  def select[DescElement <: dom.EventTarget](selector: ValueFn[E, D, DescElement]): Transition[DescElement, D, PE, PD] = js.native
  def selectAll[DescElement <: dom.EventTarget, OldDatum](selector: String): Transition[DescElement, OldDatum, E, D] = js.native
//  def selectAll[DescElement <: dom.EventTarget, OldDatum](selector: ValueFn[E, D, js.Array[DescElement] | ArrayLike[DescElement]]): Transition[DescElement, OldDatum, E, D] = js.native
  def selection(): Selection[E, D, PE, PD] = js.native
  def transition(): Transition[E, D, PE, PD] = js.native
  def attr(name: String): String = js.native
  def attr[T](name: String, value: D3Function[E, D, T])(implicit ev: T => D3AttrValue): this.type = js.native
  def attrTween(name: String, factory: Null): this.type = js.native
//  def attrTween(name: String, factory: ValueFn[E, D, js.Function1[Double, String]]): this.type = js.native
  def style(name: String, value: Null): this.type = js.native
  def style(name: String, value: String | Double | Boolean, priority: Null | String = ???): this.type = js.native
//  def style(name: String, value: ValueFn[E, D, String | Double | Boolean | Null], priority: Null | String = ???): this.type = js.native
//  def styleTween(name: String): ValueFn[E, D, js.Function1[Double, String]] | Unit = js.native
  def styleTween(name: String, factory: Null): this.type = js.native
//  def styleTween(name: String, factory: ValueFn[E, D, js.Function1[Double, String]], priority: Null | String = ???): this.type = js.native
  def text(value: Null): this.type = js.native
  def text(value: String | Double | Boolean): this.type = js.native
//  def text(value: ValueFn[E, D, String | Double | Boolean]): this.type = js.native
//  def tween(name: String): ValueFn[E, D, js.Function1[Double, Unit]] | Unit = js.native
  def tween(name: String, tweenFn: Null): this.type = js.native
//  def tween(name: String, tweenFn: ValueFn[E, D, js.Function1[Double, Unit]]): this.type = js.native
  def remove(): this.type = js.native
  def merge(other: Transition[E, D, PE, PD]): Transition[E, D, PE, PD] = js.native
  // def filter(filter: String): Transition[E, D, PE, PD] = js.native
  def filter[FE <: dom.EventTarget](filter: String): Transition[FE, D, PE, PD] = js.native
//  def filter(filter: ValueFn[E, D, Boolean]): Transition[E, D, PE, PD] = js.native
//  def filter[FilteredElement <: dom.EventTarget](filter: ValueFn[E, D, Boolean]): Transition[FilteredElement, D, PE, PD] = js.native
//  def on(`type`: String): ValueFn[E, D, Unit] | Unit = js.native
//  def on(typenames: String, listener: Null): this.type = js.native
//  def on(`type`: String, listener: ValueFn[E, D, Unit]): this.type = js.native
//  def each(func: ValueFn[E, D, Unit]): this.type = js.native
  def call(func: js.Function, args: js.Any*): this.type = js.native
  def empty(): Boolean = js.native
  def node(): E | Null = js.native
  def nodes(): js.Array[E] = js.native
  def size(): Double = js.native
  def delay(): Double = js.native
  def delay(milliseconds: Double): this.type = js.native
  // def delay(milliseconds: ValueFn[E, D, Double]): this.type = js.native
  def duration(): Double = js.native
  def duration(milliseconds: Double): this.type = js.native
  def duration(milliseconds: D3Function[E, D, Double]): this.type = js.native
  def ease(): js.Function1[Double, Double] = js.native
  def ease(easingFn: js.Function1[Double, Double]): this.type = js.native
}

