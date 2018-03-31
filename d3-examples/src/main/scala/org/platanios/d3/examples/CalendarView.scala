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

package org.platanios.d3.examples

import org.platanios.d3._

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

/** Example ported from [here](https://bl.ocks.org/mbostock/4063318).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("CalendarView") @JSExportAll
object CalendarView {
  def run(): Unit = {
    val width = 960
    val height = 136
    val cellSize = 17

    val formatPercent = d3.format.number(".1%")

    val color = d3.scale.quantize(
      domain = (-0.05, 0.05),
      range = Seq(
        "#a50026", "#d73027", "#f46d43", "#fdae61", "#fee08b", "#ffffbf",
        "#d9ef8b", "#a6d96a", "#66bd63", "#1a9850", "#006837"))

    val svg = d3.select("body")
        .selectAll("svg")
        .data(js.Array((1990 until 2011): _*))
        .enter().append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + ((width - cellSize * 53) / 2) + "," + (height - cellSize * 7 - 1) + ")")

    svg.append("text")
        .attr("transform", "translate(-6," + cellSize * 3.5 + ")rotate(-90)")
        .attr("font-family", "sans-serif")
        .attr("font-size", 10)
        .attr("text-anchor", "middle")
        .text((d: Int) => d)

    val rect = svg.append("g")
        .attr("fill", "none")
        .attr("stroke", "#ccc")
        .selectAll("rect")
        .data((d: Int) => d3.time.days(new js.Date(d, 0, 1), new js.Date(d + 1, 0, 1)))
        .enter().append("rect")
        .attr("width", cellSize)
        .attr("height", cellSize)
        .attr("x", (d: js.Date) => d3.time.week.count(d3.time.year(d), d) * cellSize)
        .attr("y", (d: js.Date) => d.getDay() * cellSize)
        .datum(d3.format.time("%Y-%m-%d"))

    svg.append("g")
        .attr("fill", "none")
        .attr("stroke", "#000")
        .selectAll("path")
        .data((d: Int) => d3.time.months(new js.Date(d, 0, 1), new js.Date(d + 1, 0, 1)))
        .enter().append("path")
        .attr("d", (t0: js.Date) => {
          val t1 = new js.Date(t0.getFullYear(), t0.getMonth() + 1, 0)
          val d0 = t0.getDay()
          val w0 = d3.time.week.count(d3.time.year(t0), t0)
          val d1 = t1.getDay()
          val w1 = d3.time.week.count(d3.time.year(t1), t1)
          "M" + (w0 + 1) * cellSize + "," + d0 * cellSize +
              "H" + w0 * cellSize + "V" + 7 * cellSize +
              "H" + w1 * cellSize + "V" + (d1 + 1) * cellSize +
              "H" + (w1 + 1) * cellSize + "V" + 0 +
              "H" + (w0 + 1) * cellSize + "Z"
        })

    d3.csv("dji.csv").then[Unit](parsed => {
      val data = parsed.groupBy(_ ("Date")).map {
        case (k, v) => k -> (v(0)("Close").toDouble - v(0)("Open").toDouble) / v(0)("Open").toDouble
      }

      rect.filter((d: String) => data.contains(d))
          .attr("fill", (d: String) => color(data(d)))
          .append("title")
          .text((d: String) => d + ": " + formatPercent(data(d)))

      ()
    })
  }
}
