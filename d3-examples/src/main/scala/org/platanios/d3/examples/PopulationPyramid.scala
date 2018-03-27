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

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

/**
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("PopulationPyramid") @JSExportAll
object PopulationPyramid {
  def run(): Unit = {
    val margin = Map("top" -> 20, "right" -> 40, "bottom" -> 30, "left" -> 20)
    val width = 960 - margin("left") - margin("right")
    val height = 500 - margin("top") - margin("bottom")
    val barWidth = Math.floor(width / 19) - 1

    val x = D3.scaleLinear().range(js.Array(barWidth / 2, width - barWidth / 2))
    val y = D3.scaleLinear().range(js.Array(height, 0))

    val yAxis = D3.axisRight(y)
        .tickSize(-width)
        .tickFormat((d, _) => Math.round(d / 1e6) + "M")

    // An SVG element with a bottom-right origin.
    val svg = D3.select("body").append("svg")
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

    D3.csv("population.csv").then[Unit](parsed => {
      // Compute the extent of the data set in people, age, and years.
      val people1 = parsed.map(d => d("people").toDouble).max
      val age1 = parsed.map(d => d("age").toDouble).max
      val year0 = parsed.map(d => d("year").toDouble).min
      val year1 = parsed.map(d => d("year").toDouble).max
      var year = year1

      // Update the scale domains.
      x.domain(js.Array(year1 - age1, year1))
      y.domain(js.Array(0, people1))

      // Produce a map from year and birth year to male/female.
      val groupedData = parsed
          .groupBy(d => d("year").toDouble)
          .mapValues(_.groupBy(d => d("year").toDouble - d("age").toDouble)
              .mapValues(v => js.Array(v.map(_("people").toDouble): _*)))

      // Add an axis to show the population values.
      svg.append("g")
          .attr("class", "y axis")
          .attr("transform", "translate(" + width + ",0)")
          .call((d: Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => yAxis(d))
          .selectAll("g")
          .filter(f((v: Double) => v == 0.0))
          .classed("zero", true)

      // Add labeled rects for each birthyear (so that no enter or exit is required).
      val birthyear = birthyears.selectAll(".birthyear")
          .data(D3.range(year0 - age1, year1 + 1, 5))
          .enter().append("g")
          .attr("class", "birthyear")
          .attr("transform", f((year: Double) => "translate(" + x(year) + ",0)"))

      birthyear.selectAll("rect")
          .data[Double]((_: dom.EventTarget, birthyear: Double) => {
            groupedData
                .getOrElse(year, Map.empty)
                .getOrElse(birthyear, js.Array[Double](0, 0))
          })
          .enter().append("rect")
          .attr("x", -barWidth / 2)
          .attr("width", barWidth)
          .attr("y", f((value: Double) => y(value)))
          .attr("height", f((value: Double) => height - y(value)))

      // Add labels to show the birth year.
      birthyear.append("text")
          .attr("y", height - 4)
          .text(f((birthyear: Double) => birthyear.toString))

      // Add labels to show the age (separate and not animated).
      svg.selectAll(".age")
          .data(D3.range(0, age1 + 1, 5))
          .enter().append("text")
          .attr("class", "age")
          .attr("x", f((age: Double) => x(year - age)))
          .attr("y", height + 4)
          .attr("dy", ".71em")
          .text(f((age: Double) => age.toString))

      // Allow the arrow keys to change the displayed year.
      dom.window.focus()
      D3.select(dom.window).on("keydown", f(() => {
        D3.event.asInstanceOf[dom.KeyboardEvent].keyCode match {
          case 37 => year = Math.max(year0, year - 10)
          case 39 => year = Math.min(year1, year + 10)
        }
        update()
      }))

      def update(): Unit = groupedData.get(year) match {
        case None => ()
        case Some(data) =>
          title.text(year.toString)

          birthyears.transition()
              .duration(750)
              .attr("transform", "translate(" + (x(year1) - x(year)) + ",0)")

          birthyear.selectAll("rect")
              .data[Double]((_: dom.EventTarget, birthyear: Double) => {
                groupedData
                    .getOrElse(year, Map.empty)
                    .getOrElse(birthyear, js.Array[Double](0, 0))
              })
              .transition()
              .duration(750)
              .attr("y", f((value: Double) => y(value)))
              .attr("height", f((value: Double) => height - y(value)))
      }
    })
  }
}
