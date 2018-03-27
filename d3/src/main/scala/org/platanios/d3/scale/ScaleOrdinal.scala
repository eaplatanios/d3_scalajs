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
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait ScaleOrdinal[Domain, Range] extends Scale[Domain, Range, Range] {
  def domain(domain: js.Array[Domain]): this.type = js.native
  def range(range: js.Array[Range]): this.type = js.native
  def unknown(): Range | js.Any = js.native
  def unknown(value: Range | js.Any): this.type = js.native
}
