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

package org.platanios.d3.axis

import org.platanios.d3.{D3Function, JsNumber}
import org.platanios.d3.scale.Scale
import org.platanios.d3.selection.{Selection, TransitionLike}

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Regardless of orientation, axes are always rendered at the origin. To change the position of the axis with respect
  * to the chart, specify a transform attribute on the containing element. For example:
  * {{{
  *   d3.select("body").append("svg")
  *     .attr("width", 1440)
  *     .attr("height", 30)
  *     .append("g")
  *     .attr("transform", "translate(0,30)")
  *     .call(axis)
  * }}}
  * The elements created by the axis are considered part of its public API. You can apply external stylesheets or modify
  * the generated axis elements to customize the axis appearance.
  *
  * An axis consists of a path element of class `"domain"` representing the extent of the scale's domain, followed by
  * transformed `g` elements of class `"tick"` representing each of the scale's ticks. Each tick has a `line` element to
  * draw the tick line, and a `text` element for the tick label. For example, here is a typical bottom-oriented axis:
  * {{{
  *   <g fill="none" font-size="10" font-family="sans-serif" text-anchor="middle">
  *   <path class="domain" stroke="#000" d="M0.5,6V0.5H880.5V6"></path>
  *   <g class="tick" opacity="1" transform="translate(0.5,0)">
  *     <line stroke="#000" y2="6"></line>
  *     <text fill="#000" y="9" dy="0.71em">0.0</text>
  *   </g>
  *   <g class="tick" opacity="1" transform="translate(176.5,0)">
  *     <line stroke="#000" y2="6"></line>
  *     <text fill="#000" y="9" dy="0.71em">0.2</text>
  *   </g>
  *   <g class="tick" opacity="1" transform="translate(352.5,0)">
  *     <line stroke="#000" y2="6"></line>
  *     <text fill="#000" y="9" dy="0.71em">0.4</text>
  *   </g>
  *   <g class="tick" opacity="1" transform="translate(528.5,0)">
  *     <line stroke="#000" y2="6"></line>
  *     <text fill="#000" y="9" dy="0.71em">0.6</text>
  *   </g>
  *   <g class="tick" opacity="1" transform="translate(704.5,0)">
  *     <line stroke="#000" y2="6"></line>
  *     <text fill="#000" y="9" dy="0.71em">0.8</text>
  *   </g>
  *   <g class="tick" opacity="1" transform="translate(880.5,0)">
  *     <line stroke="#000" y2="6"></line>
  *     <text fill="#000" y="9" dy="0.71em">1.0</text>
  *   </g>
  * </g>
  * }}}
  * The orientation of an axis is fixed; to change it, remove the old axis and create a new one.
  *
  * @author Emmanouil Antonios Platanios
  */
