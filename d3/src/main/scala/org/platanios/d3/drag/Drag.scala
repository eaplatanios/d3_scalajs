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

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-drag", JSImport.Namespace)
@js.native object Drag extends js.Object {
  def drag[GElement <: dom.Element, Datum](): DragBehavior[GElement, Datum, Datum | SubjectPosition] = js.native
  // def drag[GElement <: dom.Element, Datum, Subject](): DragBehavior[GElement, Datum, Subject] = js.native
  def dragDisable(window: dom.Window): Unit = js.native
  def dragEnable(window: dom.Window, noClick: Boolean = ???): Unit = js.native
}
