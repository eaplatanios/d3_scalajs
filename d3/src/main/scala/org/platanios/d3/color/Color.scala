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

import org.platanios.d3.Facade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Even though your browser understands a lot about colors, it doesn't offer much help in manipulating colors through
  * JavaScript. The D3 color module therefore provides representations for various color spaces, allowing specification,
  * conversion and manipulation.
  *
  * For example, take the color named "steelblue":
  * {{{
  *   val c = d3.color("steelblue") // {r: 70, g: 130, b: 180, opacity: 1}
  * }}}
  *
  * Let's try converting it to HSL:
  * {{{
  *   var c = d3.color.hsl("steelblue") // {h: 207.27, s: 0.44, l: 0.4902, opacity: 1}
  * }}}
  *
  * Now rotate the hue by 90°, bump up the saturation, and format as a string for CSS:
  * {{{
  *   c = c.copy(h = c.h + 90, s = c.s + 0.2).toString // rgb(198, 45, 205)
  * }}}
  *
  * To fade the color slightly:
  * {{{
  *   c.copy(opacity = 0.8).toString // rgba(198, 45, 205, 0.8)
  * }}}
  *
  * In addition to the ubiquitous and machine-friendly RGB and HSL color spaces, D3 color supports two color spaces that
  * are designed for humans:
  *
  *   - Dave Green's [Cubehelix](https://github.com/d3/d3-color#cubehelix), and
  *   - Lab (CIELAB) and HCL (CIELCH).
  *
  * Cubehelix features monotonic lightness, while Lab and HCL are perceptually uniform. Note that HCL is the cylindrical
  * form of Lab, similar to how HSL is the cylindrical form of RGB.
  *
  * @author Emmanouil Antonios Platanios
  */
object Color {
  val scheme: Scheme.type = Scheme

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
  def apply[C <: Color[C, _]]: Factory[C] = new Factory[C](Facade.color[C])

  /** Constructs a new [RGB](https://en.wikipedia.org/wiki/RGB_color_model) color. The channel values are exposed as
    * `r`, `g` and `b` properties on the returned instance. Use the
    * [RGB color picker](http://bl.ocks.org/mbostock/78d64ca7ef013b4dcf8f) to explore this color space. If `r`, `g` and
    * `b` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the RGB color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()`. Note that unlike
    * `Color.rgb()` this method always returns a new instance, even if color is already an RGB color.
    */
  val rgb: RGBFactory = new RGBFactory(Facade.rgb)

  /** Constructs a new [HSL](https://en.wikipedia.org/wiki/HSL_and_HSV) color. The channel values are exposed as `h`,
    * `s` and `l` properties on the returned instance. Use the
    * [HSL color picker](http://bl.ocks.org/mbostock/debaad4fcce9bcee14cf) to explore this color space. If `h`, `s` and
    * `l` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the HSL color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()` and then to HSL
    * space. Note that colors already in the HSL color space skip the conversion to RGB.
    */
  val hsl: HSLFactory = new HSLFactory(Facade.hsl)

  /** Constructs a new [Lab](https://en.wikipedia.org/wiki/Lab_color_space#CIELAB) color. The channel values are exposed
    * as `l`, `a` and `b` properties on the returned instance. Use the
    * [Lab color picker](http://bl.ocks.org/mbostock/9f37cc207c0cb166921b) to explore this color space. If `l`, `a` and
    * `b` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the Lab color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()` and then to Lab
    * space. Note that colors already in the Lab color space skip the conversion to RGB.
    */
  val lab: LabFactory = new LabFactory(Facade.lab)

  /** Constructs a new [HCL](https://en.wikipedia.org/wiki/HCL_color_space) color. The channel values are exposed
    * as `h`, `c` and `l` properties on the returned instance. Use the
    * [HCL color picker](http://bl.ocks.org/mbostock/3e115519a1b495e0bd95) to explore this color space. If `h`, `c` and
    * `l` are specified, these represent the channel values of the returned color; an opacity may also be specified. If
    * a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the HCL color space.
    * If a color instance is specified, it is converted to the RGB color space using `Color.rgb()` and then to HCL
    * space. Note that colors already in the HCL color space skip the conversion to RGB.
    */
  val hcl: HCLFactory = new HCLFactory(Facade.hcl)

