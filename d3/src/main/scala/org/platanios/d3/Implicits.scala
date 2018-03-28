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

package org.platanios.d3

import org.scalajs.dom

import scala.scalajs.js

private[d3] trait Implicits extends LowPriorityImplicits {
  implicit def nullD3Value(value: Null): D3Value = new NullD3Value(value)
  implicit def booleanD3Value(value: Boolean): D3Value = new BooleanD3Value(value)
  implicit def intD3Value(value: Int): D3Value = new IntD3Value(value)
  implicit def floatD3Value(value: Float): D3Value = new FloatD3Value(value)
  implicit def doubleD3Value(value: Double): D3Value = new DoubleD3Value(value)
  implicit def stringD3Value(value: String): D3Value = new StringD3Value(value)

  // implicit def d3ThisFn1ToD3Fn[N <: dom.EventTarget, D, R](f: (N) => R): D3Function[N, D, R] = (n: N, _: D, _: Index, _: Group) => f(n)
  implicit def d3ThisFn2ToD3Fn[N <: dom.EventTarget, D, R](f: (N, D) => R): D3Function[N, D, R] = (n: N, d: D, _: Index, _: Group) => f(n, d)
  implicit def d3ThisFn3ToD3Fn[N <: dom.EventTarget, D, R](f: (N, D, Index) => R): D3Function[N, D, R] = (n: N, d: D, i: Index, _: Group) => f(n, d, i)

  // implicit def d3JsThisFn0ToD3Fn[N <: dom.EventTarget, D, R](f: js.ThisFunction0[N, R]): D3Function[N, D, R] = (n: N, _: D, _: Index, _: Group) => f(n)
  implicit def d3JsThisFn1ToD3Fn[N <: dom.EventTarget, D, R](f: js.ThisFunction1[N, D, R]): D3Function[N, D, R] = (n: N, d: D, _: Index, _: Group) => f(n, d)
  implicit def d3JsThisFn2ToD3Fn[N <: dom.EventTarget, D, R](f: js.ThisFunction2[N, D, Index, R]): D3Function[N, D, R] = (n: N, d: D, i: Index, _: Group) => f(n, d, i)
}

private[d3] trait LowPriorityImplicits extends LowestPriorityImplicits {
  implicit def d3Fn0ToD3Fn[R](f: () => R): D3Function[dom.EventTarget, js.Any, R] = (_: dom.EventTarget, _: js.Any, _: Index, _: Group) => f()
  implicit def d3Fn1ToD3Fn[D, R](f: (D) => R): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, d: D, _: Index, _: Group) => f(d)
  implicit def d3Fn2ToD3Fn[D, R](f: (D, Index) => R): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, d: D, i: Index, _: Group) => f(d, i)
  implicit def d3Fn2ToD3Fn[D, R](f: (D, Index, Group) => R): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, d: D, i: Index, g: Group) => f(d, i, g)

  implicit def d3JsFn0ToD3Fn[R](f: js.Function0[R]): D3Function[dom.EventTarget, js.Any, R] = (_: dom.EventTarget, _: js.Any, _: Index, _: Group) => f()
  implicit def d3JsFn1ToD3Fn[D, R](f: js.Function1[D, R]): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, d: D, _: Index, _: Group) => f(d)
  implicit def d3JsFn2ToD3Fn[D, R](f: js.Function2[D, Index, R]): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, d: D, i: Index, _: Group) => f(d, i)
  implicit def d3JsFn2ToD3Fn[D, R](f: js.Function3[D, Index, Group, R]): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, d: D, i: Index, g: Group) => f(d, i, g)
}

private[d3] trait LowestPriorityImplicits {
  implicit def d3ValueToD3Fn[D, R](value: R): D3Function[dom.EventTarget, D, R] = (_: dom.EventTarget, _: D, _: Index, _: Group) => value
}
