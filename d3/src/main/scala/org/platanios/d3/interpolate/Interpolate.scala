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

package org.platanios.d3.interpolate

import org.platanios.d3.JsNumber
import org.platanios.d3.color.Color
import org.platanios.d3.zoom.ZoomView

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/** This module provides a variety of interpolation methods for blending between two values. Values may be numbers,
  * colors, strings, arrays, or even deeply-nested objects. For example:
  * {{{
  *   val i = d3.interpolate.number(10, 20)
  *   i(0.0) // 10
  *   i(0.2) // 12
  *   i(0.5) // 15
  *   i(1.0) // 20
  * }}}
  * The returned function `i` is called an interpolator. Given a starting value `a` and an ending value `b`, it takes a
  * parameter `t` in the domain `[0, 1]` and returns the corresponding interpolated value between `a` and `b`. An
  * interpolator typically returns a value equivalent to `a` at `t = 0` and a value equivalent to `b` at `t = 1`.
  *
  * You can interpolate more than just numbers. To find the perceptual midpoint between `"steelblue"` and `"brown"`:
  * {{{
  *   d3.interpolate.lab("steelblue", "brown")(0.5) // "rgb(142, 92, 109)"
  * }}}
  *
  * Here's a more elaborate example demonstrating type inference used by interpolate:
  * {{{
  *   val i = d3.interpolate(js.Dictionary(
  *     "colors" -> js.Array("red", "blue"),
  *     "colors" -> js.Array("white", "black")))
  *   i(0.0) // {colors: ["rgb(255, 0, 0)", "rgb(0, 0, 255)"]}
  *   i(0.5) // {colors: ["rgb(255, 128, 128)", "rgb(0, 0, 128)"]}
  *   i(1.0) // {colors: ["rgb(255, 255, 255)", "rgb(0, 0, 0)"]}
  * }}}
  *
  * Note that the generic value interpolator detects not only nested objects and arrays, but also color strings and
  * numbers embedded in strings!
  *
  * @author Emmanouil Antonios Platanios
  */
object Interpolate {
  trait InterpolatorFactory[T, R] {
    def apply(a: T, b: T): Interpolator[R]
  }

  @js.native trait Interpolator[R] extends js.Function1[Double, R] {
    def apply(t: Double): R = js.native
  }

  @js.native trait CopyInterpolator[R] extends Interpolator[R]

  @js.native trait ZoomInterpolator extends CopyInterpolator[ZoomView] {
    /** Recommended duration of the zoom transition in ms. */
    val duration: Double = js.native
  }

  @js.native trait ColorGammaInterpolatorFactory extends js.Function {
    /** Returns a new interpolator factory of the same type using the specified gamma value. For example, to interpolate
      * from purple to orange with a gamma of `2.2` in RGB space:
      * {{{
      *   val interpolator = d3.interpolate.rgb.gamma(2.2)("purple", "orange")
      * }}}
      * See Eric Brasseur's article,
      * [Gamma error in picture scaling](https://web.archive.org/web/20160112115812/http://www.4p8.com/eric.brasseur/gamma.html),
      * for more on gamma correction.
      *
      * @param  gamma Gamma value to use.
      * @return Interpolator using the provided value of gamma.
      */
    def gamma(gamma: Double): ColorGammaInterpolatorFactory = js.native

    def apply(a: String | Color, b: String | Color): CopyInterpolator[String] = js.native
  }

  /** Returns an interpolator between the two numbers `a` and `b`. The returned interpolator is equivalent to:
    * {{{
    *   def interpolator(t: Double): Double = a * (1 - t) + b * t
    * }}}
    * '''Caution:''' Avoid interpolating to or from the number zero when the interpolator is used to generate a string.
    * When very small values are converted to strings, they may be converted to scientific notation, which is an invalid
    * attribute or style property value in older browsers. For example, the number `0.0000001` is converted to the
    * string `"1e-7"`. This is particularly noticeable with interpolating opacity. To avoid scientific notation, start
    * or end the transition at `1e-6` (i.e., the smallest value that is not converted to a string in scientific
    * notation).
    *
    * @param  a First number.
    * @param  b Second number.
    * @return Interpolating function.
    */
  def number[N: JsNumber](a: N, b: N): CopyInterpolator[Double] = {
    Facade.interpolateNumber(a, b)
  }

