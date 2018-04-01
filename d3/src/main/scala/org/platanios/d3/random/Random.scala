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

package org.platanios.d3.random

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Generate random numbers from various distributions.
  *
  * @author Emmanouil Antonios Platanios
  */
object Random {
  /** [Uniform distribution](https://en.wikipedia.org/wiki/Uniform_distribution_(continuous)) sampler. */
  val uniform: UniformSource = Facade.randomUniform

  /** [Normal distribution](https://en.wikipedia.org/wiki/Normal_distribution) sampler. */
  val normal: NormalSource = Facade.randomNormal

  /** [Log-Normal distribution](https://en.wikipedia.org/wiki/Log-normal_distribution) sampler. */
  val logNormal: LogNormalSource = Facade.randomLogNormal

  /** [Bates distribution](https://en.wikipedia.org/wiki/Bates_distribution) sampler. */
  val bates: BatesSource = Facade.randomBates

  /** [Irwin-Hall distribution](https://en.wikipedia.org/wiki/Irwin–Hall_distribution) sampler. */
  val irwinHall: IrwinHallSource = Facade.randomIrwinHall

  /** [Exponential distribution](https://en.wikipedia.org/wiki/Exponential_distribution) sampler. */
  val exponential: ExponentialSource = Facade.randomExponential

  @js.native trait Source[S <: Source[S]] extends js.Object {
    /** Returns the same type of function for generating random numbers but where the given random number generator
      * source is used as the source of randomness instead of `Math.random`. The given random number generator must
      * implement the same interface as `Math.random` and only return values in the range `[0, 1)`. This is useful when
      * a seeded random number generator is preferable to `Math.random`.
      *
      * For example:
      * {{{
      *   val random = d3.random.normal.source(seedrandom("a22ebc7c488a3a47"))(0, 1)
      *   random() // 0.9744193494813501
      * }}}
      *
      * @param  source Random number generator (i.e., source) to use.
      * @return The same random samples source, with a modified random number generator.
      */
    def source(source: js.Function0[Double]): S = js.native
  }

  @js.native trait UniformSource extends Source[UniformSource] {
    /** Returns a function for generating random numbers with a uniform distribution. The minimum allowed value of a
      * returned number is `min`, and the maximum is `max`.
      *
      * For example:
      * {{{
      *   d3.random.uniform(6)()    // Returns a number greater than or equal to 0 and less than 6.
      *   d3.random.uniform(1, 5)() // Returns a number greater than or equal to 1 and less than 5.
      * }}}
      *
      * @param  min Minimum value in the uniform interval.
      * @param  max Maximum value in the uniform interval.
      * @return Random sample generator.
      */
    def apply(min: Double = 0.0, max: Double = 1.0): js.Function0[Double] = js.native
  }

  @js.native trait NormalSource extends Source[NormalSource] {
    /** Returns a function for generating random numbers with a normal (Gaussian) distribution. The expected value of
      * the generated numbers is `mu`, with the given standard deviation `sigma`.
      *
      * @param  mu    Normal distribution mean.
      * @param  sigma Normal distribution standard deviation.
      * @return Random sample generator.
      */
    def apply(mu: Double = 0.0, sigma: Double = 1.0): js.Function0[Double] = js.native
  }

  @js.native trait LogNormalSource extends Source[LogNormalSource] {
    /** Returns a function for generating random numbers with a normal (Gaussian) distribution. The expected value of
      * the random variable's natural logarithm is `mu`, with the given standard deviation `sigma`.
      *
      * @param  mu    Log-normal distribution mean.
      * @param  sigma Log-normal distribution standard deviation.
      * @return Random sample generator.
      */
    def apply(mu: Double = 0.0, sigma: Double = 1.0): js.Function0[Double] = js.native
  }

  @js.native trait BatesSource extends Source[BatesSource] {
    /** Returns a function for generating random numbers with a Bates distribution with `n` independent variables.
      *
      * @param  n Number of independent variables.
      * @return Random sample generator.
      */
    def apply(n: Int): js.Function0[Double] = js.native
  }

  @js.native trait IrwinHallSource extends Source[IrwinHallSource] {
    /** Returns a function for generating random numbers with an Irwin-Hall distribution with `n` independent variables.
      *
      * @param  n Number of independent variables.
      * @return Random sample generator.
      */
    def apply(n: Int): js.Function0[Double] = js.native
  }

  @js.native trait ExponentialSource extends Source[ExponentialSource] {
    /** Returns a function for generating random numbers with an exponential distribution with rate `lambda`. This is
      * equivalent to the time between events in a
      * [Poisson process](https://en.wikipedia.org/wiki/Poisson_point_process) with a mean of `1 / lambda`. For example,
      * `exponential(1/40)` generates random times between events where, on average, one event occurs every `40` units
      * of time.
      *
      * @param  lambda Rate of the exponential distribution.
      * @return Random sample generator.
      */
    def apply(lambda: Double): js.Function0[Double] = js.native
  }

  @JSImport("d3-random", JSImport.Namespace)
  @js.native private[Random] object Facade extends js.Object {
    val randomUniform    : UniformSource     = js.native
    val randomNormal     : NormalSource      = js.native
    val randomLogNormal  : LogNormalSource   = js.native
    val randomBates      : BatesSource       = js.native
    val randomIrwinHall  : IrwinHallSource   = js.native
    val randomExponential: ExponentialSource = js.native
  }
}
