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

package org.platanios.d3.color

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** This module provides categorical, sequential, and diverging color schemes designed to work with `d3.scale`'s
  * `d3.scale.ordinal` and `d3.scale.sequential`. Most of these schemes are derived from Cynthia A. Brewer's
  * [ColorBrewer](http://colorbrewer2.org/). Since ColorBrewer publishes only discrete color schemes, the sequential
  * and diverging scales are interpolated using
  * [uniform B-splines](https://bl.ocks.org/mbostock/048d21cf747371b11884f75ad896e5a5).
  *
  * For example, to create a categorical color scale using the "Accent" color scheme:
  * {{{
  *   val accent = d3.scale.ordinal(d3.color.scheme.accent)
  * }}}
  *
  * To create a sequential discrete nine-color scale using the "Blues" color scheme:
  * {{{
  *   val blues = d3.scale.ordinal(d3.color.scheme.discreteBlues(9))
  * }}}
  *
  * To create a diverging, continuous color scale using the "PiYG" color scheme:
  * {{{
  *   val piyg = d3.scale.sequential(d3.color.scheme.piYg)
  * }}}
  *
  * === Notes ===
  *
  * Diverging color schemes are available as continuous interpolators (often used with `d3.scale.sequential`) and as
  * discrete schemes (often used with `d3.scale.ordinal`). Each discrete scheme, such as
  * `d3.color.scheme.discreteBrBG`, is represented as an array of arrays of hexadecimal color strings. The `k`-th
  * element of that array contains the color scheme of size `k`; for example, `d3.color.scheme.discreteBrBG(9)` contains
  * an array of nine strings representing the nine colors of the brown-blue-green diverging color scheme. Diverging
  * color schemes support a size `k` ranging from `3` to `11`.
  * 
  * Sequential, single-hue color schemes are available as continuous interpolators (often used with
  * `d3.scale.sequential`) and as discrete schemes (often used with `d3.scale.ordinal`). Each discrete scheme, such as
  * `d3.color.scheme.discreteBlues`, is represented as an array of arrays of hexadecimal color strings. The `k`-th
  * element of this array contains the color scheme of size `k`.. For example, `d3.color.scheme.Blues(9)` contains an
  * array of nine strings representing the nine colors of the blue sequential color scheme. Sequential, single-hue color
  * schemes support a size `k` ranging from `3` to `9`.
  *
  * Sequential, multi-hue color schemes are available as continuous interpolators (often used with
  * `d3.scale.sequential`) and as discrete schemes (often used with `d3.scale.ordinal`). Each discrete scheme, such as
  * `d3.color.scheme.discreteBuGn`, is represented as an array of arrays of hexadecimal color strings. The `k`-th
  * element of this array contains the color scheme of size `k`.. For example, `d3.color.scheme.buGn(9)` contains an
  * array of nine strings representing the nine colors of the blue sequential color scheme. Sequential, single-hue color
  * schemes support a size `k` ranging from `3` to `9`.
  *
  * @author Emmanouil Antonios Platanios
  */
object Scheme {
  //region Categorical

  /** Array of ten categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/category10.png" width="100%" height="40" alt="category10" style="max-width:100%;"/>
    */
  val category10: Seq[String] = Facade.schemeCategory10

  /** Array of eight categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Accent.png" width="100%" height="40" alt="Accent" style="max-width:100%;"/>
    */
  val accent: Seq[String] = Facade.schemeAccent

  /** Array of eight categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Dark2.png" width="100%" height="40" alt="Dark2" style="max-width:100%;"/>
    */
  val dark2: Seq[String] = Facade.schemeDark2

  /** Array of twelve categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Paired.png" width="100%" height="40" alt="Paired" style="max-width:100%;"/>
    */
  val paired: Seq[String] = Facade.schemePaired

  /** Array of nine categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Pastel1.png" width="100%" height="40" alt="Pastel1" style="max-width:100%;"/>
    */
  val pastel1: Seq[String] = Facade.schemePastel1

  /** Array of eight categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Pastel2.png" width="100%" height="40" alt="Pastel2" style="max-width:100%;"/>
    */
  val pastel2: Seq[String] = Facade.schemePastel2

  /** Array of nine categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Set1.png" width="100%" height="40" alt="Set1" style="max-width:100%;"/>
    */
  val set1: Seq[String] = Facade.schemeSet1

  /** Array of eight categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Set2.png" width="100%" height="40" alt="Set2" style="max-width:100%;"/>
    */
  val set2: Seq[String] = Facade.schemeSet2

  /** Array of twelve categorical colors represented as RGB hexadecimal strings.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Set3.png" width="100%" height="40" alt="Set3" style="max-width:100%;"/>
    */
  val set3: Seq[String] = Facade.schemeSet3

  //endregion Categorical

  //region Diverging

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "BrBG" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/BrBG.png" width="100%" height="40" alt="BrBG" style="max-width:100%;"/>
    */
  def brBG(t: Double): String = Facade.interpolateBrBG(t)

  /** Discrete "BrBG" diverging color scheme. */
  val discreteBrBG: Seq[Seq[String]] = Facade.schemeBrBG.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "PRGn" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/PRGn.png" width="100%" height="40" alt="PRGn" style="max-width:100%;"/>
    */
  def pRGn(t: Double): String = Facade.interpolatePRGn(t)

  /** Discrete "PRGn" diverging color scheme. */
  val discretePRGn: Seq[Seq[String]] = Facade.schemePRGn.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "PiYG" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/PiYG.png" width="100%" height="40" alt="PiYG" style="max-width:100%;"/>
    */
  def piYG(t: Double): String = Facade.interpolatePiYG(t)

  /** Discrete "PiYG" diverging color scheme. */
  val discretePiYG: Seq[Seq[String]] = Facade.schemePiYG.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "PuOr" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/PuOr.png" width="100%" height="40" alt="PuOr" style="max-width:100%;"/>
    */
  def puOr(t: Double): String = Facade.interpolatePuOr(t)

  /** Discrete "PuOr" diverging color scheme. */
  val discretePuOr: Seq[Seq[String]] = Facade.schemePuOr.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "RdBu" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/RdBu.png" width="100%" height="40" alt="RdBu" style="max-width:100%;"/>
    */
  def rdBu(t: Double): String = Facade.interpolateRdBu(t)

  /** Discrete "RdBu" diverging color scheme. */
  val discreteRdBu: Seq[Seq[String]] = Facade.schemeRdBu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "RdGy" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/RdGy.png" width="100%" height="40" alt="RdGy" style="max-width:100%;"/>
    */
  def rdGy(t: Double): String = Facade.interpolateRdGy(t)

  /** Discrete "RdGy" diverging color scheme. */
  val discreteRdGy: Seq[Seq[String]] = Facade.schemeRdGy.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "RdYlBu" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/RdYlBu.png" width="100%" height="40" alt="RdYlBu" style="max-width:100%;"/>
    */
  def rdYlBu(t: Double): String = Facade.interpolateRdYlBu(t)

  /** Discrete "RdYlBu" diverging color scheme. */
  val discreteRdYlBu: Seq[Seq[String]] = Facade.schemeRdYlBu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "RdYlGn" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/RdYlGn.png" width="100%" height="40" alt="RdYlGn" style="max-width:100%;"/>
    */
  def rdYlGn(t: Double): String = Facade.interpolateRdYlGn(t)

  /** Discrete "RdYlGn" diverging color scheme. */
  val discreteRdYlGn: Seq[Seq[String]] = Facade.schemeRdYlGn.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Spectral" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Spectral.png" width="100%" height="40" alt="Spectral" style="max-width:100%;"/>
    */
  def spectral(t: Double): String = Facade.interpolateSpectral(t)

  /** Discrete "Spectral" diverging color scheme. */
  val discreteSpectral: Seq[Seq[String]] = Facade.schemeSpectral.map(_.toSeq).toSeq

  //endregion Diverging

  //region Sequential (Single Hue)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Blues" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Blues.png" width="100%" height="40" alt="Blues" style="max-width:100%;"/>
    */
  def blues(t: Double): String = Facade.interpolateBlues(t)

  /** Discrete "Blues" diverging color scheme. */
  val discreteBlues: Seq[Seq[String]] = Facade.schemeBlues.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Greens" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Greens.png" width="100%" height="40" alt="Greens" style="max-width:100%;"/>
    */
  def greens(t: Double): String = Facade.interpolateGreens(t)

  /** Discrete "Greens" diverging color scheme. */
  val discreteGreens: Seq[Seq[String]] = Facade.schemeGreens.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Greys" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Greys.png" width="100%" height="40" alt="Greys" style="max-width:100%;"/>
    */
  def greys(t: Double): String = Facade.interpolateGreys(t)

  /** Discrete "Greys" diverging color scheme. */
  val discreteGreys: Seq[Seq[String]] = Facade.schemeGreys.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Oranges" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Oranges.png" width="100%" height="40" alt="Oranges" style="max-width:100%;"/>
    */
  def oranges(t: Double): String = Facade.interpolateOranges(t)

  /** Discrete "Oranges" diverging color scheme. */
  val discreteOranges: Seq[Seq[String]] = Facade.schemeOranges.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Oranges" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Purples.png" width="100%" height="40" alt="Purples" style="max-width:100%;"/>
    */
  def purples(t: Double): String = Facade.interpolatePurples(t)

  /** Discrete "Purples" diverging color scheme. */
  val discretePurples: Seq[Seq[String]] = Facade.schemePurples.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "Oranges" diverging color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/Reds.png" width="100%" height="40" alt="Reds" style="max-width:100%;"/>
    */
  def reds(t: Double): String = Facade.interpolateReds(t)

  /** Discrete "Reds" diverging color scheme. */
  val discreteReds: Seq[Seq[String]] = Facade.schemeReds.map(_.toSeq).toSeq

  //endregion Sequential (Single Hue)

  //region Sequential (Multi-Hue)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "viridis" perceptually-uniform
    * color scheme designed by [van der Walt, Smith, and Firing](https://bids.github.io/colormap/) for matplotlib,
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/viridis.png" width="100%" height="40" alt="viridis" style="max-width:100%;"/>
    */
  def viridis(t: Double): String = Facade.interpolateViridis(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "inferno" perceptually-uniform
    * color scheme designed by [van der Walt, Smith, and Firing](https://bids.github.io/colormap/) for matplotlib,
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/inferno.png" width="100%" height="40" alt="inferno" style="max-width:100%;"/>
    */
  def inferno(t: Double): String = Facade.interpolateInferno(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "magma" perceptually-uniform
    * color scheme designed by [van der Walt, Smith, and Firing](https://bids.github.io/colormap/) for matplotlib,
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/magma.png" width="100%" height="40" alt="magma" style="max-width:100%;"/>
    */
  def magma(t: Double): String = Facade.interpolateMagma(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "plasma" perceptually-uniform
    * color scheme designed by [van der Walt, Smith, and Firing](https://bids.github.io/colormap/) for matplotlib,
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/plasma.png" width="100%" height="40" alt="plasma" style="max-width:100%;"/>
    */
  def plasma(t: Double): String = Facade.interpolatePlasma(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from a 180° rotation of
    * [Niccoli's perceptual rainbow](https://mycarta.wordpress.com/2013/02/21/perceptual-rainbow-palette-the-method/),
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/warm.png" width="100%" height="40" alt="warm" style="max-width:100%;"/>
    */
  def warm(t: Double): String = Facade.interpolateWarm(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from
    * [Niccoli's perceptual rainbow](https://mycarta.wordpress.com/2013/02/21/perceptual-rainbow-palette-the-method/),
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/cool.png" width="100%" height="40" alt="cool" style="max-width:100%;"/>
    */
  def cool(t: Double): String = Facade.interpolateCool(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from
    * [Green’s default Cubehelix](https://www.mrao.cam.ac.uk/%7Edag/CUBEHELIX/) represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/cubehelix.png" width="100%" height="40" alt="cubehelix" style="max-width:100%;"/>
    */
  def cubehelix(t: Double): String = Facade.interpolateCubehelixDefault(t)

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "BuGn" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/BuGn.png" width="100%" height="40" alt="BuGn" style="max-width:100%;">
    */
  def buGn(t: Double): String = Facade.interpolateBuGn(t)

  /** Discrete "BuGn" diverging color scheme. */
  val discreteBuGn: Seq[Seq[String]] = Facade.schemeBuGn.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "BuPu" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/BuPu.png" width="100%" height="40" alt="BuPu" style="max-width:100%;"/>
    */
  def buPu(t: Double): String = Facade.interpolateBuPu(t)

  /** Discrete "BuPu" diverging color scheme. */
  val discreteBuPu: Seq[Seq[String]] = Facade.schemeBuPu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "GnBu" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/GnBu.png" width="100%" height="40" alt="GnBu" style="max-width:100%;"/>
    */
  def gnBu(t: Double): String = Facade.interpolateGnBu(t)

  /** Discrete "GnBu" diverging color scheme. */
  val discreteGnBu: Seq[Seq[String]] = Facade.schemeGnBu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "OrRd" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/OrRd.png" width="100%" height="40" alt="OrRd" style="max-width:100%;"/>
    */
  def orRd(t: Double): String = Facade.interpolateOrRd(t)

  /** Discrete "OrRd" diverging color scheme. */
  val discreteOrRd: Seq[Seq[String]] = Facade.schemeOrRd.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "PuBuGn" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/PuBuGn.png" width="100%" height="40" alt="PuBuGn" style="max-width:100%;"/>
    */
  def puBuGn(t: Double): String = Facade.interpolatePuBuGn(t)

  /** Discrete "PuBuGn" diverging color scheme. */
  val discretePuBuGn: Seq[Seq[String]] = Facade.schemePuBuGn.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "PuBu" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/PuBu.png" width="100%" height="40" alt="PuBu" style="max-width:100%;"/>
    */
  def puBu(t: Double): String = Facade.interpolatePuBu(t)

  /** Discrete "PuBu" diverging color scheme. */
  val discretePuBu: Seq[Seq[String]] = Facade.schemePuBu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "PuRd" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/PuRd.png" width="100%" height="40" alt="PuRd" style="max-width:100%;"/>
    */
  def puRd(t: Double): String = Facade.interpolatePuRd(t)

  /** Discrete "PuRd" diverging color scheme. */
  val discretePuRd: Seq[Seq[String]] = Facade.schemePuRd.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "RdPu" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/RdPu.png" width="100%" height="40" alt="RdPu" style="max-width:100%;"/>
    */
  def rdPu(t: Double): String = Facade.interpolateRdPu(t)

  /** Discrete "RdPu" diverging color scheme. */
  val discreteRdPu: Seq[Seq[String]] = Facade.schemeRdPu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "YlGnBu" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/YlGnBu.png" width="100%" height="40" alt="YlGnBu" style="max-width:100%;"/>
    */
  def ylGnBu(t: Double): String = Facade.interpolateYlGnBu(t)

  /** Discrete "YlGnBu" diverging color scheme. */
  val discreteYlGnBu: Seq[Seq[String]] = Facade.schemeYlGnBu.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "YlGn" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/YlGn.png" width="100%" height="40" alt="YlGn" style="max-width:100%;"/>
    */
  def ylGn(t: Double): String = Facade.interpolateYlGn(t)

  /** Discrete "YlGn" diverging color scheme. */
  val discreteYlGn: Seq[Seq[String]] = Facade.schemeYlGn.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "YlOrBr" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/YlOrBr.png" width="100%" height="40" alt="YlOrBr" style="max-width:100%;"/>
    */
  def ylOrBr(t: Double): String = Facade.interpolateYlOrBr(t)

  /** Discrete "YlGn" diverging color scheme. */
  val discreteYlOrBr: Seq[Seq[String]] = Facade.schemeYlOrBr.map(_.toSeq).toSeq

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from the "YlOrRd" sequential color scheme
    * represented as an RGB string.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/YlOrRd.png" width="100%" height="40" alt="YlOrRd" style="max-width:100%;"/>
    */
  def ylOrRd(t: Double): String = Facade.interpolateYlOrRd(t)

  /** Discrete "YlOrRd" diverging color scheme. */
  val discreteYlOrRd: Seq[Seq[String]] = Facade.schemeYlOrRd.map(_.toSeq).toSeq
  
  //endregion Sequential (Multi-Hue)

  //region Cyclical

  /** Given a number `t` in the range `[0, 1]`, returns the corresponding color from `d3.color.scheme.warm` scale from
    * `[0.0, 0.5]` followed by the `d3.color.scheme.cool` scale from `[0.5, 1.0]`, thus implementing the cyclical
    * less-angry rainbow color scheme.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-scale-chromatic/master/img/rainbow.png" width="100%" height="40" alt="rainbow" style="max-width:100%;"/>
    */
  def rainbow(t: Double): String = Facade.interpolateRainbow(t)

  //endregion Cyclical

  @JSImport("d3-scale-chromatic", JSImport.Namespace)
  @js.native private[Scheme] object Facade extends js.Object {
    val schemeCategory10: js.Array[String] = js.native
    val schemeAccent    : js.Array[String] = js.native
    val schemeDark2     : js.Array[String] = js.native
    val schemePaired    : js.Array[String] = js.native
    val schemePastel1   : js.Array[String] = js.native
    val schemePastel2   : js.Array[String] = js.native
    val schemeSet1      : js.Array[String] = js.native
    val schemeSet2      : js.Array[String] = js.native
    val schemeSet3      : js.Array[String] = js.native

    def interpolateBrBG(t: Double): String = js.native
    def interpolatePRGn(t: Double): String = js.native
    def interpolatePiYG(t: Double): String = js.native
    def interpolatePuOr(t: Double): String = js.native
    def interpolateRdBu(t: Double): String = js.native
    def interpolateRdGy(t: Double): String = js.native
    def interpolateRdYlBu(t: Double): String = js.native
    def interpolateRdYlGn(t: Double): String = js.native
    def interpolateSpectral(t: Double): String = js.native

    val schemeBrBG    : js.Array[js.Array[String]] = js.native
    val schemePRGn    : js.Array[js.Array[String]] = js.native
    val schemePiYG    : js.Array[js.Array[String]] = js.native
    val schemePuOr    : js.Array[js.Array[String]] = js.native
    val schemeRdBu    : js.Array[js.Array[String]] = js.native
    val schemeRdGy    : js.Array[js.Array[String]] = js.native
    val schemeRdYlBu  : js.Array[js.Array[String]] = js.native
    val schemeRdYlGn  : js.Array[js.Array[String]] = js.native
    val schemeSpectral: js.Array[js.Array[String]] = js.native

    def interpolateBlues(t: Double): String = js.native
    def interpolateGreens(t: Double): String = js.native
    def interpolateGreys(t: Double): String = js.native
    def interpolateOranges(t: Double): String = js.native
    def interpolatePurples(t: Double): String = js.native
    def interpolateReds(t: Double): String = js.native

    val schemeBlues  : js.Array[js.Array[String]] = js.native
    val schemeGreens : js.Array[js.Array[String]] = js.native
    val schemeGreys  : js.Array[js.Array[String]] = js.native
    val schemeOranges: js.Array[js.Array[String]] = js.native
    val schemePurples: js.Array[js.Array[String]] = js.native
    val schemeReds   : js.Array[js.Array[String]] = js.native

    def interpolateViridis(t: Double): String = js.native
    def interpolateInferno(t: Double): String = js.native
    def interpolateMagma(t: Double): String = js.native
    def interpolatePlasma(t: Double): String = js.native
    def interpolateWarm(t: Double): String = js.native
    def interpolateCool(t: Double): String = js.native
    def interpolateCubehelixDefault(t: Double): String = js.native
    def interpolateBuGn(t: Double): String = js.native
    def interpolateBuPu(t: Double): String = js.native
    def interpolateGnBu(t: Double): String = js.native
    def interpolateOrRd(t: Double): String = js.native
    def interpolatePuBuGn(t: Double): String = js.native
    def interpolatePuBu(t: Double): String = js.native
    def interpolatePuRd(t: Double): String = js.native
    def interpolateRdPu(t: Double): String = js.native
    def interpolateYlGnBu(t: Double): String = js.native
    def interpolateYlGn(t: Double): String = js.native
    def interpolateYlOrBr(t: Double): String = js.native
    def interpolateYlOrRd(t: Double): String = js.native

    val schemeBuGn: js.Array[js.Array[String]] = js.native
    val schemeBuPu: js.Array[js.Array[String]] = js.native
    val schemeGnBu: js.Array[js.Array[String]] = js.native
    val schemeOrRd: js.Array[js.Array[String]] = js.native
    val schemePuBuGn: js.Array[js.Array[String]] = js.native
    val schemePuBu: js.Array[js.Array[String]] = js.native
    val schemePuRd: js.Array[js.Array[String]] = js.native
    val schemeRdPu: js.Array[js.Array[String]] = js.native
    val schemeYlGnBu: js.Array[js.Array[String]] = js.native
    val schemeYlGn: js.Array[js.Array[String]] = js.native
    val schemeYlOrBr: js.Array[js.Array[String]] = js.native
    val schemeYlOrRd: js.Array[js.Array[String]] = js.native

    def interpolateRainbow(t: Double): String = js.native
  }
}
