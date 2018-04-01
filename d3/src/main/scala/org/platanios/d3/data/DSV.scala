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
import scala.scalajs.js.annotation.{JSBracketAccess, JSImport}

/** This module provides a parser and formatter for delimiter-separated values, most commonly comma-
  * ([CSV](https://en.wikipedia.org/wiki/Comma-separated_values)) or tab-separated values (TSV). These tabular formats
  * are popular with spreadsheet programs such as Microsoft Excel, and are often more space-efficient than JSON. This
  * implementation is based on [RFC 4180](http://tools.ietf.org/html/rfc4180).
  *
  * Comma (CSV) and tab (TSV) delimiters are built-in. For example, to parse:
  * {{{
  *   d3.dsv.parseCSV("foo,bar\n1,2")   // [{foo: "1", bar: "2"}, columns: ["foo", "bar"]]
  *   d3.dsv.parseTSV("foo\tbar\n1\t2") // [{foo: "1", bar: "2"}, columns: ["foo", "bar"]]
  * }}}
  * Or to format:
  * {{{
  *   d3.dsv.formatCSV(js.Array(js.Dictionary("foo" -> "1", "bar" -> "2"))) // "foo,bar\n1,2"
  *   d3.dsv.formatTSV(js.Array(js.Dictionary("foo" -> "1", "bar" -> "2"))) // "foo\tbar\n1\t2"
  * }}}
  *
  * To use a different delimiter, such as `"|"` for pipe-separated values, use `d3.dsv.format()`:
  * {{{
  *   val psv = d3.dsv.format("|")
  *   psv.parse("foo|bar\n1|2")     // [{foo: "1", bar: "2"}, columns: ["foo", "bar"]]
  * }}}
  *
  * For easy loading of DSV files in a browser, see the `d3.fetch` module.
  *
  * === Content Security Policy ===
  *
  * If a [content security policy](http://www.w3.org/TR/CSP/) is in place, note that `DSV.parse()` requires
  * `unsafe-eval` in the `script-src` directive, due to the (safe) use of dynamic code generation for fast parsing (see
  * [source](https://github.com/d3/d3-dsv/blob/master/src/dsv.js)). Alternatively, use `DSV.parseRows()`.
  *
  * === Byte-Order Marks ===
  *
  * DSV files sometimes begin with a [byte order mark (BOM)](https://en.wikipedia.org/wiki/Byte_order_mark) (e.g.,
  * saving a spreadsheet in CSV UTF-8 format from Microsoft Excel will include a BOM). On the web this is not usually a
  * problem because the [UTF-8 decode algorithm](https://encoding.spec.whatwg.org/#utf-8-decode) specified in the
  * encoding standard removes the BOM. Node.js, on the other hand,
  * [does not remove the BOM](https://github.com/nodejs/node-v0.x-archive/issues/1918), when decoding UTF-8.
  *
  * If the BOM is not removed, the first character of the text is a zero-width non-breaking space. So if a CSV file with
  * a BOM is parsed by `d3.dsv.parseCSV()`, the first column's name will begin with a zero-width non-breaking space.
  * This can be hard to spot since this character is usually invisible when printed.
  *
  * To remove the BOM before parsing, consider using [strip-bom](https://www.npmjs.com/package/strip-bom).
  *
  * @author Emmanouil Antonios Platanios
  */
class DSV protected (private[d3] val facade: DSV.Facade) {
  /** Parses the specified string, which must be in the delimiter-separated values format with the appropriate
    * delimiter, returning an array of objects representing the parsed rows.
    *
    * Unlike `DSV.parseRows()`, this method requires that the first line of the DSV content contains a
    * delimiter-separated list of column names. These column names become the attributes on the returned objects.
    *
    * For example, consider the following CSV file:
    * {{{
    *   Year,Make,Model,Length
    *   1997,Ford,E350,2.34
    *   2000,Mercury,Cougar,2.38
    * }}}
    * The resulting array is:
    * {{{
    *   [{"Year": "1997", "Make": "Ford", "Model": "E350", "Length": "2.34"},
    *    {"Year": "2000", "Make": "Mercury", "Model": "Cougar", "Length": "2.38"}]
    * }}}
    *
    * The returned array also exposes a `columns` property containing the column names in input order (in contrast to
    * `Object.keys`, whose iteration order is arbitrary). For example:
    * {{{
    *   data.columns // ["Year", "Make", "Model", "Length"]
    * }}}
    *
    * Field values are treated as strings. For safety, there is no automatic conversion to numbers, dates, or other
    * types. In some cases, JavaScript may coerce strings to numbers for you automatically (for example, using the `+`
    * operator), but it is better is to specify a row conversion function.
    *
    * @param  dsvString String to parse.
    * @return Parse result.
    */
  def parse(dsvString: String): DSV.ParsedArray[DSV.Row[String]] = {
    facade.parse(dsvString)
  }

