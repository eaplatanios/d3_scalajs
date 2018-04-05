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

/** Example ported from [here](https://bl.ocks.org/mbostock/3035090).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("AreaWithMissingData") @JSExportAll
object AreaWithMissingData {
  def run(): Unit = {
    case class Point(x: Double, y: Double)
    val data = (0 until 40).map(i => {
      if (i % 5 == 0)
        None
      else
        Some(Point(i.toDouble / 39, (Math.sin(i.toDouble / 3) + 2) / 4))
    })

    val margin = Map("top" -> 40, "right" -> 40, "bottom" -> 40, "left" -> 40)
    val width = 960 - margin("left") - margin("right")
    val height = 500 - margin("top") - margin("bottom")

    val x = d3.scale.linear(range = Seq(0.0, width.toDouble))
    val y = d3.scale.linear(range = Seq(height.toDouble, 0.0))

    val xAxis = d3.axis(
      position = d3.axis.Bottom,
      scale = x)

    val yAxis = d3.axis(
      position = d3.axis.Left,
      scale = y)

    val line = d3.line()
        .x((d: Option[Point]) => d.map(p => x(p.x)).get)
        .y((d: Option[Point]) => d.map(p => y(p.y)).get)
        .defined((d: Option[Point]) => d.isDefined)

    val area = d3.area()
        .x0(line.x())
        .x1(null)
        .y0(y(0))
        .y1(line.y())
        .defined(line.defined())

    val svg = d3.select("body").append("svg")
        .datum[Seq[Option[Point]]](data)
        .attr("width", width + margin("left") + margin("right"))
        .attr("height", height + margin("top") + margin("bottom"))
        .append("g")
        .attr("transform", "translate(" + margin("left") + "," + margin("top") + ")")

    svg.append("path")
        .attr("class", "area")
        .attr("d", (_: dom.EventTarget, d: Seq[Option[Point]]) => area(d))

    svg.append("g")
        .attr("class", "x axis")
        .attr("transform", "translate(0," + height + ")")
        .call((d: Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => xAxis(d))

    svg.append("g")
        .attr("class", "y axis")
        .call((d: Selection[dom.svg.G, js.Any, dom.Element, js.Any]) => yAxis(d))

    svg.append("path")
        .attr("class", "line")
        .attr("d", (_: dom.EventTarget, d: Seq[Option[Point]]) => line(d))

    svg.selectAll(".dot")
        .data(js.Array(data.filter(_.isDefined): _*))
        .enter().append("circle")
        .attr("class", "dot")
        .attr("cx", (d: Option[Point]) => line.x()(d, 0, null))
        .attr("cy", (d: Option[Point]) => line.y()(d, 0, null))
        .attr("r", 3.5)
  }
}
