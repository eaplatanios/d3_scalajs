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

import org.platanios.d3.shape.Implicits._

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/** Represents an area generator.
  *
  * The area generator produces an area, as in an area chart. An area is defined by two bounding lines, either splines
  * or poly-lines. Typically, the two lines share the same `x`-values (`x0` = `x1`), differing only in `y`-value (`y0`
  * and `y1`). Most commonly, `y0` is defined as a constant representing
  * [[http://www.vox.com/2015/11/19/9758062/y-axis-zero-chart zero]]. The first line (the top-line) is defined by `x1`
  * and `y1` and is rendered first. The second line (the baseline) is defined by `x0` and `y0` and is rendered second,
  * with the points in reverse order. With a `curve.linear` curve, this produces a clockwise polygon.
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class Area[D, A <: Area[D, A]] protected (private[d3] val facade: Area.Facade[D]) {
  /** Returns the current `x0` accessor. */
  def x(): D3ValueAccessor[D, Double] = facade.x()

  /** Sets `x0` to `x` and `x1` to `null` and returns this area generator.
    *
    * @param  x `x0` accessor to use.
    * @return This area generator after modifying its `x0` and `x1` accessors.
    */
  def x(x: D3ValueAccessor[D, Double]): A = {
    facade.x(x)
    this.asInstanceOf[A]
  }

  /** Returns the current `x0` accessor. */
  def x0(): D3ValueAccessor[D, Double] = facade.x0()

  /** Sets the `x0` accessor to the specified function or number and returns this area generator.
    *
    * When a line is generated, the `x0` accessor will be invoked for each defined element in the input data array,
    * being passed the element `d`, the index `i`, and the array data as three arguments. The default `x0` accessor
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
    *   val area = d3.area(
    *     x = (d: js.Dictionary[js.Object]) => x(d("date")),
    *     y0 = (d: js.Dictionary[js.Object]) => y(0),
    *     y1 = (d: js.Dictionary[js.Object]) => y(d("value")))
    * }}}
    *
    * @param  x `x0` accessor to use.
    * @return This area generator after modifying its `x0` accessor.
    */
  def x0(x: D3ValueAccessor[D, Double]): A = {
    facade.x0(x)
    this.asInstanceOf[A]
  }

  /** Returns the current `x1` accessor. */
  def x1(): D3ValueAccessor[D, Double] = facade.x1()

  /** Sets the `x1` accessor to the specified function or number and returns this area generator.
    *
    * When an area is generated, the `x1` accessor will be invoked for each defined element in the input data array,
    * being passed the element `d`, the index `i`, and the array data as three arguments. See `area.x0()` for more
    * information.
    *
    * @param  x `x1` accessor to use.
    * @return This area generator after modifying its `x1` accessor.
    */
  def x1(x: D3ValueAccessor[D, Double]): A = {
    facade.x1(x)
    this.asInstanceOf[A]
  }

  /** Returns the current `y0` accessor. */
  def y(): D3ValueAccessor[D, Double] = facade.y()

  /** Sets `y0` to `y` and `y1` to `null` and returns this area generator.
    *
    * @param  y `y0` accessor to use.
    * @return This area generator after modifying its `y0` and `y1` accessors.
    */
  def y(y: D3ValueAccessor[D, Double]): A = {
    facade.y(y)
    this.asInstanceOf[A]
  }

  /** Returns the current `y0` accessor. */
  def y0(): D3ValueAccessor[D, Double] = facade.y0()

  /** Sets the `y0` accessor to the specified function or number and returns this area generator.
    *
    * When an area is generated, the `y0` accessor will be invoked for each defined element in the input data array,
    * being passed the element `d`, the index `i`, and the array data as three arguments. See `area.x0()` for more
    * information.
    *
    * @param  y `y0` accessor to use.
    * @return This line generator after modifying its `y0` accessor.
    */
  def y0(y: D3ValueAccessor[D, Double]): A = {
    facade.y0(y)
    this.asInstanceOf[A]
  }

  /** Returns the current `y1` accessor. */
  def y1(): D3ValueAccessor[D, Double] = facade.y1()

  /** Sets the `y1` accessor to the specified function or number and returns this area generator.
    *
    * A `null` accessor is allowed, indicating that the previously-computed `y0` value should be reused for the `y1`
    * value.
    *
    * When an area is generated, the `y1` accessor will be invoked for each defined element in the input data array,
    * being passed the element `d`, the index `i`, and the array data as three arguments. See `area.x0()` for more
    * information.
    *
    * @param  y `y1` accessor to use.
    * @return This line generator after modifying its `y1` accessor.
    */
  def y1(y: D3ValueAccessor[D, Double]): A = {
    facade.y1(y)
    this.asInstanceOf[A]
  }

  /** Returns the current `defined` accessor. */
  def defined(): D3ValueAccessor[D, Boolean] = facade.defined()

  /** Sets the `defined` accessor to the specified function or number and returns this area generator.
    *
    * The default accessor thus assumes that the input data is always defined. When an area is generated, the defined
    * accessor will be invoked for each element in the input data array, being passed the element `d`, the index `i`,
    * and the array data as three arguments. If the given element is defined (i.e., if the defined accessor returns a
    * truthy value for this element), the `x0`, `x1`, `y0`, and `y1` accessors will subsequently be evaluated and the
    * point will be added to the current area segment. Otherwise, the element will be skipped, the current area segment
    * will be ended, and a new area segment will be generated for the next defined point. As a result, the generated
    * area may have several discrete segments.
    *
    * For example:
    * <a href="http://bl.ocks.org/mbostock/3035090" rel="nofollow">
    *   <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/area-defined.png" width="480" height="250" alt="Area with Missing Data" style="max-width:100%;"/>
    * </a>
    *
    * Note that if an area segment consists of only a single point, it may appear invisible unless rendered with rounded
    * or square [[https://developer.mozilla.org/en-US/docs/Web/SVG/Attribute/stroke-linecap line caps]]. In addition,
    * some curves such as `cardinalOpenCurve` only render a visible segment if it contains multiple points.
    *
    * @param  defined `defined` accessor to use.
    * @return This area generator after modifying its `defined` accessor.
    */
  def defined(defined: D3ValueAccessor[D, Boolean]): A = {
    facade.defined(defined)
    this.asInstanceOf[A]
  }

  /** Returns the current curve factory. */
  def curve(): Curve.LineFactory = facade.curve()

  /** Sets the curve factory and returns this area generator. */
  def curve(curve: Curve.LineFactory): A = {
    facade.curve(curve)
    this.asInstanceOf[A]
  }

  /** Creates a new SVG area generator based on this one. */
  def svg: SVGArea[D] = {
    Area[D]()
        .x0(x0())
        .x1(x1())
        .y0(y0())
        .y1(y1())
        .defined(defined())
        .curve(curve())
  }

  /** Creates a new canvas area generator, using the provided canvas rendering context. If the context is not `null`,
    * then the generated area is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas area generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasArea[D] = {
    val facade = Area.Facade.area[D]()
        .x0(x0())
        .x1(x1())
        .y0(y0())
        .y1(y1())
        .defined(defined())
        .curve(curve())
        .context(context)
    new CanvasArea[D](facade)
  }

  /** Returns a new line generator that has this area generator's current defined accessor, curve, and context. The
    * line's `x`-accessor is this area's `x0`-accessor, and the line's `y`-accessor is this area's `y0`-accessor. */
  def lineX0(): SVGLine[D] = {
    new SVGLine[D](facade.lineX0())
  }

  /** Returns a new line generator that has this area generator's current defined accessor, curve, and context. The
    * line's `x`-accessor is this area's `x1`-accessor, and the line's `y`-accessor is this area's `y0`-accessor. */
  def lineX1(): SVGLine[D] = {
    new SVGLine[D](facade.lineX1())
  }

  /** Returns a new line generator that has this area generator's current defined accessor, curve, and context. The
    * line's `x`-accessor is this area's `x0`-accessor, and the line's `y`-accessor is this area's `y0`-accessor. */
  def lineY0(): SVGLine[D] = {
    new SVGLine[D](facade.lineY0())
  }

  /** Returns a new line generator that has this area generator's current defined accessor, curve, and context. The
    * line's `x`-accessor is this area's `x0`-accessor, and the line's `y`-accessor is this area's `y1`-accessor. */
  def lineY1(): SVGLine[D] = {
    new SVGLine[D](facade.lineY1())
  }
}

