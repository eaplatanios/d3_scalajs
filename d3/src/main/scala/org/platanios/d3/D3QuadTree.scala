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

import scalajs.js
import scala.scalajs.js.annotation._

@JSImport("d3-quadtree", JSImport.Namespace)
@js.native
object D3QuadTree extends js.Object {
  def quadtree[Datum](): D3QuadTree[Datum] = js.native
  def quadtree[Datum](data: js.Array[Datum]): D3QuadTree[Datum] = js.native
  def quadtree[Datum](data: js.Array[Datum], x: js.Function1[Datum, Double], y: js.Function1[Datum, Double]): D3QuadTree[Datum] = js.native
}

@js.native
trait D3QuadTree[Datum] extends js.Object {
  def x(x: js.Function1[Datum, Double]): D3QuadTree[Datum] = js.native
  def x(): js.Function1[Datum, Double] = js.native
  def y(y: js.Function1[Datum, Double]): D3QuadTree[Datum] = js.native
  def y(): js.Function1[Datum, Double] = js.native
  def add(datum: Datum): D3QuadTree[Datum] = js.native
  def addAll[NewDatum](data: js.Array[NewDatum]): D3QuadTree[NewDatum] = js.native
  def remove(datum: Datum): D3QuadTree[Datum] = js.native
  def root: QuadTreeNode[Datum] = js.native
  def data: js.Array[Datum] = js.native
  def size: Int = js.native
  def find(x: Double, y: Double): Datum = js.native
  def find(x: Double, y: Double, radius: Double): Datum = js.native
  def visit(callback: js.Function5[QuadTreeNode[Datum], Double, Double, Double, Double, Boolean]): D3QuadTree[Datum] = js.native
  def visitAfter(callback: js.Function5[QuadTreeNode[Datum], Double, Double, Double, Double, Any]): D3QuadTree[Datum] = js.native
  def copy: D3QuadTree[Datum] = js.native
}

@js.native
sealed trait QuadTreeNode[Datum] extends js.Any {
  def length: js.UndefOr[Int] = js.native
  def apply(index: Int): QuadTreeNode[Datum] = js.native
  def data: Datum = js.native
  def next: js.UndefOr[QuadTreeNode[Datum]] = js.native
}
