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

/** Represents an area radial generator.
  *
  * A radial area generator is equivalent to the standard Cartesian area generator, except the `x` and `y` accessors are
  * replaced with angle and radius accessors. Radial areas are always positioned relative to `(0, 0)`.
  * Use a transform (see: [[http://www.w3.org/TR/SVG/coords.html#TransformAttribute SVG]],
  * [[http://www.w3.org/TR/2dcontext/#transformations Canvas]]) to change the origin.
  *
  * <a href="https://raw.githubusercontent.com/d3/d3-shape/master/img/area-radial.png" target="_blank">
  *   <img alt="Radial Area" width="250" height="250" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/area-radial.png" style="max-width:100%;"/>
  * </a>
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class AreaRadial[D, A <: AreaRadial[D, A]] protected (private[d3] val facade: AreaRadial.Facade[D]) {
  /** Returns the current `startAngle` accessor. */
  def angle(): D3ValueAccessor[D, Double] = facade.angle()

  /** Equivalent to `Area.x()`, except the accessor returns the angle in radians, with `0` at `-y` (12 o'clock).
    *
    * @param  angle `startAngle` accessor to use.
    * @return This area generator after modifying its `startAngle` and `endAngle` accessors.
    */
  def angle(angle: D3ValueAccessor[D, Double]): A = {
    facade.angle(angle)
    this.asInstanceOf[A]
  }

  /** Returns the current `startAngle` accessor. */
  def startAngle(): D3ValueAccessor[D, Double] = facade.startAngle()

  /** Equivalent to `Area.x0()`, except the accessor returns the angle in radians, with `0` at `-y` (12 o'clock).
    *
    * @param  angle `startAngle` accessor to use.
    * @return This area generator after modifying its `startAngle` accessor.
    */
  def startAngle(angle: D3ValueAccessor[D, Double]): A = {
    facade.startAngle(angle)
    this.asInstanceOf[A]
  }

  /** Returns the current `x1` accessor. */
  def endAngle(): D3ValueAccessor[D, Double] = facade.endAngle()

  /** Equivalent to `Area.x1()`, except the accessor returns the angle in radians, with `0` at `-y` (12 o'clock).
    *
    * @param  angle `endAngle` accessor to use.
    * @return This area generator after modifying its `endAngle` accessor.
    */
  def endAngle(angle: D3ValueAccessor[D, Double]): A = {
    facade.endAngle(angle)
    this.asInstanceOf[A]
  }

  /** Returns the current `innerRadius` accessor. */
  def radius(): D3ValueAccessor[D, Double] = facade.radius()

  /** Equivalent to `Area.y()`, except the accessor returns the radius (i.e., distance from the origin `(0,0)`).
    *
    * @param  radius `innerRadius` accessor to use.
    * @return This area generator after modifying its `innerRadius` and `outerRadius` accessors.
    */
  def radius(radius: D3ValueAccessor[D, Double]): A = {
    facade.radius(radius)
    this.asInstanceOf[A]
  }

  /** Returns the current `innerRadius` accessor. */
  def innerRadius(): D3ValueAccessor[D, Double] = facade.innerRadius()

  /** Equivalent to `Area.y0()`, except the accessor returns the radius (i.e., distance from the origin `(0,0)`).
    *
    * @param  radius `innerRadius` accessor to use.
    * @return This area generator after modifying its `innerRadius` and `outerRadius` accessors.
    */
  def innerRadius(radius: D3ValueAccessor[D, Double]): A = {
    facade.innerRadius(radius)
    this.asInstanceOf[A]
  }

  /** Returns the current `outerRadius` accessor. */
  def outerRadius(): D3ValueAccessor[D, Double] = facade.outerRadius()

  /** Equivalent to `Area.y1()`, except the accessor returns the radius (i.e., distance from the origin `(0,0)`).
    *
    * @param  radius `outerRadius` accessor to use.
    * @return This area generator after modifying its `innerRadius` and `outerRadius` accessors.
    */
  def outerRadius(radius: D3ValueAccessor[D, Double]): A = {
    facade.outerRadius(radius)
    this.asInstanceOf[A]
  }

  /** Returns the current `defined` accessor. */
  def defined(): D3ValueAccessor[D, Boolean] = facade.defined()

  /** Equivalent to `Area.defined()`.
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

  /** Creates a new SVG area radial generator based on this one. */
  def svg: SVGAreaRadial[D] = {
    AreaRadial[D]()
        .startAngle(startAngle())
        .endAngle(endAngle())
        .innerRadius(innerRadius())
        .outerRadius(outerRadius())
        .defined(defined())
        .curve(curve())
  }

  /** Creates a new canvas area radial generator, using the provided canvas rendering context. If the context is not
    * `null`, then the generated area is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas area radial generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasAreaRadial[D] = {
    val facade = AreaRadial.Facade.areaRadial[D]()
        .startAngle(startAngle())
        .endAngle(endAngle())
        .innerRadius(innerRadius())
        .outerRadius(outerRadius())
        .defined(defined())
        .curve(curve())
        .context(context)
    new CanvasAreaRadial[D](facade)
  }

  /** Returns a new radial line generator that has this radial area generator's current defined accessor, curve, and
    * context. The line's `angle` accessor is this area's `startAngle` accessor, and the line's `radius` accessor is
    * this area's `innerRadius` accessor. */
  def lineStartAngle(): SVGLineRadial[D] = {
    new SVGLineRadial[D](facade.lineStartAngle())
  }

  /** Returns a new radial line generator that has this radial area generator's current defined accessor, curve, and
    * context. The line's `angle` accessor is this area's `endAngle` accessor, and the line's `radius` accessor is this
    * area's `innerRadius` accessor. */
  def lineEndAngle(): SVGLineRadial[D] = {
    new SVGLineRadial[D](facade.lineEndAngle())
  }

  /** Returns a new radial line generator that has this radial area generator's current defined accessor, curve, and
    * context. The line's `angle` accessor is this area's `startAngle` accessor, and the line's `radius` accessor is
    * this area's `innerRadius` accessor. */
  def lineInnerRadius(): SVGLineRadial[D] = {
    new SVGLineRadial[D](facade.lineInnerRadius())
  }

  /** Returns a new radial line generator that has this radial area generator's current defined accessor, curve, and
    * context. The line's `angle` accessor is this area's `startAngle` accessor, and the line's `radius` accessor is
    * this area's `outerRadius` accessor. */
  def lineOuterRadius(): SVGLineRadial[D] = {
    new SVGLineRadial[D](facade.lineOuterRadius())
  }
}

