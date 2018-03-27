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

package org.platanios.d3.array

import org.platanios.d3.ArrayLike

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait HistogramGenerator[D, V] extends js.Object {
  def apply(data: ArrayLike[D]): js.Array[Bin[D, V]] = js.native
  def value(): js.Function3[D, Double, ArrayLike[D], V] = js.native
  def value(valueAccessor: js.Function3[D, Double, ArrayLike[D], V]): this.type = js.native
  def domain(): js.Function1[ArrayLike[V], js.Tuple2[V, V]] = js.native
  def domain(domain: js.Tuple2[V, V]): this.type = js.native
  def domain(domainAccessor: js.Function1[ArrayLike[V], js.Tuple2[V, V]]): this.type = js.native
  def thresholds(): ThresholdCountGenerator | ThresholdArrayGenerator[V] = js.native
  def thresholds(count: Double): this.type = js.native
  def thresholds(count: ThresholdCountGenerator): this.type = js.native
  def thresholds(thresholds: ArrayLike[V]): this.type = js.native
  // TODO: !!! Use a type trait for this.
  // def thresholds(thresholds: ThresholdArrayGenerator[V]): this.type = js.native
}