class Axis[Domain, TickArg] protected (
    private[d3] val facade: Axis.Facade[Domain, TickArg]
) {
  def apply[C <: dom.Element](context: Selection[C, js.Any, dom.Element, js.Any]): Unit = {
    facade.apply(context)
  }

  def apply[C <: dom.Element](context: TransitionLike[C, js.Any]): Unit = facade.apply(context)

  /** Returns the scale used by this axis. */
  def scale(): Scale[Domain, Double, Double, TickArg, _, _] = facade.scale()

  /** Sets the scale used by this axis, and returns the axis.
    *
    * @param  scale Scale to use.
    * @return This axis.
    */
  def scale[Number: JsNumber](scale: Scale[Domain, Number, Number, TickArg, _, _]): Axis[Domain, TickArg] = {
    facade.scale(scale)
    this
  }

  /** Sets the arguments that will be passed to `scale.ticks()` and `scale.tickFormat()` when the axis is rendered, and
    * returns the axis generator. The meaning of the arguments depends on the axis' scale type: most commonly, the
    * arguments are a suggested count for the number of ticks (or a time interval for time scales), and an optional
    * format specifier to customize how the tick values are formatted.
    *
    * To set the tick values explicitly, use `axis.tickValues()`. To set the tick format explicitly, use
    * `axis.tickFormat()`.
    *
    * For example, to generate twenty ticks with SI-prefix formatting on a linear scale, say:
    * {{{
    *   axis.ticks(20, "s")
    * }}}
    * To generate ticks every fifteen minutes with a time scale, say:
    * {{{
    *   axis.ticks(d3.time.minute.every(15))
    * }}}
    *
    * @param  tickArgument Hint for the number of ticks to return, or the time intervals to use.
    * @param  specifier    Format specifier to use.
    * @return This axis.
    */
  def ticks(tickArgument: TickArg, specifier: Option[String] = None): Axis[Domain, TickArg] = {
    specifier match {
      case Some(s) => facade.ticks(tickArgument, s)
      case None => facade.ticks(tickArgument)
    }
    this
  }

  /** Returns the tick values used by this axis. */
  def tickValues(): js.Array[Domain] = facade.tickValues()

  /** Sets the tick values being used explicitly. If an array is provided (as opposed to `null`), the specified values
    * are used for ticks rather than using the scale's automatic tick generator. If `values` is `null`, then this method
    * clears any previously-set explicit tick values and reverts back to the scale's tick generator.
    *
    * For example, to generate ticks at specific values:
    * {{{
    *   val xAxis = d3.axis(d3.axis.Bottom, tickValues = Seq(1, 2, 3, 5, 8, 13, 21))
    * }}}
    * The explicit tick values take precedence over the tick arguments set by `axis.tickArguments()`. However, any tick
    * arguments will still be passed to the scale's `tickFormat()` function if a tick format is also not set.
    *
    * @param  values Tick values to use.
    * @return This axis.
    */
  def tickValues(values: js.Array[Domain]): Axis[Domain, TickArg] = {
    facade.tickValues(values)
    this
  }

  /** Returns the tick format function used by this axis. */
  def tickFormat(): D3Function[dom.svg.Element, Domain, String] = facade.tickFormat()

  /** Sets the tick format function and returns the axis.
    *
    * A `null` format indicates that the scale's default formatter should be used, which is obtained by calling
    * `scale.tickFormat()`. In this case, the arguments specified by `axis.tickArguments()` are likewise passed to
    * `scale.tickFormat()`.
    *
    * For example, to display integers with comma-grouping for thousands:
    * {{{
    *   axis.tickFormat(d3.format.number(",.0f"))
    * }}}
    *
    * More commonly, a format specifier is passed to `axis.ticks()`:
    * {{{
    *   axis.ticks(10, ",f")
    * }}}
    * This has the advantage of setting the format precision automatically based on the tick interval.
    *
    * @param  format Format function.
    * @return This axis.
    */
  def tickFormat(format: D3Function[dom.svg.Element, Domain, String]): Axis[Domain, TickArg] = {
    facade.tickFormat(format)
    this
  }

  /** Sets the both the inner and the outer tick size to the specified value and returns the axis.
    *
    * The inner tick size controls the length of the tick lines, offset from the native position of the axis.
    *
    * The outer tick size controls the length of the square ends of the domain path, offset from the native position of
    * the axis. Thus, the "outer ticks" are not actually ticks but part of the domain path, and their position is
    * determined by the associated scale's domain extent. Thus, outer ticks may overlap with the first or last inner
    * tick. An outer tick size of `0` suppresses the square ends of the domain path, instead producing a straight line.
    *
    * @param  size Inner tick size.
    * @return This axis.
    */
  def tickSize(size: Double): Axis[Domain, TickArg] = {
    facade.tickSize(size)
    this
  }

  /** Returns the inner tick size used by this axis. */
  def tickSizeInner(): Double = facade.tickSizeInner()

  /** Sets the inner tick size to the specified value and returns the axis. The inner tick size controls the length of
    * the tick lines, offset from the native position of the axis.
    *
    * @param  size Inner tick size.
    * @return This axis.
    */
  def tickSizeInner(size: Double): Axis[Domain, TickArg] = {
    facade.tickSizeInner(size)
    this
  }

  /** Returns the outer tick size used by this axis. */
  def tickSizeOuter(): Double = facade.tickSizeOuter()

  /** Sets the outer tick size to the specified value and returns the axis. The outer tick size controls the length of
    * the square ends of the domain path, offset from the native position of the axis. Thus, the "outer ticks" are not
    * actually ticks but part of the domain path, and their position is determined by the associated scale's domain
    * extent. Thus, outer ticks may overlap with the first or last inner tick. An outer tick size of `0` suppresses the
    * square ends of the domain path, instead producing a straight line.
    *
    * @param  size Outer tick size.
    * @return This axis.
    */
  def tickSizeOuter(size: Double): Axis[Domain, TickArg] = {
    facade.tickSizeOuter(size)
    this
  }

  /** Returns the tick padding used by this axis, as a number of pixels. */
  def tickPadding(): Int = facade.tickPadding()

  /** Sets the tick padding to the specified value in pixels and returns the axis. */
  def tickPadding(size: Int): Axis[Domain, TickArg] = {
    facade.tickPadding(size)
    this
  }
}

