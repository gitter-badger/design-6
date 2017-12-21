// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import scala.scalajs.js

import japgolly.scalajs.react.component.Js.UnmountedWithRawType

import anduin.scalajs.slate.Slate.Value

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

import anduin.scalajs.slate.SlateReact

object Editor {

  private val component = JsComponent[SlateReact.Props, Children.None, Null](SlateReact.EditorComponent)

  def apply(
    value: Value,
    onChange: Value => Callback
  ): UnmountedWithRawType[_, _, _] = {
    component(new SlateReact.Props(
      value = value,
      onChange = js.defined { (v: Value) =>
        onChange(v).runNow()
      }
    ))
  }
}
