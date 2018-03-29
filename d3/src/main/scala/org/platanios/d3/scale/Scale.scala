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

import org.platanios.d3.Facade

import scala.scalajs.js

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
object Scale {
  @js.native trait Facade[Domain, Range, Output, F <: Facade[Domain, Range, Output, F]] extends js.Object {
    def apply(x: Domain): Output = js.native
    def domain(): js.Array[Domain] = js.native
    def range(): js.Array[Range] = js.native
    def copy(): F = js.native
  }
}

trait Scale[Domain, Range, Output, F <: Scale.Facade[Domain, Range, Output, F]]
    extends Facade[Scale[Domain, Range, Output, F], F] {
  def apply(x: Domain): Output = facade.apply(x)
  def domain(): js.Array[Domain] = facade.domain()
  def range(): js.Array[Range] = facade.range()

  /** Returns an exact copy of this scale. Changes to this scale will not affect the returned scale, and vice versa. */
  def copy(): Scale[Domain, Range, Output, F] = copy(facade.copy())
}
