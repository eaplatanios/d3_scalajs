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

package org.platanios.d3.format

import org.platanios.d3.format

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

/** Ever noticed how sometimes JavaScript doesn't display numbers the way you expect? Like, you tried to print tenths
  * with a simple loop, and you got this:
  * {{{
  *   0
  *   0.1
  *   0.2
  *   0.30000000000000004
  *   0.4
  *   0.5
  *   0.6000000000000001
  *   0.7000000000000001
  *   0.8
  *   0.9
  * }}}
  *
  * Welcome to [binary floating point](https://en.wikipedia.org/wiki/Double-precision_floating-point_format)! ಠ_ಠ
  *
  * Yet rounding error is not the only reason to customize number formatting. A table of numbers should be formatted
  * consistently for comparison; above, `0.0` would be better than 0. Large numbers should have grouped digits
  * (e.g., `42,000`) or be in scientific or metric notation (`4.2e+4`, `42k`). Currencies should have fixed precision
  * (`\$3.50`). Reported numerical results should be rounded to significant digits (`4021` becomes `4000`). Number
  * formats should appropriate to the reader's locale (`42.000,00` or `42,000.00`). The list goes on.
  *
  * Formatting numbers for human consumption is the purpose of D3-format, which is modeled after Python 3's format
  * specification mini-language (PEP 3101). Revisiting the example above, using the formatter function defined by
  * `d3.format.number(".1f")`, you get this:
  * {{{
  *   0.0
  *   0.1
  *   0.2
  *   0.3
  *   0.4
  *   0.5
  *   0.6
  *   0.7
  *   0.8
  *   0.9
  * }}}
  *
  * A few more examples:
  * {{{
  *   d3.format.number(".0%")(0.123)  // Rounded percentage, "12%"
  *   d3.format.number("(\$.2f")(-3.5) // Localized fixed-point currency, "(£3.50)"
  *   d3.format.number("+20")(42)     // Space-filled and signed, "                 +42"
  *   d3.format.number(".^20")(42)    // Dot-filled and centered, ".........42........."
  *   d3.format.number(".2s")(42e6)   // SI-prefix with two significant digits, "42M"
  *   d3.format.number("#x")(48879)   // Prefixed lowercase hexadecimal, "0xbeef"
  *   d3.format.number(",.2r")(4223)  // Grouped thousands with two significant digits, "4,200"
  * }}}
  *
  * Furthermore, this package also provides an implementation of the venerable
  * [strptime](http://pubs.opengroup.org/onlinepubs/009695399/functions/strptime.html) and
  * [strftime](http://pubs.opengroup.org/onlinepubs/007908799/xsh/strftime.html) functions from the C standard library,
  * and can be used to parse or format dates in a variety of locale-specific representations. To format a date, create a
  * formatter from a specifier (a string with the desired format directives, indicated by `%`), and then pass a date to
  * the formatter, which returns a string.
  *
  * For example, to convert the current date to a human-readable string:
  * {{{
  *   val formatTime = d3.format.time("%B %d, %Y")
  *   formatTime(new js.Date()) // "June 30, 2015"
  * }}}
  *
  * Likewise, to convert a string back to a date, create a parser:
  * {{{
  *   val parseTime = d3.format.timeParse("%B %d, %Y")
  *   parseTime("June 30, 2015") // Tue Jun 30 2015 00:00:00 GMT-0700 (PDT)
  * }}}
  *
  * You can implement more elaborate conditional time formats, too. For example, here's a multi-scale time format using
  * time intervals:
  * {{{
  *   val formatMillisecond = d3.format.time(".%L")
  *   val formatSecond = d3.format.time(":%S")
  *   val formatMinute = d3.format.time("%I:%M")
  *   val formatHour = d3.format.time("%I %p")
  *   val formatDay = d3.format.time("%a %d")
  *   val formatWeek = d3.format.time("%b %d")
  *   val formatMonth = d3.format.time("%B")
  *   val formatYear = d3.format.time("%Y")
  *
  *   def multiFormat(date: js.Date): String = date match {
  *     case d if d3.time.second(d) < d => formatMillisecond(d)
  *     case d if d3.time.minute(d) < d => formatSecond(d)
  *     case d if d3.time.hour(d) < d => formatMinute(d)
  *     case d if d3.time.day(d) < d => formatHour(d)
  *     case d if d3.time.month(d) < d && d3.time.week(d) < d => formatDay(d)
  *     case d if d3.time.month(d) < d => formatWeek(d)
  *     case d if d3.time.year(d) < d => formatMonth(d)
  *     case d => formatYear(d)
  *   }
  * }}}
  *
  * This package is used by D3 scales to generate human-readable ticks.
  *
  * @author Emmanouil Antonios Platanios
  */
