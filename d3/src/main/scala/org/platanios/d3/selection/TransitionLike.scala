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

package org.platanios.d3.selection

import org.platanios.d3.D3Function

import org.scalajs.dom

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait TransitionLike[E <: dom.EventTarget, D] extends js.Object {
  def selection(): Selection[E, D, dom.Element, js.Any] = js.native
  def on(`type`: String, listener: Null): TransitionLike[E, D] = js.native
  def on(`type`: String, listener: D3Function[E, D, Unit]): TransitionLike[E, D] = js.native
  def tween(name: String, tweenFn: Null): TransitionLike[E, D] = js.native
  def tween(name: String, tweenFn: D3Function[E, D, js.Function1[Double, Unit]]): TransitionLike[E, D] = js.native
}
