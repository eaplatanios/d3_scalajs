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

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-color", JSImport.Namespace)
@js.native object Color extends js.Object {
  /** Parses the specified CSS Color Module Level 3 specifier string, returning an RGB or HSL color. If the specifier
    * was not valid, null is returned. Some examples:
    *
    *   - `rgb(255, 255, 255)`
    *   - `rgb(10%, 20%, 30%)`
    *   - `rgba(255, 255, 255, 0.4)`
    *   - `rgba(10%, 20%, 30%, 0.4)`
    *   - `hsl(120, 50%, 20%)`
    *   - `hsla(120, 50%, 20%, 0.4)`
    *   - `#ffeeaa`
    *   - `#fea`
    *   - `steelblue`
    *
    * The list supported [named colors](https://www.w3.org/TR/SVG/types.html#ColorKeywords) is specified by CSS.
    *
    * '''Note:''' This function may also be used with `isInstanceOf` to test if an object is a color instance. The same
    * is true of color subclasses, allowing you to test whether a color is in a particular color space.
    */
  val color: ColorFactory = js.native

  /** Constructs a new [RGB](https://en.wikipedia.org/wiki/RGB_color_model) color. The channel values are exposed as
    * `r`, `g` and `b` properties on the returned instance. Use the
    * [RGB color picker](http://bl.ocks.org/mbostock/78d64ca7ef013b4dcf8f) to explore this color space. If `r`, `g` and
    * `b` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the RGB color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()`. Note that unlike
    * `Color.rgb()` this method always returns a new instance, even if color is already an RGB color. */
  val rgb: RGBColorFactory = js.native

  /** Constructs a new [HSL](https://en.wikipedia.org/wiki/HSL_and_HSV) color. The channel values are exposed as `h`,
    * `s` and `l` properties on the returned instance. Use the
    * [HSL color picker](http://bl.ocks.org/mbostock/debaad4fcce9bcee14cf) to explore this color space. If `h`, `s` and
    * `l` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the HSL color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()` and then to HSL
    * space. Note that colors already in the HSL color space skip the conversion to RGB. */
  val hsl: HSLColorFactory = js.native

  /** Constructs a new [Lab](https://en.wikipedia.org/wiki/Lab_color_space#CIELAB) color. The channel values are exposed
    * as `l`, `a` and `b` properties on the returned instance. Use the
    * [Lab color picker](http://bl.ocks.org/mbostock/9f37cc207c0cb166921b) to explore this color space. If `l`, `a` and
    * `b` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the Lab color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()` and then to Lab
    * space. Note that colors already in the Lab color space skip the conversion to RGB. */
  val lab: LabColorFactory = js.native

  /** Constructs a new [HCL](https://en.wikipedia.org/wiki/HCL_color_space) color. The channel values are exposed
    * as `h`, `c` and `l` properties on the returned instance. Use the
    * [HCL color picker](http://bl.ocks.org/mbostock/3e115519a1b495e0bd95) to explore this color space. If `h`, `c` and
    * `l` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the HCL color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()` and then to HCL
    * space. Note that colors already in the HCL color space skip the conversion to RGB. */
  val hcl: HCLColorFactory = js.native

  /** Constructs a new [Cubehelix](https://www.mrao.cam.ac.uk/%7Edag/CUBEHELIX/) color. The channel values are exposed
    * `s` and `l` properties on the returned instance. Use the
    * [Cubehelix color picker](http://bl.ocks.org/mbostock/ba8d75e45794c27168b5) to explore this color space. If `h`,
    * `s` and `l` are specified, these represent the channel values of the returned color; an opacity may also be
    * specified. If a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the
    * Cubehelix color space. If a color instance is specified, it is converted to the RGB color space using
    * `Color.rgb()` and then to Cubehelix space. Note that colors already in the Cubehelix color space skip the
    * conversion to RGB. */
  val cubehelix: CubehelixColorFactory = js.native
}

