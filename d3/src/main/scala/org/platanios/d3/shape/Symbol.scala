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

/** Represents a symbol generator.
  *
  * Symbols provide a categorical shape encoding as is commonly used in scatter plots. Symbols are always centered at
  * `(0, 0)`. Use a transform (see: [[http://www.w3.org/TR/SVG/coords.html#TransformAttribute SVG]],
  * [[http://www.w3.org/TR/2dcontext/#transformations Canvas]]) to change the origin.
  *
  * <p>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/circle.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/cross.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/diamond.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/square.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/star.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/triangle.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  *   <a>
  *     <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/wye.png" width="100" height="100" style="max-width:100%;"/>
  *   </a>
  * </p>
  *
  * @author Emmanouil Antonios Platanios
  */
abstract class Symbol[D, S <: Symbol[D, S]] protected (
    private[d3] val facade: Symbol.Facade[D]
) {
  /** Returns the current `size` accessor. */
  def size(): D3ValueAccessor[D, Double] = facade.size()

  /** Sets the `size` accessor to the specified function or tuple of numbers and returns this symbol generator.
    *
    * Defaults to `64`. Specifying the size as a function is useful for constructing a scatter plot with a size
    * encoding. If you wish to scale the symbol to fit a given bounding box, rather than by area, try
    * [[http://bl.ocks.org/mbostock/3dd515e692504c92ab65 SVG's getBBox]].
    *
    * @param  size `size` accessor to use.
    * @return This symbol generator after modifying its `source` accessor.
    */
  def size(size: D3ValueAccessor[D, Double]): S = {
    facade.size(size)
    this.asInstanceOf[S]
  }

  /** Returns the current `symbolType` accessor. */
  def symbolType(): D3ValueAccessor[D, Symbol.Type] = facade.`type`()

  /** Sets the `symbolType` accessor to the specified function or tuple of numbers and returns this symbol generator.
    *
    * @param  symbolType `symbolType` accessor to use.
    * @return This symbol generator after modifying its `symbolType` accessor.
    */
  def symbolType(symbolType: D3ValueAccessor[D, Symbol.Type]): S = {
    facade.`type`(symbolType)
    this.asInstanceOf[S]
  }

  /** Creates a new SVG symbol generator based on this one. */
  def svg: SVGSymbol[D] = {
    Symbol[D]()
        .size(size())
        .symbolType(symbolType())
  }

  /** Creates a new canvas symbol generator, using the provided canvas rendering context. If the context is not `null`,
    * then the generated symbol is rendered to this context as a sequence of path method calls.
    *
    * @param  context Canvas rendering context to use.
    * @return New canvas symbol generator.
    */
  def canvas(context: dom.CanvasRenderingContext2D): CanvasSymbol[D] = {
    Symbol.Facade.symbol[D]()
        .size(size())
        .`type`(symbolType())
        .context(context)
    new CanvasSymbol[D](facade)
  }
}

/** Represents an SVG symbol generator. */
class SVGSymbol[D] private[shape] (
    override private[d3] val facade: Symbol.Facade[D]
) extends Symbol[D, SVGSymbol[D]](facade) {
  /** Returns a path data string representing the generated symbol, for the provided data. */
  def apply(data: D): String = facade.apply(data).asInstanceOf[String]
}

/** Represents a Canvas symbol generator. */
class CanvasSymbol[D] private[shape] (
    override private[d3] val facade: Symbol.Facade[D]
) extends Symbol[D, CanvasSymbol[D]](facade) {
  /** Renders the generated symbol to this generator's context as a sequence of path method calls,
    * for the provided data. */
  def apply(data: D): Unit = facade.apply(data)

  /** Returns the canvas rendering context of this symbol generator. */
  def context(): dom.CanvasRenderingContext2D = facade.context()
}

object Symbol {
  /** Array containing the set of all built-in symbol types: circle, cross, diamond, square, star, triangle, and wye.
    * Useful for constructing the range of an ordinal scale should you wish to use a shape encoding for categorical
    * data. */
  val all: Seq[Type] = Facade.symbols

  /** Circle symbol type. */
  val circle: Type = Facade.symbolCircle

  /** Greek cross symbol type, with arms of equal length. */
  val cross: Type = Facade.symbolCross

  /** Rhombus symbol type. */
  val diamond: Type = Facade.symbolDiamond

  /** Square symbol type. */
  val square: Type = Facade.symbolSquare

  /** Pentagonal star (pentagram) symbol type. */
  val star: Type = Facade.symbolStar

  /** Up-pointing triangle symbol type. */
  val triangle: Type = Facade.symbolTriangle

  /** Y-shape symbol type. */
  val wye: Type = Facade.symbolWye

  /** Creates a new symbol generator. */
  def apply[D](): SVGSymbol[D] = {
    new SVGSymbol[D](Facade.symbol[D]())
  }

  /** Represents a symbol type.
    *
    * Symbol types are typically not used directly. Instead, they are being passed to `Symbol.symbolType()`. However,
    * you can define your own symbol type implementation should none of the built-in types satisfy your needs using the
    * following interface. You can also use this low-level interface with a built-in symbol type as an alternative to
    * the symbol generator.
    */
  @js.native trait Type extends js.Object {
    /** Renders this symbol type to the specified context with the specified size in square pixels. */
    def draw(context: CanvasPathMethods, size: Double): Unit = js.native
  }

  @js.native trait CanvasPathMethods extends js.Object {
    def arc(x: Double, y: Double, radius: Double, startAngle: Double, endAngle: Double, anticlockwise: Boolean): Unit = js.native
    def arcTo(x1: Double, y1: Double, x2: Double, y2: Double, radius: Double): Unit = js.native
    def bezierCurveTo(cp1x: Double, cp1y: Double, cp2x: Double, cp2y: Double, x: Double, y: Double): Unit = js.native
    def closePath(): Unit = js.native
    def ellipse(x: Double, y: Double, radiusX: Double, radiusY: Double, rotation: Double, startAngle: Double, endAngle: Double, anticlockwise: Boolean): Unit = js.native
    def lineTo(x: Double, y: Double): Unit = js.native
    def moveTo(x: Double, y: Double): Unit = js.native
    def quadraticCurveTo(cpx: Double, cpy: Double, x: Double, y: Double): Unit = js.native
    def rect(x: Double, y: Double, w: Double, h: Double): Unit = js.native
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    val symbols       : js.Array[Type] = js.native
    val symbolCircle  : Type           = js.native
    val symbolCross   : Type           = js.native
    val symbolDiamond : Type           = js.native
    val symbolSquare  : Type           = js.native
    val symbolStar    : Type           = js.native
    val symbolTriangle: Type           = js.native
    val symbolWye     : Type           = js.native

    def symbol[D](): Symbol.Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends Generator.Facade[D, Facade[D]] {
    def apply(data: D): String | Unit = js.native
    def size(): D3ValueAccessor[D, Double] = js.native
    def size(size: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def `type`(): D3ValueAccessor[D, Type] = js.native
    def `type`(size: D3ValueAccessor[D, Type]): Facade[D] = js.native
  }
}