/** Represents an SVG area radial generator. */
class SVGAreaRadial[D] private[shape] (
    override private[d3] val facade: AreaRadial.Facade[D]
) extends AreaRadial[D, SVGAreaRadial[D]](facade) {
  /** Returns a path data string representing the generated area radial, for the provided data.
    *
    * Depending on this area generator's associated curve, the given input data may need to be sorted by 1x1-value
    * before being passed to the area generator.
    */
  def apply(data: Seq[D]): String = facade.apply(js.Array(data: _*)).asInstanceOf[String]
}

/** Represents a Canvas area radial generator. */
class CanvasAreaRadial[D] private[shape] (
    override private[d3] val facade: AreaRadial.Facade[D]
) extends AreaRadial[D, CanvasAreaRadial[D]](facade) {
  /** Renders the generated area radial to this generator's context as a sequence of path method calls,
    * for the provided data.
    *
    * Depending on this area generator's associated curve, the given input data may need to be sorted by 1x1-value
    * before being passed to the area generator.
    */
  def apply(data: Seq[D]): Unit = facade.apply(js.Array(data: _*))

  /** Returns the canvas rendering context of this area generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object AreaRadial {
  /** Creates a new area radial generator. */
  def apply[D](): SVGAreaRadial[D] = {
    new SVGAreaRadial[D](Facade.areaRadial[D]())
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def areaRadial[D](): AreaRadial.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: js.Array[D]): String | Unit = js.native
    def angle(): D3ValueAccessor[D, Double] = js.native
    def angle(angle: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def startAngle(): D3ValueAccessor[D, Double] = js.native
    def startAngle(angle: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def endAngle(): D3ValueAccessor[D, Double] = js.native
    def endAngle(angle: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def radius(): D3ValueAccessor[D, Double] = js.native
    def radius(radius: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def innerRadius(): D3ValueAccessor[D, Double] = js.native
    def innerRadius(radius: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def outerRadius(): D3ValueAccessor[D, Double] = js.native
    def outerRadius(radius: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def defined(): D3ValueAccessor[D, Boolean] = js.native
    def defined(defined: D3ValueAccessor[D, Boolean]): Facade[D] = js.native
    def curve(): Curve.LineFactory = js.native
    def curve(curve: Curve.LineFactory): Facade[D] = js.native
    def lineStartAngle(): LineRadial.Facade[D] = js.native
    def lineEndAngle(): LineRadial.Facade[D] = js.native
    def lineInnerRadius(): LineRadial.Facade[D] = js.native
    def lineOuterRadius(): LineRadial.Facade[D] = js.native
  }
}
