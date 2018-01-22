// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slider

import scala.scalajs.js

import japgolly.scalajs.react.component.Js.UnmountedWithRawType

import anduin.scalajs.slick.ReactSlick

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Slider {

  val component = {
    JsComponent[Props, Children.Varargs, Null](ReactSlick.RawComponent).addFacade[ReactSlick.RawComponent]
  }

  def apply(props: Props)(children: VdomNode*): UnmountedWithRawType[_, _, _] = {
    component(props)(children: _*)
  }

  // See https://github.com/akiran/react-slick
  final class Props(
    val arrows: Boolean = true,
    val dots: Boolean = false,
    val dotsClass: String = "slick-dots",
    val centerMode: Boolean = false,
    val centerPadding: String = "50px",
    val className: String = "",
    val customPaging: js.Function1[Int, raw.ReactElement] = (i: Int) => <.button(i + 1).rawElement
  ) extends js.Object
}
