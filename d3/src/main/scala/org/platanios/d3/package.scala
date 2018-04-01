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

package org.platanios

import org.scalajs.dom

import scalajs.js
import scala.scalajs.js.annotation.{JSBracketAccess, JSImport}

package object d3 extends Implicits {
  /** Helper trait which covers argument types like NodeListOf[T] or HTMLCollectionOf[T]. */
  @js.native trait ArrayLike[T] extends js.Object {
    var length: Double = js.native
    def item(index: Double): T = js.native
    @JSBracketAccess def apply(index: Double): T = js.native
    @JSBracketAccess def update(index: Double, v: T): Unit = js.native
  }

  //region Handling Events

  @js.native trait BaseEvent extends js.Object {
    var `type`     : String    = js.native
    var sourceEvent: dom.Event = js.native
  }

  /** User interface event (e.g., mouse event, touch or MSGestureEvent) with captured `clientX` and `clientY`
    * properties. */
  @js.native trait ClientPointEvent extends js.Object {
    var clientX: Double = js.native
    var clientY: Double = js.native
  }

  /** Trait for the optional parameters map, used when dispatching custom events on a selection. */
  @js.native trait CustomEventParameters extends js.Object {
    /** If `true`, the event is dispatched to ancestors in reverse tree order. */
    var bubbles: Boolean = js.native

    /** If `true`, event.preventDefault is allowed. */
    var cancelable: Boolean = js.native

    /** Any custom data associated with the event. */
    var detail: js.Any = js.native
  }

  //  implicit def baseEventToZoomEvent(e: BaseEvent): ZoomEvent = e.asInstanceOf[ZoomEvent]
  //  implicit def baseEventToDragEvent(e: BaseEvent): DragEvent = e.asInstanceOf[DragEvent]

  //endregion Handling Events

  type Index = js.UndefOr[Int]
  type Group = js.UndefOr[Int]

  //region Value/Function-valued Arguments

  type D3Function[-N <: dom.EventTarget, -D, +R] = js.ThisFunction3[N, D, Index, Group, R]

  sealed trait D3Value extends Any

  private[d3] class NullD3Value(val value: Null) extends AnyVal with D3Value
  private[d3] class BooleanD3Value(val value: Boolean) extends AnyVal with D3Value
  private[d3] class IntD3Value(val value: Int) extends AnyVal with D3Value
  private[d3] class FloatD3Value(val value: Float) extends AnyVal with D3Value
  private[d3] class DoubleD3Value(val value: Double) extends AnyVal with D3Value
  private[d3] class StringD3Value(val value: String) extends AnyVal with D3Value

  type D3AttrValue = D3Value
  type D3PropertyValue = D3Value
  type D3TextValue = D3Value

  //endregion Value/Function-valued Arguments

  // TODO: Re-organize the namespaces.

  implicit def d3toArray(d3: org.platanios.d3.d3.type): array.Array.type = array.Array
  implicit def d3toDrag(d3: org.platanios.d3.d3.type): drag.Drag.type = drag.Drag
  implicit def d3toDSV(d3: org.platanios.d3.d3.type): data.DSV.type = data.DSV
  implicit def d3toSelection(d3: org.platanios.d3.d3.type): selection.Selection.type = selection.Selection
  implicit def d3toLocal(d3: org.platanios.d3.d3.type): Local.type = Local
  implicit def d3toNamespaces(d3: org.platanios.d3.d3.type): Namespaces.type = Namespaces

  implicit def d3toD3Force(d3: org.platanios.d3.d3.type): D3Force.type = D3Force
  implicit def d3toD3Polygon(d3: org.platanios.d3.d3.type): D3Polygon.type = D3Polygon
  implicit def d3toD3Shape(d3: org.platanios.d3.d3.type): D3Shape.type = D3Shape
  implicit def d3toD3QuadTree(d3: org.platanios.d3.d3.type): D3QuadTree.type = D3QuadTree
  //  implicit def d3toD3Zoom(d3: D3.type): D3Zoom.type = D3Zoom

  //  implicit class SelectionExtensions[Datum](val s: Selection[Datum]) extends AnyVal {
  //    def nodeAs[T <: dom.EventTarget]: T = s.node.asInstanceOf[T]
  //  }

  implicit class SimulationExtensions[N <: SimulationNode](val s: Simulation[N]) extends AnyVal {
    def forceAs[F <: D3Force[N]](name: String): F = s.force(name).asInstanceOf[F]
  }

  //region Type Traits

  trait JsNumber[T]

  object JsNumber {
    implicit val byteJsNumber  : JsNumber[Byte]   = new JsNumber[Byte] {}
    implicit val shortJsNumber : JsNumber[Short]  = new JsNumber[Short] {}
    implicit val intJsNumber   : JsNumber[Int]    = new JsNumber[Int] {}
    implicit val floatJsNumber : JsNumber[Float]  = new JsNumber[Float] {}
    implicit val doubleJsNumber: JsNumber[Double] = new JsNumber[Double] {}
  }

  /** Container element type used for mouse/touch functions. */
  trait ContainerElement[T]

  object ContainerElement {
    implicit val htmlContainerElement  : ContainerElement[dom.html.Element] = new ContainerElement[dom.html.Element] {}
    implicit val svgContainerElement   : ContainerElement[dom.svg.Element]  = new ContainerElement[dom.svg.Element] {}
    implicit val svgSvgContainerElement: ContainerElement[dom.svg.SVG]      = new ContainerElement[dom.svg.SVG] {}
  }

  //endregion Type Traits

  //region Facades

  @JSImport("d3", JSImport.Namespace)
  @js.native private object d3Facade extends js.Object {
    val version: String = js.native
  }

  trait Facade[T, F <: js.Any] {
    private[d3] val facade: F

    protected def withFacade(facade: F): T
  }

  //endregion Facades

  //region API

  object d3 {
    val version: String = d3Facade.version

    val axis       : org.platanios.d3.axis.Axis.type               = org.platanios.d3.axis.Axis
    val color      : org.platanios.d3.color.Color.type             = org.platanios.d3.color.Color
    val fetch      : org.platanios.d3.data.Fetch.type              = org.platanios.d3.data.Fetch
    val format     : org.platanios.d3.format.Format.type           = org.platanios.d3.format.Format
    val interpolate: org.platanios.d3.interpolate.Interpolate.type = org.platanios.d3.interpolate.Interpolate
    val time       : org.platanios.d3.time.Time.type               = org.platanios.d3.time.Time
    val timer      : org.platanios.d3.time.Timer.type              = org.platanios.d3.time.Timer

    object scale extends org.platanios.d3.scale.API
  }

  //endregion API
}
