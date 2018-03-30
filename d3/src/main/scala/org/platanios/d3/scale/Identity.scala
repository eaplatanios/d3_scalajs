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

/** Identity scales are a special case of linear scales where the domain and range are identical; the scale and its
  * invert method are thus the identity function. These scales are occasionally useful when working with pixel
  * coordinates, say in conjunction with an axis or a brush.
  *
  * @author Emmanouil Antonios Platanios
  */
class Identity protected (
    override private[d3] val facade: Identity.Facade
) extends ContinuousNumeric[Double, Double, Identity.Facade] {
  override protected def withFacade(facade: Identity.Facade): Identity = new Identity(facade)
}

object Identity {
  @js.native private[d3] trait Facade extends ContinuousNumeric.Facade[Double, Double, Facade]

  @JSImport("d3-scale", JSImport.Namespace)
  @js.native private[Identity] object Facade extends js.Object {
    def scaleIdentity(): Identity.Facade = js.native
  }

  trait API {
    def identity(
        domain: Seq[Double] = Seq(0.0, 1.0),
        nice: Boolean = false,
        niceCount: Int = -1
    ): Identity = Identity(domain, nice, niceCount)
  }

  /** Constructs a new identity scale.
    *
    * @param  domain    Domain for the new scale, that also acts as its range.
    * @param  nice      If `true`, the domain will be extended so that it starts and ends on nice round values. This
    *                   method typically modifies the scale’s domain, and may only extend the bounds to the nearest
    *                   round value. An optional tick count argument. "Nicing" is useful if the domain is computed from
    *                   data, say using `extent`, and may be irregular. For example, for a domain of
    *                   `[0.201479..., 0.996679...]`, a nice domain might be `[0.2, 1.0]`. If the domain has more than
    *                   two values, "nicing" the domain only affects the first and last value.
    * @param  niceCount Optional value that allows greater control over the step size used to extend the bounds, when
    *                   `nice` is set to `true`, guaranteeing that the returned ticks will exactly cover the domain.
    * @return New scale.
    */
  def apply(
      domain: Seq[Double] = Seq(0.0, 1.0),
      nice: Boolean = false,
      niceCount: Int = -1
  ): Identity = {
    val facade = Facade.scaleIdentity()
        .domain(js.Array(domain: _*))
        .range(js.Array(domain: _*))
    if (nice && niceCount > 0)
      facade.nice(niceCount)
    else if (nice)
      facade.nice()
    new Identity(facade)
  }
}