object Format {
  /** Represents a formatting locale. */
  type Locale = format.Locale

  val Locale: format.Locale.type = format.Locale

  /** Returns a formatter function for `specifier`, using the default locale.
    *
    * @param  specifier Number format specifier.
    * @return Formatter function.
    */
  def number(specifier: NumberSpecifier): js.Function1[Double, String] = {
    NumberFacade.format(specifier.toString)
  }

  /** Returns a formatter function for `specifier`, using the default locale, will convert values to the units of the
    * appropriate [SI prefix](https://en.wikipedia.org/wiki/Metric_prefix#List_of_SI_prefixes) for the specified numeric
    * reference value before formatting in fixed point notation.
    *
    * Unlike `format()` with the `DecimalSI` format notation, this method returns a formatter with a consistent SI
    * prefix, rather than computing the prefix dynamically for each number. In addition, the precision for the given
    * specifier represents the number of digits past the decimal point (as with fixed point notation), rather than the
    * number of significant digits. For example:
    * {{{
    *   val f = d3.format.prefix(d3.format.Specifier.parse(",.0"), 1e-6)
    *   f(0.00042) // "420µ"
    *   f(0.0042)  // "4,200µ"
    * }}}
    *
    * This method is useful when formatting multiple numbers in the same units for easy comparison. See
    * `precisionPrefix` for help picking an appropriate precision, and [bl.ocks.org/9764126](bl.ocks.org/9764126) for an
    * example.
    *
    * @param  specifier Number format specifier.
    * @param  value     Reference value.
    * @return Formatter function.
    */
  def numberPrefix(specifier: NumberSpecifier, value: Double): js.Function1[Double, String] = {
    NumberFacade.formatPrefix(specifier.toString, value)
  }

  /** Returns a suggested decimal precision for fixed point notation given the specified numeric step value. The step
    * represents the minimum absolute difference between values that will be formatted. This assumes that the values to
    * be formatted are also multiples of step. For example, given the numbers `1`, `1.5`, and `2`, the step should be
    * `0.5` and the suggested precision is `1`:
    * {{{
    *   val p = d3.format.precisionFixed(0.5)
    *   val f = d3.format(Specifier.parse(s".\${p}f"))
    *   f(1)   // "1.0"
    *   f(1.5) // "1.5"
    *   f(2)   // "2.0"
    * }}}
    * Whereas for the numbers `1`, `2` and `3`, the step should be `1` and the suggested precision is `0`:
    * {{{
    *   val p = d3.format.precisionFixed(1.0)
    *   val f = d3.format(Specifier.parse(s".\${p}f"))
    *   f(1) // "1"
    *   f(2) // "2"
    *   f(3) // "3"
    * }}}
    * Note that for the percentage format notation, you should subtract `2`:
    * {{{
    *   val p = Math.max(0.0, d3.format.precisionFixed(0.05) - 2)
    *   val f = d3.format(Specifier.parse(s".\${p}%"))
    *   f(0.45) // "45%"
    *   f(0.50) // "50%"
    *   f(0.55) // "55%"
    * }}}
    *
    * @param  step Step for which to compute the required precision.
    * @return Precision value.
    */
  def precisionFixed(step: Double): Int = {
    NumberFacade.precisionFixed(step)
  }

  /** Returns a suggested decimal precision for use with `formatPrefix()` given the specified numeric step and reference
    * value. The step represents the minimum absolute difference between values that will be formatted, and the value
    * determines which SI prefix will be used. This assumes that the values to be formatted are also multiples of step.
    * For example, given the numbers `1.1e6`, `1.2e6`, and `1.3e6`, the step should be `1e5`, the value could be
    * `1.3e6`, and the suggested precision is `1`:
    * {{{
    *   val p = d3.format.precisionPrefix(1e5, 1.3e6)
    *   val f = d3.formatPrefix(Specifier.parse(s".\${p}"), 1.3e6)
    *   f(1.1e6) // "1.1M"
    *   f(1.2e6) // "1.2M"
    *   f(1.3e6) // "1.3M"
    * }}}
    *
    * @param  step  Step for which to compute the required precision.
    * @param  value Reference value to use.
    * @return Precision value.
    */
  def precisionPrefix(step: Double, value: Double): Int = {
    NumberFacade.precisionPrefix(step, value)
  }

