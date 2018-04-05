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

/** Represents a link radial generator.
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class LinkRadial[D, L <: LinkRadial[D, L]] protected (
    private[d3] val facade: LinkRadial.Facade[D]
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

  /** Returns the current `angle` accessor. */
  def angle(): D3ValueAccessor[D, Double] = facade.angle()

  /** Sets the `angle` accessor to the specified function or number and returns this link generator.
    *
    * @param  angle `angle` accessor to use.
    * @return This link generator after modifying its `angle` accessor.
    */
  def angle(angle: D3ValueAccessor[D, Double]): L = {
    facade.angle(angle)
    this.asInstanceOf[L]
  }

  /** Returns the current `radius` accessor. */
  def radius(): D3ValueAccessor[D, Double] = facade.radius()

  /** Sets the `radius` accessor to the specified function or number and returns this link generator.
    *
    * @param  radius `radius` accessor to use.
    * @return This link generator after modifying its `radius` accessor.
    */
  def radius(radius: D3ValueAccessor[D, Double]): L = {
    facade.radius(radius)
    this.asInstanceOf[L]
  }

  /** Creates a new SVG link generator based on this one. */
  def svg: SVGLinkRadial[D] = {
    LinkRadial[D]()
        .source(source())
        .target(target())
        .angle(angle())
        .radius(radius())
  }

  /** Creates a new canvas link generator, using the provided canvas rendering context. If the context is not `null`,
    * then the generated link is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas link radial generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasLinkRadial[D] = {
    LinkRadial.Facade.linkRadial[D]()
        .source(source())
        .target(target())
        .angle(angle())
        .radius(radius())
        .context(context)
    new CanvasLinkRadial[D](facade)
  }
}

/** Represents an SVG link radial generator. */
class SVGLinkRadial[D] private[shape] (
    override private[d3] val facade: LinkRadial.Facade[D]
) extends LinkRadial[D, SVGLinkRadial[D]](facade) {
  /** Returns a path data string representing the generated link, for the provided data. */
  def apply(data: D): String = facade.apply(data).asInstanceOf[String]
}

/** Represents a Canvas link radial generator. */
class CanvasLinkRadial[D] private[shape] (
    override private[d3] val facade: LinkRadial.Facade[D]
) extends LinkRadial[D, CanvasLinkRadial[D]](facade) {
  /** Renders the generated link to this generator's context as a sequence of path method calls,
    * for the provided data. */
  def apply(data: D): Unit = facade.apply(data)

  /** Returns the canvas rendering context of this link generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object LinkRadial {
  /** Creates a new link radial generator. */
  def apply[D](): SVGLinkRadial[D] = {
    new SVGLinkRadial[D](Facade.linkRadial[D]())
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def linkRadial[D](): LinkRadial.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: D): String | Unit = js.native
    def source(): D3ValueAccessor[D, js.Tuple2[Double, Double]] = js.native
    def source(source: D3ValueAccessor[D, js.Tuple2[Double, Double]]): Facade[D] = js.native
    def target(): D3ValueAccessor[D, js.Tuple2[Double, Double]] = js.native
    def target(target: D3ValueAccessor[D, js.Tuple2[Double, Double]]): Facade[D] = js.native
    def angle(): D3ValueAccessor[D, Double] = js.native
    def angle(angle: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def radius(): D3ValueAccessor[D, Double] = js.native
    def radius(radius: D3ValueAccessor[D, Double]): Facade[D] = js.native
  }
}
