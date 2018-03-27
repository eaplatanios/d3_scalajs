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

package org.platanios.d3

import org.platanios.d3.color.ColorCommonInstance
import org.platanios.d3.zoom.ZoomView

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSImport

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-interpolate", JSImport.Namespace)
@js.native object Interpolate extends js.Object {
  // TODO: Fix argument union types.

  def interpolate(a: js.Any, b: Null): js.Function1[Double, Null] = js.native
  def interpolate(a: Double | js.Any, b: Double): js.Function1[Double, Double] = js.native
  def interpolate(a: js.Any, b: ColorCommonInstance): js.Function1[Double, String] = js.native
  def interpolate(a: js.Date, b: js.Date): js.Function1[Double, js.Date] = js.native
  def interpolate(a: String | js.Any, b: String): js.Function1[Double, String] = js.native
  def interpolate[U <: js.Array[js.Any]](a: js.Array[js.Any], b: U): js.Function1[Double, U] = js.native
  def interpolate(a: Double | js.Any, b: js.Any): js.Function1[Double, Double] = js.native
  def interpolate[U <: js.Any](a: js.Any, b: U): js.Function1[Double, U] = js.native
  def interpolate(a: js.Any, b: js.Dictionary[js.Any]): js.Function1[Double, js.Dictionary[js.Any]] = js.native
  def interpolateNumber(a: Double | js.Any, b: Double | js.Any): js.Function1[Double, Double] = js.native
  def interpolateRound(a: Double | js.Any, b: Double | js.Any): js.Function1[Double, Double] = js.native
  def interpolateString(a: String | js.Any, b: String | js.Any): js.Function1[Double, String] = js.native
  def interpolateDate(a: js.Date, b: js.Date): js.Function1[Double, js.Date] = js.native
  def interpolateArray[A <: js.Array[js.Any]](a: js.Array[js.Any], b: A): js.Function1[Double, A] = js.native
  def interpolateObject[U <: js.Any](a: js.Any, b: U): js.Function1[Double, U] = js.native
  def interpolateObject(a: js.Dictionary[js.Any], b: js.Dictionary[js.Any]): js.Function1[Double, js.Dictionary[js.Any]] = js.native
  def interpolateTransformCss(a: String, b: String): js.Function1[Double, String] = js.native
  def interpolateTransformSvg(a: String, b: String): js.Function1[Double, String] = js.native
  def interpolateZoom(a: ZoomView, b: ZoomView): ZoomInterpolator = js.native
  def quantize[T](interpolator: js.Function1[Double, T], n: Double): js.Array[T] = js.native
  val interpolateRgb: ColorGammaInterpolationFactory = js.native
  def interpolateRgbBasis(colors: js.Array[String | ColorCommonInstance]): js.Function1[Double, String] = js.native
  def interpolateRgbBasisClosed(colors: js.Array[String | ColorCommonInstance]): js.Function1[Double, String] = js.native
  def interpolateHsl(a: String | ColorCommonInstance, b: String | ColorCommonInstance): js.Function1[Double, String] = js.native
  def interpolateHslLong(a: String | ColorCommonInstance, b: String | ColorCommonInstance): js.Function1[Double, String] = js.native
  def interpolateLab(a: String | ColorCommonInstance, b: String | ColorCommonInstance): js.Function1[Double, String] = js.native
  def interpolateHcl(a: String | ColorCommonInstance, b: String | ColorCommonInstance): js.Function1[Double, String] = js.native
  def interpolateHclLong(a: String | ColorCommonInstance, b: String | ColorCommonInstance): js.Function1[Double, String] = js.native
  val interpolateCubehelix    : ColorGammaInterpolationFactory = js.native
  val interpolateCubehelixLong: ColorGammaInterpolationFactory = js.native
  def interpolateBasis(splineNodes: js.Array[Double]): js.Function1[Double, Double] = js.native
  def interpolateBasisClosed(splineNodes: js.Array[Double]): js.Function1[Double, Double] = js.native
}

@js.native trait ZoomInterpolator extends js.Function {
  def apply(t: Double): ZoomView = js.native

  /** Recommended duration of the zoom transition in ms. */
  var duration: Double = js.native
}

@js.native trait ColorGammaInterpolationFactory extends js.Function {
  def apply(
      a: String | ColorCommonInstance,
      b: String | ColorCommonInstance
  ): js.Function1[Double, String] = js.native

  def gamma(g: Double): ColorGammaInterpolationFactory = js.native
}
