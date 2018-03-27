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

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-dsv", JSImport.Namespace)
@js.native object DSV extends js.Object {
  def csvParse(csvString: String): DSVParsedArray[DSVRow[String]] = js.native
  def csvParse[T](csvString: String, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): DSVParsedArray[DSVRow[T]] = js.native
  def csvParseRows(csvString: String): js.Array[js.Array[String]] = js.native
  def csvParseRows[T](csvString: String, row: js.Function2[js.Array[String], Double, DSVRow[T] | Unit | Null]): js.Array[DSVRow[T]] = js.native
  def csvFormat[T](rows: js.Array[DSVRow[T]], columns: js.Array[String] = ???): String = js.native
  def csvFormatRows(rows: js.Array[js.Array[String]]): String = js.native
  def tsvParse(tsvString: String): DSVParsedArray[DSVRow[String]] = js.native
  def tsvParse[T](tsvString: String, row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]): DSVParsedArray[DSVRow[T]] = js.native
  def tsvParseRows(tsvString: String): js.Array[js.Array[String]] = js.native
  def tsvParseRows[T](tsvString: String, row: js.Function2[js.Array[String], Double, DSVRow[T] | Unit | Null]): js.Array[DSVRow[T]] = js.native
  def tsvFormat[T](rows: js.Array[DSVRow[T]], columns: js.Array[String] = ???): String = js.native
  def tsvFormatRows(rows: js.Array[js.Array[String]]): String = js.native
  def dsvFormat(delimiter: String): DSV = js.native
}

@js.native trait DSV extends js.Object {
  def parse(dsvString: String): DSVParsedArray[DSVRow[String]] = js.native

  def parse[T](
      dsvString: String,
      row: js.Function3[DSVRow[String], Double, js.Array[String], DSVRow[T] | Unit | Null]
  ): DSVParsedArray[DSVRow[T]] = js.native

  def parseRows(dsvString: String): js.Array[js.Array[String]] = js.native

  def parseRows[T](
      dsvString: String,
      row: js.Function2[js.Array[String], Double, DSVRow[T] | Unit | Null]
  ): js.Array[DSVRow[T]] = js.native

  def format(rows: js.Array[DSVRow[_]], columns: js.Array[String] = ???): String = js.native

  def formatRows(rows: js.Array[js.Array[String]]): String = js.native
}
