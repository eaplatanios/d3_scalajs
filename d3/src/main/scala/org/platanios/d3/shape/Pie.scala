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

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** Represents a pie generator. The pie generator does not produce a shape directly, but instead computes the necessary
  * angles to represent a tabular dataset as a pie or donut chart. These angles can then be passed to an arc generator.
  *
  * @author Emmanouil Antonios Platanios
  */
class Pie[D] private[shape] (
    private[d3] val facade: Pie.Facade[D]
) {
  /** Generates a pie for the given array of data, returning an array of objects representing each datum's arc angles.
    * The length of the returned array is the same as data, and each element `i` in the returned array corresponds to
    * the element `i` in the input data.
    *
    * Given a small dataset of numbers, here is how to compute the arc angles to render this data as a pie chart:
    * {{{
    *   val data = Seq(1, 1, 2, 3, 5, 8, 13, 21)
    *   val arcs = d3.pie()(data)
    * }}}
    *
    * The first pair of parentheses, `pie()`, constructs a default pie generator. The second, `pie()(data)`, invokes 
    * this generator on the dataset, returning an array of `Pie.DatumArc[Int]` objects with the following fields:
    * {{{
    *   [{"data":  1, "value":  1, "index": 6, "startAngle": 6.050474740247008, "endAngle": 6.166830023713296, "padAngle": 0},
    *    {"data":  1, "value":  1, "index": 7, "startAngle": 6.166830023713296, "endAngle": 6.283185307179584, "padAngle": 0},
    *    {"data":  2, "value":  2, "index": 5, "startAngle": 5.817764173314431, "endAngle": 6.050474740247008, "padAngle": 0},
    *    {"data":  3, "value":  3, "index": 4, "startAngle": 5.468698322915565, "endAngle": 5.817764173314431, "padAngle": 0},
    *    {"data":  5, "value":  5, "index": 3, "startAngle": 4.886921905584122, "endAngle": 5.468698322915565, "padAngle": 0},
    *    {"data":  8, "value":  8, "index": 2, "startAngle": 3.956079637853813, "endAngle": 4.886921905584122, "padAngle": 0},
    *    {"data": 13, "value": 13, "index": 1, "startAngle": 2.443460952792061, "endAngle": 3.956079637853813, "padAngle": 0},
    *    {"data": 21, "value": 21, "index": 0, "startAngle": 0.000000000000000, "endAngle": 2.443460952792061, "padAngle": 0}]
    * }}}
    * Note that the returned array is in the same order as the data, even though this pie chart is sorted by descending
    * value, starting with the arc for the last datum (value `21`) at 12 o'clock.
    *
    * @param  data Array of data.
    * @return Array of objects representing each datum's arc angles.
    */
  def apply(data: Seq[D]): Seq[Pie.DatumArc[D]] = {
    facade(js.Array(data: _*))
  }

  /** Returns the current value accessor. */
  def value(): D3ValueAccessor[D, Double] = facade.value()

  /** Sets the value accessor to the specified function or number and returns this pie generator.
    *
    * When a pie is generated, the value accessor will be invoked for each element in the input data array, being passed
    * the element `d`, the index `i`, and the array data as three arguments. If your data are not simply numbers, then
    * you should specify an accessor that returns the corresponding numeric value for a given datum.
    *
    * For example:
    * {{{
    *   val data = Seq(
    *     Map("number" -> 4, "name" -> "Locke"),
    *     Map("number" -> 8, "name" -> "Reyes"),
    *     Map("number" -> 15, "name" -> "Ford"),
    *     Map("number" -> 16, "name" -> "Jarrah"),
    *     Map("number" -> 23, "name" -> "Shephard"),
    *     Map("number" -> 42, "name" -> "Kwon"))
    *
    *   val arcs = d3.pie(value = (d: Map[String, Int]) => d("number"))(data)
    * }}}
    * This is similar to mapping your data to values before invoking the pie generator:
    * {{{
    *   val arcs = d3.pie()(data.map(_("number")))
    * }}}
    *
    * The benefit of an accessor is that the input data remains associated with the returned objects, thereby making it
    * easier to access other fields of the data (for example, to set the color or to add text labels).
    *
    * @param  value Value accessor to use.
    * @return This pie generator after modifying its value accessor.
    */
  def value(value: D3ValueAccessor[D, Double]): Pie[D] = {
    facade.value(value)
    this
  }

  /** Returns the current data comparator, which defaults to `null`. */
  def sort(): (D, D) => Double = facade.sort()

  /** Sets the data comparator to the specified function and returns this pie generator.
    *
    * If both the data comparator and the value comparator are `null`, then arcs are positioned in the original input
    * order. Otherwise, the data is sorted according to the data comparator, and the resulting order is used. Setting
    * the data comparator implicitly sets the value comparator to `null`.
    *
    * The compare function takes two arguments `a` and `b`, each elements from the input data array. If the arc for `a`
    * should be before the arc for `b`, then the comparator must return a number less than zero;. If the arc for `a`
    * should be after the arc for `b`, then the comparator must return a number greater than zero. Returning zero means
    * that the relative order of `a` and `b` is unspecified.
    *
    * For example, to sort arcs by their associated name:
    * {{{
    *   pie.sort((a: Map[String, String], b: Map[String, String]) => a("name").localeCompare(b("name")))
    * }}}
    *
    * Sorting does not affect the order of the generated arc array which is always in the same order as the input data
    * array. It merely affects the computed angles of each arc. The first arc starts at the start angle and the last arc
    * ends at the end angle.
    *
    * @param  comparator Data comparator to use.
    * @return This pie generator after modifying its data comparator.
    */
  def sort(comparator: (D, D) => Double): Pie[D] = {
    facade.sort(comparator)
    this
  }

  /** Returns the current values comparator, which defaults to `null`. */
  def sortValues(): (Double, Double) => Double = facade.sortValues()

  /** Sets the value comparator to the specified function and returns this pie generator.
    *
    * If both the data comparator and the value comparator are `null`, then arcs are positioned in the original input
    * order. Otherwise, the data is sorted according to the data comparator, and the resulting order is used. Setting
    * the value comparator implicitly sets the data comparator to `null`.
    *
    * The value comparator is similar to the data comparator, except that the two arguments `a` and `b` are values
    * derived from the input data array using the value accessor, not the data elements. If the arc for `a` should be
    * before the arc for `b`, then the comparator must return a number less than zero. If the arc for `a` should be
    * after the arc for `b`, then the comparator must return a number greater than zero. Returning zero means that the
    * relative order of `a` and `b` is unspecified.
    *
    * For example, to sort arcs by ascending value:
    * {{{
    *   pie.sortValues((a: Double, b: Double) => a - b)
    * }}}
    *
    * Sorting does not affect the order of the generated arc array which is always in the same order as the input data
    * array. It merely affects the computed angles of each arc. The first arc starts at the start angle and the last arc
    * ends at the end angle.
    *
    * @param  comparator Values comparator to use.
    * @return This pie generator after modifying its values comparator.
    */
  def sortValues(comparator: (Double, Double) => Double): Pie[D] = {
    facade.sortValues(comparator)
    this
  }

  /** Returns the current start angle accessor. */
  def startAngle(): D3ArcAccessor[D, Double] = facade.startAngle()

  /** Sets the start angle to the specified function or number and returns this pie generator.
    *
    * The start angle here means the overall start angle of the pie (i.e., the start angle of the first arc). The start
    * angle accessor is invoked once, being passed the same arguments and this context as the pie generator. The units
    * of angle are arbitrary, but if you plan to use the pie generator in conjunction with an arc generator, you should
    * specify an angle in radians, with `0` at `-y` (12 o'clock) and positive angles proceeding clockwise.
    *
    * @param  angle Start angle accessor to use.
    * @return This pie generator after modifying its start angle accessor.
    */
  def startAngle(angle: D3ArcAccessor[D, Double]): Pie[D] = {
    facade.startAngle(angle)
    this
  }

  /** Returns the current end angle accessor. */
  def endAngle(): D3ArcAccessor[D, Double] = facade.endAngle()

  /** Sets the end angle to the specified function or number and returns this pie generator.
    *
    * The end angle here means the overall end angle of the pie (i.e., the end angle of the last arc). The end angle
    * accessor is invoked once, being passed the same arguments and this context as the pie generator. The units of
    * angle are arbitrary, but if you plan to use the pie generator in conjunction with an arc generator, you should
    * specify an angle in radians, with `0` at `-y` (12 o'clock) and positive angles proceeding clockwise.
    *
    * The value of the end angle is constrained to `startAngle ± τ`, such that `|endAngle - startAngle| ≤ τ`.
    *
    * @param  angle End angle accessor to use.
    * @return This pie generator after modifying its end angle accessor.
    */
  def endAngle(angle: D3ArcAccessor[D, Double]): Pie[D] = {
    facade.endAngle(angle)
    this
  }

  /** Returns the current pad angle accessor. */
  def padAngle(): D3ArcAccessor[D, Double] = facade.padAngle()

  /** Sets the pad angle to the specified function or number and returns this pie generator.
    *
    * The pad angle here means the angular separation between each adjacent arc. The total amount of padding reserved is
    * the specified angle times the number of elements in the input data array, and at most `|endAngle - startAngle|`.
    * The remaining space is then divided proportionally by value such that the relative area of each arc is preserved.
    * See the [[http://bl.ocks.org/mbostock/3e961b4c97a1b543fff2 pie padding animation]] for an illustration.
    *
    * The pad angle accessor is invoked once, being passed the same arguments and this context as the pie generator.
    * The units of angle are arbitrary, but if you plan to use the pie generator in conjunction with an arc generator,
    * you should specify an angle in radians.
    *
    * @param  angle Pad angle accessor to use.
    * @return This pie generator after modifying its pad angle accessor.
    */
  def padAngle(angle: D3ArcAccessor[D, Double]): Pie[D] = {
    facade.padAngle(angle)
    this
  }
}