  /** Returns a suggested decimal precision for format types that round to significant digits given the specified
    * numeric step and max value. The step represents the minimum absolute difference between values that will be
    * formatted, and the max represents the largest absolute value that will be formatted. This assumes that the values
    * to be formatted are also multiples of step. For example, given the numbers `0.99`, `1.0`, and `1.01`, the step
    * should be `0.01`, the max should be `1.01`, and the suggested precision is `3`:
    * {{{
    *   val p = d3.format.precisionRound(0.01, 1.01)
    *   val f = d3.format(Specifier.parse(s".\${p}r"))
    *   f(0.99) // "0.990"
    *   f(1.0)  // "1.00"
    *   f(1.01) // "1.01"
    * }}}
    * Whereas for the numbers `0.9`, `1.0`, and `1.1`, the step should be `0.1`, the max should be `1.1`, and the
    * suggested precision is `2`:
    * {{{
    *   val p = d3.format.precisionRound(0.1, 1.1)
    *   val f = d3.format(Specifier.parse(s".\${p}r"))
    *   f(0.9) // "0.90"
    *   f(1.0) // "1.0"
    *   f(1.1) // "1.1"
    * }}}
    * Note that for the exponent notation, you should subtract `1`:
    * {{{
    *   val p = Math.max(0.0, d3.format.precisionRound(0.01, 1.01) - 1)
    *   val f = d3.format(Specifier.parse(s".\${p}e"))
    *   f(0.01) // "1.00e-2"
    *   f(1.01) // "1.01e+0"
    * }}}
    *
    * @param  step Step for which to compute the required precision.
    * @param  max  Largest absolute value that will be formatted.
    * @return Precision value.
    */
  def precisionRound(step: Double, max: Double): Int = {
    NumberFacade.precisionRound(step, max)
  }

  /** Returns a time formatter function for `specifier`, using the default locale.
    * 
    * The specifier string may contain the following directives:
    * 
    *   - `%a`: abbreviated weekday name.*
    *   - `%A`: full weekday name.*
    *   - `%b`: abbreviated month name.*
    *   - `%B`: full month name.*
    *   - `%c`: the locale’s date and time, such as %x, %X.*
    *   - `%d`: zero-padded day of the month as a decimal number [01,31].
    *   - `%e`: space-padded day of the month as a decimal number [ 1,31]; equivalent to %_d.
    *   - `%f`: microseconds as a decimal number [000000, 999999].
    *   - `%H`: hour (24-hour clock) as a decimal number [00,23].
    *   - `%I`: hour (12-hour clock) as a decimal number [01,12].
    *   - `%j`: day of the year as a decimal number [001,366].
    *   - `%m`: month as a decimal number [01,12].
    *   - `%M`: minute as a decimal number [00,59].
    *   - `%L`: milliseconds as a decimal number [000, 999].
    *   - `%p`: either AM or PM.*
    *   - `%Q`: milliseconds since UNIX epoch.
    *   - `%s`: seconds since UNIX epoch.
    *   - `%S`: second as a decimal number [00,61].
    *   - `%u`: Monday-based (ISO 8601) weekday as a decimal number [1,7].
    *   - `%U`: Sunday-based week of the year as a decimal number [00,53].
    *   - `%V`: ISO 8601 week of the year as a decimal number [01, 53].
    *   - `%w`: Sunday-based weekday as a decimal number [0,6].
    *   - `%W`: Monday-based week of the year as a decimal number [00,53].
    *   - `%x`: the locale’s date, such as %-m/%-d/%Y.*
    *   - `%X`: the locale’s time, such as %-I:%M:%S %p.*
    *   - `%y`: year without century as a decimal number [00,99].
    *   - `%Y`: year with century as a decimal number.
    *   - `%Z`: time zone offset, such as -0700, -07:00, -07, or Z.
    *   - `%%`: a literal percent sign (%).
    * Directives marked with an asterisk (*) may be affected by the locale definition.
    *
    * For `%U`, all days in a new year preceding the first Sunday are considered to be in week 0. For `%W`, all days
    * in a new year preceding the first Monday are considered to be in week 0. Week numbers are computed using
    * `interval.count()`. For example, 2015-52 and 2016-00 represent Monday, December 28, 2015, while 2015-53 and
    * 2016-01 represent Monday, January 4, 2016. This differs from the ISO week date specification (`%V`), which uses a
    * more complicated definition!
    *
    * For `%V`, per the `strftime` man page: In this system, weeks start on a Monday, and are numbered from 01, for the
    * first week, up to 52 or 53, for the last week. Week 1 is the first week where four or more days fall within the
    * new year (or, synonymously, week 01 is the first week of the year that contains a Thursday, or the week that has
    * 4 January in it). The `%` sign indicating a directive may be immediately followed by a padding modifier:
    *
    *   - `0`: zero-padding.
    *   - `_`: space-padding.
    *   - `-`: no padding.
    *
    * If no padding modifier is specified, the default is `0` for all directives except `%e`, which defaults to `_`.
    * In some implementations of `strftime` and `strptime`, a directive may include an optional field width or
    * precision. This feature is not yet implemented.
    *
    * The returned function formats a specified date, returning the corresponding string.
    * {{{
    *   val formatMonth = d3.format.time("%B")
    *   val formatDay = d3.format.time("%A")
    *   val date = new js.Date(2014, 4, 1) // Thu May 01 2014 00:00:00 GMT-0700 (PDT)
    *   formatMonth(date)                  // "May"
    *   formatDay(date)                    // "Thursday""
    * }}}
    *
    * @param  specifier Time format specifier.
    * @return Formatter function.
    */
  def time(specifier: String): js.Function1[js.Date, String] = {
    TimeFacade.timeFormat(specifier)
  }

