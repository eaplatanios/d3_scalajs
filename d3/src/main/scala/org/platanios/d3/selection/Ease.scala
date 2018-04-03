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

package org.platanios.d3.selection

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Easing is a method of distorting time to control apparent motion in animation. It is most commonly used for
  * [slow-in, slow-out](https://en.wikipedia.org/wiki/12_basic_principles_of_animation#Slow_In_and_Slow_Out). By easing
  * time, animated transitions are smoother and exhibit more plausible motion.
  *
  * The easing types in this module implement the ease method, which takes a normalized time `t` and returns the
  * corresponding "eased" time `t'`. Both the normalized time and the eased time are typically in the range `[0, 1]`,
  * where `0` represents the start of the animation and `1` represents the end. Some easing types, such as elastic, may
  * return eased times slightly outside this range. A good easing type should return `0` if `t = 0` and `1` if `t = 1`.
  * See the [easing explorer](http://bl.ocks.org/mbostock/248bac3b8e354a9103c4) for a visual demonstration.
  *
  * These easing types are largely based on work by [Robert Penner](http://robertpenner.com/easing/).
  *
  * @author Emmanouil Antonios Platanios
  */
object Ease {
  /** Linear easing (i.e., the identity function).
    *
    * `linear(t) = t`
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/linear.png" alt="linear" width="100%" height="240" style="max-width:100%;"/>
    */
  val linear: EaseFactory = Facade.easeLinear

  /** Polynomial easing. Raises `t` to the specified exponent.
    *
    * If the exponent is not specified, it defaults to `3`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/polyIn.png" alt="polyIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val polyIn: PolynomialEaseFactory = Facade.easePolyIn

  /** Reverse polynomial easing. Equivalent to `1 - polyIn(1 - t)`.
    *
    * If the exponent is not specified, it defaults to `3`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/polyOut.png" alt="polyOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val polyOut: PolynomialEaseFactory = Facade.easePolyOut

  /** Symmetric polynomial easing. Equivalent to `polyIn` for `t` in `[0, 0.5]` and to `polyOut` for `t` in `[0.5, 1]`.
    *
    * If the exponent is not specified, it defaults to `3`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/polyInOut.png" alt="polyInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val polyInOut: PolynomialEaseFactory = Facade.easePolyInOut

  /** Symmetric polynomial easing. Equivalent to `polyIn` for `t` in `[0, 0.5]` and to `polyOut` for `t` in `[0.5, 1]`.
    *
    * If the exponent is not specified, it defaults to `3`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/polyInOut.png" alt="polyInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val poly: PolynomialEaseFactory = Facade.easePoly

  /** Quadratic easing. Equivalent to `polyIn(2)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/quadIn.png" alt="quadIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val quadIn: EaseFactory = Facade.easeQuadIn

  /** Reverse quadratic easing. Equivalent to `1 - polyIn(2)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/quadOut.png" alt="quadOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val quadOut: EaseFactory = Facade.easeQuadOut

  /** Symmetric quadratic easing. Equivalent to `quadIn` for `t` in `[0, 0.5]` and to `quadOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/quadInOut.png" alt="quadInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val quadInOut: EaseFactory = Facade.easeQuadInOut

  /** Symmetric quadratic easing. Equivalent to `quadIn` for `t` in `[0, 0.5]` and to `quadOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/quadInOut.png" alt="quadInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val quad: EaseFactory = Facade.easeQuad

  /** Cubic easing. Equivalent to `polyIn(2)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/cubicIn.png" alt="cubicIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val cubicIn: EaseFactory = Facade.easeCubicIn

  /** Reverse cubic easing. Equivalent to `1 - polyIn(2)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/cubicOut.png" alt="cubicOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val cubicOut: EaseFactory = Facade.easeCubicOut

  /** Symmetric cubic easing. Equivalent to `cubicIn` for `t` in `[0, 0.5]` and to `cubicOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/cubicInOut.png" alt="cubicInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val cubicInOut: EaseFactory = Facade.easeCubicInOut

  /** Symmetric cubic easing. Equivalent to `cubicIn` for `t` in `[0, 0.5]` and to `cubicOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/cubicInOut.png" alt="cubicInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val cubic: EaseFactory = Facade.easeCubic

  /** Sinusoidal easing. Returns `sin(t)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/sinIn.png" alt="sinIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val sinIn: EaseFactory = Facade.easeSinIn

  /** Reverse sinusoidal easing. Equivalent to `1 - sinIn(1 - t)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/sinOut.png" alt="sinOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val sinOut: EaseFactory = Facade.easeSinOut

  /** Symmetric sinusoidal easing. Equivalent to `sinIn` for `t` in `[0, 0.5]` and to `sinOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/sinInOut.png" alt="sinInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val sinInOut: EaseFactory = Facade.easeSinInOut

  /** Symmetric sinusoidal easing. Equivalent to `sinIn` for `t` in `[0, 0.5]` and to `sinOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/sinInOut.png" alt="sinInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val sin: EaseFactory = Facade.easeSin

  /** Exponential easing. Returns `2 ^ (10 * (t - 1))`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/expIn.png" alt="expIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val expIn: EaseFactory = Facade.easeExpIn

  /** Reverse exponential easing. Equivalent to `1 - expIn(1 - t)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/expOut.png" alt="expOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val expOut: EaseFactory = Facade.easeExpOut

  /** Symmetric exponential easing. Equivalent to `expIn` for `t` in `[0, 0.5]` and to `expOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/expInOut.png" alt="expInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val expInOut: EaseFactory = Facade.easeExpInOut

  /** Symmetric exponential easing. Equivalent to `expIn` for `t` in `[0, 0.5]` and to `expOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/expInOut.png" alt="expInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val exp: EaseFactory = Facade.easeExp

  /** Circular easing.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/circleIn.png" alt="circleIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val circleIn: EaseFactory = Facade.easeCircleIn

  /** Reverse circular easing. Equivalent to `1 - circleIn(1 - t)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/circleOut.png" alt="circleOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val circleOut: EaseFactory = Facade.easeCircleOut

  /** Symmetric circular easing. Equivalent to `circleIn` for `t` in `[0, 0.5]` and to `circleOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/circleInOut.png" alt="circleInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val circleInOut: EaseFactory = Facade.easeCircleInOut

  /** Symmetric circular easing. Equivalent to `circleIn` for `t` in `[0, 0.5]` and to `circleOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/circleInOut.png" alt="circleInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val circle: EaseFactory = Facade.easeCircle

  /** Elastic easing, like a rubber band.
    *
    * The amplitude and period of the oscillation are configurable. If not specified, they default to `1` and `0.3`,
    * respectively.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/elasticIn.png" alt="elasticIn" width="100%" height="360" style="max-width:100%;"/>
    */
  val elasticIn: ElasticEaseFactory = Facade.easeElasticIn

  /** Reverse elastic easing, like a rubber band. Equivalent to `1 - elasticIn(1 - t)`.
    *
    * The amplitude and period of the oscillation are configurable. If not specified, they default to `1` and `0.3`,
    * respectively.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/elasticOut.png" alt="elasticOut" width="100%" height="360" style="max-width:100%;"/>
    */
  val elasticOut: ElasticEaseFactory = Facade.easeElasticOut

  /** Symmetric elastic easing. Equivalent to `elasticIn` for `t` in `[0, 0.5]` and to `elasticOut` for `t` in
    * `[0.5, 1]`.
    *
    * The amplitude and period of the oscillation are configurable. If not specified, they default to `1` and `0.3`,
    * respectively.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/elasticInOut.png" alt="elasticInOut" width="100%" height="360" style="max-width:100%;"/>
    */
  val elasticInOut: ElasticEaseFactory = Facade.easeElasticInOut

  /** Symmetric elastic easing. Equivalent to `elasticIn` for `t` in `[0, 0.5]` and to `elasticOut` for `t` in
    * `[0.5, 1]`.
    *
    * The amplitude and period of the oscillation are configurable. If not specified, they default to `1` and `0.3`,
    * respectively.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/elasticInOut.png" alt="elasticInOut" width="100%" height="360" style="max-width:100%;"/>
    */
  val elastic: ElasticEaseFactory = Facade.easeElastic

  /** [Anticipatory easing](https://en.wikipedia.org/wiki/12_basic_principles_of_animation#Anticipation), like a dancer
    * bending his knees before jumping off the floor.
    *
    * The degree of overshoot is configurable. If not specified, it defaults to `1.70158`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/backIn.png" alt="backIn" width="100%" height="300" style="max-width:100%;"/>
    */
  val backIn: BackEaseFactory = Facade.easeBackIn

  /** Reverse [anticipatory easing](https://en.wikipedia.org/wiki/12_basic_principles_of_animation#Anticipation).
    * Equivalent to `1 - backIn(1 - t)`.
    *
    * The degree of overshoot is configurable. If not specified, it defaults to `1.70158`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/backOut.png" alt="backOut" width="100%" height="300" style="max-width:100%;"/>
    */
  val backOut: BackEaseFactory = Facade.easeBackOut

  /** Symmetric back easing. Equivalent to `backIn` for `t` in `[0, 0.5]` and to `backOut` for `t` in `[0.5, 1]`.
    *
    * The degree of overshoot is configurable. If not specified, it defaults to `1.70158`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/backInOut.png" alt="backInOut" width="100%" height="300" style="max-width:100%;"/>
    */
  val backInOut: BackEaseFactory = Facade.easeBackInOut

  /** Symmetric back easing. Equivalent to `backIn` for `t` in `[0, 0.5]` and to `backOut` for `t` in `[0.5, 1]`.
    *
    * The degree of overshoot is configurable. If not specified, it defaults to `1.70158`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/backInOut.png" alt="backInOut" width="100%" height="300" style="max-width:100%;"/>
    */
  val back: BackEaseFactory = Facade.easeBack

  /** Bounce easing, like a rubber ball.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/bounceIn.png" alt="bounceIn" width="100%" height="240" style="max-width:100%;"/>
    */
  val bounceIn: EaseFactory = Facade.easeBounceIn

  /** Reverse bounce easing. Equivalent to `1 - bounceIn(1 - t)`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/bounceOut.png" alt="bounceOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val bounceOut: EaseFactory = Facade.easeBounceOut

  /** Symmetric bounce easing. Equivalent to `bounceIn` for `t` in `[0, 0.5]` and to `bounceOut` for `t` in `[0.5, 1]`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-ease/master/img/bounceInOut.png" alt="bounceInOut" width="100%" height="240" style="max-width:100%;"/>
    */
  val bounceInOut: EaseFactory = Facade.easeBounceInOut

  @js.native trait EaseFactory extends js.Object {
    /** Given the specified normalized time `t`, typically in the range `[0, 1]`, returns the "eased" time `t'`, also
      * typically in `[0, 1]`. `0` represents the start of the animation and `1` represents the end. A good
      * implementation returns `0` if `t = 0` and `1` if `t = 1`.
      * See the [easing explorer](http://bl.ocks.org/mbostock/248bac3b8e354a9103c4) for a visual demonstration.
      *
      * For example, to apply cubic easing:
      * {{{
      *   val te = d3.ease.cubic(t)
      * }}}
      *
      * Similarly, to apply custom elastic easing:
      * {{{
      *   // Before the animation starts, create your easing function.
      *   val customElastic = d3.ease.elastic.period(0.4)
      *
      *   // During the animation, apply the easing function.
      *   val te = customElastic(t)
      * }}}
      *
      * @param  normalizedTime Normalized time, typically in the range `[0, 1]`.
      * @return "Eased" time, typically in the range `[0, 1]`.
      */
    def apply(normalizedTime: Double): Double = js.native
  }

  @js.native trait PolynomialEaseFactory extends EaseFactory {
    /** Returns a new polynomial easing with the specified exponent `e`. For example, to create equivalents of
      * `linear`, `quad`, and `cubic`:
      *
      * {{{
      *   val linear = d3.ease.poly.exponent(1)
      *   val quad = d3.ease.poly.exponent(2)
      *   val cubic = d3.ease.poly.exponent(3)
      * }}}
      *
      * @param  e Exponent value.
      * @return New polynomial ease factory.
      */
    def exponent(e: Double): PolynomialEaseFactory = js.native
  }

  @js.native trait ElasticEaseFactory extends EaseFactory {
    /** Returns a new elastic ease factory with the specified amplitude. */
    def amplitude(a: Double): ElasticEaseFactory = js.native

    /** Returns a new elastic ease factory with the specified period. */
    def period(p: Double): ElasticEaseFactory = js.native
  }

  @js.native trait BackEaseFactory extends EaseFactory {
    /** Returns a new back ease factory with the specified overshoot. */
    def overshoot(s: Double): BackEaseFactory = js.native
  }

  @JSImport("d3-ease", JSImport.Namespace)
  @js.native private[Ease] object Facade extends js.Object {
    val easeLinear      : EaseFactory           = js.native
    val easePolyIn      : PolynomialEaseFactory = js.native
    val easePolyOut     : PolynomialEaseFactory = js.native
    val easePolyInOut   : PolynomialEaseFactory = js.native
    val easePoly        : PolynomialEaseFactory = js.native
    val easeQuadIn      : EaseFactory           = js.native
    val easeQuadOut     : EaseFactory           = js.native
    val easeQuadInOut   : EaseFactory           = js.native
    val easeQuad        : EaseFactory           = js.native
    val easeCubicIn     : EaseFactory           = js.native
    val easeCubicOut    : EaseFactory           = js.native
    val easeCubicInOut  : EaseFactory           = js.native
    val easeCubic       : EaseFactory           = js.native
    val easeSinIn       : EaseFactory           = js.native
    val easeSinOut      : EaseFactory           = js.native
    val easeSinInOut    : EaseFactory           = js.native
    val easeSin         : EaseFactory           = js.native
    val easeExpIn       : EaseFactory           = js.native
    val easeExpOut      : EaseFactory           = js.native
    val easeExpInOut    : EaseFactory           = js.native
    val easeExp         : EaseFactory           = js.native
    val easeCircleIn    : EaseFactory           = js.native
    val easeCircleOut   : EaseFactory           = js.native
    val easeCircleInOut : EaseFactory           = js.native
    val easeCircle      : EaseFactory           = js.native
    val easeElasticIn   : ElasticEaseFactory    = js.native
    val easeElasticOut  : ElasticEaseFactory    = js.native
    val easeElasticInOut: ElasticEaseFactory    = js.native
    val easeElastic     : ElasticEaseFactory    = js.native
    val easeBackIn      : BackEaseFactory       = js.native
    val easeBackOut     : BackEaseFactory       = js.native
    val easeBackInOut   : BackEaseFactory       = js.native
    val easeBack        : BackEaseFactory       = js.native
    val easeBounceIn    : EaseFactory           = js.native
    val easeBounceOut   : EaseFactory           = js.native
    val easeBounceInOut : EaseFactory           = js.native
    val easeBounce      : EaseFactory           = js.native
  }
}
