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

package org.platanios.d3.data

import org.scalajs.dom
import org.scalajs.dom.experimental._

import scala.scalajs.js
import scala.scalajs.js.|
import scala.scalajs.js.annotation.JSImport

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-fetch", JSImport.Namespace)
@js.native object Fetch extends js.Object {
  def blob(url: String, init: RequestInit = ???): js.Promise[dom.Blob] = js.native
  def buffer(url: String, init: RequestInit = ???): js.Promise[js.typedarray.ArrayBuffer] = js.native
  def csv(url: String): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  def csv(url: String, init: RequestInit = ???): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  def csv[T](url: String, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): js.Promise[DSVParsedArray[DSVRow[T]]] = js.native
  def csv[T](url: String, init: RequestInit, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): js.Promise[DSVParsedArray[DSVRow[T]]] = js.native
  def dsv(delimiter: String, url: String): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  def dsv(delimiter: String, url: String, init: RequestInit = ???): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  def dsv[T](delimiter: String, url: String, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): js.Promise[DSVParsedArray[DSVRow[T]]] = js.native
  def dsv[T](delimiter: String, url: String, init: RequestInit, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): js.Promise[DSVParsedArray[DSVRow[T]]] = js.native
  def image(url: String, init: js.Dictionary[js.Any] = ???): js.Promise[dom.html.Image] = js.native
  def json[ParsedJSONObject <: js.Any](url: String, init: RequestInit = ???): js.Promise[ParsedJSONObject] = js.native
  def text(url: String, init: RequestInit = ???): js.Promise[String] = js.native
  def tsv(url: String): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  def tsv(url: String, init: RequestInit = ???): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  def tsv[T](url: String, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): js.Promise[DSVParsedArray[DSVRow[T]]] = js.native
  def tsv[T](url: String, init: RequestInit, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): js.Promise[DSVParsedArray[DSVRow[T]]] = js.native
}
