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

/** Represents a link generator.
  *
  * The link shape generates a smooth cubic Bézier curve from a source point to a target point. The tangents of the
  * curve at the start and end are either vertical, horizontal, or radial.
  *
  * <a href="http://bl.ocks.org/mbostock/9d0899acb5d3b8d839d9d613a9e1fe04" rel="nofollow">
  *   <img alt="Tidy Tree" src="https://raw.githubusercontent.com/d3/d3-hierarchy/master/img/tree.png" style="max-width:100%;"/>
  * </a>
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class Link[D, L <: Link[D, L]] protected (
    val orientation: Link.Orientation,
    private[d3] val facade: Link.Facade[D]
) {
  /** Returns the current `source` accessor. */
  def source(): D3ValueAccessor[D, js.Tuple2[Double, Double]] = facade.source()

  /** Sets the `source` accessor to the specified function or tuple of numbers and returns this link generator.
    *
    * @param  source `source` accessor to use.
    * @return This link generator after modifying its `source` accessor.
    */
  def source(source: D3ValueAccessor[D, js.Tuple2[Double, Double]]): L = {
    facade.source(source)
    this.asInstanceOf[L]
  }

  /** Returns the current `target` accessor. */
  def target(): D3ValueAccessor[D, js.Tuple2[Double, Double]] = facade.target()

  /** Sets the `target` accessor to the specified function or tuple of numbers and returns this link generator.
    *
    * @param  target `target` accessor to use.
    * @return This link generator after modifying its `target` accessor.
    */
  def target(target: D3ValueAccessor[D, js.Tuple2[Double, Double]]): L = {
    facade.target(target)
    this.asInstanceOf[L]
  }

  /** Returns the current `x` accessor. */
  def x(): D3ValueAccessor[D, Double] = facade.x()

  /** Sets the `x` accessor to the specified function or number and returns this link generator.
    *
    * @param  x `x` accessor to use.
    * @return This link generator after modifying its `x` accessor.
    */
  def x(x: D3ValueAccessor[D, Double]): L = {
    facade.x(x)
    this.asInstanceOf[L]
  }

  /** Returns the current `y` accessor. */
  def y(): D3ValueAccessor[D, Double] = facade.y()

  /** Sets the `y` accessor to the specified function or number and returns this link generator.
    *
    * @param  y `y` accessor to use.
    * @return This link generator after modifying its `y` accessor.
    */
  def y(y: D3ValueAccessor[D, Double]): L = {
    facade.y(y)
    this.asInstanceOf[L]
  }

  /** Creates a new SVG link generator based on this one. */
  def svg: SVGLink[D] = {
    Link[D](orientation)
        .source(source())
        .target(target())
        .x(x())
        .y(y())
  }

  /** Creates a new canvas link generator, using the provided canvas rendering context. If the context is not `null`,
    * then the generated link is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas link generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasLink[D] = {
    val facade = orientation match {
      case Link.Horizontal => Link.Facade.linkHorizontal[D]()
      case Link.Vertical => Link.Facade.linkVertical[D]()
    }
    facade
        .source(source())
        .target(target())
        .x(x())
        .y(y())
        .context(context)
    new CanvasLink[D](orientation, facade)
  }
}

/** Represents an SVG link generator. */
class SVGLink[D] private[shape] (
    override val orientation: Link.Orientation,
    override private[d3] val facade: Link.Facade[D]
) extends Link[D, SVGLink[D]](orientation, facade) {
  /** Returns a path data string representing the generated link, for the provided data. */
  def apply(data: D): String = facade.apply(data).asInstanceOf[String]
}

/** Represents a Canvas link generator. */
class CanvasLink[D] private[shape] (
    override val orientation: Link.Orientation,
    override private[d3] val facade: Link.Facade[D]
) extends Link[D, CanvasLink[D]](orientation, facade) {
  /** Renders the generated link to this generator's context as a sequence of path method calls,
    * for the provided data. */
  def apply(data: D): Unit = facade.apply(data)

  /** Returns the canvas rendering context of this link generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object Link {
  sealed trait Orientation
  case object Horizontal extends Orientation
  case object Vertical extends Orientation

  /** Creates a new link generator. */
  def apply[D](orientation: Orientation): SVGLink[D] = orientation match {
    case Horizontal => new SVGLink[D](orientation, Facade.linkHorizontal[D]())
    case Vertical => new SVGLink[D](orientation, Facade.linkVertical[D]())
  }

  // @js.native trait DefaultObject extends js.Object {
  //   var source: js.Tuple2[Double, Double] = js.native
  //   var target: js.Tuple2[Double, Double] = js.native
  // }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def linkHorizontal[D](): Link.Facade[D] = js.native
    def linkVertical[D](): Link.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: D): String | Unit = js.native
    def source(): D3ValueAccessor[D, js.Tuple2[Double, Double]] = js.native
    def source(source: D3ValueAccessor[D, js.Tuple2[Double, Double]]): Facade[D] = js.native
    def target(): D3ValueAccessor[D, js.Tuple2[Double, Double]] = js.native
    def target(target: D3ValueAccessor[D, js.Tuple2[Double, Double]]): Facade[D] = js.native
    def x(): D3ValueAccessor[D, Double] = js.native
    def x(x: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def y(): D3ValueAccessor[D, Double] = js.native
    def y(y: D3ValueAccessor[D, Double]): Facade[D] = js.native
  }
}