  /** Constructs a new [Cubehelix](https://www.mrao.cam.ac.uk/%7Edag/CUBEHELIX/) color. The channel values are exposed
    * `s` and `l` properties on the returned instance. Use the
    * [Cubehelix color picker](http://bl.ocks.org/mbostock/ba8d75e45794c27168b5) to explore this color space. If `h`,
    * `s` and `l` are specified, these represent the channel values of the returned color; an opacity may also be
    * specified. If a CSS Color Module Level 3 specifier string is specified, it is parsed and then converted to the
    * Cubehelix color space. If a color instance is specified, it is converted to the RGB color space using
    * `Color.rgb()` and then to Cubehelix space. Note that colors already in the Cubehelix color space skip the
    * conversion to RGB.
    */
  val cubehelix: CubehelixFactory = new CubehelixFactory(Facade.cubehelix)

  @JSImport("d3-color", JSImport.Namespace)
  @js.native private[color] object Facade extends js.Object {
    def color[C <: Color[C, _]]: Factory.Facade[C]       = js.native
    val rgb                    : RGBFactory.Facade       = js.native
    val hsl                    : HSLFactory.Facade       = js.native
    val lab                    : LabFactory.Facade       = js.native
    val hcl                    : HCLFactory.Facade       = js.native
    val cubehelix              : CubehelixFactory.Facade = js.native
  }

  @js.native private[color] trait Facade[F <: Facade[F]] extends js.Object {
    def displayable(): Boolean = js.native
    def brighter(k: Double = 1): F = js.native
    def darker(k: Double = 1): F = js.native
    def rgb(): RGB.Facade = js.native
    override def toString: String = js.native
  }

  object Factory {
    @js.native private[color] trait Facade[C <: Color[C, _]] extends js.Function {
      def apply(cssColorSpecifier: String): C = js.native
      def apply(color: Color.Facade[_]): C = js.native
    }
  }

  class Factory[C <: Color[C, _]] private[color] (
      private[d3] val facade: Factory.Facade[C]
  ) {
    def apply(cssColorSpecifier: String): C = facade(cssColorSpecifier)
    def apply[F <: Color.Facade[F]](color: Color[_, F]): C = facade(color.facade)
  }

  class RGB private[color](
      override private[d3] val facade: RGB.Facade
  ) extends Color[RGB, RGB.Facade] {
    val r: Double = facade.r
    val g: Double = facade.g
    val b: Double = facade.b

    /** Returns the color's opacity, typically in the range `[0, 1]`. */
    val opacity: Double = facade.opacity

    def copy(r: Double = r, g: Double = g, b: Double = b, opacity: Double = opacity): RGB = {
      Color.rgb(r, g, b, opacity)
    }

    override protected def withFacade(facade: RGB.Facade): RGB = new RGB(facade)
  }

  object RGB {
    @js.native private[color] trait Facade extends Color.Facade[Facade] {
      val r      : Double = js.native
      val g      : Double = js.native
      val b      : Double = js.native
      val opacity: Double = js.native
    }
  }

  object RGBFactory {
    @js.native private[color] trait Facade extends Factory.Facade[RGB] {
      def apply(r: Double, g: Double, b: Double, opacity: Double = 1.0): RGB = js.native

      override def apply(cssColorSpecifier: String): RGB = js.native
      override def apply(color: Color.Facade[_]): RGB = js.native
    }
  }

  class RGBFactory private[color] (
      override private[d3] val facade: RGBFactory.Facade
  ) extends Factory[RGB](facade) {
    def apply(r: Double, g: Double, b: Double, opacity: Double = 1.0): RGB = facade(r, g, b, opacity)

    override def apply(cssColorSpecifier: String): RGB = facade(cssColorSpecifier)
    override def apply[F <: Color.Facade[F]](color: Color[_, F]): RGB = facade(color.facade)
  }