  /** Returns a new parser for the given string specifier. The specifier string may contain the same directives as
    * `time()`. The `%d` and `%e` directives are considered equivalent for parsing.
    *
    * The returned function parses strings, returning the corresponding dates or `null` if the string could not be
    * parsed according to this format's specifier. Parsing is strict, meaning that if the specified string does not
    * exactly match the associated specifier, the function returns `null`. For example, if the associated specifier is
    * `%Y-%m-%dT%H:%M:%SZ` (note that the literal `Z` here is different from the time zone offset directive `%Z`), then
    * the string `"2011-07-01T19:15:28Z"` will be parsed as expected, but `"2011-07-01T19:15:28"`,
    * `"2011-07-01 19:15:28"`, and `"2011-07-01"` will all result to `null`.  If a more flexible parser is desired, try
    * multiple formats sequentially until one returns non-`null`.
    *
    * @param  specifier Time format specifier.
    * @return Formatter function.
    */
  def timeParse(specifier: String): js.Function1[String, js.Date | Null] = {
    TimeFacade.timeParse(specifier)
  }

  /** Equivalent to `time()` except that all directives are interpreted as
    * [Coordinated Universal Time (UTC)](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    * rather than local time. */
  def timeUTC(specifier: String): js.Function1[js.Date, String] = {
    TimeFacade.utcFormat(specifier)
  }

  /** Equivalent to `timeParse()` except that all directives are interpreted as
    * [Coordinated Universal Time (UTC)](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    * rather than local time. */
  def timeParseUTC(specifier: String): js.Function1[String, js.Date | Null] = {
    TimeFacade.utcParse(specifier)
  }

  /** Formats the provided date using the full [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) UTC time formatter.
    * Where available, this method will use
    * [`Date.toISOString`](https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Date/toISOString)
    * to format.
    *
    * @param  date Date to format.
    * @return Formatted date string.
    */
  def timeISO(date: js.Date): String = {
    TimeFacade.isoFormat(date)
  }

  /** Parses the provided string into a date using the full [ISO 8601](https://en.wikipedia.org/wiki/ISO_8601) UTC time
    * parser. Where available, this method will use the
    * [date constructor](https://developer.mozilla.org/en-US/docs/JavaScript/Reference/Global_Objects/Date) to parse
    * strings. If you depend on strict validation of the input format according to ISO 8601, you should construct a
    * UTC parser function:
    * {{{
    *   val strictIsoParse = d3.format.timeParseUTC("%Y-%m-%dT%H:%M:%S.%LZ")
    * }}}
    *
    * @param  dateString String to parse as a date.
    * @return Parsed date, or `null`, if it could not be parsed.
    */
  def timeParseISO(dateString: String): js.Date | Null = {
    TimeFacade.isoParse(dateString)
  }

