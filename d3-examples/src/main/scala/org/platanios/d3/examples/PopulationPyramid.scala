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
import org.platanios.d3.selection.Selection

import org.scalajs.dom

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

/** Example ported from [here](https://bl.ocks.org/mbostock/4062085).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("PopulationPyramid") @JSExportAll
object PopulationPyramid {
  def run(): Unit = {
    val margin = Map("top" -> 20, "right" -> 40, "bottom" -> 30, "left" -> 20)
    val width = 960 - margin("left") - margin("right")
    val height = 500 - margin("top") - margin("bottom")
    val barWidth = Math.floor(width / 19) - 1

    // An SVG element with a bottom-right origin.
    val svg = d3.select("body").append("svg")
        .attr("width", width + margin("left") + margin("right"))
        .attr("height", height + margin("top") + margin("bottom"))
        .append("g")
        .attr("transform", "translate(" + margin("left") + "," + margin("top") + ")")

    // A sliding container to hold the bars by birth year.
    val birthyears = svg.append("g") attr("class", "birthyears")

    // A label for the current year.
    val title = svg.append("text")
        .attr("class", "title")
        .attr("dy", ".71em")
        .text("2000")

    d3.fetch.csv("population.csv").foreach(parsed => {
      // Compute the extent of the data set in people, age, and years.
      val people1 = parsed.map(d => d("people").toDouble).max
      val age1 = parsed.map(d => d("age").toDouble).max
      val year0 = parsed.map(d => d("year").toDouble).min
      val year1 = parsed.map(d => d("year").toDouble).max
      var year = year1

      val x = d3.scale.linear(
        domain = Seq(year1 - age1, year1),
        range = Seq(barWidth / 2, width - barWidth / 2))

      val y = d3.scale.linear(
        domain = Seq(0.0, people1),
        range = Seq(height, 0.0))

      val yAxis = d3.axis(
        position = d3.axis.Right,
        scale = y,
        tickSizeInner = -width,
        tickSizeOuter = -width,
        tickFormat = (d: Double) => Math.round(d / 1e6) + "M")

      // Produce a map from year and birth year to male/female.
      val groupedData = parsed
          .groupBy(d => d("year").toDouble)
          .mapValues(_.groupBy(d => d("year").toDouble - d("age").toDouble)
              .mapValues(v => js.Array(v.map(_ ("people").toDouble): _*)))

      // Add an axis to show the population values.
      svg.append("g")
          .attr("class", "y axis")
          .attr("transform", "translate(" + width + ",0)")
          //.call((d: Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => yAxis(d))
          .call((d: Selection[dom.Element, js.Any, dom.Element, js.Any]) => yAxis(d))
          .selectAll("g")
          .filter((v: Double) => v == 0.0)
          .classed("zero", true)

      // Add labeled rects for each birthyear (so that no enter or exit is required).
      val birthyear = birthyears.selectAll(".birthyear")
          .data(d3.range(year0 - age1, year1 + 1, 5))
          .enter().append("g")
          .attr("class", "birthyear")
          .attr("transform", (year: Double) => "translate(" + x(year) + ",0)")

      birthyear.selectAll("rect")
          .data((_: dom.EventTarget, birthyear: Double) => {
            groupedData
                .getOrElse(year, Map.empty)
                .getOrElse(birthyear, js.Array[Double](0, 0))
          })
          .enter().append("rect")
          .attr("x", -barWidth / 2)
          .attr("width", barWidth)
          .attr("y", (value: Double) => y(value))
          .attr("height", (value: Double) => height - y(value))

      // Add labels to show the birth year.
      birthyear.append("text")
          .attr("y", height - 4)
          .text((birthyear: Double) => birthyear.toString)

      // Add labels to show the age (separate and not animated).
      svg.selectAll(".age")
          .data(d3.range(0, age1 + 1, 5))
          .enter().append("text")
          .attr("class", "age")
          .attr("x", (age: Double) => x(year - age))
          .attr("y", height + 4)
          .attr("dy", ".71em")
          .text((age: Double) => age.toString)

      // Allow the arrow keys to change the displayed year.
      dom.window.focus()
      d3.select(dom.window).on("keydown", () => {
        d3.event().asInstanceOf[dom.KeyboardEvent].keyCode match {
          case 37 => year = Math.max(year0, year - 10)
          case 39 => year = Math.min(year1, year + 10)
        }
        update()
      })

      def update(): Unit = groupedData.get(year) match {
        case None => ()
        case Some(data) =>
          title.text(year.toString)

          birthyears.transition()
              .duration(750)
              .attr("transform", "translate(" + (x(year1) - x(year)) + ",0)")

          birthyear.selectAll("rect")
              .data((_: dom.EventTarget, birthyear: Double) => {
                groupedData
                    .getOrElse(year, Map.empty)
                    .getOrElse(birthyear, js.Array[Double](0, 0))
              })
              .transition()
              .duration(750)
              .attr("y", (value: Double) => y(value))
              .attr("height", (value: Double) => height - y(value))
      }
    })
  }
}
