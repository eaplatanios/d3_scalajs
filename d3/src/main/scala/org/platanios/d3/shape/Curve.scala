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

package org.platanios.d3.shape

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** While lines are defined as a sequence of two-dimensional `(x, y)` points, and areas are similarly defined by a
  * top-line and a baseline, there remains the task of transforming this discrete representation into a continuous
  * shape (i.e., how to interpolate between the points). A variety of curves are provided for this purpose.
  *
  * Curves are typically not constructed or used directly, instead being passed to `Line.curve()` and `Area.curve()`.
  * For example:
  * {{{
  *   val line = d3.line()
  *     .x((d: js.Dictionary[Double]) => d("date"))
  *     .y((d: js.Dictionary[Double]) => d("value"))
  *     .curve(d3.curve.catmullRom.alpha(0.5))
  * }}}
  *
  * @author Emmanouil Antonios Platanios
  */
object Curve {
  /** Produces a cubic [[https://en.wikipedia.org/wiki/B-spline basis spline]] using the specified control points. The
    * first and last points are triplicated such that the spline starts at the first point and ends at the last point,
    * and is tangent to the line between the first and second points, and to the line between the penultimate and last
    * points.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/basis.png" width="888" height="240" alt="basis" style="max-width:100%;"/>
    */
  val basis: LineFactory = Facade.curveBasis

  /** Produces a closed cubic [[https://en.wikipedia.org/wiki/B-spline basis spline]] using the specified control
    * points. When a line segment ends, the first three control points are repeated, producing a closed loop with C2
    * continuity.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/basisClosed.png" width="888" height="240" alt="basisClosed" style="max-width:100%;"/>
    */
  val basisClosed: LineFactory = Facade.curveBasisClosed

  /** Produces a cubic [[https://en.wikipedia.org/wiki/B-spline basis spline]] using the specified control points.
    * Unlike basis, the first and last points are not repeated, and thus the curve typically does not intersect these
    * points.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/basisOpen.png" width="888" height="240" alt="basisOpen" style="max-width:100%;"/>
    */
  val basisOpen: LineFactory = Facade.curveBasisOpen

  /** Produces a straightened cubic [[https://en.wikipedia.org/wiki/B-spline basis spline]] using the specified control
    * points, with the spline straightened according to the curve's beta, which defaults to `0.85`. This curve is
    * typically used in [[http://bl.ocks.org/mbostock/7607999 hierarchical edge bundling]] to disambiguate connections,
    * as proposed by [[https://www.win.tue.nl/vis1/home/dholten/ Danny Holten]] in
    * [[https://www.win.tue.nl/vis1/home/dholten/papers/bundles_infovis.pdf Hierarchical Edge Bundles: Visualization of Adjacency Relations in Hierarchical Data]].
    * This curve does not implement `curve.areaStart()` and `curve.areaEnd()`; it is intended to work with `d3.line`,
    * not `d3.area`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/bundle.png" width="888" height="240" alt="bundle" style="max-width:100%;"/>
    */
  val bundle: BundleFactory = Facade.curveBundle

  /** Produces a cubic [[https://en.wikipedia.org/wiki/Cubic_Hermite_spline#Cardinal_spline cardinal spline]] using the
    * specified control points, with one-sided differences used for the first and last piece. The default tension is
    * `0`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/cardinal.png" width="888" height="240" alt="cardinal" style="max-width:100%;"/>
    */
  val cardinal: CardinalFactory = Facade.curveCardinal

  /** Produces a closed cubic [[https://en.wikipedia.org/wiki/Cubic_Hermite_spline#Cardinal_spline cardinal spline]]
    * using the specified control points. When a line segment ends, the first three control points are repeated,
    * producing a closed loop. The default tension is `0`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/cardinalClosed.png" width="888" height="240" alt="cardinalClosed" style="max-width:100%;"/>
    */
  val cardinalClosed: CardinalFactory = Facade.curveCardinalClosed

  /** Produces a cubic [[https://en.wikipedia.org/wiki/Cubic_Hermite_spline#Cardinal_spline cardinal spline]] using the
    * specified control points. Unlike curveCardinal, one-sided differences are not used for the first and last piece,
    * and thus the curve starts at the second point and ends at the penultimate point. The default tension is `0`.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/cardinalOpen.png" width="888" height="240" alt="cardinalOpen" style="max-width:100%;"/>
    */
  val cardinalOpen: CardinalFactory = Facade.curveCardinalOpen

