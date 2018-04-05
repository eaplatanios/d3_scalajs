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

/** Represents an arc generator. The arc generator produces a [[https://en.wikipedia.org/wiki/Circular_sector circular]]
  * or [[https://en.wikipedia.org/wiki/Annulus_(mathematics) annular sector]], as in a pie or donut chart. If the
  * difference between the start and end angles (the angular span) is greater than τ, the arc generator will produce a
  * complete circle or annulus. If it is less than τ, arcs may have rounded corners and angular padding. Arcs are always
  * centered at `(0, 0)`. Use a transform (see: [[http://www.w3.org/TR/SVG/coords.html#TransformAttribute SVG]],
  * [[http://www.w3.org/TR/2dcontext/#transformations Canvas]]) to change the origin.
  *
  * <p>
  *   <a href="http://bl.ocks.org/mbostock/8878e7fd82034f1d63cf" rel="nofollow">
  *     <img alt="Pie Chart" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/pie.png" width="295" height="295" style="max-width:100%;"/>
  *   </a>
  *   <a href="http://bl.ocks.org/mbostock/2394b23da1994fc202e1" rel="nofollow">
  *     <img alt="Donut Chart" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/donut.png" width="295" height="295" style="max-width:100%;"/>
  *   </a>
  * </p>
  *
  * See also the pie generator, which computes the necessary angles to represent an array of data as a pie or donut
  * chart. These angles can then be passed to an arc generator.
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class Arc[D, A <: Arc[D, A]] protected (
    private[d3] val facade: Arc.Facade[D]
) {
  /** Computes the midpoint `(x, y)` of the center line of the arc that would be generated by the given arguments.
    * To be consistent with the generated arc, the accessors must be deterministic (i.e., return the same value given
    * the same arguments). The midpoint is defined as `(startAngle + endAngle) / 2 and (innerRadius + outerRadius) / 2`.
    *
    * For example:
    * <p>
    *   <a href="http://bl.ocks.org/mbostock/9b5a2fd1ce1a146f27e4" rel="nofollow">
    *     <img alt="Circular Sector Centroids" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/centroid-circular-sector.png" width="250" height="250" style="max-width:100%;"/>
    *   </a>
    *   <a href="http://bl.ocks.org/mbostock/c274877f647361f3df7d" rel="nofollow">
    *     <img alt="Annular Sector Centroids" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/centroid-annular-sector.png" width="250" height="250" style="max-width:100%;"/>
    *   </a>
    * </p>
    *
    * @param  data Data propagated to the accessors.
    * @return Centroid coordinates.
    */
  def centroid(data: D): (Double, Double) = facade.centroid(data)

  /** Returns the current inner radius accessor. */
  def innerRadius(): D3ArcAccessor[D, Double] = facade.innerRadius()

  /** Sets the inner radius to the specified function or number and returns this arc generator.
    *
    * Specifying the inner radius as a function is useful for constructing a stacked polar bar chart, often in
    * conjunction with a square root scale. More commonly, a constant inner radius is used for a donut or pie chart. If
    * the outer radius is smaller than the inner radius, the inner and outer radii are swapped. A negative value is
    * treated as zero.
    *
    * @param  radius Inner radius accessor to use.
    * @return This arc generator after modifying its inner radius accessor.
    */
  def innerRadius(radius: D3ArcAccessor[D, Double]): A = {
    facade.innerRadius(radius)
    this.asInstanceOf[A]
  }

  /** Returns the current outer radius accessor. */
  def outerRadius(): D3ArcAccessor[D, Double] = facade.outerRadius()

  /** Sets the outer radius to the specified function or number and returns this arc generator.
    *
    * Specifying the outer radius as a function is useful for constructing a coxcomb or polar bar chart, often in
    * conjunction with a square root scale. More commonly, a constant inner radius is used for a donut or pie chart. If
    * the outer radius is smaller than the inner radius, the inner and outer radii are swapped. A negative value is
    * treated as zero.
    *
    * @param  radius Outer radius accessor to use.
    * @return This arc generator after modifying its outer radius accessor.
    */
  def outerRadius(radius: D3ArcAccessor[D, Double]): A = {
    facade.outerRadius(radius)
    this.asInstanceOf[A]
  }

  /** Returns the current corner radius accessor. */
  def cornerRadius(): D3ArcAccessor[D, Double] = facade.cornerRadius()

  /** Sets the corner radius to the specified function or number and returns this arc generator.
    *
    * If the corner radius is greater than zero, the corners of the arc are rounded using circles of the given radius.
    * For a circular sector, the two outer corners are rounded. For an annular sector, all four corners are rounded.
    *
    * The corner circles are shown in this diagram:
    * <p>
    *   <a href="http://bl.ocks.org/mbostock/e5e3680f3079cf5c3437" rel="nofollow">
    *     <img alt="Rounded Circular Sectors" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/rounded-circular-sector.png" width="250" height="250" style="max-width:100%;"/>
    *   </a>
    *   <a href="http://bl.ocks.org/mbostock/f41f50e06a6c04828b6e" rel="nofollow">
    *     <img alt="Rounded Annular Sectors" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/rounded-annular-sector.png" width="250" height="250" style="max-width:100%;"/>
    *   </a>
    * </p>
    *
    * The corner radius may not be larger than `(outerRadius - innerRadius) / 2`. In addition, for arcs whose angular
    * span is less than π, the corner radius may be reduced as two adjacent rounded corners intersect. This occurs
    * more often with the inner corners. See the
    * [[http://bl.ocks.org/mbostock/b7671cb38efdfa5da3af arc corners animation]] for an illustration.
    *
    * @param  radius Corner radius accessor to use.
    * @return This arc generator after modifying its corner radius accessor.
    */
  def cornerRadius(radius: D3ArcAccessor[D, Double]): A = {
    facade.cornerRadius(radius)
    this.asInstanceOf[A]
  }

  /** Returns the current start angle accessor. */
  def startAngle(): D3ArcAccessor[D, Double] = facade.startAngle()

  /** Sets the start angle to the specified function or number and returns this arc generator.
    *
    * The angle is specified in radians, with `0` at `-y` (12 o'clock) and positive angles proceeding clockwise. If
    * `|endAngle - startAngle| ≥ τ`, a complete circle or annulus is generated rather than a sector.
    *
    * @param  angle Start angle accessor to use.
    * @return This arc generator after modifying its start angle accessor.
    */
  def startAngle(angle: D3ArcAccessor[D, Double]): A = {
    facade.startAngle(angle)
    this.asInstanceOf[A]
  }

  /** Returns the current end angle accessor. */
  def endAngle(): D3ArcAccessor[D, Double] = facade.endAngle()

  /** Sets the end angle to the specified function or number and returns this arc generator.
    *
    * The angle is specified in radians, with `0` at `-y` (12 o'clock) and positive angles proceeding clockwise. If
    * `|endAngle - startAngle| ≥ τ`, a complete circle or annulus is generated rather than a sector.
    *
    * @param  angle End angle accessor to use.
    * @return This arc generator after modifying its end angle accessor.
    */
  def endAngle(angle: D3ArcAccessor[D, Double]): A = {
    facade.endAngle(angle)
    this.asInstanceOf[A]
  }

  /** Returns the current pad angle accessor. */
  def padAngle(): D3ArcAccessor[D, Double] = facade.padAngle()

  /** Sets the pad angle to the specified function or number and returns this arc generator.
    *
    * The pad angle is converted to a fixed linear distance separating adjacent arcs, defined as `padRadius * padAngle`.
    * This distance is subtracted equally from the start and end of the arc. If the arc forms a complete circle or
    * annulus, as when `|endAngle - startAngle| ≥ τ`, the pad angle is ignored.
    *
    * If the inner radius or angular span is small relative to the pad angle, it may not be possible to maintain
    * parallel edges between adjacent arcs. In this case, the inner edge of the arc may collapse to a point, similar to
    * a circular sector. For this reason, padding is typically only applied to annular sectors (i.e., when `innerRadius`
    * is positive), as shown in this diagram:
    * <p>
    *   <a href="http://bl.ocks.org/mbostock/f37b07b92633781a46f7" rel="nofollow">
    *     <img alt="Padded Circular Sectors" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/padded-circular-sector.png" width="250" height="250" style="max-width:100%;"/>
    *   </a>
    *   <a href="http://bl.ocks.org/mbostock/99f0a6533f7c949cf8b8" rel="nofollow">
    *     <img alt="Padded Annular Sectors" src="https://raw.githubusercontent.com/d3/d3-shape/master/img/padded-annular-sector.png" width="250" height="250" style="max-width:100%;"/>
    *   </a>
    * </p>
    *
    * The recommended minimum inner radius when using padding is `outerRadius * padAngle / sin(θ)`, where `θ` is the
    * angular span of the smallest arc before padding. For example, if the outer radius is 200 pixels and the pad angle
    * is 0.02 radians, a reasonable `θ` is 0.04 radians, and a reasonable inner radius is 100 pixels.
    * See the [[http://bl.ocks.org/mbostock/053fcc2295a445afab07 arc padding animation]] for an illustration.
    *
    * Often, the pad angle is not set directly on the arc generator, but is instead computed by the pie generator so as
    * to ensure that the area of padded arcs is proportional to their value (see `Pie.padAngle()`).
    * See the [[http://bl.ocks.org/mbostock/3e961b4c97a1b543fff2 pie padding animation]] for an illustration. If you
    * apply a constant pad angle to the arc generator directly, it tends to subtract disproportionately from smaller
    * arcs, introducing distortion.
    *
    * @param  angle Pad angle accessor to use.
    * @return This arc generator after modifying its pad angle accessor.
    */
  def padAngle(angle: D3ArcAccessor[D, Double]): A = {
    facade.padAngle(angle)
    this.asInstanceOf[A]
  }

  /** Returns the current pad radius accessor. */
  def padRadius(): D3ArcAccessor[D, Double] = facade.padRadius()

  /** Sets the pad radius to the specified function or number and returns this arc generator.
    *
    * The pad radius determines the fixed linear distance separating adjacent arcs, defined as `padRadius * padAngle`.
    *
    * Defaults to `null`, indicating that the pad radius should be automatically computed as
    * `sqrt(innerRadius * innerRadius + outerRadius * outerRadius)`.
    *
    * @param  radius Pad radius accessor to use.
    * @return This arc generator after modifying its pad radius accessor.
    */
  def padRadius(radius: D3ArcAccessor[D, Double]): A = {
    facade.padRadius(radius)
    this.asInstanceOf[A]
  }

  /** Creates a new SVG arc generator based on this one. */
  def svg: SVGArc[D] = {
    Arc()
        .innerRadius(innerRadius())
        .outerRadius(outerRadius())
        .cornerRadius(cornerRadius())
        .startAngle(startAngle())
        .endAngle(endAngle())
        .padAngle(padAngle())
        .padRadius(padRadius())
  }

  /** Creates a new canvas arc generator, using the provided canvas rendering context. If the context is not `null`,
    * then the generated line is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas line generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasArc[D] = {
    val facade = Arc.Facade.arc[D]()
        .innerRadius(innerRadius())
        .outerRadius(outerRadius())
        .cornerRadius(cornerRadius())
        .startAngle(startAngle())
        .endAngle(endAngle())
        .padAngle(padAngle())
        .padRadius(padRadius())
        .context(context)
    new CanvasArc[D](facade)
  }
}

/** Represents an SVG arc generator. */
class SVGArc[D] private[shape] (
    override private[d3] val facade: Arc.Facade[D]
) extends Arc[D, SVGArc[D]](facade) {
  /** Returns a path data string representing the generated arc, for the provided data. */
  def apply(data: D): String = facade.apply(data).asInstanceOf[String]
}