  /** Returns an interpolator between the two numbers `a` and `b`. The interpolator is similar to `number()`, except
    * that it will round the resulting number to the nearest integer.
    *
    * @param  a First number.
    * @param  b Second number.
    * @return Interpolating function.
    */
  def round[N: JsNumber](a: N, b: N): CopyInterpolator[Double] = {
    Facade.interpolateRound(a, b)
  }

  /** Returns an interpolator between the two strings `a` and `b`. The string interpolator finds numbers embedded in `a`
    * and `b`, where each number is of the form understood by JavaScript. A few examples of numbers that will be
    * detected within a string are `-1`, `42`, `3.14159`, and `6.0221413e+23`.
    *
    * For each number embedded in `b`, the interpolator will attempt to find a corresponding number in `a`. If a
    * corresponding number is found, a numeric interpolator is created using `number()`. The remaining parts of the
    * string `b` are used as a template: the static parts of the string `b` remain constant for the interpolation,
    * with the interpolated numeric values embedded in the template.
    *
    * For example, if `a` is `"300 12px sans-serif"`, and `b` is `"500 36px Comic-Sans"`, two embedded numbers are
    * found. The remaining static parts of the string are a space between the two numbers (`" "`), and the suffix
    * (`"px Comic-Sans"`). The result of the interpolator at `t = 0.5` is `"400 24px Comic-Sans"`.
    *
    * @param  a First string.
    * @param  b Second string.
    * @return Interpolating function.
    */
  def string(a: String, b: String): CopyInterpolator[String] = {
    Facade.interpolateString(a, b)
  }

  /** Returns an interpolator between the two dates `a` and `b`.
    *
    * '''Note:''' No defensive copy of the returned date is created. The same date instance is returned for every
    * evaluation of the interpolator. No copy is made for performance reasons; interpolators are often part of the inner
    * loop of animated transitions.
    *
    * @param  a First date.
    * @param  b Second date.
    * @return Interpolating function.
    */
  def date(a: js.Date, b: js.Date): Interpolator[js.Date] = {
    Facade.interpolateDate(a, b)
  }

  /** Returns an interpolator between the two arrays `a` and `b`. Internally, an array template is created that is the
    * same length in `b`. For each element in `b`, if there exists a corresponding element in `a`, a generic
    * interpolator is created for the two elements. If there is no such element, the static value from `b` is used in
    * the template. Then, for the given parameter `t`, the template's embedded interpolators are evaluated. The updated
    * array template is then returned.
    *
    * For example, if `a` is the array `[0, 1]` and `b` is the array `[1, 10, 100]`, then the result of the interpolator
    * for `t = 0.5` is the array `[0.5, 5.5, 100]`.
    *
    * '''Note:''' No defensive copy of the template array is created. Modifications of the returned array may adversely
    * affect subsequent evaluation of the interpolator. No copy is made for performance reasons; interpolators are often
    * part of the inner loop of animated transitions.
    *
    * @param  a First array.
    * @param  b Second array.
    * @return Interpolating function.
    */
  def array[A <: js.Array[js.Any]](a: js.Array[js.Any], b: A): Interpolator[A] = {
    Facade.interpolateArray(a, b)
  }