  /** Returns a locale object for the specified definition.
    *
    * @param  definition Locale definition.
    * @return Constructed locale.
    */
  def formatLocale(definition: Locale.Definition): Locale = {
    new Locale(definition)
  }

  /** Sets the default locale to the one corresponding to the provided definition. If you do not set a default locale,
    * it defaults to U.S. English (i.e., `Locale.enUS`).
    *
    * @param  definition Locale definition.
    * @return Constructed locale.
    */
  def formatDefaultLocale(definition: Locale.Definition): Locale = {
    new Locale(definition)
  }

  /** Represents a format specifier. */
  case class NumberSpecifier(
      fill: String = "",
      alignment: Format.Alignment = Format.RightAlignment,
      sign: Format.Sign = Format.MinusSign,
      symbol: Format.Symbol = Format.NoSymbol,
      width: Option[Int] = None,
      useGroupSeparator: Boolean = false,
      notation: Format.Notation = Format.Plain()) {
    override def toString: String = {
      s"$fill$alignment$sign$symbol${width.map(_.toString).getOrElse("")}${if (useGroupSeparator) "," else ""}$notation"
    }
  }

  object NumberSpecifier {
    implicit def stringToSpecifier(specifier: String): NumberSpecifier = parse(specifier)

    def parse(specifier: String): NumberSpecifier = {
      val facadeSpecifier = NumberFacade.formatSpecifier(specifier)
      NumberSpecifier(
        fill = if (facadeSpecifier.zero) "0" else facadeSpecifier.fill,
        alignment = if (facadeSpecifier.zero) RightLeftOfPaddingAlignment else Alignment.parse(facadeSpecifier.align),
        sign = Sign.parse(facadeSpecifier.sign),
        symbol = Symbol.parse(facadeSpecifier.symbol),
        width = facadeSpecifier.width.toOption,
        useGroupSeparator = facadeSpecifier.comma,
        notation = Notation.parse(facadeSpecifier.`type`, facadeSpecifier.precision.toOption))
    }
  }

  /** Represents the field alignment used by a format. */
  sealed trait Alignment {
    /** Symbol used to represent this alignment in D3. */
    val symbol: String

    override def toString: String = symbol
  }

  object Alignment {
    def parse(value: String): Alignment = value match {
      case ">" => RightAlignment
      case "<" => LeftAlignment
      case "^" => CenterAlignment
      case "=" => RightLeftOfPaddingAlignment
      case _ => throw new IllegalArgumentException(s"'$value' does not represent a valid format alignment.")
    }
  }

  /** Forces the field to be right-aligned within the available space. */
  case object RightAlignment extends Alignment {
    override val symbol: String = ">"
  }

  /** Forces the field to be left-aligned within the available space. */
  case object LeftAlignment extends Alignment {
    override val symbol: String = "<"
  }

  /** Forces the field to be centered within the available space. */
  case object CenterAlignment extends Alignment {
    override val symbol: String = "^"
  }

  /** Like `Alignment.Right`, but with any sign and symbol to the left of any padding. */
  case object RightLeftOfPaddingAlignment extends Alignment {
    override val symbol: String = "="
  }

  /** Represents the way in which signs for numbers are formatted. */
  sealed trait Sign {
    /** Symbol used to represent this sign in D3. */
    val symbol: String

    override def toString: String = symbol
  }

  object Sign {
    def parse(value: String): Sign = value match {
      case "-" => MinusSign
      case "+" => PlusSign
      case "(" => ParenthesesSign
      case " " => SpaceSign
      case _ => throw new IllegalArgumentException(s"'$value' does not represent a valid format sign.")
    }
  }

  /** Uses nothing for zero or positive numbers and a minus sign for negative numbers. */
  case object MinusSign extends Sign {
    override val symbol: String = "-"
  }

  /** Uses a plus sign for zero or positive numbers and a minus sign for negative numbers. */
  case object PlusSign extends Sign {
    override val symbol: String = "+"
  }

  /** Uses nothing for zero or positive numbers and parentheses for negative numbers. */
  case object ParenthesesSign extends Sign {
    override val symbol: String = "("
  }

  /** Uses a space for zero or positive numbers and a minus sign for negative numbers. */
  case object SpaceSign extends Sign {
    override val symbol: String = " "
  }

