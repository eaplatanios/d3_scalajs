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

package org.platanios.d3.zoom

import org.platanios.d3.selection.{D3VOrFn, Selection, TransitionLike}

import org.scalajs.dom

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait ZoomBehavior[E <: dom.Element, Datum] extends js.Function {
  def apply(selection: Selection[E, Datum, dom.Element, js.Any], args: js.Any*): Unit = js.native
  def transform[T: D3VOrFn[ZoomTransform]#CB](selection: Selection[E, Datum, dom.Element, js.Any], transform: T): Unit = js.native
  def transform[T: D3VOrFn[ZoomTransform]#CB](transition: TransitionLike[E, Datum], transform: T): Unit = js.native
  def translateBy[Tx: D3VOrFn[Double]#CB, Ty: D3VOrFn[Double]#CB](selection: Selection[E, Datum, dom.Element, js.Any], x: Tx, y: Ty): Unit = js.native
  def translateBy[Tx: D3VOrFn[Double]#CB, Ty: D3VOrFn[Double]#CB](transition: TransitionLike[E, Datum], x: Tx, y: Ty): Unit = js.native
  def translateTo[Tx: D3VOrFn[Double]#CB, Ty: D3VOrFn[Double]#CB](selection: Selection[E, Datum, dom.Element, js.Any], x: Tx, y: Ty): Unit = js.native
  def translateTo[Tx: D3VOrFn[Double]#CB, Ty: D3VOrFn[Double]#CB](transition: TransitionLike[E, Datum], x: Tx, y: Ty): Unit = js.native
  def scaleBy[T: D3VOrFn[Double]#CB](selection: Selection[E, Datum, dom.Element, js.Any], k: T): Unit = js.native
  def scaleBy[T: D3VOrFn[Double]#CB](transition: TransitionLike[E, Datum], k: T): Unit = js.native
  def scaleTo[T: D3VOrFn[Double]#CB](selection: Selection[E, Datum, dom.Element, js.Any], k: T): Unit = js.native
  def scaleTo[T: D3VOrFn[Double]#CB](transition: TransitionLike[E, Datum], k: T): Unit = js.native
  def constrain(): js.Function3[ZoomTransform, js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], ZoomTransform] = js.native
  def constrain(constraint: js.Function3[ZoomTransform, js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], ZoomTransform]): this.type = js.native
  def filter[T: D3VOrFn[Boolean]#CB](): T = js.native
  def filter[T: D3VOrFn[Boolean]#CB](filterFn: T): this.type = js.native
  def touchable[T: D3VOrFn[Boolean]#CB](): T = js.native
  def touchable[T: D3VOrFn[Boolean]#CB](touchable: T): this.type = js.native
  def wheelDelta[T: D3VOrFn[Double]#CB](): T = js.native
  def wheelDelta[T: D3VOrFn[Double]#CB](delta: T): this.type = js.native
  def extent[T: D3VOrFn[js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]]]#CB](): T = js.native
  def extent(extent: js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]]): this.type = js.native
  def extent[T: D3VOrFn[js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]]]#CB](extent: T): this.type = js.native
  def scaleExtent(): js.Tuple2[Double, Double] = js.native
  def scaleExtent(extent: js.Tuple2[Double, Double]): this.type = js.native
  def translateExtent(): js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]] = js.native
  def translateExtent(extent: js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]]): this.type = js.native
  def clickDistance(): Double = js.native
  def clickDistance(distance: Double): this.type = js.native
  def duration(): Double = js.native
  def duration(duration: Double): this.type = js.native
  def interpolate(): (ZoomView, ZoomView) => (Double => ZoomView) = js.native
  def interpolate(interpolatorFactory: (ZoomView, ZoomView) => (Double => ZoomView)): this.type = js.native

  def on[T: D3VOrFn[Unit]#CB](typenames: String): T = js.native
  def on[T: D3VOrFn[Unit]#CB](typenames: String, listener: T): this.type = js.native
  def on[T: D3VOrFn[Unit]#CB](typenames: String, listener: Null): this.type = js.native
}
