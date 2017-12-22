// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import scala.scalajs.js
import scala.scalajs.js.|

import japgolly.scalajs.react.component.Js.UnmountedWithRawType
import japgolly.scalajs.react.raw.ReactElement
import org.scalajs.dom.KeyboardEvent

import anduin.scalajs.slate.Slate.{Change, Mark, Value}
import anduin.scalajs.slate.SlateReact

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

private[slate] object Editor {

  private val component = JsComponent[Props, Children.None, Null](SlateReact.EditorComponent)

  type RenderMarkOutput = ReactElement | Null

  def apply(
    value: Value,
    onChange: Change => Callback,
    onKeyDown: (KeyboardEvent, Change) => Callback,
    renderMark: RenderMarkProps => RenderMarkOutput
  ): UnmountedWithRawType[_, _, _] = {
    component(new Props(
      value = value,
      onChange = js.defined { onChange(_).runNow() },
      onKeyDown = js.defined { (e: KeyboardEvent, c: Change) =>
        onKeyDown(e, c).runNow()
      },
      renderMark = js.defined { renderMark }
    ))
  }

  final class Props(
    val value: Value,
    val onChange: js.UndefOr[js.Function1[Change, Unit]] = js.undefined,
    val onKeyDown: js.UndefOr[js.Function2[KeyboardEvent, Change, Unit]] = js.undefined,
    val renderMark: js.UndefOr[js.Function1[RenderMarkProps, RenderMarkOutput]] = js.undefined
  ) extends js.Object

  final class RenderMarkProps(
    val mark: Mark,
    val children: js.Object
  ) extends js.Object
}
