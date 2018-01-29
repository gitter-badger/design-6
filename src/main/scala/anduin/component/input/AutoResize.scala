// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input

import scala.scalajs.js

import japgolly.scalajs.react.component.Js.UnmountedWithRawType
import org.scalajs.dom.raw.Event

import anduin.scalajs.reactinputautosize.ReactInputAutosize.{Props, RawComponent}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object AutoResize {

  private val component = JsComponent[Props, Children.None, Null](RawComponent)

  def apply(
    value: String,
    onChange: Event => Callback
  ): UnmountedWithRawType[_, _, _] = {
    component(
      new Props(
        value = value,
        onChange = js.defined { v =>
          onChange(v).runNow()
        }
      )
    )
  }
}