/** Represents an SVG area generator. */
class SVGArea[D] private[shape] (
    override private[d3] val facade: Area.Facade[D]
) extends Area[D, SVGArea[D]](facade) {
  /** Returns a path data string representing the generated area, for the provided data.
    *
    * Depending on this area generator's associated curve, the given input data may need to be sorted by 1x1-value
    * before being passed to the area generator.
    */
  def apply(data: Seq[D]): String = facade.apply(js.Array(data: _*)).asInstanceOf[String]
}

/** Represents a Canvas area generator. */
class CanvasArea[D] private[shape] (
    override private[d3] val facade: Area.Facade[D]
) extends Area[D, CanvasArea[D]](facade) {
  /** Renders the generated area to this generator's context as a sequence of path method calls,
    * for the provided data.
    *
    * Depending on this area generator's associated curve, the given input data may need to be sorted by 1x1-value
    * before being passed to the area generator.
    */
  def apply(data: Seq[D]): Unit = facade.apply(js.Array(data: _*))

  /** Returns the canvas rendering context of this area generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object Area {
  /** Creates a new area generator. */
  def apply[D](): SVGArea[D] = {
    new SVGArea[D](Facade.area[D]())
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def area[D](): Area.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: js.Array[D]): String | Unit = js.native
    def x(): D3ValueAccessor[D, Double] = js.native
    def x(x: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def x0(): D3ValueAccessor[D, Double] = js.native
    def x0(x: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def x1(): D3ValueAccessor[D, Double] = js.native
    def x1(x: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def y(): D3ValueAccessor[D, Double] = js.native
    def y(y: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def y0(): D3ValueAccessor[D, Double] = js.native
    def y0(y: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def y1(): D3ValueAccessor[D, Double] = js.native
    def y1(y: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def defined(): D3ValueAccessor[D, Boolean] = js.native
    def defined(defined: D3ValueAccessor[D, Boolean]): Facade[D] = js.native
    def curve(): Curve.LineFactory = js.native
    def curve(curve: Curve.LineFactory): Facade[D] = js.native
    def lineX0(): Line.Facade[D] = js.native
    def lineY0(): Line.Facade[D] = js.native
    def lineX1(): Line.Facade[D] = js.native
    def lineY1(): Line.Facade[D] = js.native
  }
}
