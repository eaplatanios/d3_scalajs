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

/** Quantile scales map a sampled input domain to a discrete range. The domain is considered continuous and thus the
  * scale will accept any reasonable input value. However, the domain is specified as a discrete set of sample values.
  * The number of values in (the cardinality of) the output range determines the number of quantiles that will be
  * computed from the domain. To compute the quantiles, the domain is sorted, and treated as a
  * [population of discrete values](https://en.wikipedia.org/wiki/Quantile#Quantiles_of_a_population).
  * See [bl.ocks.org/8ca036b3505121279daf](http://bl.ocks.org/mbostock/8ca036b3505121279daf) for an example.
  *
  * @author Emmanouil Antonios Platanios
  */
class Quantile[Range] protected (
    override private[d3] val facade: Quantile.Facade[Range]
) extends Scale[Double, Range, Range, Quantile.Facade[Range]] {
  /** Returns the quantile thresholds. If the range contains `n` discrete values, the returned array will contain
    * `n - 1` thresholds. Values less than the first threshold are considered in the first quantile; values greater
    * than or equal to the first threshold but less than the second threshold are in the second quantile, and so on.
    * Internally, the thresholds array is used with bisect to find the output quantile associated with the given input
    * value.
    *
    * @return Quantiles in this scale.
    */
  def quantiles(): js.Array[Double] = facade.quantiles()

  /** Returns the extent of values in the domain `[x0, x1]` for the corresponding value in the range: the inverse of
    * quantize. This method is useful for interaction, say to determine the value in the domain that corresponds to the
    * pixel location under the mouse.
    *
    * @param  value Value in the range to invert.
    * @return Inverted value in the domain of this scale.
    */
  def invert(value: Range): js.Tuple2[Double, Double] = facade.invertExtent(value)

  override protected def copy(facade: Quantile.Facade[Range]): Quantile[Range] = {
    new Quantile(facade)
  }
}

object Quantile {
  @js.native private[d3] trait Facade[Range]
      extends Scale.Facade[Double, Range, Range, Facade[Range]] {
    def domain(domain: js.Array[Double]): this.type = js.native
    def range(range: js.Array[Range]): this.type = js.native

    def quantiles(): js.Array[Double] = js.native
    def invertExtent(value: Range): js.Tuple2[Double, Double] = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Quantile] object Facade extends js.Object {
    def scaleQuantile[Range](): Quantile.Facade[Range] = js.native
  }

  trait API {
    def quantile(
        domain: Seq[Double],
        range: Seq[Range]
    ): Quantile[Range] = Quantile(domain, range)
  }

  /** Constructs a new quantile scale.
    *
    * @param  domain Domain for the new scale. A copy of the input array is sorted and stored internally.
    * @param  range  Range for the new scale. The number of values in (the cardinality, or length, of) the range array
    *                determines the number of quantiles that are computed. For example, to compute quartiles, range must
    *                be an array of four elements such as `[0, 1, 2, 3]`.
    * @return New scale.
    */
  def apply[Range](
      domain: Seq[Double],
      range: Seq[Range]
  ): Quantile[Range] = {
    val facade = Facade.scaleQuantile[Range]()
        .domain(js.Array(domain: _*))
        .range(js.Array(range: _*))
    new Quantile(facade)
  }
}
