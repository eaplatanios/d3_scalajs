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
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.typedarray._
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-array", JSImport.Namespace)
@js.native object Array extends js.Object {
//  def max(array: ArrayLike[String]): String | Unit = js.native
//  def max[T <: Numeric](array: ArrayLike[T]): T | Unit = js.native
//  def max[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], String | Unit | Null]): String | Unit = js.native
//  def max[T, U <: Numeric](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], U | Unit | Null]): U | Unit = js.native

//  def min(array: ArrayLike[String]): String | Unit = js.native
//  def min[T <: Numeric](array: ArrayLike[T]): T | Unit = js.native
//  def min[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], String | Unit | Null]): String | Unit = js.native
//  def min[T, U <: Numeric](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], U | Unit | Null]): U | Unit = js.native
//
//  def extent(array: ArrayLike[String]): js.Tuple2[String, String] | js.Tuple2[Unit, Unit] = js.native
//  def extent[T <: Numeric](array: ArrayLike[T]): js.Tuple2[T, T] | js.Tuple2[Unit, Unit] = js.native
//  def extent[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], String | Unit | Null]): js.Tuple2[String, String] | js.Tuple2[Unit, Unit] = js.native
//  def extent[T, U <: Numeric](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], U | Unit | Null]): js.Tuple2[U, U] | js.Tuple2[Unit, Unit] = js.native

  def mean[T <: Numeric](array: ArrayLike[T | Unit | Null]): Double | Unit = js.native
  def mean[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], Double | Unit | Null]): Double | Unit = js.native
  def median[T <: Numeric](array: ArrayLike[T | Unit | Null]): Double | Unit = js.native
  def median[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], Double | Unit | Null]): Double | Unit = js.native
  def quantile[T <: Numeric](array: ArrayLike[T | Unit | Null], p: Double): Double | Unit = js.native
  def quantile[T](array: ArrayLike[T], p: Double, accessor: js.Function3[T, Double, ArrayLike[T], Double | Unit | Null]): Double | Unit = js.native
  def sum[T <: Numeric](array: ArrayLike[T | Unit | Null]): Double = js.native
  def sum[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], Double | Unit | Null]): Double = js.native
  def deviation[T <: Numeric](array: ArrayLike[T | Unit | Null]): Double | Unit = js.native
  def deviation[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], Double | Unit | Null]): Double | Unit = js.native
  def variance[T <: Numeric](array: ArrayLike[T | Unit | Null]): Double | Unit = js.native
  def variance[T](array: ArrayLike[T], accessor: js.Function3[T, Double, ArrayLike[T], Double | Unit | Null]): Double | Unit = js.native

  def scan(array: ArrayLike[Double]): Double | Unit = js.native
  def scan(array: ArrayLike[Double], comparator: js.Function2[Double, Double, Double]): Double | Unit = js.native
  // def scan[T](array: ArrayLike[T], comparator: js.Function2[T, T, Double]): Double | Unit = js.native

  def bisectLeft(array: ArrayLike[Double], x: Double): Double = js.native
  def bisectLeft(array: ArrayLike[Double], x: Double, lo: Double): Double = js.native
  def bisectLeft(array: ArrayLike[Double], x: Double, lo: Double, hi: Double): Double = js.native
  def bisectLeft(array: ArrayLike[String], x: String): Double = js.native
  def bisectLeft(array: ArrayLike[String], x: String, lo: Double): Double = js.native
  def bisectLeft(array: ArrayLike[String], x: String, lo: Double, hi: Double): Double = js.native
  def bisectLeft(array: ArrayLike[js.Date], x: js.Date): Double = js.native
  def bisectLeft(array: ArrayLike[js.Date], x: js.Date, lo: Double): Double = js.native
  def bisectLeft(array: ArrayLike[js.Date], x: js.Date, lo: Double, hi: Double): Double = js.native

  def bisectRight(array: ArrayLike[Double], x: Double): Double = js.native
  def bisectRight(array: ArrayLike[Double], x: Double, lo: Double): Double = js.native
  def bisectRight(array: ArrayLike[Double], x: Double, lo: Double, hi: Double): Double = js.native
  def bisectRight(array: ArrayLike[String], x: String): Double = js.native
  def bisectRight(array: ArrayLike[String], x: String, lo: Double): Double = js.native
  def bisectRight(array: ArrayLike[String], x: String, lo: Double, hi: Double): Double = js.native
  def bisectRight(array: ArrayLike[js.Date], x: js.Date): Double = js.native
  def bisectRight(array: ArrayLike[js.Date], x: js.Date, lo: Double): Double = js.native
  def bisectRight(array: ArrayLike[js.Date], x: js.Date, lo: Double, hi: Double): Double = js.native

  val bisect: js.Function = js.native
  def bisector[T, U](comparator: js.Function2[T, U, Double]): Bisector[T, U] = js.native
  def bisector[T, U](accessor: js.Function1[T, U]): Bisector[T, U] = js.native

  // TODO: !!!
  // type Primitive = Double | String | Boolean | Date
  // def ascending(a: Primitive | Unit, b: Primitive | Unit): Double = js.native
  // def descending(a: Primitive | Unit, b: Primitive | Unit): Double = js.native

  def cross[S, T](a: ArrayLike[S], b: ArrayLike[T]): js.Array[js.Tuple2[S, T]] = js.native
  def cross[S, T, U](a: ArrayLike[S], b: ArrayLike[T], reducer: js.Function2[S, T, U]): js.Array[U] = js.native
  def merge[T](arrays: ArrayLike[ArrayLike[T]]): js.Array[T] = js.native
  def pairs[T](array: ArrayLike[T]): js.Array[js.Tuple2[T, T]] = js.native
  def pairs[T, U](array: ArrayLike[T], reducer: js.Function2[T, T, U]): js.Array[U] = js.native
  def permute[T](array: js.Any, keys: ArrayLike[Double]): js.Array[T] = js.native
  def permute[T](`object`: js.Dictionary[T], keys: ArrayLike[String]): js.Array[T] = js.native
  def range(stop: Double): js.Array[Double] = js.native
  def range(start: Double, stop: Double, step: Double = ???): js.Array[Double] = js.native

  def shuffle[T](array: js.Array[T]): js.Array[T] = js.native
  def shuffle[T](array: js.Array[T], lo: Double): js.Array[T] = js.native
  def shuffle[T](array: js.Array[T], lo: Double, hi: Double): js.Array[T] = js.native
