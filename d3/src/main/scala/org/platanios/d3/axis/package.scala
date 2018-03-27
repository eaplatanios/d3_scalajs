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

import org.platanios.d3.scale.Scale

import org.scalajs.dom

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
package object axis {
  type AxisScale[Domain] = Scale[Domain, Double, Double]
  type AxisContainerElement = dom.svg.SVG | dom.svg.Element

  trait TickArgumentsArgs[T]

  object TickArgumentsArgs {
    implicit val double                : TickArgumentsArgs[Double]                              = new TickArgumentsArgs[Double] {}
    implicit val axisTimeInterval      : TickArgumentsArgs[AxisTimeInterval]                    = new TickArgumentsArgs[AxisTimeInterval] {}
    implicit val doubleString          : TickArgumentsArgs[js.Tuple2[Double, String]]           = new TickArgumentsArgs[js.Tuple2[Double, String]] {}
    implicit val axisTimeIntervalString: TickArgumentsArgs[js.Tuple2[AxisTimeInterval, String]] = new TickArgumentsArgs[js.Tuple2[AxisTimeInterval, String]] {}
    implicit val anyArray              : TickArgumentsArgs[js.Array[js.Any]]                    = new TickArgumentsArgs[js.Array[js.Any]] {}
  }
}