  /** Parses the specified string, which must be in the delimiter-separated values format with the appropriate
    * delimiter, returning an array of objects representing the parsed rows.
    *
    * Unlike `DSV.parseRows()`, this method requires that the first line of the DSV content contains a
    * delimiter-separated list of column names. These column names become the attributes on the returned objects.
    *
    * For example, consider the following CSV file:
    * {{{
    *   Year,Make,Model,Length
    *   1997,Ford,E350,2.34
    *   2000,Mercury,Cougar,2.38
    * }}}
    * The resulting array is:
    * {{{
    *   [{"Year": "1997", "Make": "Ford", "Model": "E350", "Length": "2.34"},
    *    {"Year": "2000", "Make": "Mercury", "Model": "Cougar", "Length": "2.38"}]
    * }}}
    *
    * The returned array also exposes a `columns` property containing the column names in input order (in contrast to
    * `Object.keys`, whose iteration order is arbitrary). For example:
    * {{{
    *   data.columns // ["Year", "Make", "Model", "Length"]
    * }}}
    *
    * If a row conversion function is not specified, field values are treated as strings. For safety, there is no
    * automatic conversion to numbers, dates, or other types. In some cases, JavaScript may coerce strings to numbers
    * for you automatically (for example, using the `+` operator), but it is better is to specify a row conversion
    * function.
    *
    * If a row conversion function is specified, the specified function is invoked for each row, being passed an object
    * representing the current row (`d`), the index (`i`) starting at zero for the first non-header row, and the array
    * of column names. If the returned value is `null`, the row is skipped and will be omitted from the array returned
    * by `DSV.parse()`. Otherwise, the returned value defines the corresponding row object.
    *
    * For example:
    * {{{
    *   var data = d3.dsv.parseCSV(string, (d: String, _, _) => {
    *     js.Dictionary(
    *       "year" -> new Date(d("Year").toInt, 0, 1), // lowercase and convert "Year" to Date
    *       "make" -> d("Make"), // lowercase
    *       "model" -> d("Model"), // lowercase
    *       "length" -> d("Length").toInt // lowercase and convert "Length" to number
    *      )
    *   })
    * }}}
    *
    * In effect, row is similar to applying a map and filter operator to the returned rows.
    *
    * @param  dsvString String to parse.
    * @param  rowFn     Optional row conversion function to use.
    * @return Parse result.
    */
  def parse[T](dsvString: String, rowFn: DSV.RowFunction[T]): DSV.ParsedArray[DSV.Row[T]] = {
    facade.parse(dsvString, rowFn)
  }

  /** Parses the specified string, which must be in the delimiter-separated values format with the appropriate
    * delimiter, returning an array of objects representing the parsed rows.
    *
    * Unlike `DSV.parse()`, this method treats the header line as a standard row, and should be used whenever DSV
    * content does not contain a header. Each row is represented as an array rather than an object. Rows may have
    * variable length.
    *
    * For example, consider the following CSV file, which notably lacks a header line:
    * {{{
    *   1997,Ford,E350,2.34
    *   2000,Mercury,Cougar,2.38
    * }}}
    * The resulting array is:
    * {{{
    *   [["1997", "Ford", "E350", "2.34"],
    *    ["2000", "Mercury", "Cougar", "2.38"]]
    * }}}
    *
    * Field values are treated as strings. For safety, there is no automatic conversion to numbers, dates, or other
    * types. In some cases, JavaScript may coerce strings to numbers for you automatically (for example, using the `+`
    * operator), but it is better is to specify a row conversion function.
    *
    * @param  dsvString String to parse.
    * @return Parse result.
    */
  def parseRows(dsvString: String): js.Array[DSV.Row[String]] = {
    facade.parseRows(dsvString)
  }

