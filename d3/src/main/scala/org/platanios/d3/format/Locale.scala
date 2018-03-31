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

import scala.scalajs.js
import scala.scalajs.js.|

/** Represents a formatting locale.
  *
  * @author Emmanouil Antonios Platanios
  */
class Locale protected[format] (val definition: Locale.Definition) {
  private[d3] val numberFacade: Locale.NumberFacade = Format.NumberFacade.formatLocale(definition.numberFacade)
  private[d3] val timeFacade  : Locale.TimeFacade   = Format.TimeFacade.timeFormatLocale(definition.timeFacade)

  /** Returns a formatter function for `specifier`, using this locale.
    *
    * @param  specifier Number format specifier.
    * @return Formatter function.
    */
  def number(specifier: Format.NumberSpecifier): js.Function1[Double, String] = {
    numberFacade.format(specifier.toString)
  }

  /** Returns a formatter function for `specifier`, using this locale, will convert values to the units of the
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
  def numberPrefix(specifier: Format.NumberSpecifier, value: Double): js.Function1[Double, String] = {
    numberFacade.formatPrefix(specifier.toString, value)
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
    timeFacade.format(specifier)
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
    timeFacade.parse(specifier)
  }

  /** Equivalent to `time()` except that all directives are interpreted as
    * [Coordinated Universal Time (UTC)](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    * rather than local time. */
  def timeUTC(specifier: String): js.Function1[js.Date, String] = {
    timeFacade.utcFormat(specifier)
  }

  /** Equivalent to `timeParse()` except that all directives are interpreted as
    * [Coordinated Universal Time (UTC)](https://en.wikipedia.org/wiki/Coordinated_Universal_Time)
    * rather than local time. */
  def timeParseUTC(specifier: String): js.Function1[String, js.Date | Null] = {
    timeFacade.utcParse(specifier)
  }
}

object Locale {
  @js.native private[format] trait NumberFacade extends js.Object {
    def format(specifier: String): js.Function1[Double, String] = js.native
    def formatPrefix(specifier: String, value: Double): js.Function1[Double, String] = js.native
  }

  @js.native private[format] trait TimeFacade extends js.Object {
    def format(specifier: String): js.Function1[js.Date, String] = js.native
    def parse(specifier: String): js.Function1[String, js.Date | Null] = js.native
    def utcFormat(specifier: String): js.Function1[js.Date, String] = js.native
    def utcParse(specifier: String): js.Function1[String, js.Date | Null] = js.native
  }

  private type Numerals = js.Tuple10[String, String, String, String, String, String, String, String, String, String]
  private type Days = js.Tuple7[String, String, String, String, String, String, String]
  private type Months = js.Tuple12[String, String, String, String, String, String, String, String, String, String, String, String]

  case class Definition(
      decimal: String,
      thousands: String,
      grouping: js.Array[Int],
      currency: js.Tuple2[String, String],
      numerals: Numerals,
      percent: String,
      dateTime: String,
      date: String,
      time: String,
      periods: js.Tuple2[String, String],
      days: Days,
      shortDays: Days,
      months: Months,
      shortMonths: Months) {
    private[d3] val numberFacade: js.Dictionary[js.Object] = {
      js.Dictionary(
        "decimal" -> js.Object(decimal),
        "thousands" -> js.Object(thousands),
        "grouping" -> js.Object(grouping),
        "currency" -> js.Object(currency),
        "numerals" -> js.Object(numerals),
        "percent" -> js.Object(percent))
    }

    private[d3] val timeFacade: js.Dictionary[js.Object] = {
      js.Dictionary(
        "dateTime" -> js.Object(dateTime),
        "time" -> js.Object(time),
        "periods" -> js.Object(periods),
        "days" -> js.Object(days),
        "shortDays" -> js.Object(shortDays),
        "months" -> js.Object(months),
        "shortMonths" -> js.Object(shortMonths))
    }
  }