  /** Returns an interpolator between the two objects `a` and `b`. Internally, an object template is created that has
    * the same properties as `b`. For each property in `b`, if there exists a corresponding property in `a`, a generic
    * interpolator is created for the two elements using interpolate. If there is no such property, the static value
    * from `b` is used in the template. Then, for the given parameter `t`, the template's embedded interpolators are
    * evaluated and the updated object template is then returned.
    *
    * For example, if `a` is the object `{x: 0, y: 1}` and `b` is the object `{x: 1, y: 10, z: 100}`, the result of the
    * interpolator for `t = 0.5` is the object `{x: 0.5, y: 5.5, z: 100}`.
    *
    * Object interpolation is particularly useful for data space interpolation, where data is interpolated rather than
    * attribute values. For example, you can interpolate an object which describes an arc in a pie chart, and then use
    * `d3.svg.arc` to compute the new SVG path data.
    *
    * '''Note:''' No defensive copy of the template object is created. Modifications of the returned object may
    * adversely affect subsequent evaluation of the interpolator. No copy is made for performance reasons; interpolators
    * are often part of the inner loop of animated transitions.
    *
    * @param  a First object.
    * @param  b Second object.
    * @return Interpolating function.
    */
  def any[T <: js.Any](a: js.Any, b: T): Interpolator[T] = {
    Facade.interpolateObject(a, b)
  }

  /** Returns an interpolator between the two objects `a` and `b`. Internally, an object template is created that has
    * the same properties as `b`. For each property in `b`, if there exists a corresponding property in `a`, a generic
    * interpolator is created for the two elements using interpolate. If there is no such property, the static value
    * from `b` is used in the template. Then, for the given parameter `t`, the template's embedded interpolators are
    * evaluated and the updated object template is then returned.
    *
    * For example, if `a` is the object `{x: 0, y: 1}` and `b` is the object `{x: 1, y: 10, z: 100}`, the result of the
    * interpolator for `t = 0.5` is the object `{x: 0.5, y: 5.5, z: 100}`.
    *
    * Object interpolation is particularly useful for data space interpolation, where data is interpolated rather than
    * attribute values. For example, you can interpolate an object which describes an arc in a pie chart, and then use
    * `d3.svg.arc` to compute the new SVG path data.
    *
    * '''Note:''' No defensive copy of the template object is created. Modifications of the returned object may
    * adversely affect subsequent evaluation of the interpolator. No copy is made for performance reasons; interpolators
    * are often part of the inner loop of animated transitions.
    *
    * @param  a First object.
    * @param  b Second object.
    * @return Interpolating function.
    */
  def dictionary(a: js.Dictionary[js.Any], b: js.Dictionary[js.Any]): Interpolator[js.Dictionary[js.Any]] = {
    Facade.interpolateObject(a, b)
  }


  /** Returns an interpolator between the two 2D CSS transforms represented by `a` and `b`. Each transform is decomposed
    * to a standard representation of translate, rotate, x-skew and scale; these component transformations are then
    * interpolated. This behavior is standardized by CSS: see
    * [matrix decomposition for animation](http://www.w3.org/TR/css3-2d-transforms/#matrix-decomposition).
    *
    * @param  a First CSS transform.
    * @param  b Second CSS transform.
    * @return Interpolating function.
    */
  def transformCSS(a: String, b: String): CopyInterpolator[String] = {
    Facade.interpolateTransformCss(a, b)
  }

  /** Returns an interpolator between the two 2D SVG transforms represented by `a` and `b`. Each transform is decomposed
    * to a standard representation of translate, rotate, x-skew and scale; these component transformations are then
    * interpolated. This behavior is standardized by CSS: see
    * [matrix decomposition for animation](http://www.w3.org/TR/css3-2d-transforms/#matrix-decomposition).
    *
    * @param  a First SVG transform.
    * @param  b Second SVG transform.
    * @return Interpolating function.
    */
  def transformSVG(a: String, b: String): CopyInterpolator[String] = {
    Facade.interpolateTransformSvg(a, b)
  }

