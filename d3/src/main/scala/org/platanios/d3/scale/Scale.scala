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

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-scale", JSImport.Namespace)
@js.native object Scale extends js.Object {
  // TODO: !!! Create constructors for scales that are strongly-typed (both domain and range).

  def scaleLinear(): ScaleLinear[Double, Double] = js.native
  // def scaleLinear[Output](): ScaleLinear[Output, Output] = js.native
  // def scaleLinear[Range, Output](): ScaleLinear[Range, Output] = js.native
  def scalePow(): ScalePower[Double, Double] = js.native
  // def scalePow[Output](): ScalePower[Output, Output] = js.native
  // def scalePow[Range, Output](): ScalePower[Range, Output] = js.native
  def scaleSqrt(): ScalePower[Double, Double] = js.native
  // def scaleSqrt[Output](): ScalePower[Output, Output] = js.native
  // def scaleSqrt[Range, Output](): ScalePower[Range, Output] = js.native
  def scaleLog(): ScaleLogarithmic[Double, Double] = js.native
  // def scaleLog[Output](): ScaleLogarithmic[Output, Output] = js.native
  // def scaleLog[Range, Output](): ScaleLogarithmic[Range, Output] = js.native
  def scaleIdentity(): ScaleIdentity = js.native
  def scaleTime(): ScaleTime[Double, Double] = js.native
  // def scaleTime[Output](): ScaleTime[Output, Output] = js.native
  // def scaleTime[Range, Output](): ScaleTime[Range, Output] = js.native
  def scaleUtc(): ScaleTime[Double, Double] = js.native
  // def scaleUtc[Output](): ScaleTime[Output, Output] = js.native
  // def scaleUtc[Range, Output](): ScaleTime[Range, Output] = js.native
  def scaleSequential[Output](interpolator: js.Function1[Double, Output]): ScaleSequential[Output] = js.native
  def scaleQuantize(): ScaleQuantize[Double] = js.native
  // def scaleQuantize[Range](): ScaleQuantize[Range] = js.native
  def scaleQuantile(): ScaleQuantile[Double] = js.native
  // def scaleQuantile[Range](): ScaleQuantile[Range] = js.native
  def scaleThreshold(): ScaleThreshold[Double, Double] = js.native
  // def scaleThreshold[Domain <: ScaleThresholdDomain, Range](): ScaleThreshold[Domain, Range] = js.native
  def scaleOrdinal[Range](): ScaleOrdinal[String, Range] = js.native
  def scaleOrdinal[Range](range: js.Array[Range]): ScaleOrdinal[String, Range] = js.native
  // def scaleOrdinal[Domain, Range](): ScaleOrdinal[Domain, Range] = js.native
  // def scaleOrdinal[Domain, Range](range: js.Array[Range]): ScaleOrdinal[Domain, Range] = js.native
  def scaleBand(): ScaleBand[String] = js.native
  // def scaleBand[Domain](): ScaleBand[Domain] = js.native
  def scalePoint(): ScalePoint[String] = js.native
  // def scalePoint[Domain](): ScalePoint[Domain] = js.native
}

trait Scale[Domain, Range, Output] extends js.Object {
  def apply(x: Domain): Output = js.native
  def domain(): js.Array[Domain] = js.native
  def range(): js.Array[Range] = js.native
  def copy(): this.type = js.native
  def bandwidth(): Double = js.native
}
