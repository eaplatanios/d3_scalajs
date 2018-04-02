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

package org.platanios.d3.selection

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSBracketAccess, JSImport, JSName}
import scala.scalajs.js.|

/** XML namespaces are fun! Right? Fortunately you can mostly ignore them.
  *
  * @author Emmanouil Antonios Platanios
  */
@JSImport("d3-selection", JSImport.Namespace)
@js.native object Namespaces extends js.Object {
  /** Trait for maps of namespace prefixes to corresponding fully qualified namespace strings. */
  @js.native trait NamespaceMap extends js.Object {
    @JSBracketAccess def apply(prefix: String): String = js.native
    @JSBracketAccess def update(prefix: String, v: String): Unit = js.native
  }

  /** Trait for object literal containing local name with related fully qualified namespace. */
  @js.native trait NamespaceLocalObject extends js.Object {
    var space: String = js.native
    var local: String = js.native
  }

  /** Map of namespace prefixes to corresponding fully qualified namespace strings. */
  @JSName("namespaces") val map: NamespaceMap = js.native

  /** Obtains an object with the properties of a fully qualified namespace string and the name of local by parsing a
    * shorthand string `"prefix:local"`. If the prefix does not exist in the `namespaces` object provided by D3, then
    * the local name is returned as a simple string.
    *
    * @param  prefixedLocal String composed of the namespace prefix and the local name separated by a colon.
    *                       For example, `"svg:text"`.
    */
  @JSName("namespace") def apply(prefixedLocal: String): NamespaceLocalObject | String = js.native
}
