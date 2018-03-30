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

package org.platanios.d3.scale

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Band scales are like ordinal scales except that the output range is continuous and numeric. Discrete output values
  * are automatically computed by the scale by dividing the continuous range into uniform bands. Band scales are
  * typically used for bar charts with an ordinal or categorical dimension. The unknown value of a band scale is
  * effectively undefined: they do not allow implicit domain construction.
  *
  * Example:
  * {{{
  *                                                      range
  *          ├───────────────────────────────────────────────────────────────────────────────────────────┤
  *
  *        step*paddingOuter     step*paddingInner     step*paddingInner     step*paddingInner     step*paddingOuter
  *          ├───┼─────────────────┼───┼─────────────────┼───┼─────────────────┼───┼─────────────────┼───┤
  *
  *              ┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐   ┌─────────────────┐
  *              │    bandwidth    │   │    bandwidth    │   │    bandwidth    │   │    bandwidth    │
  *              │                 │   │                 │   │                 │   │                 │
  *              │        0        │   │        1        │   │        2        │   │        3        │
  *              ╎                 ╎   ╎                 ╎   ╎                 ╎   ╎                 ╎
  *
  *              ├─────────────────────┤
  *                        step
  * }}}
  *
  * Given a value in the input domain, this scale returns the start of the corresponding band derived from the output
  * range. If the given value is not in the scale's domain, the scale returns `undefined`.
  *
  * @author Emmanouil Antonios Platanios
  */
class Band[Domain] protected (
    override private[d3] val facade: Band.Facade[Domain]
) extends Scale[Domain, js.Tuple2[Double, Double], Double, Nothing, Band.Facade[Domain]] {
  /** Returns `true` if the range is rounded. */
  def rounded(): Boolean = facade.round()

  /** Returns the padding value. */
  def padding(): Double = facade.padding()

  /** Returns the inner padding value. */
  def paddingInner(): Double = facade.paddingInner()

  /** Returns the outer padding value. */
  def paddingOuter(): Double = facade.paddingOuter()

  /** Returns the alignment value. */
  def alignment(): Double = facade.align()

  /** Returns the width of each band. */
  def bandwidth(): Double = facade.bandwidth()

  /** Returns the distance between the starts of adjacent points, which is always equal to zero. */
  def step(): Double = facade.step()

  /** This scale does not support `ticks()`. */
  override def ticks(tickArgument: Nothing): js.Array[Domain] = ???

  /** This scale does not support `tickFormat()`. */
  override def tickFormat(tickArgument: Nothing, specifier: String): js.Function1[Domain, String] = ???

  override protected def withFacade(facade: Band.Facade[Domain]): Band[Domain] = {
    new Band(facade)
  }
}

object Band {
  @js.native private[d3] trait Facade[Domain]
      extends Scale.Facade[Domain, js.Tuple2[Double, Double], Double, Facade[Domain]] {
    def domain(domain: js.Array[Domain]): this.type = js.native
    def range(range: js.Tuple2[Double, Double]): this.type = js.native
    def rangeRound(range: js.Tuple2[Double, Double]): this.type = js.native
    def round(round: Boolean): this.type = js.native
    def padding(padding: Double): this.type = js.native
    def paddingInner(padding: Double): this.type = js.native
    def paddingOuter(padding: Double): this.type = js.native
    def align(align: Double): this.type = js.native

    def round(): Boolean = js.native
    def padding(): Double = js.native
    def paddingInner(): Double = js.native
    def paddingOuter(): Double = js.native
    def align(): Double = js.native
    def bandwidth(): Double = js.native
    def step(): Double = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Band] object Facade extends js.Object {
    def scaleBand[Domain](): Band.Facade[Domain] = js.native
  }

  trait API {
    def band[Domain](
        domain: Seq[Domain] = Seq.empty,
        range: (Double, Double) = (0.0, 1.0),
        paddingInner: Double = 0.0,
        paddingOuter: Double = 0.0,
        alignment: Double = 0.5,
        rounded: Boolean = false
    ): Band[Domain] = Band(domain, range, paddingInner, paddingOuter, alignment, rounded)
  }

  /** Constructs a new band scale.
    *
    * @param  domain       Domain for the new scale. The first element in the domain will be mapped to the first band,
    *                      the second domain to the second band, and so on. Domain values are stored internally in a map
    *                      from string representations of the values to their indices. The resulting index is then used
    *                      to determine the band. Thus, a band scale's values must be coercible to a string, and the
    *                      string version of the domain value uniquely identifies the corresponding band.
    * @param  range        Range for the new scale. If the elements in the given array are not numbers, they will be
    *                      coerced to numbers.
    * @param  paddingInner Specifies the inner padding, which must be in the range `[0, 1]`. The inner padding
    *                      determines the ratio of the range that is reserved for blank space between bands.
    * @param  paddingOuter Specifies the outer padding, which must be in the range `[0, 1]`. The outer padding
    *                      determines the ratio of the range that is reserved for blank space before the first band and
    *                      after the last band.
    * @param  alignment    Specifies the alignment, which must be in the range `[0, 1]`. The alignment determines how
    *                      any leftover/unused space in the range is distributed. A value of `0.5` indicates that the
    *                      leftover space should be equally distributed before the first band and after the last band
    *                      (i.e., the bands should be centered within the range). A value of `0` or `1` may be used to
    *                      shift the bands to one side, say to position them adjacent to an axis.
    * @param  rounded      If `true`, rounding is enabled. If rounding is enabled, the start and stop of each band will
    *                      be integer values. Rounding is sometimes useful for avoiding antialiasing artifacts (though
    *                      also consider the shape-rendering `crispEdges` styles). Note that, if the width of the domain
    *                      is not a multiple of the cardinality of the range, there may be leftover unused space, even
    *                      when no padding is being used. Use `alignment` to specify how the leftover space is
    *                      distributed.
    * @return New scale.
    */
  def apply[Domain](
      domain: Seq[Domain] = Seq.empty,
      range: (Double, Double) = (0.0, 1.0),
      paddingInner: Double = 0.0,
      paddingOuter: Double = 0.0,
      alignment: Double = 0.5,
      rounded: Boolean = false
  ): Band[Domain] = {
    val facade = Facade.scaleBand[Domain]()
        .domain(js.Array(domain: _*))
        .range(range)
        .paddingInner(paddingInner)
        .paddingOuter(paddingOuter)
        .align(alignment)
        .round(rounded)
    new Band(facade)
  }
}
