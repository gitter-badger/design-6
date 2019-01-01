// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.reactintersectionobserver

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import anduin.scalajs.intersectionobserver.IntersectionObserver

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object ReactIntersectionObserver {

  @JSImport("@researchgate/react-intersection-observer", JSImport.Default, "Observer")
  @js.native
  object RawComponent extends js.Object

  @js.native
  trait IntersectionObserverEntry extends js.Object {
    val isIntersecting: Boolean = js.native
  }

  // See https://github.com/researchgate/react-intersection-observer#options
  trait Props extends js.Object {
    val disabled: Boolean
    val rootMargin: String
    val threshold: Double
    val onChange: js.UndefOr[js.Function1[IntersectionObserverEntry, Unit]]
  }

  val intersectionObserverPolyfill = IntersectionObserver
  private val component = JsComponent[Props, Children.Varargs, Null](ReactIntersectionObserver.RawComponent)

  def apply(
    disabledParam: Boolean = false,
    rootMarginParam: String = "0px 0px 0px 0px",
    thresholdParam: Double = 0,
    onChangeParam: IntersectionObserverEntry => Callback = _ => Callback.empty
  )(children: VdomElement*): VdomElement = {
    val props = new Props {
      override val disabled = disabledParam
      override val rootMargin = rootMarginParam
      override val threshold = thresholdParam
      override val onChange = js.defined { e =>
        onChangeParam(e).runNow()
      }
    }
    component(props)(children: _*)
  }
}