  /** Produces a cubic Catmull–Rom spline using the specified control points and the parameter alpha, which defaults to
    * `0.5`, as proposed by Yuksel et al. in
    * [[http://www.cemyuksel.com/research/catmullrom_param/ On the Parameterization of Catmull–Rom Curves]], with
    * one-sided differences used for the first and last piece.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/catmullRom.png" width="888" height="240" alt="catmullRom" style="max-width:100%;"/>
    */
  val catmullRom: CatmullRomFactory = Facade.curveCatmullRom

  /** Produces a closed cubic Catmull–Rom spline using the specified control points and the parameter alpha, which
    * defaults to `0.5`, as proposed by Yuksel et al. When a line segment ends, the first three control points are
    * repeated, producing a closed loop.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/catmullRomClosed.png" width="888" height="330" alt="catmullRomClosed" style="max-width:100%;"/>
    */
  val catmullRomClosed: CatmullRomFactory = Facade.curveCatmullRomClosed

  /** Produces a cubic Catmull–Rom spline using the specified control points and the parameter alpha, which defaults to
    * `0.5`, as proposed by Yuksel et al. Unlike `curve.catmullRom`, one-sided differences are not used for the first
    * and last piece, and thus the curve starts at the second point and ends at the penultimate point.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/catmullRomOpen.png" width="888" height="240" alt="catmullRomOpen" style="max-width:100%;"/>
    */
  val catmullRomOpen: CatmullRomFactory = Facade.curveCatmullRomOpen

  /** Produces a polyline through the specified points.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/linear.png" width="888" height="240" alt="linear" style="max-width:100%;"/>
    */
  val linear: LineFactory = Facade.curveLinear

  /** Produces a closed polyline through the specified points by repeating the first point when the line segment ends.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/linearClosed.png" width="888" height="240" alt="linearClosed" style="max-width:100%;"/>
    */
  val linearClosed: LineFactory = Facade.curveLinearClosed

  /** Produces a cubic spline that [[https://en.wikipedia.org/wiki/Monotone_cubic_interpolation preserves monotonicity]]
    * in `y`, assuming monotonicity in `x`, as proposed by Steffen in
    * [[http://adsabs.harvard.edu/full/1990A%26A...239..443S A simple method for monotonic interpolation in one dimension]]:
    * "a smooth curve with continuous first-order derivatives that passes through any given set of data points without
    * spurious oscillations. Local extrema can occur only at grid points where they are given by the data, but not in
    * between two adjacent grid points."
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/monotoneX.png" width="888" height="240" alt="monotoneX" style="max-width:100%;"/>
    */
  val monotoneX: LineFactory = Facade.curveMonotoneX

  /** Produces a cubic spline that [[https://en.wikipedia.org/wiki/Monotone_cubic_interpolation preserves monotonicity]]
    * in `x`, assuming monotonicity in `y`, as proposed by Steffen in
    * [[http://adsabs.harvard.edu/full/1990A%26A...239..443S A simple method for monotonic interpolation in one dimension]]:
    * "a smooth curve with continuous first-order derivatives that passes through any given set of data points without
    * spurious oscillations. Local extrema can occur only at grid points where they are given by the data, but not in
    * between two adjacent grid points."
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/monotoneY.png" width="888" height="240" alt="monotoneY" style="max-width:100%;"/>
    */
  val monotoneY: LineFactory = Facade.curveMonotoneY

  /** Produces a [[https://en.wikipedia.org/wiki/Spline_interpolation natural]]
    * [[http://mathworld.wolfram.com/CubicSpline.html cubic spline]] with the second derivative of the spline set to
    * zero at the endpoints.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/natural.png" width="888" height="240" alt="natural" style="max-width:100%;"/>
    */
  val natural: LineFactory = Facade.curveNatural

  /** Produces a piecewise constant function (a [[https://en.wikipedia.org/wiki/Step_function step function]])
    * consisting of alternating horizontal and vertical lines. The `y`-value changes at the midpoint of each pair of
    * adjacent `x`-values.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/step.png" width="888" height="240" alt="step" style="max-width:100%;"/>
    */
  val step: LineFactory = Facade.curveStep

  /** Produces a piecewise constant function (a [[https://en.wikipedia.org/wiki/Step_function step function]])
    * consisting of alternating horizontal and vertical lines. The `y`-value changes after the `x`-value.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/stepAfter.png" width="888" height="240" alt="stepAfter" style="max-width:100%;"/>
    */
  val stepAfter: LineFactory = Facade.curveStepAfter

