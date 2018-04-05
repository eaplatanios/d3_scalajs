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

/** Represents a line radial generator.
  *
  * A radial line generator is equivalent to the standard Cartesian line generator, except that the `x` and `y`
  * accessors are replaced with `angle` and `radius` accessors. Radial lines are always positioned relative to `(0, 0)`.
  * Use a transform (see: [[http://www.w3.org/TR/SVG/coords.html#TransformAttribute SVG]],
  * [[http://www.w3.org/TR/2dcontext/#transformations Canvas]]) to change the origin.
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class LineRadial[D, L <: LineRadial[D, L]] protected (private[d3] val facade: LineRadial.Facade[D]) {
  /** Returns the current `angle` accessor. */
  def angle(): D3ValueAccessor[D, Double] = facade.angle()

  /** Equivalent to `Line.x()`, except the accessor returns the angle in radians, with `0` at `-y` (12 o'clock).
    *
    * @param  angle `angle` accessor to use.
    * @return This line radial generator after modifying its `angle` accessor.
    */
  def angle(angle: D3ValueAccessor[D, Double]): L = {
    facade.angle(angle)
    this.asInstanceOf[L]
  }

  /** Returns the current `radius` accessor. */
  def radius(): D3ValueAccessor[D, Double] = facade.radius()

  /** Equivalent to `Line.y()`, except the accessor returns the radius: the distance from the origin `(0, 0)`.
    *
    * @param  radius `radius` accessor to use.
    * @return This line radial generator after modifying its `radius` accessor.
    */
  def radius(radius: D3ValueAccessor[D, Double]): L = {
    facade.radius(radius)
    this.asInstanceOf[L]
  }

  /** Returns the current `defined` accessor. */
  def defined(): D3ValueAccessor[D, Boolean] = facade.defined()

  /** Equivalent to `Line.defined()`.
    *
    * @param  defined `defined` accessor to use.
    * @return This line radial generator after modifying its `defined` accessor.
    */
  def defined(defined: D3ValueAccessor[D, Boolean]): L = {
    facade.defined(defined)
    this.asInstanceOf[L]
  }

  /** Returns the current curve factory. */
  def curve(): Curve.LineFactory = facade.curve()

  /** Sets the curve factory and returns this line radial generator. */
  def curve(curve: Curve.LineFactory): L = {
    facade.curve(curve)
    this.asInstanceOf[L]
  }

  /** Creates a new SVG line radial generator based on this one. */
  def toSVG(): SVGLineRadial[D] = {
    LineRadial(angle(), radius(), defined(), curve())
  }

  /** Creates a new canvas line radial generator, using the provided canvas rendering context. If the context is not
    * `null`, then the generated line is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas line radial generator.
    */
  def toCanvas(context: dom.CanvasRenderingContext2D): CanvasLineRadial[D] = {
    val facade = LineRadial.Facade.lineRadial[D]()
        .angle(angle())
        .radius(radius())
        .defined(defined())
        .curve(curve())
        .context(context)
    new CanvasLineRadial[D](facade)
  }
}

/** Represents an SVG line radial generator. */
class SVGLineRadial[D] private[shape] (
    override private[d3] val facade: LineRadial.Facade[D]
) extends LineRadial[D, SVGLineRadial[D]](facade) {
  /** Returns a path data string representing the generated line, for the provided data. */
  def apply(data: Seq[D]): String = facade.apply(js.Array(data: _*)).asInstanceOf[String]
}

/** Represents a Canvas line radial generator. */
class CanvasLineRadial[D] private[shape] (
    override private[d3] val facade: LineRadial.Facade[D]
) extends LineRadial[D, CanvasLineRadial[D]](facade) {
  /** Renders the generated line to this generator's context as a sequence of path method calls,
    * for the provided data. */
  def apply(data: Seq[D]): Unit = facade.apply(js.Array(data: _*))

  /** Returns the canvas rendering context of this line radial generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object LineRadial {
  /** Creates a new line radial generator.
    *
    * @param  angle   `angle` accessor to use.
    * @param  radius  `radius` accessor to use.
    * @param  defined `defined` accessor to use.
    * @param  curve   Curve factory to use.
    * @return Created line radial generator.
    */
  def apply[D](
      angle: D3ValueAccessor[D, Double],
      radius: D3ValueAccessor[D, Double],
      defined: D3ValueAccessor[D, Boolean] = (_: D) => true,
      curve: Curve.LineFactory = Curve.linear
  ): SVGLineRadial[D] = {
    val facade = Facade.lineRadial[D]()
        .angle(angle)
        .radius(radius)
        .defined(defined)
        .curve(curve)
    new SVGLineRadial[D](facade)
  }

  /** Creates a new default line radial generator over pairs of `angle` and `radius` values.
    *
    * @param  defined `defined` accessor to use.
    * @param  curve   Curve factory to use.
    * @return Created line radial generator.
    */
  def default(
      defined: D3ValueAccessor[js.Tuple2[Double, Double], Boolean] = (_: js.Tuple2[Double, Double]) => true,
      curve: Curve.LineFactory = Curve.linear
  ): SVGLineRadial[js.Tuple2[Double, Double]] = {
    val facade = Facade.lineRadial[js.Tuple2[Double, Double]]()
        .defined(defined)
        .curve(curve)
    new SVGLineRadial[js.Tuple2[Double, Double]](facade)
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def lineRadial[D](): LineRadial.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: js.Array[D]): String | Unit = js.native
    def angle(): D3ValueAccessor[D, Double] = js.native
    def angle(angle: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def radius(): D3ValueAccessor[D, Double] = js.native
    def radius(radius: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def defined(): D3ValueAccessor[D, Boolean] = js.native
    def defined(defined: D3ValueAccessor[D, Boolean]): Facade[D] = js.native
    def curve(): Curve.LineFactory = js.native
    def curve(curve: Curve.LineFactory): Facade[D] = js.native
  }
}
