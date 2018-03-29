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
import scala.scalajs.js.|

/** Unlike continuous scales, ordinal scales have a discrete domain and range. For example, an ordinal scale might map
  * a set of named categories to a set of colors, or determine the horizontal positions of columns in a column
  * chart, etc.
  *
  * Given a value in the input domain, this scale returns the corresponding value in the output range. If the given
  * value is not in the scale’s domain, returns the unknown; or, if the unknown value is implicit (the default), then
  * the value is implicitly added to the domain and the next-available value in the range is assigned to value, such
  * that this and subsequent invocations of the scale given the same input value return the same output value.
  *
  * @author Emmanouil Antonios Platanios
  */
class Ordinal[Domain, Range] protected (
    override private[d3] val facade: Ordinal.Facade[Domain, Range]
) extends Scale[Domain, Range, Range, Ordinal.Facade[Domain, Range]] {
  /** Returns the value used for unknown input values. */
  def unknown(): Range | js.Any = facade.unknown()

  override protected def copy(facade: Ordinal.Facade[Domain, Range]): Ordinal[Domain, Range] = {
    new Ordinal(facade)
  }
}

object Ordinal {
  @js.native private[d3] trait Facade[Domain, Range]
      extends Scale.Facade[Domain, Range, Range, Facade[Domain, Range]] {
    def domain(domain: js.Array[Domain]): this.type = js.native
    def range(range: js.Array[Range]): this.type = js.native
    def unknown(value: Range | js.Any): this.type = js.native

    def unknown(): Range | js.Any = js.native
  }

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Ordinal] object Facade extends js.Object {
    val scaleImplicit: js.Any = js.native

    def scaleOrdinal[Domain, Range](range: js.Array[Range]): Ordinal.Facade[Domain, Range] = js.native
  }

  /** Special value for unknown values that enables implicit domain construction: unknown values are implicitly added
    * to the domain. */
  val implicitUnknown: js.Any = Facade.scaleImplicit

  trait API {
    def ordinal[Domain, Range](
        range: Seq[Range],
        domain: Seq[Domain] = null,
        unknown: Range = null
    ): Ordinal[Domain, Range] = Ordinal(range, domain, unknown)
  }

  /** Constructs a new ordinal scale.
    *
    * @param  range  Range for the new scale. The first element in the domain will be mapped to the first element in
    *                range, the second domain value to the second range value, and so on. If there are fewer elements
    *                in the range than in the domain, the scale will reuse values from the start of the range. If range
    *                is not specified, this method returns the current range.
    * @param  domain Domain for the new scale. The first element in domain will be mapped to the first element in the
    *                range, the second domain value to the second range value, and so on. Domain values are stored
    *                internally in a map from string representations of the values to their indices. The resulting
    *                index is then used to retrieve a value from the range. Thus, an ordinal scale's values must be
    *                coercible to a string, and the string version of the domain value uniquely identifies the
    *                corresponding range value. Setting the domain on an ordinal scale is optional if the unknown value
    *                is implicit (the default). In this case, the domain will be inferred implicitly from usage by
    *                assigning each unique value passed to the scale a new value from the range. Note that an explicit
    *                domain is recommended to ensure deterministic behavior, as inferring the domain from usage will be
    *                dependent on ordering.
    * @param unknown Output value of the scale for unknown input values and returns this scale. The
    *                `Ordinal.implicitUnknown` value (default) enables implicit domain construction.
    * @return New scale.
    */
  def apply[Domain, Range](
      range: Seq[Range],
      domain: Seq[Domain] = null,
      unknown: Range = null
  ): Ordinal[Domain, Range] = {
    val facade = Facade.scaleOrdinal[Domain, Range](js.Array(range: _*))
    if (domain != null)
      facade.domain(js.Array(domain: _*))
    if (unknown != null)
      facade.unknown(unknown)
    new Ordinal(facade)
  }
}
