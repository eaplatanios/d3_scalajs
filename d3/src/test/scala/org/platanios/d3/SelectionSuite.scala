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

package org.platanios.d3

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalatest._

/**
  * @author Emmanouil Antonios Platanios
  */
class SelectionSuite extends FunSuite {
  val element: Element = dom.document.createElement("p")
  dom.document.body.appendChild(element)

  test("'D3.select' should work for a single element.") {
    val selection = d3.select("p")
    println(selection)
    val node = selection.node()
    assert(element === node)
  }

  test("'D3.selectAll' should work for a single element.") {
    val selection = d3.selectAll("p")
    val node = selection.node()
    assert(element === node)
  }

  test("'D3.Selection.select' should work for a single element.") {
    val selection = d3.select("body").select("p")
    val node = selection.node()
    assert(element === node)
  }

  test("'D3.Selection.selectAll' should work for a single element.") {
    val selection = d3.selectAll("body").selectAll("p")
    val node = selection.node()
    assert(element === node)
  }
}