  /** Produces a piecewise constant function (a [[https://en.wikipedia.org/wiki/Step_function step function]])
    * consisting of alternating horizontal and vertical lines. The `y`-value changes before the `x`-value.
    *
    * <img src="https://raw.githubusercontent.com/d3/d3-shape/master/img/stepBefore.png" width="888" height="240" alt="stepBefore" style="max-width:100%;"/>
    */
  val stepBefore: LineFactory = Facade.curveStepBefore

  @js.native trait LineGenerator extends js.Object {
    /** Indicates the start of a new area segment. Each area segment consists of exactly two line segments: the
      * top-line, followed by the baseline, with the baseline points in reverse order. */
    def areaStart(): Unit = js.native

    /** Indicates the end of the current area segment. */
    def areaEnd(): Unit = js.native

    /** Indicates the start of a new line segment. Zero or more points will follow. */
    def lineStart(): Unit = js.native

    /** Indicates the end of the current line segment. */
    def lineEnd(): Unit = js.native

    /** Indicates a new point in the current line segment with the given `x`- and `y`-values. */
    def point(x: Double, y: Double): Unit = js.native
  }

  @js.native trait Factory[G <: LineGenerator] extends js.Object {
    def apply(context: dom.CanvasRenderingContext2D): G = js.native
    def apply(path: dom.svg.Path): G = js.native
  }

  @js.native trait LineFactory extends Factory[LineGenerator]

  @js.native trait BundleFactory extends LineFactory {
    /** Returns a bundle curve with the specified beta in the range `[0, 1]`, representing the bundle strength. If
      * `beta` equals zero, a straight line between the first and last point is produced. If `beta` equals one, a
      * standard basis spline is produced.
      *
      * For example:
      * {{{
      *   val line = d3.line().curve(d3.curve.bundle.beta(0.5))
      * }}}
      */
    def beta(beta: Double): BundleFactory = js.native
  }

  @js.native trait CardinalFactory extends Factory[LineGenerator] {
    /** Returns a cardinal curve with the specified tension in the range `[0, 1]`. The tension determines the length of
      * the tangents: a tension of one yields all zero tangents, equivalent to `curve.linear`. A tension of zero
      * produces a uniform Catmull–Rom spline.
      *
      * For example:
      * {{{
      *   val line = d3.line().curve(d3.curve.cardinal.tension(0.5))
      * }}}
      */
    def tension(tension: Double): CardinalFactory = js.native
  }

  @js.native trait CatmullRomFactory extends Factory[LineGenerator] {
    /** Returns a cubic Catmull–Rom curve with the specified alpha in the range `[0, 1]`. If alpha is zero, produces a
      * uniform spline, equivalent to `curve.cardinal` with a tension of zero. If `alpha` is one, produces a chordal
      * spline. If `alpha` is `0.5`, produces a centripetal spline. Centripetal splines are recommended to avoid
      * self-intersections and overshoot.
      *
      * For example:
      * {{{
      *   val line = d3.line().curve(d3.curve.catmullRom.alpha(0.5))
      * }}}
      */
    def alpha(alpha: Double): CatmullRomFactory = js.native
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[shape] object Facade extends js.Object {
    val curveBasis           : LineFactory       = js.native
    val curveBasisClosed     : LineFactory       = js.native
    val curveBasisOpen       : LineFactory       = js.native
    val curveBundle          : BundleFactory     = js.native
    val curveCardinal        : CardinalFactory   = js.native
    val curveCardinalClosed  : CardinalFactory   = js.native
    val curveCardinalOpen    : CardinalFactory   = js.native
    val curveCatmullRom      : CatmullRomFactory = js.native
    val curveCatmullRomClosed: CatmullRomFactory = js.native
    val curveCatmullRomOpen  : CatmullRomFactory = js.native
    val curveLinear          : LineFactory       = js.native
    val curveLinearClosed    : LineFactory       = js.native
    val curveMonotoneX       : LineFactory       = js.native
    val curveMonotoneY       : LineFactory       = js.native
    val curveNatural         : LineFactory       = js.native
    val curveStep            : LineFactory       = js.native
    val curveStepAfter       : LineFactory       = js.native
    val curveStepBefore      : LineFactory       = js.native
  }
}