  /** Returns an interpolator between the two views a and b of a two-dimensional plane, based on
    * ["Smooth and efficient zooming and panning"](http://www.win.tue.nl/%7Evanwijk/zoompan.pdf) by Jarke J. van Wijk
    * and Wim A.A. Nuij. Each view is defined as a tuple of three numbers: `cx`, `cy` and `width`. The first two
    * coordinates, `cx` and `cy`, represent the center of the viewport. The last coordinate, `width`, represents the
    * size of the viewport.
    *
    * The returned interpolator exposes a duration property which encodes the recommended transition duration in
    * milliseconds. This duration is based on the path length of the curved trajectory through `x`, `y` space. If you
    * want a slower or faster transition, multiply this by an arbitrary scale factor (`V` as described in the original
    * paper).
    *
    * @param  a First zoom view.
    * @param  b Second zoom view.
    * @return Zoom interpolating function.
    */
  def zoom(a: ZoomView, b: ZoomView): ZoomInterpolator = {
    Facade.interpolateZoom(a, b)
  }

  /** Returns `n` uniformly-spaced samples from the specified interpolator, where `n` is an integer greater than one.
    * The first sample is always at `t = 0`, and the last sample is always at `t = 1`. This can be useful in generating
    * a fixed number of samples from a given interpolator, such as to derive the range of a quantize scale from a
    * continuous interpolator.
    *
    * This method does not accept non-copy interpolators. In that case, you must wrap the interpolator and create a copy
    * for each returned value, before passing it to this method.
    *
    * @param  interpolator Interpolator to use.
    * @param  n            Number of samples to return.
    * @return Array containing `n` uniformly-spaced samples from `interpolator`.
    */
  def quantize[R](interpolator: CopyInterpolator[R], n: Int): js.Array[R] = {
    Facade.quantize(interpolator, n)
  }

  /** RGB color space interpolator factory between the two colors `a` and `b` with a configurable gamma. If the gamma is
    * not specified, it defaults to `1.0`. The colors `a` and `b` need not be in RGB. They will be converted to RGB
    * using `d3.color.rgb`. The return value of the interpolator is an RGB string.
    *
    * For example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/rgb.png" width="100%" height="40" alt="rgb" style="max-width:100%;"/>
    * Or, with a corrected gamma of `2.2`:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/rgbGamma.png" width="100%" height="40" alt="rgbGamma" style="max-width:100%;"/>
    */
  val rgb: ColorGammaInterpolatorFactory = Facade.interpolateRgb

  /** Returns a uniform non-rational B-spline interpolator through the specified array of colors, which are converted to
    * RGB color space. Implicit control points are generated such that the interpolator returns `colors(0)` at `t = 0`
    * and `colors(colors.length - 1)` at `t = 1`. Opacity interpolation is not currently supported.
    *
    * @param  colors Array of colors to use.
    * @return Interpolating function.
    */
  def rgbBasis(colors: js.Array[String | Color]): CopyInterpolator[String] = {
    Facade.interpolateRgbBasis(colors)
  }

  /** Returns a uniform non-rational B-spline interpolator through the specified array of colors, which are converted to
    * RGB color space. The control points are implicitly repeated such that the resulting spline has cyclical C²
    * continuity when repeated around `t` in `[0, 1]`. This is useful, for example, to create cyclical color scales.
    *
    * @param  colors Array of colors to use.
    * @return Interpolating function.
    */
  def rgbBasisClosed(colors: js.Array[String | Color]): CopyInterpolator[String] = {
    Facade.interpolateRgbBasisClosed(colors)
  }

  /** Returns an HSL color space interpolator between the two colors `a` and `b`. The colors `a` and `b` need not be in
    * HSL. They will be converted to HSL using `d3.color.hsl`. If either color's hue or saturation is `NaN`, the
    * opposing color's channel value is used. The shortest path between hues is used. The return value of the
    * interpolator is an RGB string.
    *
    * Example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/hsl.png" width="100%" height="40" alt="hsl" style="max-width:100%;"/>
    *
    * @param  a First color.
    * @param  b Second color.
    * @return Interpolating function.
    */
  def hsl(a: String | Color, b: String | Color): CopyInterpolator[String] = {
    Facade.interpolateHsl(a, b)
  }

