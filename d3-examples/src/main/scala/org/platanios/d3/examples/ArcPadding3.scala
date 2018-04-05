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

/** Example ported from [here](https://bl.ocks.org/mbostock/f37b07b92633781a46f7).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("ArcPadding3") @JSExportAll
object ArcPadding3 {
  def run(): Unit = {
    val data = Seq(1, 1, 2, 3, 5, 8, 13)

    val canvas = dom.document.querySelector("canvas").asInstanceOf[dom.html.Canvas]
    val context = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

    val width = canvas.width
    val height = canvas.height
    val radius = Math.min(width, height) / 2
    val colors = Seq(
      "#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd",
      "#8c564b", "#e377c2", "#7f7f7f", "#bcbd22", "#17becf")

    val arc = d3.arc[d3.pie.DatumArc[Int]]()
        .innerRadius(radius - 70.0)
        .outerRadius(radius - 10.0)
        .padAngle(0.03)
        .canvas(context)
    val pie = d3.pie[Int]()
    val arcs = pie(data)

    context.translate(width / 2, height / 2)
    context.globalAlpha = 0.5

    arcs.zip(colors).foreach {
      case (a, c) =>
        context.beginPath()
        arc(a)
        context.fillStyle = c.asInstanceOf[js.Any]
        context.fill()
    }

    context.globalAlpha = 1.0
    context.beginPath()
    arcs.foreach(a => arc(a))
    context.lineWidth = 1.5
    context.stroke()
  }
}
