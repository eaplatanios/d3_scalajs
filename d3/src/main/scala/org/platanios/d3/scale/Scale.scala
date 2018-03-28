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

/** Scales are a convenient abstraction for a fundamental task in visualization: mapping a dimension of abstract data to
  * a visual representation. Although most often used for position-encoding quantitative data, such as mapping a
  * measurement in meters to a position in pixels for dots in a scatter plot, scales can represent virtually any visual
  * encoding, such as diverging colors, stroke widths, or symbol size. Scales can also be used with virtually any type
  * of data, such as named categorical data or discrete data that requires sensible breaks.
  *
  * For [continuous](https://github.com/d3/d3-scale#continuous-scales) quantitative data, you typically want a
  * [linear scale](https://github.com/d3/d3-scale#linear-scales), or for time series data, a
  * [time scale](https://github.com/d3/d3-scale#time-scales). If the distribution calls for it, consider transforming
  * the data using a [power](https://github.com/d3/d3-scale#power-scales) or a
  * [log](https://github.com/d3/d3-scale#log-scales) scale. A
  * [quantize scale](https://github.com/d3/d3-scale#quantize-scales) may aid differentiation by rounding continuous data
  * to a fixed set of discrete values; similarly, a [quantile scale](https://github.com/d3/d3-scale#quantile-scales)
  * computes quantiles from a sample population, and a
  * [threshold scale](https://github.com/d3/d3-scale#threshold-scales) allows you to specify arbitrary breaks in
  * continuous data.
  *
  * Scales have no intrinsic visual representation. However, most scales can
  * [generate](https://github.com/d3/d3-scale#continuous_ticks) and
  * [format](https://github.com/d3/d3-scale#continuous_tickFormat) ticks for reference marks to aid in the construction
  * of axes.
  *
  * For more details, refer to the [official D3 documentation](https://github.com/d3/d3-scale).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-scale", JSImport.Namespace)
@js.native private[scale] object Scale extends js.Object {
  private[scale] val scaleImplicit: js.Any = js.native
  private[scale] def scaleLinear[Range, Output](): Linear[Range, Output] = js.native
  private[scale] def scalePow[Range, Output](): Power[Range, Output] = js.native
  private[scale] def scaleLog[Range, Output](): Logarithmic[Range, Output] = js.native
  private[scale] def scaleIdentity(): Identity = js.native
  private[scale] def scaleTime[Range, Output](): Time[Range, Output] = js.native
  private[scale] def scaleUTC[Range, Output](): Time[Range, Output] = js.native
  private[scale] def scaleSequential[Output](interpolator: js.Function1[Double, Output]): Sequential[Output] = js.native
  private[scale] def scaleQuantize[Range](): Quantize[Range] = js.native
  private[scale] def scaleQuantile[Range](): Quantile[Range] = js.native
  private[scale] def scaleThreshold[Domain, Range](): Threshold[Domain, Range] = js.native
  private[scale] def scaleOrdinal[Domain, Range](range: js.Array[Range]): Ordinal[Domain, Range] = js.native
  private[scale] def scaleBand[Domain](): Band[Domain] = js.native
  private[scale] def scalePoint[Domain](): Point[Domain] = js.native
}

trait Scale[Domain, Range, Output] extends js.Object {
  def apply(x: Domain): Output = js.native
  def domain(): js.Array[Domain] = js.native
  def range(): js.Array[Range] = js.native

  /** Returns an exact copy of this scale. Changes to this scale will not affect the returned scale, and vice versa. */
  def copy(): this.type = js.native
}
