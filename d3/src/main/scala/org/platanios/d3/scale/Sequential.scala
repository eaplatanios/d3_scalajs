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

/** Sequential scales are similar to continuous scales in that they map a continuous, numeric input domain to a
  * continuous output range. However, unlike continuous scales, the output range of a sequential scale is fixed by its
  * interpolator and not configurable. These scales do not expose invert, range, rangeRound and interpolate methods.
  *
  * @author Emmanouil Antonios Platanios
  */
class Sequential[Output] protected (
    override private[d3] val facade: Sequential.Facade[Output]
) extends Scale[Double, Double, Output, Nothing, Sequential.Facade[Output]] {
  /** Returns a boolean indicating whether clamping is enabled for this scale. */
  def clamped(): Boolean = facade.clamp()

  /** Returns the interpolator used by this scale. */
  def interpolator(): js.Function1[Double, Output] = facade.interpolate()

  /** This scale does not support `ticks()`. */
  override def ticks(tickArgument: Nothing): js.Array[Double] = ???

  /** This scale does not support `tickFormat()`. */
  override def tickFormat(tickArgument: Nothing, specifier: String): js.Function1[Double, String] = ???

  override protected def withFacade(facade: Sequential.Facade[Output]): Sequential[Output] = {
    new Sequential(facade)
  }
}

object Sequential {
  @js.native private[d3] trait Facade[Output]
      extends Scale.Facade[Double, Double, Output, Facade[Output]] {
    def domain(domain: js.Tuple2[Double, Double]): this.type = js.native
    def interpolator[IO](interpolator: js.Function1[Double, IO]): Sequential.Facade[IO] = js.native
    def clamp(clamp: Boolean): this.type = js.native

    def clamp(): Boolean = js.native
    def interpolate(): js.Function1[Double, Output] = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Sequential] object Facade extends js.Object {
    def scaleSequential[Output](interpolator: js.Function1[Double, Output]): Sequential.Facade[Output] = js.native
  }

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
    val facade = Facade.scaleSequential[Output](interpolator)
        .domain(domain)
        .clamp(clamped)
    new Sequential(facade)
  }
}