object Axis {
  @js.native trait Facade[Domain, TickArg] extends js.Object {
    def apply[C <: dom.Element](context: Selection[C, js.Any, dom.Element, js.Any]): Unit = js.native
    def apply[C <: dom.Element](context: TransitionLike[C, js.Any]): Unit = js.native
    def scale(): Scale[Domain, Double, Double, TickArg, _, _] = js.native
    def scale[Number: JsNumber](scale: Scale[Domain, Number, Number, TickArg, _, _]): this.type = js.native
    def ticks(tick: Any, specifier: String = ???): Facade[Domain, TickArg] = js.native
    def tickValues(): js.Array[Domain] = js.native
    def tickValues(values: js.Array[Domain]): this.type = js.native
    def tickFormat(): D3Function[dom.svg.Element, Domain, String] = js.native
    def tickFormat(format: D3Function[dom.svg.Element, Domain, String]): this.type = js.native
    def tickSize(): Double = js.native
    def tickSize(size: Double): this.type = js.native
    def tickSizeInner(): Double = js.native
    def tickSizeInner(size: Double): this.type = js.native
    def tickSizeOuter(): Double = js.native
    def tickSizeOuter(size: Double): this.type = js.native
    def tickPadding(): Int = js.native
    def tickPadding(padding: Int): this.type = js.native
  }

  @JSImport("d3-axis", JSImport.Namespace)
  @js.native object Facade extends js.Object {
    def axisTop[Domain, TickArg, Number: JsNumber, F <: Scale.Facade[Domain, Number, Number, F]](
        scale: Scale.Facade[Domain, Number, Number, F]
    ): Axis.Facade[Domain, TickArg] = js.native

    def axisRight[Domain, TickArg, Number: JsNumber, F <: Scale.Facade[Domain, Number, Number, F]](
        scale: Scale.Facade[Domain, Number, Number, F]
    ): Axis.Facade[Domain, TickArg] = js.native

    def axisBottom[Domain, TickArg, Number: JsNumber, F <: Scale.Facade[Domain, Number, Number, F]](
        scale: Scale.Facade[Domain, Number, Number, F]
    ): Axis.Facade[Domain, TickArg] = js.native

    def axisLeft[Domain, TickArg, Number: JsNumber, F <: Scale.Facade[Domain, Number, Number, F]](
        scale: Scale.Facade[Domain, Number, Number, F]
    ): Axis.Facade[Domain, TickArg] = js.native
  }

  sealed trait Position
  case object Top extends Position
  case object Right extends Position
  case object Bottom extends Position
  case object Left extends Position

  def apply[Domain, TickArg, Number: JsNumber, F <: Scale.Facade[Domain, Number, Number, F]](
      position: Position,
      scale: Scale[Domain, Number, Number, TickArg, _, F],
      tickValues: js.Array[Domain] = null,
      tickFormat: D3Function[dom.svg.Element, Domain, String] = null,
      tickSizeInner: Int = 6,
      tickSizeOuter: Int = 6,
      tickPadding: Int = 3
  ): Axis[Domain, TickArg] = {
    val facade = position match {
      case Top => Facade.axisTop[Domain, TickArg, Number, F](scale.facade)
      case Right => Facade.axisRight[Domain, TickArg, Number, F](scale.facade)
      case Bottom => Facade.axisBottom[Domain, TickArg, Number, F](scale.facade)
      case Left => Facade.axisLeft[Domain, TickArg, Number, F](scale.facade)
    }
    facade.tickValues(tickValues)
    facade.tickFormat(tickFormat)
    facade.tickSizeInner(tickSizeInner)
    facade.tickSizeOuter(tickSizeOuter)
    facade.tickPadding(tickPadding)
    new Axis(facade)
  }
}
