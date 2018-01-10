// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slider

import scala.scalajs.js

import japgolly.scalajs.react.CtorType.ChildArg

import anduin.scalajs.slick.ReactSlick

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object Slider {

  private val component: JsComponent[Props, Null, CtorType.PropsAndChildren] = {
    JsComponent[Props, Children.Varargs, Null](ReactSlick.RawComponent)
  }

  def apply(props: Props)(children: ChildArg*): JsComponent.Unmounted[_, _] = {
    component(props)(children: _*)
  }

  // See https://github.com/akiran/react-slick
  final class Props(
    val dots: Boolean = false
  ) extends js.Object
}
