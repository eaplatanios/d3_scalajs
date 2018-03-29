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

/** Quantize scales are similar to linear scales, except that they use a discrete rather than continuous range. The
  * continuous input domain is divided into uniform segments based on the number of values in (i.e., the cardinality of)
  * the output range. Each range value `y` can be expressed as a quantized linear function of the domain value
  * `x`: `y = m round(x) + b`. See [bl.ocks.org/4060606](http://bl.ocks.org/mbostock/4060606) for an example.
  *
  * Given a value in the input domain, a quantize scale returns the corresponding value in the output range. For
  * example, to apply a color encoding:
  * {{{
  *   val color = d3.scale.quantize(
  *     domain = (0.0, 1.0),
  *     range = Seq("brown", "steelblue"))
  *
  *   color(0.49) // "brown"
  *   color(0.51) // "steelblue"
  * }}}
  * Or dividing the domain into three equally-sized parts with different range values to compute an appropriate stroke
  * width:
  * {{{
  *   val width = d3.scale.quantize(
  *     domain = (10.0, 100.0),
  *     range = Seq(1, 2, 4))
  *
  *   width(20) // 1
  *   width(50) // 2
  *   width(80) // 4
  * }}}
  *
  * @author Emmanouil Antonios Platanios
  */
class Quantize[Range] protected (
    override private[d3] val facade: Quantize.Facade[Range]
) extends TickScale[Double, Range, Range, Int, Quantize.Facade[Range]] {
  /** Returns the extent of values in the domain `[x0, x1]` for the corresponding value in the range: the inverse of
    * quantize. This method is useful for interaction, say to determine the value in the domain that corresponds to the
    * pixel location under the mouse.
    *
    * For example:
    * {{{
    *   val width = d3.scale.quantize(
    *     domain = (10.0, 100.0),
    *     range = Seq(1, 2, 4))
    *
    *   width.invert(2) // [40, 70]
    * }}}
    *
    * @param  value Value in the range to invert.
    * @return Inverted value in the domain of this scale.
    */
  def invert(value: Range): js.Tuple2[Double, Double] = facade.invertExtent(value)

  override protected def copy(facade: Quantize.Facade[Range]): Quantize[Range] = {
    new Quantize(facade)
  }
}

object Quantize {
  @js.native private[d3] trait Facade[Range]
      extends TickScale.Facade[Double, Range, Range, Int, Facade[Range]] {
    def domain(domain: js.Tuple2[Double, Double]): this.type = js.native
    def range(range: js.Array[Range]): this.type = js.native
    def nice(count: Int = -1): this.type = js.native

    def invertExtent(value: Range): js.Tuple2[Double, Double] = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Quantize] object Facade extends js.Object {
    def scaleQuantize[Range](): Quantize.Facade[Range] = js.native
  }

  trait API {
    def quantize[Range](
        domain: (Double, Double) = (0.0, 1.0),
        range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
        nice: Boolean = false,
        niceCount: Int = -1
    ): Quantize[Range] = Quantize(domain, range, nice, niceCount)
  }

  /** Constructs a new quantize scale.
    *
    * @param  domain    Domain for the new scale.
    * @param  range     Range for the new scale.
    * @param  nice      If `true`, the domain will be extended so that it starts and ends on nice round values. This
    *                   method typically modifies the scale’s domain, and may only extend the bounds to the nearest
    *                   round value. An optional tick count argument. "Nicing" is useful if the domain is computed from
    *                   data, say using `extent`, and may be irregular. For example, for a domain of
    *                   `[0.201479..., 0.996679...]`, a nice domain might be `[0.2, 1.0]`. If the domain has more than
    *                   two values, "nicing" the domain only affects the first and last value.
    * @param  niceCount Optional value that allows greater control over the step size used to extend the bounds, when
    *                   `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @return New scale.
    */
  def apply[Range](
      domain: (Double, Double) = (0.0, 1.0),
      range: Seq[Range] = Seq(0.0, 1.0).asInstanceOf[Seq[Range]],
      nice: Boolean = false,
      niceCount: Int = -1
  ): Quantize[Range] = {
    val facade = Facade.scaleQuantize[Range]()
        .domain(domain)
        .range(js.Array(range: _*))
    if (nice && niceCount > 0)
      facade.nice(niceCount)
    else if (nice)
      facade.nice()
    new Quantize(facade)
  }
}
