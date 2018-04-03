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

import org.platanios.d3.D3Function
import org.platanios.d3.selection.{Selection, Transition}

import org.scalajs.dom

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait ZoomBehavior[E <: dom.Element, D] extends js.Function {
  def apply(selection: Selection.Facade[E, D, dom.Element, js.Any], args: js.Any*): Unit = js.native
  def transform(selection: Selection.Facade[E, D, dom.Element, js.Any], transform: D3Function[E, D, ZoomTransform]): Unit = js.native
  def transform(transition: Transition.Facade[E, D, dom.Element, js.Any], transform: D3Function[E, D, ZoomTransform]): Unit = js.native

  def translateBy(selection: Selection.Facade[E, D, dom.Element, js.Any], x: D3Function[E, D, Double], y: D3Function[E, D, Double]): Unit = js.native
  def translateBy(transition: Transition.Facade[E, D, dom.Element, js.Any], x: D3Function[E, D, Double], y: D3Function[E, D, Double]): Unit = js.native

  def translateTo(selection: Selection.Facade[E, D, dom.Element, js.Any], x: D3Function[E, D, Double], y: D3Function[E, D, Double]): Unit = js.native
  def translateTo(transition: Transition.Facade[E, D, dom.Element, js.Any], x: D3Function[E, D, Double], y: D3Function[E, D, Double]): Unit = js.native

  def scaleBy(selection: Selection.Facade[E, D, dom.Element, js.Any], k: D3Function[E, D, Double]): Unit = js.native
  def scaleBy(transition: Transition.Facade[E, D, dom.Element, js.Any], k: D3Function[E, D, Double]): Unit = js.native

  def scaleTo(selection: Selection.Facade[E, D, dom.Element, js.Any], k: D3Function[E, D, Double]): Unit = js.native
  def scaleTo(transition: Transition.Facade[E, D, dom.Element, js.Any], k: D3Function[E, D, Double]): Unit = js.native

  def constrain(): js.Function3[ZoomTransform, js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], ZoomTransform] = js.native
  def constrain(constraint: js.Function3[ZoomTransform, js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]], ZoomTransform]): this.type = js.native

  def filter(): D3Function[E, D, Boolean] = js.native
  def filter(filterFn: D3Function[E, D, Boolean]): this.type = js.native

  def touchable(): D3Function[E, D, Boolean] = js.native
  def touchable(touchable: D3Function[E, D, Boolean]): this.type = js.native

  def wheelDelta(): D3Function[E, D, Double] = js.native
  def wheelDelta(delta: D3Function[E, D, Double]): this.type = js.native

  def extent(): D3Function[E, D, js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]]] = js.native
  def extent(extent: D3Function[E, D, js.Tuple2[js.Tuple2[Double, Double], js.Tuple2[Double, Double]]]): this.type = js.native

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

  def on(typenames: String): D3Function[E, D, Unit] = js.native
  def on(typenames: String, listener: D3Function[E, D, Unit]): this.type = js.native
}