  class HSL private[color](
      override private[d3] val facade: HSL.Facade
  ) extends Color[HSL, HSL.Facade] {
    val h: Double = facade.h
    val s: Double = facade.s
    val l: Double = facade.l

    /** Returns the color's opacity, typically in the range `[0, 1]`. */
    val opacity: Double = facade.opacity

    def copy(h: Double = h, s: Double = s, l: Double = l, opacity: Double = opacity): HSL = {
      Color.hsl(h, s, l, opacity)
    }

    override protected def withFacade(facade: HSL.Facade): HSL = new HSL(facade)
  }

  object HSL {
    @js.native private[color] trait Facade extends Color.Facade[Facade] {
      val h      : Double = js.native
      val s      : Double = js.native
      val l      : Double = js.native
      val opacity: Double = js.native
    }
  }

  object HSLFactory {
    @js.native private[color] trait Facade extends Factory.Facade[HSL] {
      def apply(h: Double, s: Double, l: Double, opacity: Double = 1.0): HSL = js.native

      override def apply(cssColorSpecifier: String): HSL = js.native
      override def apply(color: Color.Facade[_]): HSL = js.native
    }
  }

  class HSLFactory private[color] (
      override private[d3] val facade: HSLFactory.Facade
  ) extends Factory[HSL](facade) {
    def apply(h: Double, s: Double, l: Double, opacity: Double = 1.0): HSL = facade(h, s, l, opacity)

    override def apply(cssColorSpecifier: String): HSL = facade(cssColorSpecifier)
    override def apply[F <: Color.Facade[F]](color: Color[_, F]): HSL = facade(color.facade)
  }

  class Lab private[color](
      override private[d3] val facade: Lab.Facade
  ) extends Color[Lab, Lab.Facade] {
    val l: Double = facade.l
    val a: Double = facade.a
    val b: Double = facade.b

    /** Returns the color's opacity, typically in the range `[0, 1]`. */
    val opacity: Double = facade.opacity

    def copy(l: Double = l, a: Double = a, b: Double = b, opacity: Double = opacity): Lab = {
      Color.lab(l, a, b, opacity)
    }

    override protected def withFacade(facade: Lab.Facade): Lab = new Lab(facade)
  }

  object Lab {
    @js.native private[color] trait Facade extends Color.Facade[Facade] {
      val l      : Double = js.native
      val a      : Double = js.native
      val b      : Double = js.native
      val opacity: Double = js.native
    }
  }

  object LabFactory {
    @js.native private[color] trait Facade extends Factory.Facade[Lab] {
      def apply(l: Double, a: Double, b: Double, opacity: Double = 1.0): Lab = js.native

      override def apply(cssColorSpecifier: String): Lab = js.native
      override def apply(color: Color.Facade[_]): Lab = js.native
    }
  }

  class LabFactory private[color] (
      override private[d3] val facade: LabFactory.Facade
  ) extends Factory[Lab](facade) {
    def apply(l: Double, a: Double, b: Double, opacity: Double = 1.0): Lab = facade(l, a, b, opacity)

    override def apply(cssColorSpecifier: String): Lab = facade(cssColorSpecifier)
    override def apply[F <: Color.Facade[F]](color: Color[_, F]): Lab = facade(color.facade)
  }

  class HCL private[color](
      override private[d3] val facade: HCL.Facade
  ) extends Color[HCL, HCL.Facade] {
    val h: Double = facade.h
    val c: Double = facade.c
    val l: Double = facade.l

    /** Returns the color's opacity, typically in the range `[0, 1]`. */
    val opacity: Double = facade.opacity

    def copy(h: Double = h, c: Double = c, l: Double = l, opacity: Double = opacity): HCL = {
      Color.hcl(h, c, l, opacity)
    }

    override protected def withFacade(facade: HCL.Facade): HCL = new HCL(facade)
  }

  object HCL {
    @js.native private[color] trait Facade extends Color.Facade[Facade] {
      val h      : Double = js.native
      val c      : Double = js.native
      val l      : Double = js.native
      val opacity: Double = js.native
    }
  }

  object HCLFactory {
    @js.native private[color] trait Facade extends Factory.Facade[HCL] {
      def apply(h: Double, c: Double, l: Double, opacity: Double = 1.0): HCL = js.native

      override def apply(cssColorSpecifier: String): HCL = js.native
      override def apply(color: Color.Facade[_]): HCL = js.native
    }
  }