  /** Like `hsl()`, but does not use the shortest path between hues.
    *
    * Example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/hslLong.png" width="100%" height="40" alt="hslLong" style="max-width:100%;"/>
    *
    * @param  a First color.
    * @param  b Second color.
    * @return Interpolating function.
    */
  def hslLong(a: String | Color, b: String | Color): CopyInterpolator[String] = {
    Facade.interpolateHslLong(a, b)
  }

  /** Returns a Lab color space interpolator between the two colors `a` and `b`. The colors `a` and `b` need not be in
    * Lab. They will be converted to Lab using `d3.color.lab`. The returned value of the interpolator is an RGB string.
    *
    * Example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/lab.png" width="100%" height="40" alt="lab" style="max-width:100%;"/>
    *
    * @param  a First color.
    * @param  b Second color.
    * @return Interpolating function.
    */
  def lab(a: String | Color, b: String | Color): CopyInterpolator[String] = {
    Facade.interpolateLab(a, b)
  }

  /** Returns an HCL color space interpolator between the two colors `a` and `b`. The colors `a` and `b` need not be in
    * HCL. They will be converted to HCL using `d3.color.hcl`. If either color's hue or chroma is `NaN`, the opposing
    * color's channel value is used. The shortest path between hues is used. The return value of the interpolator is an
    * RGB string.
    *
    * Example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/hcl.png" width="100%" height="40" alt="hcl" style="max-width:100%;"/>
    *
    * @param  a First color.
    * @param  b Second color.
    * @return Interpolating function.
    */
  def hcl(a: String | Color, b: String | Color): CopyInterpolator[String] = {
    Facade.interpolateHcl(a, b)
  }

  /** Like `hcl()`, but does not use the shortest path between hues.
    *
    * Example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/hclLong.png" width="100%" height="40" alt="hclLong" style="max-width:100%;"/>
    *
    * @param  a First color.
    * @param  b Second color.
    * @return Interpolating function.
    */
  def hclLong(a: String | Color, b: String | Color): CopyInterpolator[String] = {
    Facade.interpolateHclLong(a, b)
  }

  /** Returns a Cubehelix color space interpolator factory between the two colors `a` and `b` using a configurable
    * gamma. If the gamma is not specified, it defaults to `1.0`. The colors `a` and `b` need not be in Cubehelix. They
    * will be converted to Cubehelix using `d3.color.cubehelix`. If either color's hue or saturation is `NaN`, the
    * opposing color's channel value is used. The shortest path between hues is used. The return value of the
    * interpolator is an RGB string.
    *
    * For example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/cubehelix.png" width="100%" height="40" alt="cubehelix" style="max-width:100%;"/>
    * Or, with a corrected gamma of `3.0` to emphasize high-intensity values:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/cubehelixGamma.png" width="100%" height="40" alt="cubehelixGamma" style="max-width:100%;"/>
    */
  val cubehelix: ColorGammaInterpolatorFactory = Facade.interpolateCubehelix

  /** Like `cubehelix`, but does not use the shortest path between hues.
    *
    * For example:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/cubehelixLong.png" width="100%" height="40" alt="cubehelixLong" style="max-width:100%;"/>
    * Or, with a corrected gamma of `3.0` to emphasize high-intensity values:
    * <img src="https://raw.githubusercontent.com/d3/d3-interpolate/master/img/cubehelixGammaLong.png" width="100%" height="40" alt="cubehelixGammaLong" style="max-width:100%;"/>
    */
  val cubehelixLong: ColorGammaInterpolatorFactory = Facade.interpolateCubehelixLong

  /** Returns a uniform non-rational B-spline interpolator through the specified array of values, which must be numbers.
    * Implicit control points are generated such that the interpolator returns `values(0)` at `t = 0` and
    * `values(values.length - 1)` at `t = 1`.
    *
    * Whereas standard interpolators blend from a starting value `a` at `t = 0` to an ending value `b` at `t = 1`,
    * spline interpolators smoothly blend multiple input values for `t` in `[0, 1]` using piecewise polynomial
    * functions. Only cubic uniform non-rational [B-splines](https://en.wikipedia.org/wiki/B-spline) are currently
    * supported, also known as basis splines.
    *
    * @param  values Values to use as spline nodes.
    * @return Interpolating function.
    */
  def basis(values: js.Array[Double]): CopyInterpolator[Double] = {
    Facade.interpolateBasis(values)
  }

