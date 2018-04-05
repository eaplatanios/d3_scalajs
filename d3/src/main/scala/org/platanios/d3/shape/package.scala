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

import scala.scalajs.js

/**
  * @author Emmanouil Antonios Platanios
  */
package object shape {
  // TODO: Make D contravariant.
  type D3ValueAccessor[D, +R] = js.Function3[D, Int, js.Array[D], R]

  type D3ArcAccessor[-D, +R] = js.Function1[D, R]

  trait API {
    val arc       : org.platanios.d3.shape.Arc.type        = org.platanios.d3.shape.Arc
    val area      : org.platanios.d3.shape.Area.type       = org.platanios.d3.shape.Area
    val areaRadial: org.platanios.d3.shape.AreaRadial.type = org.platanios.d3.shape.AreaRadial
    val curve     : org.platanios.d3.shape.Curve.type      = org.platanios.d3.shape.Curve
    val line      : org.platanios.d3.shape.Line.type       = org.platanios.d3.shape.Line
    val lineRadial: org.platanios.d3.shape.LineRadial.type = org.platanios.d3.shape.LineRadial
    val link      : org.platanios.d3.shape.Link.type       = org.platanios.d3.shape.Link
    val linkRadial: org.platanios.d3.shape.LinkRadial.type = org.platanios.d3.shape.LinkRadial
    val pie       : org.platanios.d3.shape.Pie.type        = org.platanios.d3.shape.Pie
    val polygon   : org.platanios.d3.shape.Polygon.type    = org.platanios.d3.shape.Polygon
  }
}
