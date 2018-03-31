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

/**
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("GeneralUpdatePattern2") @JSExportAll
object GeneralUpdatePattern2 {
  def run(): Unit = {
    val alphabet = js.Array("abcdefghijklmnopqrstuvwxyz".split(""): _*)

    val width = 960
    val height = 500
    val svg = d3.select("body").append("svg")
    svg.attr("width", width)
    svg.attr("height", height)
    val g = svg.append("g").attr("transform", s"translate(32,${height / 2})")

    def update(data: js.Array[String]): Unit = {
      // DATA JOIN: Join new data with old elements, if any.
      val text = g.selectAll("text").data(data, (d: String) => d)

      // UPDATE: Update old elements as needed.
      text.attr("class", "update")

      // ENTER: Create new elements as needed.
      // ENTER + UPDATE: After merging the entered elements with the update selection, apply operations to both.
      text.enter()
          .append("text")
          .attr("class", "enter")
          .attr("dy", ".35em")
          .text((d: String) => d)
          .merge(text)
          .attr("x", (_: String, i: Index) => i.asInstanceOf[Int] * 32.0)

      // EXIT: Remove old elements as needed.
      text.exit().remove()
    }

    // The initial display.
    update(alphabet)

    // Grab a random sample of letters from the alphabet, in alphabetical order.
    d3.timer.interval(_ => update(d3.shuffle(alphabet).jsSlice(0, Math.floor(Math.random() * 26).toInt).sort()), 1500)
  }
}
