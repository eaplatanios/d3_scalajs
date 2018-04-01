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

import scala.concurrent.Future
import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/** This module provides convenient parsing on top of [Fetch](https://fetch.spec.whatwg.org/) and has built-in support
  * for parsing HTML, SVG, image, JSON, DSV, CSV, and TSV files.
  *
  * @author Emmanouil Antonios Platanios
  */
object Fetch {
  type RowFunction[T] = js.Function3[DSVRow[String], Int, js.Array[String], DSVRow[T]]

  /** Fetches the binary file at the specified input URL as a blob.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched blob.
    */
  def blob(url: String, init: RequestInit = null): Future[dom.Blob] = {
    if (init != null)
      Facade.blob(url, init).toFuture
    else
      Facade.blob(url).toFuture
  }

  /** Fetches the binary file at the specified input URL as a blob.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched array buffer.
    */
  def buffer(url: String, init: RequestInit = null): Future[js.typedarray.ArrayBuffer] = {
    if (init != null)
      Facade.buffer(url, init).toFuture
    else
      Facade.buffer(url).toFuture
  }

  /** Fetches the file at the specified input URL as an HTML document.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched HTML document.
    */
  def html(url: String, init: RequestInit = null): Future[dom.html.Document] = {
    if (init != null)
      Facade.html(url, init).toFuture
    else
      Facade.html(url).toFuture
  }

  /** Fetches the file at the specified input URL as an image.
    *
    * For example, to set an anonymous
    * [cross-origin request](https://developer.mozilla.org/en-US/docs/Web/HTML/CORS_enabled_image):
    * {{{
    *   d3.fetch.image("https://example.com/test.png", js.Dictionary(crossOrigin -> "anonymous"))
    * }}}
    *
    * @param  url  URL for the file.
    * @param  init Additional properties to set on the image before loading.
    * @return Future for the fetched image.
    */
  def image(url: String, init: js.Dictionary[js.Any] = null): Future[dom.html.Image] = {
    if (init != null)
      Facade.image(url, init).toFuture
    else
      Facade.image(url).toFuture
  }

  /** Fetches the [JSON](http://json.org/) file at the specified input URL.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched array buffer.
    */
  def json(url: String, init: RequestInit = null): Future[js.Object] = {
    if (init != null)
      Facade.json(url, init).toFuture
    else
      Facade.json(url).toFuture
  }

  /** Fetches the file at the specified input URL as text and then parses it as SVG.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched SVG element.
    */
  def svg(url: String, init: RequestInit = null): Future[dom.svg.Element] = {
    if (init != null)
      Facade.svg(url, init).toFuture
    else
      Facade.svg(url).toFuture
  }

  /** Fetches the text file at the specified input URL.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched text.
    */
  def text(url: String, init: RequestInit = null): Future[String] = {
    if (init != null)
      Facade.text(url, init).toFuture
    else
      Facade.text(url).toFuture
  }

  /** Fetches the XML document at the specified input URL.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @return Future for the fetched XML document.
    */
  def xml(url: String, init: RequestInit = null): Future[dom.Document] = {
    if (init != null)
      Facade.xml(url, init).toFuture
    else
      Facade.xml(url).toFuture
  }

  /** Fetches the DSV (i.e., delimited) file at the specified input URL.
    *
    * @param  delimiter Delimiter used in the file.
    * @param  url       URL for the file.
    * @param  init      If specified, passed along to the underlying call to fetch.
    * @param  row       Optional conversion function used to map and filter row objects to a more specific
    *                   representation. In order to filter a row out, this function must return `null` for that row.
    * @return
    */
  def dsv[T](
      delimiter: String,
      url: String,
      init: RequestInit = null,
      row: RowFunction[T] = null
  ): Future[DSVParsedArray[DSVRow[String]]] = (init, row) match {
    case (null, null) => Facade.dsv[T](delimiter, url).toFuture
    case (i, null) => Facade.dsv[T](delimiter, url, init = i).toFuture
    case (null, r) => Facade.dsv[T](delimiter, url, row = r).toFuture
    case (i, r) => Facade.dsv[T](delimiter, url, init = i, row = r).toFuture
  }

  /** Fetches the CSV (i.e., comma-separated) file at the specified input URL.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @param  row  Optional conversion function used to map and filter row objects to a more specific representation.
    *              In order to filter a row out, this function must return `null` for that row.
    * @return
    */
  def csv[T](
      url: String,
      init: RequestInit = null,
      row: RowFunction[T] = null
  ): Future[DSVParsedArray[DSVRow[String]]] = (init, row) match {
    case (null, null) => Facade.csv[T](url).toFuture
    case (i, null) => Facade.csv[T](url, init = i).toFuture
    case (null, r) => Facade.csv[T](url, row = r).toFuture
    case (i, r) => Facade.csv[T](url, init = i, row = r).toFuture
  }

  /** Fetches the TSV (i.e., tab-separated) file at the specified input URL.
    *
    * @param  url  URL for the file.
    * @param  init If specified, passed along to the underlying call to fetch.
    * @param  row  Optional conversion function used to map and filter row objects to a more specific representation.
    *              In order to filter a row out, this function must return `null` for that row.
    * @return
    */
  def tsv[T](
      url: String,
      init: RequestInit = null,
      row: RowFunction[T] = null
  ): Future[DSVParsedArray[DSVRow[String]]] = (init, row) match {
    case (null, null) => Facade.tsv[T](url).toFuture
    case (i, null) => Facade.tsv[T](url, init = i).toFuture
    case (null, r) => Facade.tsv[T](url, row = r).toFuture
    case (i, r) => Facade.tsv[T](url, init = i, row = r).toFuture
  }

  @JSImport("d3-fetch", JSImport.Namespace)
  @js.native private[Fetch] object Facade extends js.Object {
    def blob(url: String, init: RequestInit = null): js.Promise[dom.Blob] = js.native
    def buffer(url: String, init: RequestInit = null): js.Promise[js.typedarray.ArrayBuffer] = js.native
    def html(url: String, init: RequestInit = null): js.Promise[dom.html.Document] = js.native
    def image(url: String, init: js.Dictionary[js.Any] = null): js.Promise[dom.html.Image] = js.native
    def json(url: String, init: RequestInit = null): js.Promise[js.Object] = js.native
    def svg(url: String, init: RequestInit = null): js.Promise[dom.svg.Element] = js.native
    def text(url: String, init: RequestInit = null): js.Promise[String] = js.native
    def xml(url: String, init: RequestInit = null): js.Promise[dom.Document] = js.native
    def dsv[T](delimiter: String, url: String, init: RequestInit = null, row: RowFunction[T] = null): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
    def csv[T](url: String, init: RequestInit = null, row: RowFunction[T] = null): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
    def tsv[T](url: String, init: RequestInit = null, row: RowFunction[T] = null): js.Promise[DSVParsedArray[DSVRow[String]]] = js.native
  }
}
