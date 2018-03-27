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

import scalajs.js
import scala.scalajs.js.annotation._
import org.scalajs.dom.CanvasRenderingContext2D

@JSImport("d3-shape", JSImport.Namespace)
@js.native
object D3Shape extends js.Object {
  def pie(): PieGenerator = js.native
  def arc(): ArcGenerator = js.native
  def line(): LineGenerator = js.native
  def curveBasisClosed: CurveFactory = js.native
  def curveCardinalClosed: CurveFactory = js.native
  def curveCatmullRomClosed: CurveCatmullRomFactory = js.native
  def curveLinear: CurveFactory = js.native
  def curveLinearClosed: CurveFactory = js.native
}

@js.native
trait BaseGenerator[G <: BaseGenerator[G]] extends js.Object {
  def context(context: CanvasRenderingContext2D):G = js.native
  def context():CanvasRenderingContext2D = js.native
}

@js.native
trait BaseLineGenerator[G <: BaseLineGenerator[G]] extends js.Object with BaseGenerator[G] {
  def curve(curve: CurveFactory): G = js.native
}

@js.native
trait LineGenerator extends BaseLineGenerator[LineGenerator] {
  def apply(data: js.Array[js.Tuple2[Double, Double]]): String = js.native
}

@js.native
trait CurveFactory extends js.Object

@js.native
trait CurveCatmullRomFactory extends CurveFactory {
  def alpha(alpha: Double): CurveCatmullRomFactory = js.native
}

@js.native
trait PieGenerator extends js.Object {
  def value(value: Double): PieGenerator = js.native
  def padAngle(angle: Double): PieGenerator = js.native
  def apply[Datum](data: js.Array[Datum]): js.Array[PieArcDatum[Datum]] = js.native
}

@js.native
trait PieArcDatum[Datum] extends ArcDatum {
  def data: Datum = js.native
  def value: Double = js.native
  def index: Int = js.native
  def startAngle: Double = js.native
  def endAngle: Double = js.native
  def padAngle: Double = js.native
}

@js.native
trait ArcDatum extends js.Object

@js.native
trait BaseArcGenerator[G <: BaseArcGenerator[G]] extends js.Object with BaseGenerator[G] {
  def innerRadius(radius: Double): G = js.native
  def outerRadius(radius: Double): G = js.native
  def cornerRadius(radius: Double): G = js.native
}

@js.native
trait ArcGenerator extends BaseArcGenerator[ArcGenerator] {
  def apply[T <: ArcDatum](arguments: T): String = js.native
  def centroid[T <: ArcDatum](arguments: T): js.Tuple2[Double, Double] = js.native
}

@js.native
trait ArcGeneratorWithContext extends BaseArcGenerator[ArcGeneratorWithContext] {
  def apply[T <: ArcDatum](arguments: T): Unit = js.native
}