  /** Returns a uniform non-rational B-spline interpolator through the specified array of values, which must be numbers.
    * The control points are implicitly repeated such that the resulting one-dimensional spline has cyclical C²
    * continuity when repeated around `t` in `[0, 1]`.
    *
    * Whereas standard interpolators blend from a starting value `a` at `t = 0` to an ending value `b` at `t = 1`,
    * spline interpolators smoothly blend multiple input values for `t` in `[0, 1]` using piecewise polynomial
    * functions. Only cubic uniform non-rational [B-splines](https://en.wikipedia.org/wiki/B-spline) are currently
    * supported, also known as basis splines.
    *
    * @param  values Values to use as spline nodes.
    * @return Interpolating function.
    */
  def basisClosed(values: js.Array[Double]): CopyInterpolator[Double] = {
    Facade.interpolateBasisClosed(values)
  }

  /** Returns an interpolator between the two arbitrary values `a` and `b`. The interpolator implementation depends on 
    * the types of `a` and `b`:
    *
    *   - If `b` is null, undefined or a boolean, use the constant `b`.
    *   - If `b` is a number, use `number()`.
    *   - If `b` is a color or a string coercible to a color, use `rgb()`.
    *   - If `b` is a date, use `date()`.
    *   - If `b` is a string, use `string()`.
    *   - If `b` is an array, use `array()`.
    *   - If `b` is coercible to a number, use `number()`.
    *   - Else, use `object()`.
    *
    * @param  a First value.
    * @param  b Second value.
    * @return Interpolating function.
    */
  def apply[T, R, I <: Interpolator[R]](a: T, b: T)(implicit ev: Supported[T, R, I]): I = {
    ev.interpolate(a, b)
  }

  trait Supported[T, R, I <: Interpolator[R]] {
    def interpolate: (T, T) => I
  }

  object Supported {
    implicit val nullSupported: Supported[Null, Null, CopyInterpolator[Null]] = {
      new Supported[Null, Null, CopyInterpolator[Null]] {
        override def interpolate: (Null, Null) => CopyInterpolator[Null] = Facade.interpolate
      }
    }

    implicit def numericSupported[N: JsNumber]: Supported[N, Double, CopyInterpolator[Double]] = {
      new Supported[N, Double, CopyInterpolator[Double]] {
        override def interpolate: (N, N) => CopyInterpolator[Double] = number
      }
    }

    implicit val stringSupported: Supported[String, String, CopyInterpolator[String]] = {
      new Supported[String, String, CopyInterpolator[String]] {
        override def interpolate: (String, String) => CopyInterpolator[String] = Facade.interpolate
      }
    }

    implicit val colorSupported: Supported[Color, String, CopyInterpolator[String]] = {
      new Supported[Color, String, CopyInterpolator[String]] {
        override def interpolate: (Color, Color) => CopyInterpolator[String] = (a, b) => rgb.apply(a, b)
      }
    }

    implicit val dateSupported: Supported[js.Date, js.Date, Interpolator[js.Date]] = {
      new Supported[js.Date, js.Date, Interpolator[js.Date]] {
        override def interpolate: (js.Date, js.Date) => Interpolator[js.Date] = date
      }
    }

    implicit def arraySupported[A <: js.Array[js.Any]]: Supported[A, A, Interpolator[A]] = {
      new Supported[A, A, Interpolator[A]] {
        override def interpolate: (A, A) => Interpolator[A] = array
      }
    }

