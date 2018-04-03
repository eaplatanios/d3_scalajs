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

// TODO: Fix this when we support SVG lines.

/** Example ported from [here](https://bl.ocks.org/mbostock/248bac3b8e354a9103c4).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("Easing") @JSExportAll
object Easing {
  def run(): Unit = {
    val margin = Map("top" -> 120, "right" -> 120, "bottom" -> 120, "left" -> 120)
    val width = 960 - margin("left") - margin("right")
    val height = 960 - margin("top") - margin("bottom")

    val x = d3.scale.linear(range = Seq(0.0, width.toDouble))
    val y = d3.scale.linear(range = Seq(height.toDouble, 0.0))

    // An SVG element with a bottom-right origin.
    val svg = d3.select("body").append("svg")
        .attr("width", width + margin("left") + margin("right"))
        .attr("height", height + margin("top") + margin("bottom"))
        .append("g")
        .attr("transform", "translate(" + margin("left") + "," + margin("top") + ")")

    svg.append("g")
        .attr("class", "axis axis--x")
        .attr("transform", "translate(0," + height + ")")
        .call(d3.axis(d3.axis.Bottom, x)
            .tickSize(-height)
            .tickPadding(6).apply)
        .append("text")
        .attr("class", "axis-title")
        .attr("x", width - 10)
        .attr("y", 8)
        .attr("dy", ".71em")
        .attr("text-anchor", "end")
        .attr("font-weight", "bold")
        .text("t = ")

    svg.append("g")
        .attr("class", "axis axis--y")
        .call(d3.axis(d3.axis.Left, y)
            .tickSize(-width)
            .tickPadding(6).apply)
        .append("text")
        .attr("class", "axis-title")
        .attr("x", -24)
        .attr("dy", ".32em")
        .attr("text-anchor", "end")
        .attr("font-weight", "bold")
        .text("ease(t) = ")

//    val line = svg.append("g")
//        .attr("class", "line")
//        .append("path")
//        .datum(d3.range(0, 1, 0.002).concat(js.Array(1)))

    val dot1 = svg.append("circle")
        .attr("r", 5)

    val dot2 = svg.append("circle")
        .attr("cx", width + 20)
        .attr("r", 5)

    var ease = d3.ease.linear

    def changed(e: dom.html.Select): Unit = {
      ease = e.value match {
        case "linear" => d3.ease.linear
        case "quadIn" => d3.ease.quadIn
        case "quadOut" => d3.ease.quadOut
        case "quadInOut" => d3.ease.quadInOut
        case "cubicIn" => d3.ease.cubicIn
        case "cubicOut" => d3.ease.cubicOut
        case "cubicInOut" => d3.ease.cubicInOut
        case "polyIn" => d3.ease.polyIn
        case "polyOut" => d3.ease.polyOut
        case "polyInOut" => d3.ease.polyInOut
        case "sinIn" => d3.ease.sinIn
        case "sinOut" => d3.ease.sinOut
        case "sinInOut" => d3.ease.sinInOut
        case "expIn" => d3.ease.expIn
        case "expOut" => d3.ease.expOut
        case "expInOut" => d3.ease.expInOut
        case "circleIn" => d3.ease.circleIn
        case "circleOut" => d3.ease.circleOut
        case "circleInOut" => d3.ease.circleInOut
        case "bounceIn" => d3.ease.bounceIn
        case "bounceOut" => d3.ease.bounceOut
        case "bounceInOut" => d3.ease.bounceInOut
        case "backIn" => d3.ease.backIn
        case "backOut" => d3.ease.backOut
        case "backInOut" => d3.ease.backInOut
        case "elasticIn" => d3.ease.elasticIn
        case "elasticOut" => d3.ease.elasticOut
        case "elasticInOut" => d3.ease.elasticInOut
        case _ => d3.ease.linear
      }
      //    var path = d3.line()
      //        .x(function(t) { return x(t); })
      //        .y(function(t) { return y(ease(t)); });
      //
      //    var ease;
      // val path = d3.line()
      // line.attr("d", path)
    }

    val select = d3.select("#ease-type")
        .on("change", (e: dom.html.Select, _: js.Any) => changed(e))
        // .property("value", top.location.hash ? top.location.hash.slice(1) : "linear")
        .each((e: dom.html.Select, _: js.Any) => changed(e))

    d3.timer((elapsed: Double) => {
      val t = (elapsed % 3000) / 3000
      dot1.attr("cx", x(t)).attr("cy", y(ease(t)))
      dot2.attr("cy", y(ease(t)))
    })
  }
}
