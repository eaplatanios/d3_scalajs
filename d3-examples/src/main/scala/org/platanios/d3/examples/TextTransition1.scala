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

/** Example ported from [here](https://bl.ocks.org/mbostock/7004f92cac972edef365).
  *
  * @author Emmanouil Antonios Platanios
  */
@JSExportTopLevel("TextTransition1") @JSExportAll
object TextTransition1 {
  def run(): Unit = {
    val format = d3.format.number(",d")

    d3.select("h1")
        .transition()
        .duration(2500)
        .on("start", (e: dom.Element, _: js.Any) => repeat(e))

    def repeat(e: dom.Element): Unit = {
      d3.transition.active(e).foreach(_
          .tween("text", (d: dom.Element, _: js.Any) => {
            val that = d3.select(d)
            val i = d3.interpolate.number(that.text().replace(",", "").toDouble, Math.random() * 1e6)
            ((t: Double) => that.text(format(i(t)))): js.Function1[Double, Unit]
          })
          .transition()
          .delay(1500)
          .on("start", (e: dom.Element, _: js.Any) => repeat(e)))
    }
  }
}
