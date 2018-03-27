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

package org.platanios.d3.color

import scala.scalajs.js
import scala.scalajs.js.|

/**
  * @author Emmanouil Antonios Platanios
  */
@js.native trait ColorFactory extends js.Function {
  def apply(cssColorSpecifier: String): RGBColor | HSLColor = js.native
  def apply(color: ColorSpaceObject | ColorCommonInstance): RGBColor | HSLColor = js.native
}

@js.native trait RGBColorFactory extends js.Function {
  def apply(r: Double, g: Double, b: Double, opacity: Double = ???): RGBColor = js.native
  def apply(cssColorSpecifier: String): RGBColor = js.native
  def apply(color: ColorSpaceObject | ColorCommonInstance): RGBColor = js.native
}

@js.native trait HSLColorFactory extends js.Function {
  def apply(h: Double, s: Double, l: Double, opacity: Double = ???): HSLColor = js.native
  def apply(cssColorSpecifier: String): HSLColor = js.native
  def apply(color: ColorSpaceObject | ColorCommonInstance): HSLColor = js.native
}

@js.native trait LabColorFactory extends js.Function {
  def apply(l: Double, a: Double, b: Double, opacity: Double = ???): LabColor = js.native
  def apply(cssColorSpecifier: String): LabColor = js.native
  def apply(color: ColorSpaceObject | ColorCommonInstance): LabColor = js.native
}

@js.native trait HCLColorFactory extends js.Function {
  def apply(h: Double, l: Double, c: Double, opacity: Double = ???): HCLColor = js.native
  def apply(cssColorSpecifier: String): HCLColor = js.native
  def apply(color: ColorSpaceObject | ColorCommonInstance): HCLColor = js.native
}

@js.native
trait CubehelixColorFactory extends js.Function {
  def apply(h: Double, s: Double, l: Double, opacity: Double = ???): CubehelixColor = js.native
  def apply(cssColorSpecifier: String): CubehelixColor = js.native
  def apply(color: ColorSpaceObject | ColorCommonInstance): CubehelixColor = js.native
}
