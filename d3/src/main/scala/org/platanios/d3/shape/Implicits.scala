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

private[d3] object Implicits extends Implicits

private[d3] trait Implicits {
  //region Value Accessor

  implicit def d3ValueToD3ValueAccessor[D, R](value: R): D3ValueAccessor[D, R] = (_: D, _: Int, _: js.Array[D]) => value

  implicit def d3Fn1ToD3ValueAccessor[D, R](f: (D) => R): D3ValueAccessor[D, R] = (d: D, _: Int, _: js.Array[D]) => f(d)
  implicit def d3Fn2ToD3ValueAccessor[D, R](f: (D, Int) => R): D3ValueAccessor[D, R] = (d: D, i: Int, _: js.Array[D]) => f(d, i)
  implicit def d3Fn2ToD3ValueAccessor[D, R](f: (D, Int, Seq[D]) => R): D3ValueAccessor[D, R] = (d: D, i: Int, a: js.Array[D]) => f(d, i, a.toSeq)

  implicit def d3JsFn1ToD3ValueAccessor[D, R](f: js.Function1[D, R]): D3ValueAccessor[D, R] = (d: D, _: Int, _: js.Array[D]) => f(d)
  implicit def d3JsFn2ToD3ValueAccessor[D, R](f: js.Function2[D, Int, R]): D3ValueAccessor[D, R] = (d: D, i: Int, _: js.Array[D]) => f(d, i)

  //endregion Value Accessor

  //region Arc Accessor

  implicit def d3ValueToD3ArcAccessor[D, R](value: R): D3ArcAccessor[D, R] = (_: D) => value

  implicit def d3Fn1ToD3ArcAccessor[D, R](f: (D) => R): D3ArcAccessor[D, R] = (d: D) => f(d)

  implicit def d3JsFn1ToD3ArcAccessor[D, R](f: js.Function1[D, R]): D3ArcAccessor[D, R] = (d: D) => f(d)

  //endregion Arc Accessor
}