  /** Parses the specified string, which must be in the delimiter-separated values format with the appropriate
    * delimiter, returning an array of objects representing the parsed rows.
    *
    * Unlike `DSV.parse()`, this method treats the header line as a standard row, and should be used whenever DSV
    * content does not contain a header. Each row is represented as an array rather than an object. Rows may have
    * variable length.
    *
    * For example, consider the following CSV file, which notably lacks a header line:
    * {{{
    *   1997,Ford,E350,2.34
    *   2000,Mercury,Cougar,2.38
    * }}}
    * The resulting array is:
    * {{{
    *   [["1997", "Ford", "E350", "2.34"],
    *    ["2000", "Mercury", "Cougar", "2.38"]]
    * }}}
    *
    * If a row conversion function is not specified, field values are treated as strings. For safety, there is no
    * automatic conversion to numbers, dates, or other types. In some cases, JavaScript may coerce strings to numbers
    * for you automatically (for example, using the `+` operator), but it is better is to specify a row conversion
    * function.
    *
    * If a row conversion function is specified, the specified function is invoked for each row, being passed an object
    * representing the current row (`d`), the index (`i`) starting at zero for the first non-header row, and the array
    * of column names. If the returned value is `null`, the row is skipped and will be omitted from the array returned
    * by `DSV.parseRows()`. Otherwise, the returned value defines the corresponding row object.
    *
    * For example:
    * {{{
    *   var data = d3.dsv.parseCSVRows(string, (d: String, _, _) => {
    *     js.Dictionary(
    *       "year" -> new Date(d(0).toInt, 0, 1), // lowercase and convert "Year" to Date
    *       "make" -> d(1), // lowercase
    *       "model" -> d(2), // lowercase
    *       "length" -> d(3).toInt // lowercase and convert "Length" to number
    *      )
    *   })
    * }}}
    *
    * In effect, row is similar to applying a map and filter operator to the returned rows.
    *
    * @param  dsvString String to parse.
    * @param  rowFn     Optional row conversion function to use.
    * @return Parse result.
    */
  def parseRows[T](dsvString: String, rowFn: DSV.RowFunction[T]): js.Array[DSV.Row[T]] = {
    facade.parseRows(dsvString, rowFn)
  }

  /** Formats the specified array of object rows as delimiter-separated values, returning a string. This operation is
    * the inverse of `DSV.parse()`. Each row will be separated by a newline (`\n`), and each column within each row will
    * be separated by the delimiter (such as a comma, `,`). Values that contain either the delimiter, a double-quote
    * (`"`) or a newline will be escaped using double-quotes.
    *
    * If `columns` is not specified, the list of column names that forms the header row is determined by the union of
    * all properties on all objects in `rows`. The order of columns is nondeterministic. If `columns` is specified, it
    * is an array of strings representing the column names.
    *
    * All fields in each row object will be coerced to strings. For more control over which and how fields are
    * formatted, first map rows to an array of array of strings, and then use `DSV.formatRows()`.
    *
    * @param  rows    Rows to format.
    * @param  columns Optional column names to use.
    * @return Formatted string.
    */
  def format(rows: js.Array[js.Object], columns: js.Array[String] = null): String = {
    if (columns != null)
      facade.format(rows, columns)
    else
      facade.format(rows)
  }

  /** Formats the specified array of array of string rows as delimiter-separated values, returning a string. This
    * operation is the reverse of `DSV.parseRows()`. Each row will be separated by a newline (`\n`), and each column
    * within each row will be separated by the delimiter (such as a comma, `,`). Values that contain either the
    * delimiter, a double-quote (`"`) or a newline will be escaped using double-quotes.
    *
    * You can optionally add a header with a column names to the formatted string, by prepending an array with the
    * column names to `rows`.
    *
    * @param  rows Rows to format.
    * @return Formatted string.
    */
  def formatRows(rows: js.Array[js.Array[String]]): String = {
    facade.formatRows(rows)
  }
}

object DSV {
  /** Represents functions for parsing rows of DSV files. */
  type RowFunction[T] = js.Function3[DSV.Row[String], Int, js.Array[String], DSV.Row[T]]

  /** Constructs a new DSV parser and formatter for the specified delimiter.
    *
    * @param  delimiter Column delimiter.
    * @return Constructed DSV parser/formatter.
    */
  def format(delimiter: Char): DSV = {
    new DSV(Facade.dsvFormat(delimiter.toString))
  }

  /** Alias for `DSV.format(",").parse()`. */
  def parseCSV(csvString: String): DSV.ParsedArray[DSV.Row[String]] = {
    Facade.csvParse(csvString)
  }