/** Represents a Canvas arc generator. */
class CanvasArc[D] private[shape] (
    override private[d3] val facade: Arc.Facade[D]
) extends Arc[D, CanvasArc[D]](facade) {
  /** Renders the generated arc to this generator's context as a sequence of path method calls,
    * for the provided data. */
  def apply(data: D): Unit = facade.apply(data)

  /** Returns the canvas rendering context of this arc generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object Arc {
  def apply[D](): SVGArc[D] = {
    new SVGArc[D](Facade.arc[D]())
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def arc[D](): Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D]
      extends Generator.Facade[D, Facade[D]] {
    def apply(data: D): String | Unit = js.native
    def centroid(data: D): js.Tuple2[Double, Double] = js.native
    def innerRadius(): D3ArcAccessor[D, Double] = js.native
    def innerRadius(radius: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def outerRadius(): D3ArcAccessor[D, Double] = js.native
    def outerRadius(radius: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def cornerRadius(): D3ArcAccessor[D, Double] = js.native
    def cornerRadius(radius: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def startAngle(): D3ArcAccessor[D, Double] = js.native
    def startAngle(angle: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def endAngle(): D3ArcAccessor[D, Double] = js.native
    def endAngle(angle: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def padAngle(): D3ArcAccessor[D, Double] = js.native
    def padAngle(angle: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def padRadius(): D3ArcAccessor[D, Double] = js.native
    def padRadius(radius: D3ArcAccessor[D, Double]): Facade[D] = js.native
  }
}