    implicit def anySupported[T <: js.Any]: Supported[T, T, Interpolator[T]] = {
      new Supported[T, T, Interpolator[T]] {
        override def interpolate: (T, T) => Interpolator[T] = any
      }
    }

    implicit val dictionarySupported: Supported[js.Dictionary[js.Any], js.Dictionary[js.Any], Interpolator[js.Dictionary[js.Any]]] = {
      new Supported[js.Dictionary[js.Any], js.Dictionary[js.Any], Interpolator[js.Dictionary[js.Any]]] {
        override def interpolate: (js.Dictionary[js.Any], js.Dictionary[js.Any]) => Interpolator[js.Dictionary[js.Any]] = dictionary
      }
    }
  }

  @JSImport("d3-interpolate", JSImport.Namespace)
  @js.native private[Interpolate] object Facade extends js.Object {
    def interpolate(a: js.Any, b: Null): CopyInterpolator[Null] = js.native
    def interpolate(a: String, b: String): CopyInterpolator[String] = js.native

    // def interpolate(a: js.Any, b: Double): js.Function1[Double, Double] = js.native
    // def interpolate(a: js.Any, b: Color): js.Function1[Double, String] = js.native
    // def interpolate(a: js.Date, b: js.Date): js.Function1[Double, js.Date] = js.native
    // def interpolate[U <: js.Array[js.Any]](a: js.Array[js.Any], b: U): js.Function1[Double, U] = js.native
    // def interpolate[U <: js.Any](a: js.Any, b: U): js.Function1[Double, U] = js.native
    // def interpolate(a: js.Any, b: js.Dictionary[js.Any]): js.Function1[Double, js.Dictionary[js.Any]] = js.native

    def interpolateNumber[N: JsNumber](a: N, b: N): CopyInterpolator[Double] = js.native
    def interpolateRound[N: JsNumber](a: N, b: N): CopyInterpolator[Double] = js.native
    def interpolateString(a: String, b: String): CopyInterpolator[String] = js.native
    def interpolateDate(a: js.Date, b: js.Date): Interpolator[js.Date] = js.native
    def interpolateArray[A <: js.Array[js.Any]](a: js.Array[js.Any], b: A): Interpolator[A] = js.native
    def interpolateObject[T <: js.Any](a: js.Any, b: T): Interpolator[T] = js.native
    def interpolateObject(a: js.Dictionary[js.Any], b: js.Dictionary[js.Any]): Interpolator[js.Dictionary[js.Any]] = js.native
    def interpolateTransformCss(a: String, b: String): CopyInterpolator[String] = js.native
    def interpolateTransformSvg(a: String, b: String): CopyInterpolator[String] = js.native
    def interpolateZoom(a: ZoomView, b: ZoomView): ZoomInterpolator = js.native

    def quantize[R](interpolator: CopyInterpolator[R], n: Int): js.Array[R] = js.native

    val interpolateRgb: ColorGammaInterpolatorFactory = js.native

    def interpolateRgbBasis(colors: js.Array[String | Color]): CopyInterpolator[String] = js.native
    def interpolateRgbBasisClosed(colors: js.Array[String | Color]): CopyInterpolator[String] = js.native
    def interpolateHsl(a: String | Color, b: String | Color): CopyInterpolator[String] = js.native
    def interpolateHslLong(a: String | Color, b: String | Color): CopyInterpolator[String] = js.native
    def interpolateLab(a: String | Color, b: String | Color): CopyInterpolator[String] = js.native
    def interpolateHcl(a: String | Color, b: String | Color): CopyInterpolator[String] = js.native
    def interpolateHclLong(a: String | Color, b: String | Color): CopyInterpolator[String] = js.native

    val interpolateCubehelix    : ColorGammaInterpolatorFactory = js.native
    val interpolateCubehelixLong: ColorGammaInterpolatorFactory = js.native

    def interpolateBasis(basis: js.Array[Double]): CopyInterpolator[Double] = js.native
    def interpolateBasisClosed(basis: js.Array[Double]): CopyInterpolator[Double] = js.native
  }
}
