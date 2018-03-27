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

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
package object selection {
  // TODO: !!! Incorporate element and datum types as fixed.
  trait D3ValueOrFn[T, E[_]]

  object D3ValueOrFn {
    implicit def d3Value[T: E, E[_]]: D3ValueOrFn[T, E] = new D3ValueOrFn[T, E] {}
    implicit def d3Fn0[T: E, E[_]]: D3ValueOrFn[D3Function0[T], E] = new D3ValueOrFn[D3Function0[T], E] {}
    implicit def d3Fn1[D, T: E, E[_]]: D3ValueOrFn[D3Function1[D, T], E] = new D3ValueOrFn[D3Function1[D, T], E] {}
    implicit def d3Fn2[D, T: E, E[_]]: D3ValueOrFn[D3Function2[D, T], E] = new D3ValueOrFn[D3Function2[D, T], E] {}
    implicit def d3Fn3[D, T: E, E[_]]: D3ValueOrFn[D3Function3[D, T], E] = new D3ValueOrFn[D3Function3[D, T], E] {}
  }

  trait D3AttrValue[T]

  object D3AttrValue {
    implicit val d3Null   : D3AttrValue[Null]    = new D3AttrValue[Null] {}
    implicit val d3Boolean: D3AttrValue[Boolean] = new D3AttrValue[Boolean] {}
    implicit val d3Int    : D3AttrValue[Int]     = new D3AttrValue[Int] {}
    implicit val d3Float  : D3AttrValue[Float]   = new D3AttrValue[Float] {}
    implicit val d3Double : D3AttrValue[Double]  = new D3AttrValue[Double] {}
    implicit val d3String : D3AttrValue[String]  = new D3AttrValue[String] {}
  }

  trait D3Text[T]

  object D3Text {
    implicit val d3Null  : D3Text[Null]   = new D3Text[Null] {}
    implicit val d3String: D3Text[String] = new D3Text[String] {}
  }

  trait D3Property[T]

  object D3Property {
    implicit val d3Null: D3Property[Null]   = new D3Property[Null] {}
    implicit val d3Any : D3Property[js.Any] = new D3Property[js.Any] {}
  }

  type Is[V] = {type E[T] = =:=[V, T]}
  type D3VOrFn[V] = {type CB[T] = D3ValueOrFn[T, Is[V]#E]}
  type D3AttrValueOrFn[T] = D3ValueOrFn[T, D3AttrValue]
  type D3TextOrFn[T] = D3ValueOrFn[T, D3Text]
  type D3PropertyOrFn[T] = D3ValueOrFn[T, D3Property]
}