  /** Alias for `DSV.format(",").parse()`. */
  def parseCSV[T](csvString: String, rowFn: DSV.RowFunction[T]): DSV.ParsedArray[DSV.Row[T]] = {
    Facade.csvParse(csvString, rowFn)
  }

  /** Alias for `DSV.format(",").parseRows()`. */
  def parseRowsCSV(csvString: String): js.Array[DSV.Row[String]] = {
    Facade.csvParseRows(csvString)
  }

  /** Alias for `DSV.format(",").parseRows()`. */
  def parseRowsCSV[T](csvString: String, rowFn: DSV.RowFunction[T]): js.Array[DSV.Row[T]] = {
    Facade.csvParseRows(csvString, rowFn)
  }

  /** Alias for `DSV.format(",").format()`. */
  def formatCSV(rows: js.Array[js.Object], columns: js.Array[String] = null): String = {
    if (columns != null)
      Facade.csvFormat(rows, columns)
    else
      Facade.csvFormat(rows)
  }

  /** Alias for `DSV.format(",").formatRows()`. */
  def formatRowsCSV(rows: js.Array[js.Array[String]]): String = {
    Facade.csvFormatRows(rows)
  }

  /** Alias for `DSV.format("\t").parse()`. */
  def parseTSV(tsvString: String): DSV.ParsedArray[DSV.Row[String]] = {
    Facade.tsvParse(tsvString)
  }

  /** Alias for `DSV.format("\T").parse()`. */
  def parseTSV[T](tsvString: String, rowFn: DSV.RowFunction[T]): DSV.ParsedArray[DSV.Row[T]] = {
    Facade.tsvParse(tsvString, rowFn)
  }

  /** Alias for `DSV.format("\t").parseRows()`. */
  def parseRowsTSV(tsvString: String): js.Array[DSV.Row[String]] = {
    Facade.tsvParseRows(tsvString)
  }

  /** Alias for `DSV.format("\t").parseRows()`. */
  def parseRowsTSV[T](tsvString: String, rowFn: DSV.RowFunction[T]): js.Array[DSV.Row[T]] = {
    Facade.tsvParseRows(tsvString, rowFn)
  }

  /** Alias for `DSV.format("\t").format()`. */
  def formatTSV(rows: js.Array[js.Object], columns: js.Array[String] = null): String = {
    if (columns != null)
      Facade.tsvFormat(rows, columns)
    else
      Facade.tsvFormat(rows)
  }

  /** Alias for `DSV.format("\t").formatRows()`. */
  def formatRowsTSV(rows: js.Array[js.Array[String]]): String = {
    Facade.tsvFormatRows(rows)
  }

  /** Represents a parsed DSV file and contains its parsed rows. */
  @js.native trait ParsedArray[T] extends js.Array[T] {
    var columns: js.Array[String] = js.native
  }

  /** Represents a parsed DSV file row. */
  @js.native trait Row[T] extends js.Object {
    @JSBracketAccess def apply(key: String): T = js.native
    @JSBracketAccess def update(key: String, v: T): Unit = js.native
  }

  @JSImport("d3-dsv", JSImport.Namespace)
  @js.native object Facade extends js.Object {
    def dsvFormat(delimiter: String): Facade = js.native

    def csvParse[T](csvString: String, rowFn: RowFunction[T] = null): ParsedArray[Row[T]] = js.native
    def csvParseRows[T](dsvString: String, rowFn: RowFunction[T] = null): js.Array[Row[T]] = js.native
    def csvFormat(rows: js.Array[js.Object], columns: js.Array[String] = null): String = js.native
    def csvFormatRows(rows: js.Array[js.Array[String]]): String = js.native

    def tsvParse[T](tsvString: String, rowFn: RowFunction[T] = null): ParsedArray[Row[T]] = js.native
    def tsvParseRows[T](tsvString: String, rowFn: RowFunction[T] = null): js.Array[Row[T]] = js.native
    def tsvFormat(rows: js.Array[js.Object], columns: js.Array[String] = null): String = js.native
    def tsvFormatRows(rows: js.Array[js.Array[String]]): String = js.native
  }

  @js.native trait Facade extends js.Object {
    def parse[T](dsvString: String, rowFn: RowFunction[T] = null): ParsedArray[Row[T]] = js.native
    def parseRows[T](dsvString: String, rowFn: RowFunction[T] = null): js.Array[Row[T]] = js.native
    def format(rows: js.Array[js.Object], columns: js.Array[String] = null): String = js.native
    def formatRows(rows: js.Array[js.Array[String]]): String = js.native
  }
}
