// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.intersectionobserver

import scala.scalajs.js

import japgolly.scalajs.react.component.Js.UnmountedWithRawType

import anduin.scalajs.intersectionobserver.IntersectionObserver
import anduin.scalajs.reactintersectionobserver.ReactIntersectionObserver
import anduin.scalajs.reactintersectionobserver.ReactIntersectionObserver.{IntersectionObserverEntry, Props}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Observer {
  val intersectionObserverPolyfill = IntersectionObserver

  val component = JsComponent[Props, Children.Varargs, Null](ReactIntersectionObserver.RawComponent)

  def apply(
    disabled: Boolean = false,
    onChange: IntersectionObserverEntry => Callback = _ => Callback.empty
  )(children: VdomElement*): UnmountedWithRawType[_, _, _] = {
    component(
      new Props(
        disabled = disabled,
        onChange = js.defined { e =>
          onChange(e).runNow()
        }
      )
    )(children: _*)
  }
}