@js.native trait Color extends js.Object {
  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  def displayable(): Boolean = js.native

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = js.native
}

@js.native trait RGBColor extends Color {
  var r: Double = js.native
  var g: Double = js.native
  var b: Double = js.native

  /** Returns the color's opacity, typically in the range `[0, 1]`. */
  var opacity: Double = js.native

  /** Returns a brighter copy of this color. If `k` is specified, it controls
    * how much brighter the returned color should be. If `k` is not specified,
    * it defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def brighter(k: Double = 1): this.type = js.native

  /** Returns a darker copy of this color. If `k` is specified, it controls how
    * much darker the returned color should be. If `k` is not specified, it
    * defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def darker(k: Double = 1): this.type = js.native

  /** Returns the RGB equivalent of this color. */
  def rgb(): RGBColor = js.native

  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  override def displayable(): Boolean = js.native

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = js.native
}

@js.native trait HSLColor extends Color {
  var h: Double = js.native
  var s: Double = js.native
  var l: Double = js.native

  /** Returns the color's opacity, typically in the range `[0, 1]`. */
  var opacity: Double = js.native

  /** Returns a brighter copy of this color. If `k` is specified, it controls
    * how much brighter the returned color should be. If `k` is not specified,
    * it defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def brighter(k: Double = 1): this.type = js.native

  /** Returns a darker copy of this color. If `k` is specified, it controls how
    * much darker the returned color should be. If `k` is not specified, it
    * defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def darker(k: Double = 1): this.type = js.native

  /** Returns the RGB equivalent of this color. */
  def rgb(): RGBColor = js.native

  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  override def displayable(): Boolean = js.native

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = js.native
}

@js.native trait LabColor extends Color {
  var l: Double = js.native
  var a: Double = js.native
  var b: Double = js.native

  /** Returns the color's opacity, typically in the range `[0, 1]`. */
  var opacity: Double = js.native

  /** Returns a brighter copy of this color. If `k` is specified, it controls
    * how much brighter the returned color should be. If `k` is not specified,
    * it defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def brighter(k: Double = 1): this.type = js.native

  /** Returns a darker copy of this color. If `k` is specified, it controls how
    * much darker the returned color should be. If `k` is not specified, it
    * defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def darker(k: Double = 1): this.type = js.native

  /** Returns the RGB equivalent of this color. */
  def rgb(): RGBColor = js.native

  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  override def displayable(): Boolean = js.native

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = js.native
}

@js.native trait HCLColor extends Color {
  var h: Double = js.native
  var c: Double = js.native
  var l: Double = js.native

  /** Returns the color's opacity, typically in the range `[0, 1]`. */
  var opacity: Double = js.native

  /** Returns a brighter copy of this color. If `k` is specified, it controls
    * how much brighter the returned color should be. If `k` is not specified,
    * it defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def brighter(k: Double = 1): this.type = js.native

  /** Returns a darker copy of this color. If `k` is specified, it controls how
    * much darker the returned color should be. If `k` is not specified, it
    * defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def darker(k: Double = 1): this.type = js.native

  /** Returns the RGB equivalent of this color. */
  def rgb(): RGBColor = js.native

  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  override def displayable(): Boolean = js.native

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = js.native
}

@js.native trait CubehelixColor extends Color {
  var h: Double = js.native
  var s: Double = js.native
  var l: Double = js.native

  /** Returns the color's opacity, typically in the range `[0, 1]`. */
  var opacity: Double = js.native

  /** Returns a brighter copy of this color. If `k` is specified, it controls
    * how much brighter the returned color should be. If `k` is not specified,
    * it defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def brighter(k: Double = 1): this.type = js.native

  /** Returns a darker copy of this color. If `k` is specified, it controls how
    * much darker the returned color should be. If `k` is not specified, it
    * defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def darker(k: Double = 1): this.type = js.native

  /** Returns the RGB equivalent of this color. */
  def rgb(): RGBColor = js.native

  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  override def displayable(): Boolean = js.native

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = js.native
}