  object Definition {
    val ar001: Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arAE : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062f\u002e\u0625\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arBH : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062f\u002e\u0628\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arDJ : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("\u200f\u0046\u0064\u006a ", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arDZ : Definition = Definition(
      decimal = "\u002c",
      thousands = "\u002e",
      grouping = js.Array(3),
      currency = ("\u062f\u002e\u062c\u002e ", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arEG : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062c\u002e\u0645\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arEH : Definition = Definition(
      decimal = "\u002e",
      thousands = "\u002c",
      grouping = js.Array(3),
      currency = ("\u062f\u002e\u0645\u002e ", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arER : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("\u004e\u0066\u006b ", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arIL : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("\u20aa ", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arIQ : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062f\u002e\u0639\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arJO : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062f\u002e\u0623\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arKM : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0641\u002e\u062c\u002e\u0642\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arKW : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062f\u002e\u0643\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arLB : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0644\u002e\u0644\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arLY : Definition = Definition(
      decimal = "\u002c",
      thousands = "\u002e",
      grouping = js.Array(3),
      currency = ("\u062f\u002e\u0644\u002e ", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arMA : Definition = Definition(
      decimal = "\u002c",
      thousands = "\u002e",
      grouping = js.Array(3),
      currency = ("\u062f\u002e\u0645\u002e ", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arMR : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0623\u002e\u0645\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arOM : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0631\u002e\u0639\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arPS : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("\u20aa ", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arQA : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0631\u002e\u0642\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arSA : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0631\u002e\u0633\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arSD : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u062c\u002e\u0633\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arSO : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("\u200f\u0053 ", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arSY : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0644\u002e\u0633\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arTD : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("\u200f\u0046\u0043\u0046\u0041 ", ""),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arTN : Definition = Definition(
      decimal = "\u002c",
      thousands = "\u002e",
      grouping = js.Array(3),
      currency = ("\u062f\u002e\u062a\u002e ", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val arYE : Definition = Definition(
      decimal = "\u066b",
      thousands = "\u066c",
      grouping = js.Array(3),
      currency = ("", " \u0631\u002e\u0649\u002e"),
      numerals = ("\u0660", "\u0661", "\u0662", "\u0663", "\u0664", "\u0665", "\u0666", "\u0667", "\u0668", "\u0669"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("ص", "م"),
      days = ("الأحد", "الاثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة", "السبت"),
      shortDays = ("ح", "ن", "ث", "ر", "خ", "ج", "س"),
      months = ("ديسمبر", "فبراير", "مارس", "أبريل", "مايو", "يونيو", "يوليو", "أغسطس", "سبتمبر", "أكتوبر", "نوفمبر", "ديسمب"),
      shortMonths = ("ينا", "فبر", "مار", "أبر", "ماي", "يون", "يول", "أغس", "سبت", "أكت", "نوف", "ديس"))
    val caES : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "\u00a0€"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e de %B de %Y, %X",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("diumenge", "dilluns", "dimarts", "dimecres", "dijous", "divendres", "dissabte"),
      shortDays = ("dg.", "dl.", "dt.", "dc.", "dj.", "dv.", "ds."),
      months = ("gener", "febrer", "març", "abril", "maig", "juny", "juliol", "agost", "setembre", "octubre", "novembre", "desembre"),
      shortMonths = ("gen.", "febr.", "març", "abr.", "maig", "juny", "jul.", "ag.", "set.", "oct.", "nov.", "des."))
    val csCZ : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "\u00a0Kč"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A,%e.%B %Y, %X",
      date = "%-d.%-m.%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("neděle", "pondělí", "úterý", "středa", "čvrtek", "pátek", "sobota"),
      shortDays = ("ne.", "po.", "út.", "st.", "čt.", "pá.", "so."),
      months = ("leden", "únor", "březen", "duben", "květen", "červen", "červenec", "srpen", "září", "říjen", "listopad", "prosinec"),
      shortMonths = ("led", "úno", "břez", "dub", "kvě", "čer", "červ", "srp", "zář", "říj", "list", "pros"))
    val daDK : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "\u00a0kr"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A den %d %B %Y %X",
      date = "%d-%m-%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("søndag", "mandag", "tirsdag", "onsdag", "torsdag", "fredag", "lørdag"),
      shortDays = ("søn", "man", "tir", "ons", "tor", "fre", "lør"),
      months = ("januar", "februar", "marts", "april", "maj", "juni", "juli", "august", "september", "oktober", "november", "december"),
      shortMonths = ("jan", "feb", "mar", "apr", "maj", "jun", "jul", "aug", "sep", "okt", "nov", "dec"))
    val deCH : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "\u00a0CHF"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, der %e. %B %Y, %X",
      date = "%d.%m.%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"),
      shortDays = ("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"),
      months = ("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"),
      shortMonths = ("Jan", "Feb", "Mrz", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"))
    val deDE : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "\u00a0€"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, der %e. %B %Y, %X",
      date = "%d.%m.%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag"),
      shortDays = ("So", "Mo", "Di", "Mi", "Do", "Fr", "Sa"),
      months = ("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember"),
      shortMonths = ("Jan", "Feb", "Mrz", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"))
    val enCA : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("$", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %b %e %X %Y",
      date = "%Y-%m-%d",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
      shortDays = ("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
      months = ("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
      shortMonths = ("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
    val enGB : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("£", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
      shortDays = ("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
      months = ("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
      shortMonths = ("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
    val enIN : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3, 2, 2, 2, 2, 2, 2, 2, 2, 2),
      currency = ("₹", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %b %e %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
      shortDays = ("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
      months = ("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
      shortMonths = ("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
    val enUS : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("$", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%x, %X",
      date = "%-m/%-d/%Y",
      time = "%-I:%M:%S %p",
      periods = ("AM", "PM"),
      days = ("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"),
      shortDays = ("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"),
      months = ("January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"),
      shortMonths = ("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"))
    val esES : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "\u00a0€"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e de %B de %Y, %X",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"),
      shortDays = ("dom", "lun", "mar", "mié", "jue", "vie", "sáb"),
      months = ("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"),
      shortMonths = ("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"))
    val esMX : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("$", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%x, %X",
      date = "%d/%m/%Y",
      time = "%-I:%M:%S %p",
      periods = ("AM", "PM"),
      days = ("domingo", "lunes", "martes", "miércoles", "jueves", "viernes", "sábado"),
      shortDays = ("dom", "lun", "mar", "mié", "jue", "vie", "sáb"),
      months = ("enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"),
      shortMonths = ("ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"))
    val fiFI : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "\u00a0€"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %-d. %Bta %Y klo %X",
      date = "%-d.%-m.%Y",
      time = "%H:%M:%S",
      periods = ("a.m.", "p.m."),
      days = ("sunnuntai", "maanantai", "tiistai", "keskiviikko", "torstai", "perjantai", "lauantai"),
      shortDays = ("Su", "Ma", "Ti", "Ke", "To", "Pe", "La"),
      months = ("tammikuu", "helmikuu", "maaliskuu", "huhtikuu", "toukokuu", "kesäkuu", "heinäkuu", "elokuu", "syyskuu", "lokakuu", "marraskuu", "joulukuu"),
      shortMonths = ("Tammi", "Helmi", "Maalis", "Huhti", "Touko", "Kesä", "Heinä", "Elo", "Syys", "Loka", "Marras", "Joulu"))
    val frCA : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "$"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %Y %X",
      date = "%Y-%m-%d",
      time = "%H:%M:%S",
      periods = ("", ""),
      days = ("dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"),
      shortDays = ("dim", "lun", "mar", "mer", "jeu", "ven", "sam"),
      months = ("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"),
      shortMonths = ("jan", "fév", "mar", "avr", "mai", "jui", "jul", "aoû", "sep", "oct", "nov", "déc"))
    val frFR : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "\u00a0€"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "\u202f%",
      dateTime = "%A, le %e %B %Y, %X",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("dimanche", "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi"),
      shortDays = ("dim.", "lun.", "mar.", "mer.", "jeu.", "ven.", "sam."),
      months = ("janvier", "février", "mars", "avril", "mai", "juin", "juillet", "août", "septembre", "octobre", "novembre", "décembre"),
      shortMonths = ("janv.", "févr.", "mars", "avr.", "mai", "juin", "juil.", "août", "sept.", "oct.", "nov.", "déc."))
    val heIL : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("₪", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e ב%B %Y %X",
      date = "%d.%m.%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("ראשון", "שני", "שלישי", "רביעי", "חמישי", "שישי", "שבת"),
      shortDays = ("א׳", "ב׳", "ג׳", "ד׳", "ה׳", "ו׳", "ש׳"),
      months = ("ינואר", "פברואר", "מרץ", "אפריל", "מאי", "יוני", "יולי", "אוגוסט", "ספטמבר", "אוקטובר", "נובמבר", "דצמבר"),
      shortMonths = ("ינו׳", "פבר׳", "מרץ", "אפר׳", "מאי", "יוני", "יולי", "אוג׳", "ספט׳", "אוק׳", "נוב׳", "דצמ׳"))
    val huHU : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "\u00a0Ft"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%Y. %B %-e., %A %X",
      date = "%Y. %m. %d.",
      time = "%H:%M:%S",
      periods = ("de.", "du."),
      days = ("vasárnap", "hétfő", "kedd", "szerda", "csütörtök", "péntek", "szombat"),
      shortDays = ("V", "H", "K", "Sze", "Cs", "P", "Szo"),
      months = ("január", "február", "március", "április", "május", "június", "július", "augusztus", "szeptember", "október", "november", "december"),
      shortMonths = ("jan.", "feb.", "már.", "ápr.", "máj.", "jún.", "júl.", "aug.", "szept.", "okt.", "nov.", "dec."))
    val itIT : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("€", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A %e %B %Y, %X",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Domenica", "Lunedì", "Martedì", "Mercoledì", "Giovedì", "Venerdì", "Sabato"),
      shortDays = ("Dom", "Lun", "Mar", "Mer", "Gio", "Ven", "Sab"),
      months = ("Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre"),
      shortMonths = ("Gen", "Feb", "Mar", "Apr", "Mag", "Giu", "Lug", "Ago", "Set", "Ott", "Nov", "Dic"))
    val jaJP : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("", "円"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%Y %b %e %a %X",
      date = "%Y/%m/%d",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日"),
      shortDays = ("日", "月", "火", "水", "木", "金", "土"),
      months = ("睦月", "如月", "弥生", "卯月", "皐月", "水無月", "文月", "葉月", "長月", "神無月", "霜月", "師走"),
      shortMonths = ("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"))
    val koKR : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("₩", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%Y/%m/%d %a %X",
      date = "%Y/%m/%d",
      time = "%H:%M:%S",
      periods = ("오전", "오후"),
      days = ("일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"),
      shortDays = ("일", "월", "화", "수", "목", "금", "토"),
      months = ("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"),
      shortMonths = ("1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"))
    val mkMK : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "\u00a0ден."),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e %B %Y г. %X",
      date = "%d.%m.%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("недела", "понеделник", "вторник", "среда", "четврток", "петок", "сабота"),
      shortDays = ("нед", "пон", "вто", "сре", "чет", "пет", "саб"),
      months = ("јануари", "февруари", "март", "април", "мај", "јуни", "јули", "август", "септември", "октомври", "ноември", "декември"),
      shortMonths = ("јан", "фев", "мар", "апр", "мај", "јун", "јул", "авг", "сеп", "окт", "ное", "дек"))
    val nlNL : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("€\u00a0", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %B %Y %T",
      date = "%d-%m-%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("zondag", "maandag", "dinsdag", "woensdag", "donderdag", "vrijdag", "zaterdag"),
      shortDays = ("zo", "ma", "di", "wo", "do", "vr", "za"),
      months = ("januari", "februari", "maart", "april", "mei", "juni", "juli", "augustus", "september", "oktober", "november", "december"),
      shortMonths = ("jan", "feb", "mrt", "apr", "mei", "jun", "jul", "aug", "sep", "okt", "nov", "dec"))
    val plPL : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "zł"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e %B %Y, %X",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Niedziela", "Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek", "Sobota"),
      shortDays = ("Niedz.", "Pon.", "Wt.", "Śr.", "Czw.", "Pt.", "Sob."),
      months = ("Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"),
      shortMonths = ("Stycz.", "Luty", "Marz.", "Kwie.", "Maj", "Czerw.", "Lipc.", "Sierp.", "Wrz.", "Paźdz.", "Listop.", "Grudz."))
    val ptBR : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("R$", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e de %B de %Y. %X",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Domingo", "Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado"),
      shortDays = ("Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"),
      months = ("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"),
      shortMonths = ("Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"))
    val ruRU : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "\u00a0руб."),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e %B %Y г. %X",
      date = "%d.%m.%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("воскресенье", "понедельник", "вторник", "среда", "четверг", "пятница", "суббота"),
      shortDays = ("вс", "пн", "вт", "ср", "чт", "пт", "сб"),
      months = ("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"),
      shortMonths = ("янв", "фев", "мар", "апр", "май", "июн", "июл", "авг", "сен", "окт", "ноя", "дек"))
    val svSE : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "SEK"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A den %d %B %Y %X",
      date = "%Y-%m-%d",
      time = "%H:%M:%S",
      periods = ("fm", "em"),
      days = ("Söndag", "Måndag", "Tisdag", "Onsdag", "Torsdag", "Fredag", "Lördag"),
      shortDays = ("Sön", "Mån", "Tis", "Ons", "Tor", "Fre", "Lör"),
      months = ("Januari", "Februari", "Mars", "April", "Maj", "Juni", "Juli", "Augusti", "September", "Oktober", "November", "December"),
      shortMonths = ("Jan", "Feb", "Mar", "Apr", "Maj", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"))
    val trTR : Definition = Definition(
      decimal = ",",
      thousands = ".",
      grouping = js.Array(3),
      currency = ("", "TL"),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%a %e %b %X %Y",
      date = "%d/%m/%Y",
      time = "%H:%M:%S",
      periods = ("AM", "PM"),
      days = ("Pazar", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi"),
      shortDays = ("Paz", "Pzt", "Sal", "Çar", "Per", "Cum", "Cmt"),
      months = ("Ocak", "Şubat", "Mart", "Nisan", "Mayıs", "Haziran", "Temmuz", "Ağustos", "Eylül", "Ekim", "Kasım", "Aralık"),
      shortMonths = ("Oca", "Şub", "Mar", "Nis", "May", "Haz", "Tem", "Ağu", "Eyl", "Eki", "Kas", "Ara"))
    val ukUA : Definition = Definition(
      decimal = ",",
      thousands = "\u00a0",
      grouping = js.Array(3),
      currency = ("", "\u00a0₴."),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%A, %e %B %Y р. %X",
      date = "%d.%m.%Y",
      time = "%H:%M:%S",
      periods = ("дп", "пп"),
      days = ("неділя", "понеділок", "вівторок", "середа", "четвер", "п'ятниця", "субота"),
      shortDays = ("нд", "пн", "вт", "ср", "чт", "пт", "сб"),
      months = ("січня", "лютого", "березня", "квітня", "травня", "червня", "липня", "серпня", "вересня", "жовтня", "листопада", "грудня"),
      shortMonths = ("січ.", "лют.", "бер.", "квіт.", "трав.", "черв.", "лип.", "серп.", "вер.", "жовт.", "лист.", "груд."))
    val zhCN : Definition = Definition(
      decimal = ".",
      thousands = ",",
      grouping = js.Array(3),
      currency = ("¥", ""),
      numerals = ("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"),
      percent = "%",
      dateTime = "%x %A %X",
      date = "%Y年%-m月%-d日",
      time = "%H:%M:%S",
      periods = ("上午", "下午"),
      days = ("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"),
      shortDays = ("周日", "周一", "周二", "周三", "周四", "周五", "周六"),
      months = ("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"),
      shortMonths = ("一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"))
  }

  val ar001: Locale = Format.formatLocale(Definition.ar001)
  val arAE : Locale = Format.formatLocale(Definition.arAE)
  val arBH : Locale = Format.formatLocale(Definition.arBH)
  val arDJ : Locale = Format.formatLocale(Definition.arDJ)
  val arDZ : Locale = Format.formatLocale(Definition.arDZ)
  val arEG : Locale = Format.formatLocale(Definition.arEG)
  val arEH : Locale = Format.formatLocale(Definition.arEH)
  val arER : Locale = Format.formatLocale(Definition.arER)
  val arIL : Locale = Format.formatLocale(Definition.arIL)
  val arIQ : Locale = Format.formatLocale(Definition.arIQ)
  val arJO : Locale = Format.formatLocale(Definition.arJO)
  val arKM : Locale = Format.formatLocale(Definition.arKM)
  val arKW : Locale = Format.formatLocale(Definition.arKW)
  val arLB : Locale = Format.formatLocale(Definition.arLB)
  val arLY : Locale = Format.formatLocale(Definition.arLY)
  val arMA : Locale = Format.formatLocale(Definition.arMA)
  val arMR : Locale = Format.formatLocale(Definition.arMR)
  val arOM : Locale = Format.formatLocale(Definition.arOM)
  val arPS : Locale = Format.formatLocale(Definition.arPS)
  val arQA : Locale = Format.formatLocale(Definition.arQA)
  val arSA : Locale = Format.formatLocale(Definition.arSA)
  val arSD : Locale = Format.formatLocale(Definition.arSD)
  val arSO : Locale = Format.formatLocale(Definition.arSO)
  val arSY : Locale = Format.formatLocale(Definition.arSY)
  val arTD : Locale = Format.formatLocale(Definition.arTD)
  val arTN : Locale = Format.formatLocale(Definition.arTN)
  val arYE : Locale = Format.formatLocale(Definition.arYE)
  val caES : Locale = Format.formatLocale(Definition.caES)
  val csCZ : Locale = Format.formatLocale(Definition.csCZ)
  val daDK : Locale = Format.formatLocale(Definition.daDK)
  val deCH : Locale = Format.formatLocale(Definition.deCH)
  val deDE : Locale = Format.formatLocale(Definition.deDE)
  val enCA : Locale = Format.formatLocale(Definition.enCA)
  val enGB : Locale = Format.formatLocale(Definition.enGB)
  val enIN : Locale = Format.formatLocale(Definition.enIN)
  val enUS : Locale = Format.formatLocale(Definition.enUS)
  val esES : Locale = Format.formatLocale(Definition.esES)
  val esMX : Locale = Format.formatLocale(Definition.esMX)
  val fiFI : Locale = Format.formatLocale(Definition.fiFI)
  val frCA : Locale = Format.formatLocale(Definition.frCA)
  val frFR : Locale = Format.formatLocale(Definition.frFR)
  val heIL : Locale = Format.formatLocale(Definition.heIL)
  val huHU : Locale = Format.formatLocale(Definition.huHU)
  val itIT : Locale = Format.formatLocale(Definition.itIT)
  val jaJP : Locale = Format.formatLocale(Definition.jaJP)
  val koKR : Locale = Format.formatLocale(Definition.koKR)
  val mkMK : Locale = Format.formatLocale(Definition.mkMK)
  val nlNL : Locale = Format.formatLocale(Definition.nlNL)
  val plPL : Locale = Format.formatLocale(Definition.plPL)
  val ptBR : Locale = Format.formatLocale(Definition.ptBR)
  val ruRU : Locale = Format.formatLocale(Definition.ruRU)
  val svSE : Locale = Format.formatLocale(Definition.svSE)
  val trTR : Locale = Format.formatLocale(Definition.trTR)
  val ukUA : Locale = Format.formatLocale(Definition.ukUA)
  val zhCN : Locale = Format.formatLocale(Definition.zhCN)
}
