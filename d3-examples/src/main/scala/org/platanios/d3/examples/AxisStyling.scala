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
import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSExportAll, JSExportTopLevel}

/**
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("AxisStyling") @JSExportAll
object AxisStyling {
  def run(): Unit = {
    val margin = Map("top" -> 20, "right" -> 0, "bottom" -> 20, "left" -> 0)
    val width = 960 - margin("left") - margin("right")
    val height = 500 - margin("top") - margin("bottom")

    // An SVG element with a bottom-right origin.
    val g = d3.select("body").append("svg")
        .attr("width", width + margin("left") + margin("right"))
        .attr("height", height + margin("top") + margin("bottom"))
        .append("g")
        .attr("transform", "translate(" + margin("left") + "," + margin("top") + ")")

    val formatNumber = d3.format.number(".1f")

    val x = d3.scale.time(
      domain = Seq(new js.Date(2010, 7, 1), new js.Date(2012, 7, 1)),
      range = Seq(0, width))

    val y = d3.scale.linear(
      domain = Seq(0, 2e6),
      range = Seq(height, 0))

    val xAxis = d3.axis(
      position = d3.axis.Bottom,
      scale = x).ticks(d3.timeYear)

    val yAxis = d3.axis(
      position = d3.axis.Right,
      scale = y,
      tickSizeInner = width,
      tickSizeOuter = width,
      tickFormat = (g: dom.svg.Element, d: Double) => {
        val s = formatNumber(d / 1e6)
        if (g.parentNode.nextSibling != null)
          "\u00a0" + s
        else
          "$" + s + " million"
      })

    g.append("g")
        .attr("transform", "translate(0," + height + ")")
        .call((g: selection.Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => xAxis(g))
        .call((g: selection.Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => {
          g.select(".domain").remove()
        })

    g.append("g")
        .call((g: selection.Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => yAxis(g))
        .call((g: selection.Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => {
          g.select(".domain").remove()
          g.selectAll(".tick:not(:first-of-type) line")
              .attr("stroke", "#777")
              .attr("stroke-dasharray", "2,2")
          g.selectAll(".tick text")
              .attr("x", 4)
              .attr("dy", -4)
          g
        })
  }
}