  /** Represents the symbol prefix to use when numbers are formatted. */
  sealed trait Symbol {
    /** Symbol used to represent this symbol in D3. */
    val symbol: String

    override def toString: String = symbol
  }

  /** Do not use any prefix symbol. */
  case object NoSymbol extends Symbol {
    override val symbol: String = ""
  }

  /** Use currency symbols per the locale definition. */
  case object CurrencySymbol extends Symbol {
    override val symbol: String = "$"
  }

  /** Use a prefix representing the number base (e.g., `0b` for binary, `0o` for octal, and `0x` for hexadecimal. */
  case object NumberBaseSymbol extends Symbol {
    override val symbol: String = "#"
  }

  object Symbol {
    def parse(value: String): Symbol = value match {
      case "" => NoSymbol
      case "$" => CurrencySymbol
      case "#" => NumberBaseSymbol
      case _ => throw new IllegalArgumentException(s"'$value' does not represent a valid format symbol.")
    }
  }

  sealed trait Notation {
    /** Symbol used to represent this format type in D3. */
    val symbol: String

    /** Precision of this format type. */
    protected val _precision: Option[Int] = None

    override def toString: String = _precision.map(p => s".$p").getOrElse("") + symbol
  }

  /** Default notation used, after trimming insignificant trailing zeros. Defaults to decimal notation if the resulting
    * string would have `precision` or fewer digits; otherwise, exponent notation is used. */
  case class Plain(precision: Int = 12) extends Notation {
    override           val symbol    : String      = ""
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Fixed point notation. `precision` represents the number of digits that follow the decimal point. */
  case class FixedPoint(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "f"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Decimal notation, rounded to the number of significant digits.
    * `precision` represents the number of significant digits. */
  case class RoundedDecimal(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "r"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Exponent notation. `precision` represents the number of significant digits. */
  case class Exponent(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "e"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Default notation used, after rounding to the number of significant digits. Defaults to decimal notation if the
    * resulting string would have `precision` or fewer digits; otherwise, exponent notation is used. */
  case class RoundedDecimalOrExponent(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "g"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Decimal notation with an SI prefix, rounded to the number of significant digits.
    * `precision` represents the number of significant digits. */
  case class DecimalSI(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "s"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Multiply by 100 and then use decimal notation, rounded to the number of significant digits, with a percent sign.
    * `precision` represents the number of significant digits. */
  case class DecimalPercent(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "p"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Multiply by 100 and then use decimal notation with a percent sign.
    * `precision` represents the number of digits that follow the decimal point. */
  case class Percent(precision: Int = 6) extends Notation {
    override           val symbol    : String      = "%"
    override protected val _precision: Option[Int] = Some(precision)
  }

  /** Binary notation, rounded to the closest integer. */
  case object Binary extends Notation {
    override val symbol: String = "b"
  }

  /** Octal notation, rounded to the closest integer. */
  case object Octal extends Notation {
    override val symbol: String = "o"
  }

  /** Decimal notation, rounded to the closest integer. */
  case object Decimal extends Notation {
    override val symbol: String = "d"
  }

  /** Hexadecimal notation, using lower-case letters, rounded to the closest integer. */
  case object Hexadecimal extends Notation {
    override val symbol: String = "x"
  }

  /** Hexadecimal notation, using upper-case letters, rounded to the closest integer. */
  case object HexadecimalUpper extends Notation {
    override val symbol: String = "X"
  }

  /** Converts the integer to the corresponding unicode character. */
  case object Character extends Notation {
    override val symbol: String = "c"
  }

  object Notation {
    def parse(value: String, precision: Option[Int]): Notation = value match {
      case "" => Plain(precision.getOrElse(12))
      case "f" => FixedPoint(precision.getOrElse(6))
      case "r" => RoundedDecimal(precision.getOrElse(6))
      case "e" => Exponent(precision.getOrElse(6))
      case "g" => RoundedDecimalOrExponent(precision.getOrElse(6))
      case "s" => DecimalSI(precision.getOrElse(6))
      case "p" => DecimalPercent(precision.getOrElse(6))
      case "%" => Percent(precision.getOrElse(6))
      case "b" => Binary
      case "o" => Octal
      case "d" => Decimal
      case "x" => Hexadecimal
      case "X" => HexadecimalUpper
      case "c" => Character
      case _ => throw new IllegalArgumentException(s"'$value' does not represent a valid format notation.")
    }
  }

  sealed trait SIPrefix {
    /** Symbol used to represent this SI prefix in D3. */
    val symbol: String

    override def toString: String = symbol
  }

  /** SI prefix for 10⁻²⁴. */
  case object Yocto extends SIPrefix {
    override val symbol: String = "y"
  }

  /** SI prefix for 10⁻²¹. */
  case object Zepto extends SIPrefix {
    override val symbol: String = "z"
  }

  /** SI prefix for 10⁻¹⁸. */
  case object Atto extends SIPrefix {
    override val symbol: String = "a"
  }

  /** SI prefix for 10⁻¹⁵. */
  case object Fempto extends SIPrefix {
    override val symbol: String = "f"
  }

  /** SI prefix for 10⁻¹². */
  case object Pico extends SIPrefix {
    override val symbol: String = "p"
  }

  /** SI prefix for 10⁻⁹. */
  case object Nano extends SIPrefix {
    override val symbol: String = "n"
  }

  /** SI prefix for 10⁻⁶. */
  case object Micro extends SIPrefix {
    override val symbol: String = "µ"
  }

  /** SI prefix for 10⁻³. */
  case object Milli extends SIPrefix {
    override val symbol: String = "m"
  }

  /** SI prefix for 10⁰. */
  case object NoPrefix extends SIPrefix {
    override val symbol: String = ""
  }

  /** SI prefix for 10³. */
  case object Kilo extends SIPrefix {
    override val symbol: String = "k"
  }

  /** SI prefix for 10⁶. */
  case object Mega extends SIPrefix {
    override val symbol: String = "M"
  }

  /** SI prefix for 10⁹. */
  case object Giga extends SIPrefix {
    override val symbol: String = "G"
  }

  /** SI prefix for 10¹². */
  case object Tera extends SIPrefix {
    override val symbol: String = "T"
  }

  /** SI prefix for 10¹⁵. */
  case object Peta extends SIPrefix {
    override val symbol: String = "P"
  }

  /** SI prefix for 10¹⁸. */
  case object Exa extends SIPrefix {
    override val symbol: String = "E"
  }

  /** SI prefix for 10²¹. */
  case object Zetta extends SIPrefix {
    override val symbol: String = "Z"
  }

  /** SI prefix for 10²⁴. */
  case object Yotta extends SIPrefix {
    override val symbol: String = "Y"
  }

  @js.native private[format] trait FormatSpecifier extends js.Object {
    var fill     : String          = js.native
    var align    : String          = js.native
    var sign     : String          = js.native
    var symbol   : String          = js.native
    var zero     : Boolean         = js.native
    var width    : js.UndefOr[Int] = js.native
    var comma    : Boolean         = js.native
    var precision: js.UndefOr[Int] = js.native
    var `type`   : String          = js.native
  }

  @JSImport("d3-format", JSImport.Namespace)
  @js.native private[format] object NumberFacade extends js.Object {
    def format(specifier: String): js.Function1[Double, String] = js.native
    def formatPrefix(specifier: String, value: Double): js.Function1[Double, String] = js.native
    def formatSpecifier(specifier: String): FormatSpecifier = js.native
    def precisionFixed(step: Double): Int = js.native
    def precisionPrefix(step: Double, value: Double): Int = js.native
    def precisionRound(step: Double, max: Double): Int = js.native
    def formatLocale(definition: js.Dictionary[js.Object]): Locale.NumberFacade = js.native
    def formatDefaultLocale(definition: js.Dictionary[js.Object]): Locale.NumberFacade = js.native
  }

  @JSImport("d3-time-format", JSImport.Namespace)
  @js.native private[format] object TimeFacade extends js.Object {
    def timeFormat(specifier: String): js.Function1[js.Date, String] = js.native
    def timeParse(specifier: String): js.Function1[String, js.Date | Null] = js.native
    def utcFormat(specifier: String): js.Function1[js.Date, String] = js.native
    def utcParse(specifier: String): js.Function1[String, js.Date | Null] = js.native
    def isoFormat(date: js.Date): String = js.native
    def isoParse(dateString: String): js.Date | Null = js.native
    def timeFormatLocale(definition: js.Dictionary[js.Object]): Locale.TimeFacade = js.native
    def timeFormatDefaultLocale(definition: js.Dictionary[js.Object]): Locale.TimeFacade = js.native
  }
}
