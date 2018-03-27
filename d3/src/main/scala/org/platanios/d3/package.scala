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

import scalajs.js
import scala.scalajs.js.annotation.JSBracketAccess
import org.scalajs.dom

package object d3 {
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

  type Index = Int
  type Group = js.UndefOr[Int]

  type D3Function0[R] = js.Function0[R]
  type D3Function1[D, R] = js.Function1[D, R]
  type D3Function2[D, R] = js.Function2[D, Index, R]
  type D3Function3[D, R] = js.Function3[D, Index, Group, R]

  type D3ThisFunction0[N <: dom.EventTarget, R] = js.ThisFunction0[N, R]
  type D3ThisFunction1[N <: dom.EventTarget, D, R] = js.ThisFunction1[N, D, R]
  type D3ThisFunction2[N <: dom.EventTarget, D, R] = js.ThisFunction2[N, D, Index, R]
  type D3ThisFunction3[N <: dom.EventTarget, D, R] = js.ThisFunction3[N, D, Index, Group, R]

  implicit def d3Fn0ToFn1[D, R](f: D3Function0[R]): D3Function1[D, R] = (_: D) => f()
  implicit def d3Fn1ToFn2[D, R](f: D3Function1[D, R]): D3Function2[D, R] = (d: D, _: Index) => f(d)
  implicit def d3Fn2ToFn3[D, R](f: D3Function2[D, R]): D3Function3[D, R] = (d: D, i: Index, _: Group) => f(d, i)

  implicit def d3Fn0ToThisFn0[N <: dom.EventTarget, R](f: D3Function0[R]): D3ThisFunction0[N, R] = (_: N) => f()
  implicit def d3Fn1ToThisFn1[N <: dom.EventTarget, D, R](f: D3Function1[D, R]): D3ThisFunction1[N, D, R] = (_: N, d: D) => f(d)
  implicit def d3Fn2ToThisFn2[N <: dom.EventTarget, D, R](f: D3Function2[D, R]): D3ThisFunction2[N, D, R] = (_: N, d: D, i: Index) => f(d, i)
  implicit def d3Fn2ToThisFn3[N <: dom.EventTarget, D, R](f: D3Function3[D, R]): D3ThisFunction3[N, D, R] = (_: N, d: D, i: Index, g: Group) => f(d, i, g)

  def f[R](f: Function0[R]): D3Function0[R] = f
  def f[D, R](f: Function1[D, R]): D3Function1[D, R] = f
  def f[D, R](f: Function2[D, Index, R]): D3Function2[D, R] = f
  def f[D, R](f: Function3[D, Index, Group, R]): D3Function3[D, R] = f

  //  def f[C, R](f: Function1[C, R]): D3ThisFunction0[C, R] = f
  //  def f[C, D, R](f: Function2[C, D, R]): D3ThisFunction1[C, D, R] = f
  //  def f[C, D, R](f: Function3[C, D, Index, R]): D3ThisFunction2[C, D, R] = f
  //  def f[C, D, R](f: Function4[C, D, Index, Group, R]): D3ThisFunction3[C, D, R] = f

  implicit def d3toArray(d3: D3.type): array.Array.type = array.Array
  implicit def d3toAxis(d3: D3.type): axis.Axis.type = axis.Axis
  implicit def d3toColor(d3: D3.type): color.Color.type = color.Color
  implicit def d3toColorScheme(d3: D3.type): color.ColorScheme.type = color.ColorScheme
  implicit def d3toDrag(d3: D3.type): drag.Drag.type = drag.Drag
  implicit def d3toDSV(d3: D3.type): data.DSV.type = data.DSV
  implicit def d3toFetch(d3: D3.type): data.Fetch.type = data.Fetch
  implicit def d3toScale(d3: D3.type): scale.Scale.type = scale.Scale
  implicit def d3toSelection(d3: D3.type): selection.Selection.type = selection.Selection
  implicit def d3toTime(d3: D3.type): time.Time.type = time.Time
  implicit def d3toTimer(d3: D3.type): time.Timer.type = time.Timer
  implicit def d3toInterpolate(d3: D3.type): Interpolate.type = Interpolate
  implicit def d3toLocal(d3: D3.type): Local.type = Local
  implicit def d3toNamespaces(d3: D3.type): Namespaces.type = Namespaces

  implicit def d3toD3Force(d3: D3.type): D3Force.type = D3Force
  implicit def d3toD3Polygon(d3: D3.type): D3Polygon.type = D3Polygon
  implicit def d3toD3Shape(d3: D3.type): D3Shape.type = D3Shape
  implicit def d3toD3QuadTree(d3: D3.type): D3QuadTree.type = D3QuadTree
  //  implicit def d3toD3Zoom(d3: D3.type): D3Zoom.type = D3Zoom

  //  implicit class SelectionExtensions[Datum](val s: Selection[Datum]) extends AnyVal {
  //    def nodeAs[T <: dom.EventTarget]: T = s.node.asInstanceOf[T]
  //  }

  implicit class SimulationExtensions[N <: SimulationNode](val s: Simulation[N]) extends AnyVal {
    def forceAs[F <: D3Force[N]](name: String): F = s.force(name).asInstanceOf[F]
  }

  //region Type Traits

  /** Container element type used for mouse/touch functions. */
  trait ContainerElement[T]

  object ContainerElement {
    implicit val htmlContainerElement  : ContainerElement[dom.html.Element] = new ContainerElement[dom.html.Element] {}
    implicit val svgContainerElement   : ContainerElement[dom.svg.Element]  = new ContainerElement[dom.svg.Element] {}
    implicit val svgSvgContainerElement: ContainerElement[dom.svg.SVG]      = new ContainerElement[dom.svg.SVG] {}
  }

  //endregion Type Traits
}
