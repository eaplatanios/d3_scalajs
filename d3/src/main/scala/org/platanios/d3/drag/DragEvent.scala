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

package org.platanios.d3.drag

import org.platanios.d3.D3Function

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait DragEvent[E <: dom.Element, D, S] extends js.Object {
  var target     : DragBehavior[E, D, S] = js.native
  var `type`     : String                = js.native
  var subject    : S                     = js.native
  var x          : Double                = js.native
  var y          : Double                = js.native
  var dx         : Double                = js.native
  var dy         : Double                = js.native
  var identifier : String | Double       = js.native
  var active     : Double                = js.native
  var sourceEvent: js.Any                = js.native

  def on(typenames: String): D3Function[E, D, Unit] = js.native
  def on(typenames: String, listener: D3Function[E, D, Unit]): this.type = js.native
  def on(typenames: String, listener: Null): this.type = js.native
}
