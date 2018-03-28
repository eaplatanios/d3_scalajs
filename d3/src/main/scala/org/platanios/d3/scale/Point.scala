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
import scala.scalajs.js.annotation.JSName

/** Point scales are a variant of band scales with the bandwidth fixed to zero. Point scales are typically used for
  * scatter plots with an ordinal or categorical dimension. The unknown value of a point scale is always undefined:
  * they do not allow implicit domain construction.
  *
  * Example:
  * {{{
  *                                           range
  *             ├───────────────────────────────────────────────────────────────┤
  *
  *            step*padding        step                   step        step*padding
  *             ├────────┼──────────────────────┼──────────────────────┼────────┤
  *                      ◉                      ◉                      ◉
  *                      0                      1                      2
  * }}}
  *
  * Given a value in the input domain, this scale returns the corresponding point derived from the output range.
  *
  * @author Emmanouil Antonios Platanios
  */
@js.native trait Point[Domain] extends Scale[Domain, js.Tuple2[Double, Double], Double] {
  /** Sets the domain of this scale. */
  private[scale] def domain(domain: js.Array[Domain]): this.type = js.native

  /** Sets the range of this scale. */
  private[scale] def range(range: js.Tuple2[Double, Double]): this.type = js.native

  /** Sets the range of this scale with rounding enabled. */
  private[scale] def rangeRound(range: js.Tuple2[Double, Double]): this.type = js.native

  /** Enables/disables rounding for this scale. */
  private[scale] def round(round: Boolean): this.type = js.native

  /** Sets the padding of this scale. */
  private[scale] def padding(padding: Double): this.type = js.native

  /** Sets the alignment of this scale. */
  private[scale] def align(align: Double): this.type = js.native

  /** Returns `true` if the range is rounded. */
  @JSName("round") def rounded(): Boolean = js.native

  /** Returns the padding value. */
  def padding(): Double = js.native

  /** Returns the alignment value. */
  @JSName("align") def alignment(): Double = js.native

  /** Returns the distance between the starts of adjacent points, which is always equal to zero. */
  def step(): Double = js.native
}

object Point {
  trait API {
    def point[Domain](
        domain: Seq[Domain] = Seq.empty,
        range: (Double, Double) = (0.0, 1.0),
        padding: Double = 0.0,
        alignment: Double = 0.5,
        rounded: Boolean = false
    ): Point[Domain] = Point(domain, range, padding, alignment, rounded)
  }

  /** Constructs a new point scale.
    *
    * @param  domain    Domain for the new scale. The first element in the domain will be mapped to the first point,
    *                   the second domain to the second point, and so on. Domain values are stored internally in a
    *                   map from string representations of the values to their indices. The resulting index is then
    *                   used to determine the point. Thus a point scale's values must be coercible to a string, and
    *                   the string version of the domain value uniquely identifies the corresponding point.
    * @param  range     Range for the new scale. If the elements in the given array are not numbers, they will be
    *                   coerced to numbers.
    * @param  padding   Specifies the outer padding, which must be in the range `[0, 1]`. The outer padding
    *                   determines the ratio of the range that is reserved for blank space before the first point
    *                   and after the last point.
    * @param  alignment Specifies the alignment, which must be in the range `[0, 1]`. The alignment determines how
    *                   any leftover/unused space in the range is distributed. A value of `0.5` indicates that the
    *                   leftover space should be equally distributed before the first point and after the last point
    *                   (i.e., the points should be centered within the range). A value of `0` or `1` may be used to
    *                   shift the points to one side, say to position them adjacent to an axis.
    * @param  rounded   If `true`, rounding is enabled. If rounding is enabled, the start and stop of each point will
    *                   be integer values. Rounding is sometimes useful for avoiding antialiasing artifacts (though
    *                   also consider the shape-rendering `crispEdges` styles). Note that, if the width of the domain
    *                   is not a multiple of the cardinality of the range, there may be leftover unused space, even
    *                   when no padding is being used. Use `alignment` to specify how the leftover space is
    *                   distributed.
    * @return New scale.
    */
  def apply[Domain](
      domain: Seq[Domain] = Seq.empty,
      range: (Double, Double) = (0.0, 1.0),
      padding: Double = 0.0,
      alignment: Double = 0.5,
      rounded: Boolean = false
  ): Point[Domain] = {
    Scale.scalePoint[Domain]()
        .domain(js.Array(domain: _*))
        .range(range)
        .padding(padding)
        .align(alignment)
        .round(rounded)
  }
}