  class HCLFactory private[color] (
      override private[d3] val facade: HCLFactory.Facade
  ) extends Factory[HCL](facade) {
    def apply(h: Double, c: Double, l: Double, opacity: Double = 1.0): HCL = facade(h, c, l, opacity)

    override def apply(cssColorSpecifier: String): HCL = facade(cssColorSpecifier)
    override def apply[F <: Color.Facade[F]](color: Color[_, F]): HCL = facade(color.facade)
  }

  class Cubehelix private[color](
      override private[d3] val facade: Cubehelix.Facade
  ) extends Color[Cubehelix, Cubehelix.Facade] {
    val h: Double = facade.h
    val s: Double = facade.s
    val l: Double = facade.l

    /** Returns the color's opacity, typically in the range `[0, 1]`. */
    val opacity: Double = facade.opacity

    def copy(h: Double = h, s: Double = s, l: Double = l, opacity: Double = opacity): Cubehelix = {
      Color.cubehelix(h, s, l, opacity)
    }

    override protected def withFacade(facade: Cubehelix.Facade): Cubehelix = new Cubehelix(facade)
  }

  object Cubehelix {
    @js.native private[color] trait Facade extends Color.Facade[Facade] {
      val h      : Double = js.native
      val s      : Double = js.native
      val l      : Double = js.native
      val opacity: Double = js.native
    }
  }

  object CubehelixFactory {
    @js.native private[color] trait Facade extends Factory.Facade[Cubehelix] {
      def apply(h: Double, s: Double, l: Double, opacity: Double = 1.0): Cubehelix = js.native

      override def apply(cssColorSpecifier: String): Cubehelix = js.native
      override def apply(color: Color.Facade[_]): Cubehelix = js.native
    }
  }

  class CubehelixFactory private[color] (
      override private[d3] val facade: CubehelixFactory.Facade
  ) extends Factory[Cubehelix](facade) {
    def apply(h: Double, s: Double, l: Double, opacity: Double = 1.0): Cubehelix = facade(h, s, l, opacity)

    override def apply(cssColorSpecifier: String): Cubehelix = facade(cssColorSpecifier)
    override def apply[F <: Color.Facade[F]](color: Color[_, F]): Cubehelix = facade(color.facade)
  }
}

trait Color[C <: Color[C, F], F <: Color.Facade[F]] extends Facade[C, F] {
  /** Returns `true` if and only if the color is displayable on standard
    * hardware. For example, this returns `false` for an RGB color if any
    * channel value is less than zero or greater than 255, or if the opacity is
    * not in the range `[0, 1]`. */
  def displayable(): Boolean = facade.displayable()

  /** Returns a brighter copy of this color. If `k` is specified, it controls
    * how much brighter the returned color should be. If `k` is not specified,
    * it defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def brighter(k: Double = 1): C = withFacade(facade.brighter(k))

  /** Returns a darker copy of this color. If `k` is specified, it controls how
    * much darker the returned color should be. If `k` is not specified, it
    * defaults to `1`. The behavior of this method is dependent on the
    * implementing color space. */
  def darker(k: Double = 1): C = withFacade(facade.darker(k))

  /** Returns the RGB equivalent of this color. */
  def rgb(): Color.RGB = Color.rgb(this)

  /** Returns the HSL equivalent of this color. */
  def hsl(): Color.HSL = Color.hsl(this)

  /** Returns the Lab equivalent of this color. */
  def lab(): Color.Lab = Color.lab(this)

  /** Returns the HCL equivalent of this color. */
  def hcl(): Color.HCL = Color.hcl(this)

  /** Returns the Cubehelix equivalent of this color. */
  def cubehelix(): Color.Cubehelix = Color.cubehelix(this)

  /** Returns a string representing this color according to the
    * [CSS Object Model specification](https://drafts.csswg.org/cssom/#serialize-a-css-component-value),
    * such as `rgb(247, 234, 186)`. If this color is not displayable, a suitable
    * displayable color is returned instead. For example, RGB channel values
    * greater than 255 are clamped to 255. */
  override def toString: String = facade.toString
}