object Pie {
  def apply[D](
      value: D3ValueAccessor[D, Double] = null,
      startAngle: D3ArcAccessor[D, Double] = null,
      endAngle: D3ArcAccessor[D, Double] = null,
      padAngle: D3ArcAccessor[D, Double] = null
  ): Pie[D] = {
    val facade = Facade.pie[D]()
    if (value != null)
      facade.value(value)
    if (startAngle != null)
      facade.startAngle(startAngle)
    if (endAngle != null)
      facade.endAngle(endAngle)
    if (padAngle != null)
      facade.padAngle(padAngle)
    new Pie[D](facade)
  }

  /** Represents each input datum's arc angles. This representation is designed to work with the arc generator's default
    * `startAngle`, `endAngle`, and `padAngle` accessors. The angular units are arbitrary, but if you plan to use the
    * pie generator in conjunction with an arc generator, you should specify angles in radians, with `0` at `-y`
    * (12 o'clock) and positive angles proceeding clockwise.
    */
  @js.native trait DatumArc[D] extends js.Object {
    /** Corresponding element in the input data array. */
    val data: D = js.native

    /** Numeric value of the arc. */
    val value: Double = js.native

    /** Zero-based sorted index of the arc. */
    val index: Double = js.native

    /** Start angle of the arc. */
    val startAngle: Double = js.native

    /** End angle of the arc. */
    val endAngle: Double = js.native

    /** Pad angle of the arc. */
    val padAngle: Double = js.native
  }

  @JSImport("d3-shape", JSImport.Namespace)
  @js.native private[d3] object Facade extends js.Object {
    def pie[D](): Facade[D] = js.native
  }

  @js.native private[d3] trait Facade[D] extends js.Object {
    def apply(data: js.Array[D]): js.Array[DatumArc[D]] = js.native
    def value(): D3ValueAccessor[D, Double] = js.native
    def value(value: D3ValueAccessor[D, Double]): Facade[D] = js.native
    def sort(): js.Function2[D, D, Double] = js.native
    def sort(comparator: js.Function2[D, D, Double]): Facade[D] = js.native
    def sortValues(): js.Function2[Double, Double, Double] = js.native
    def sortValues(comparator: js.Function2[Double, Double, Double]): Facade[D] = js.native
    def startAngle(): D3ArcAccessor[D, Double] = js.native
    def startAngle(angle: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def endAngle(): D3ArcAccessor[D, Double] = js.native
    def endAngle(angle: D3ArcAccessor[D, Double]): Facade[D] = js.native
    def padAngle(): D3ArcAccessor[D, Double] = js.native
    def padAngle(angle: D3ArcAccessor[D, Double]): Facade[D] = js.native
  }
}