//  def shuffle(array: Int8Array, lo: Double = ???, hi: Double = ???): Int8Array = js.native
//  def shuffle(array: Uint8Array, lo: Double = ???, hi: Double = ???): Uint8Array = js.native
//  def shuffle(array: Uint8ClampedArray, lo: Double = ???, hi: Double = ???): Uint8ClampedArray = js.native
//  def shuffle(array: Int16Array, lo: Double = ???, hi: Double = ???): Int16Array = js.native
//  def shuffle(array: Uint16Array, lo: Double = ???, hi: Double = ???): Uint16Array = js.native
//  def shuffle(array: Int32Array, lo: Double = ???, hi: Double = ???): Int32Array = js.native
//  def shuffle(array: Uint32Array, lo: Double = ???, hi: Double = ???): Uint32Array = js.native
//  def shuffle(array: Float32Array, lo: Double = ???, hi: Double = ???): Float32Array = js.native
//  def shuffle(array: Float64Array, lo: Double = ???, hi: Double = ???): Float64Array = js.native

  def ticks(start: Double, stop: Double, count: Double): js.Array[Double] = js.native
  def tickIncrement(start: Double, stop: Double, count: Double): Double = js.native
  def tickStep(start: Double, stop: Double, count: Double): Double = js.native
  def transpose[T](matrix: ArrayLike[ArrayLike[T]]): js.Array[js.Array[T]] = js.native
  def zip[T](arrays: ArrayLike[T]*): js.Array[js.Array[T]] = js.native

  def histogram(): HistogramGenerator[Double, Double] = js.native
  def histogram[D, V: NumberOrDate](): HistogramGenerator[D, V] = js.native
  def thresholdFreedmanDiaconis(values: ArrayLike[Double], min: Double, max: Double): Double = js.native
  def thresholdScott(values: ArrayLike[Double], min: Double, max: Double): Double = js.native
  def thresholdSturges(values: ArrayLike[Double]): Double = js.native
}
