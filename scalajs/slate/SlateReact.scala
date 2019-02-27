// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

import japgolly.scalajs.react.raw.React.Element
import japgolly.scalajs.react.{Callback, Children, JsComponent}
import org.scalajs.dom.KeyboardEvent

import anduin.scalajs.slate.Slate.{Editor, Mark, Node, Value}

object SlateReact {

  @JSImport("slate-react", "Editor")
  @js.native
  object EditorComponent extends js.Object

  @js.native
  trait EditorComponent extends js.Object {
    def focus(): Unit = js.native
  }

  final class Change(val value: Value) extends js.Object

  val component = JsComponent[Props, Children.None, Null](SlateReact.EditorComponent)
    .addFacade[SlateReact.EditorComponent]

  type RenderOutput = Element | Null
  type DecorateNodeFn = js.Function1[Node, js.Array[js.Object]]

  def props(
    placeholder: String,
    value: Value,
    readOnly: Boolean,
    onChange: Value => Callback,
    onKeyDown: (KeyboardEvent, Editor) => Callback,
    renderNode: RenderNodeProps => RenderOutput,
    renderMark: RenderMarkProps => RenderOutput,
    decorateNodeOpt: Option[Node => js.Array[js.Object]] = None
  ): Props = {
    new Props(
      placeholder = placeholder,
      value = value,
      readOnly = readOnly,
      onChange = js.defined { change =>
        onChange(change.value).runNow()
      },
      onKeyDown = js.defined { (e: KeyboardEvent, c: Editor) =>
        onKeyDown(e, c).runNow()
      },
      renderNode = js.defined { renderNode },
      renderMark = js.defined { renderMark },
      decorateNode = decorateNodeOpt.fold[js.UndefOr[DecorateNodeFn]](js.undefined)(js.defined(_))
    )
  }

  // See https://docs.slatejs.org/slate-react/editor
  final class Props(
    val placeholder: String,
    val value: Value,
    val readOnly: Boolean,
    val onChange: js.UndefOr[js.Function1[Change, Unit]] = js.undefined,
    val onKeyDown: js.UndefOr[js.Function2[KeyboardEvent, Editor, Unit]] = js.undefined,
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
