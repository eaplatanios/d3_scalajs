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
import scala.scalajs.js.|

/** Threshold scales are similar to quantize scales, except they allow you to map arbitrary subsets of the domain to
  * discrete values in the range. The input domain is still continuous, and divided into slices based on a set of
  * threshold values. See [bl.ocks.org/3306362](http://bl.ocks.org/mbostock/3306362) for an example.
  *
  * Given a value in the input domain, this scale returns the corresponding value in the output range. For example:
  * {{{
  *   val color = d3.scale.threshold(
  *     domain = Seq(0, 1),
  *     range = Seq("red", "white", "green"))
  *
  *   color(-1)   // "red"
  *   color(0)    // "white"
  *   color(0.5)  // "white"
  *   color(1)    // "green"
  *   color(1000) // "green"
  * }}}
  *
  * @author Emmanouil Antonios Platanios
  */
@js.native trait Threshold[Domain, Range] extends Scale[Domain, Range, Range] {
  /** Sets the domain of this scale. */
  private[scale] def domain(domain: js.Array[Domain]): this.type = js.native

  /** Sets the range of this scale. */
  private[scale] def range(range: js.Array[Range]): this.type = js.native

  /** Returns the extent of values in the domain `[x0, x1]` for the corresponding value in the range, representing the
    * inverse mapping from range to domain. This method is useful for interaction, say to determine the value in the
    * domain that corresponds to the pixel location under the mouse. For example:
    * {{{
    *   val color = d3.scale.threshold(
    *     domain = Seq(0, 1),
    *     range = Seq("red", "white", "green"))
    *
    *   color.invert("red")   // [undefined,         0]
    *   color.invert("white") // [        0,         1]
    *   color.invert("green") // [        1, undefined]
    * }}}
    *
    * @param  value Value in the range to invert.
    * @return Inverted value in the domain of this scale.
    */
  @JSName("invertExtent") def invert(value: Range): js.Tuple2[Domain | Unit, Domain | Unit] = js.native
}

object Threshold {
  trait API {
    def threshold[Domain, Range](
        domain: Seq[Domain] = Seq(0.5).asInstanceOf[Seq[Domain]],
        range: Seq[Range] = Seq(0, 1).asInstanceOf[Seq[Range]]
    ): Threshold[Domain, Range] = Threshold(domain, range)
  }

  /** Constructs a new threshold scale.
    *
    * @param  domain Domain for the new scale. The values must be in sorted ascending order, or the behavior of the
    *                scale is undefined. The values are typically numbers, but any naturally ordered values (such as
    *                strings) will work. A threshold scale can be used to encode any type that is ordered. If the number
    *                of values in the scale's range is `N + 1`, the number of values in the scale's domain must be `N`.
    *                If there are fewer than `N` elements in the domain, the additional values in the range are ignored.
    *                If there are more than `N` elements in the domain, the scale may return undefined for some inputs.
    * @param  range  Range for the new scale. If the number of values in the scale's domain is `N`, the number of values
    *                in the scale's range must be `N + 1`. If there are fewer than `N + 1` elements in the range, the
    *                scale may return undefined for some inputs. If there are more than `N + 1` elements in the range,
    *                the additional values are ignored. The elements in the given array need not be numbers; any value
    *                or type will work.
    * @return New scale.
    */
  def apply[Domain, Range](
      domain: Seq[Domain] = Seq(0.5).asInstanceOf[Seq[Domain]],
      range: Seq[Range] = Seq(0, 1).asInstanceOf[Seq[Range]]
  ): Threshold[Domain, Range] = {
    Scale.scaleThreshold[Domain, Range]()
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
  }
}
