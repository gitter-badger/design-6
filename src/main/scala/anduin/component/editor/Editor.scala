// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js
import scala.scalajs.js.|

import japgolly.scalajs.react.component.Js.UnmountedWithRawType
import japgolly.scalajs.react.raw.ReactElement
import org.scalajs.dom.KeyboardEvent

import anduin.scalajs.slate.SlateReact

// scalastyle:off underscore.import
import japgolly.scalajs.react._

import anduin.scalajs.slate.Slate._
// scalastyle:on underscore.import

private[editor] object Editor {

  private val component = JsComponent[Props, Children.None, Null](SlateReact.EditorComponent)

  type RenderOutput = ReactElement | Null
  type DecorateNodeFn = js.Function1[Node, js.Array[js.Object]]

  def apply(
    placeholder: String,
    value: Value,
    readOnly: Boolean,
    onChange: Change => Callback,
    onKeyDown: (KeyboardEvent, Change) => Callback,
    renderNode: RenderNodeProps => RenderOutput,
    renderMark: RenderMarkProps => RenderOutput,
    decorateNodeOpt: Option[Node => js.Array[js.Object]] = None
  ): UnmountedWithRawType[_, _, _] = {
    component(
      new Props(
        placeholder = placeholder,
        value = value,
        readOnly = readOnly,
        onChange = js.defined { onChange(_).runNow() },
        onKeyDown = js.defined { (e: KeyboardEvent, c: Change) =>
          onKeyDown(e, c).runNow()
        },
        renderNode = js.defined { renderNode },
        renderMark = js.defined { renderMark },
        decorateNode = decorateNodeOpt.fold[js.UndefOr[DecorateNodeFn]](js.undefined)(js.defined(_))
      ))
  }

  // See https://docs.slatejs.org/slate-react/editor
  final class Props(
    val placeholder: String,
    val value: Value,
    val readOnly: Boolean,
    val onChange: js.UndefOr[js.Function1[Change, Unit]] = js.undefined,
    val onKeyDown: js.UndefOr[js.Function2[KeyboardEvent, Change, Unit]] = js.undefined,
    val renderNode: js.UndefOr[js.Function1[RenderNodeProps, RenderOutput]] = js.undefined,
    val renderMark: js.UndefOr[js.Function1[RenderMarkProps, RenderOutput]] = js.undefined,
    val decorateNode: js.UndefOr[DecorateNodeFn] = js.undefined
  ) extends js.Object

  final class RenderMarkProps(
    val mark: Mark,
    val children: js.Object
  ) extends js.Object

  final class RenderNodeProps(
    val node: Node,
    val children: js.Object
  ) extends js.Object
}
