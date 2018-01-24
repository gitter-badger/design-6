// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.slider

import scala.scalajs.js

import anduin.scalajs.slick.ReactSlick

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object Slider {

  val component = {
    JsComponent[Props, Children.Varargs, Null](ReactSlick.RawComponent).addFacade[ReactSlick.RawComponent]
  }

  // scalastyle:off parameter.number
  def props(
    arrows: Boolean = true,
    dots: Boolean = false,
    dotsClass: String = "slick-dots",
    centerMode: Boolean = false,
    centerPadding: String = "50px",
    className: String = "",
    customPaging: Int => raw.ReactElement = (i: Int) => <.button(i + 1).rawElement,
    afterChange: Int => Callback = _ => Callback.empty,
    beforeChange: (Int, Int) => Callback = (_: Int, _: Int) => Callback.empty
  ): Props = {
    new Props(
      arrows = arrows,
      dots = dots,
      dotsClass = dotsClass,
      centerMode = centerMode,
      centerPadding = centerPadding,
      className = className,
      customPaging = customPaging,
      afterChange = js.defined { index =>
        afterChange(index).runNow()
      },
      beforeChange = js.defined { (currSlide: Int, nextSlide: Int) =>
        beforeChange(currSlide, nextSlide).runNow()
      }
    )
  }
  // scalastyle:on parameter.number

  // See https://github.com/akiran/react-slick
  final class Props(
    val arrows: Boolean,
    val dots: Boolean,
    val dotsClass: String,
    val centerMode: Boolean,
    val centerPadding: String,
    val className: String,
    val customPaging: js.Function1[Int, raw.ReactElement],
    val afterChange: js.UndefOr[js.Function1[Int, Unit]],
    val beforeChange: js.UndefOr[js.Function2[Int, Int, Unit]]
  ) extends js.Object
}
