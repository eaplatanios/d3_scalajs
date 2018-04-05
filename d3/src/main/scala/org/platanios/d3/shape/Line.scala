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

package org.platanios.d3.shape

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/** Represents a line generator.
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class Line[D, L <: Line[D, L]] protected (private[d3] val facade: Line.Facade[D]) {
  /** Returns the current `x` accessor. */
  def x(): D3ValueAccessor[D, Double] = facade.x()

  /** Sets the `x` accessor to the specified function or number and returns this line generator.
    *
    * When a line is generated, the `x` accessor will be invoked for each defined element in the input data array,
    * being passed the element `d`, the index `i`, and the array data as three arguments. The default `x` accessor
    * assumes that the input data are two-element arrays of numbers. If your data are in a different format, or if you
    * wish to transform the data before rendering, then you should specify a custom accessor.
    *
    * For example, if `x` is a time scale and `y` is a linear scale:
    * {{{
    *   val data = js.Array(
    *     js.Dictionary("date" -> new js.Date(2007, 3, 24), "value" -> 93.24),
    *     js.Dictionary("date" -> new js.Date(2007, 3, 25), "value" -> 95.35),
    *     js.Dictionary("date" -> new js.Date(2007, 3, 26), "value" -> 98.84),
    *     js.Dictionary("date" -> new js.Date(2007, 3, 27), "value" -> 99.92),
    *     js.Dictionary("date" -> new js.Date(2007, 3, 30), "value" -> 99.80),
    *     js.Dictionary("date" -> new js.Date(2007, 4, 1), "value" -> 99.47),
    *     ...)
    *
    *   val line = d3.line(
    *     x = (d: js.Dictionary[js.Object]) => x(d("date")),
    *     y = (d: js.Dictionary[js.Object]) => y(d("value")))
    * }}}
    *
    * @param  x `x` accessor to use.
    * @return This line generator after modifying its `x` accessor.
    */
  def x(x: D3ValueAccessor[D, Double]): L = {
    facade.x(x)
    this.asInstanceOf[L]
  }

  /** Returns the current `y` accessor. */
  def y(): D3ValueAccessor[D, Double] = facade.y()

  /** Sets the `y` accessor to the specified function or number and returns this line generator.
    *
    * When a line is generated, the `y` accessor will be invoked for each defined element in the input data array,
    * being passed the element `d`, the index `i`, and the array data as three arguments. The default `y` accessor
    * assumes that the input data are two-element arrays of numbers. If your data are in a different format, or if you
    * wish to transform the data before rendering, then you should specify a custom accessor.
    *
    * @param  y `y` accessor to use.
    * @return This line generator after modifying its `y` accessor.
    */
  def y(y: D3ValueAccessor[D, Double]): L = {
    facade.y(y)
    this.asInstanceOf[L]
  }

  /** Returns the current `defined` accessor. */
  def defined(): D3ValueAccessor[D, Boolean] = facade.defined()

  /** Sets the `defined` accessor to the specified function or number and returns this line generator.
    *
    * The default accessor thus assumes that the input data is always defined. When a line is generated, the defined
    * accessor will be invoked for each element in the input data array, being passed the element `d`, the index `i`,
    * and the array data as three arguments. If the given element is defined (i.e., if the defined accessor returns a
    * truthy value for this element), the `x` and `y` accessors will subsequently be evaluated and the point will be
    * added to the current line segment. Otherwise, the element will be skipped, the current line segment will be ended,
    * and a new line segment will be generated for the next defined point. As a result, the generated line may have
    * several discrete segments.
    *
    * For example:
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/line-defined.png" width="480" height="250" alt="Line with Missing Data" style="max-width:100%;"/>
    *
    * Note that if a line segment consists of only a single point, it may appear invisible unless rendered with rounded
    * or square [[https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/stroke-linecap line caps]]. In addition,
    * some curves such as `cardinalOpenCurve` only render a visible segment if it contains multiple points.
    *
    * @param  defined `defined` accessor to use.
    * @return This line generator after modifying its `defined` accessor.
    */
  def defined(defined: D3ValueAccessor[D, Boolean]): L = {
    facade.defined(defined)
    this.asInstanceOf[L]
  }

  /** Returns the current curve factory. */
  def curve(): Curve.LineFactory = facade.curve()

  /** Sets the curve factory and returns this line generator. */
  def curve(curve: Curve.LineFactory): L = {
    facade.curve(curve)
    this.asInstanceOf[L]
  }

  /** Creates a new SVG line generator based on this one. */
  def svg: SVGLine[D] = {
    Line()
        .x(x())
        .y(y())
        .defined(defined())
        .curve(curve())
  }

  /** Creates a new canvas line generator, using the provided canvas rendering context. If the context is not `null`,
    * then the generated line is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas line generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasLine[D] = {
    val facade = Line.Facade.line[D]()
        .x(x())
        .y(y())
        .defined(defined())
        .curve(curve())
        .context(context)
    new CanvasLine[D](facade)
  }
}

/** Represents an SVG line generator. */
class SVGLine[D] private[shape] (
    override private[d3] val facade: Line.Facade[D]
) extends Line[D, SVGLine[D]](facade) {
  /** Returns a path data string representing the generated line, for the provided data. */
  def apply(data: Seq[D]): String = facade.apply(js.Array(data: _*)).asInstanceOf[String]
}

/** Represents a Canvas line generator. */
class CanvasLine[D] private[shape] (
    override private[d3] val facade: Line.Facade[D]
) extends Line[D, CanvasLine[D]](facade) {
  /** Renders the generated line to this generator's context as a sequence of path method calls,
    * for the provided data. */
  def apply(data: Seq[D]): Unit = facade.apply(js.Array(data: _*))

  /** Returns the canvas rendering context of this line generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object Line {
  /** Creates a new line generator. */
  def apply[D](): SVGLine[D] = {
    new SVGLine[D](Facade.line[D]())
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def line[D](): Line.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: js.Array[D]): String | Unit = js.native
    def x(): D3ValueAccessor[D, Double] = js.native
    def x(x: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def y(): D3ValueAccessor[D, Double] = js.native
    def y(y: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def defined(): D3ValueAccessor[D, Boolean] = js.native
    def defined(defined: D3ValueAccessor[D, Boolean]): Facade[D] = js.native
    def curve(): Curve.LineFactory = js.native
    def curve(curve: Curve.LineFactory): Facade[D] = js.native
  }
}
