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

/** Sequential scales are similar to continuous scales in that they map a continuous, numeric input domain to a
  * continuous output range. However, unlike continuous scales, the output range of a sequential scale is fixed by its
  * interpolator and not configurable. These scales do not expose invert, range, rangeRound and interpolate methods.
  *
  * @author Emmanouil Antonios Platanios
  */
@js.native trait Sequential[Output] extends Scale[Double, Double, Output] {
  /** Sets the domain of this scale. */
  private[scale] def domain(domain: js.Tuple2[Double, Double]): this.type = js.native

  private[scale] def interpolator[IO](interpolator: js.Function1[Double, IO]): Sequential[IO] = js.native

  /** Enables/disables clamping for this scale. */
  private[scale] def clamp(clamp: Boolean): this.type = js.native

  /** Returns a boolean indicating whether clamping is enabled for this scale. */
  @JSName("clamp") def clamped(): Boolean = js.native

  /** Returns the interpolator used by this scale. */
  @JSName("interpolate") def interpolator(): js.Function1[Double, Output] = js.native
}

object Sequential {
  trait API {
    def sequential[Output](
        interpolator: Double => Output,
        domain: (Double, Double) = (0.0, 1.0),
        clamped: Boolean = false
    ): Sequential[Output] = Sequential(interpolator, domain, clamped)
  }

  /** Constructs a new sequential scale with the given interpolator function. When the scale is applied, the
    * interpolator will be invoked with a value typically in the range `[0, 1]`, where `0` represents the start of the
    * domain, and `1` represents the end of the domain. For example, to implement the ill-advised HSL rainbow scale:
    * {{{
    *   val rainbow = d3.scale.sequential(t => d3.color.hsl(t * 360, 1, 0.5).toString)
    * }}}
    * A more aesthetically-pleasing and perceptually-effective cyclical hue encoding is to use
    * `d3.color.interpolateRainbow`.
    *
    * @param  interpolator Interpolator function to use.
    * @param  domain       Domain for the new scale.
    * @param  clamped      Boolean value that represents whether to use clamping or not.
    * @return New scale.
    */
  def apply[Output](
      interpolator: Double => Output,
      domain: (Double, Double) = (0.0, 1.0),
      clamped: Boolean = false
  ): Sequential[Output] = {
    Scale.scaleSequential[Output](interpolator)
        .domain(domain)
        .clamp(clamped)
  }
}
