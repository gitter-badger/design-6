// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.slate

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

import japgolly.scalajs.react.raw.React.Element
import org.scalajs.dom.KeyboardEvent

import anduin.scalajs.slate.Slate.{Editor, Mark, Node, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

object SlateReact {

  @JSImport("slate-react", "Editor")
  @js.native
  object EditorComponent extends js.Object

  @js.native
  trait EditorComponent extends Editor

  final class Change(val value: Value) extends js.Object

  type EditorComponentType = JsComponentWithFacade[Props, Null, SlateReact.EditorComponent, CtorType.Props]
  type EditorComponentRef = Ref.ToJsComponent[
    SlateReact.Props,
    Null,
    JsComponent.RawMounted[SlateReact.Props, Null] with SlateReact.EditorComponent
  ]

  val component: EditorComponentType = JsComponent[Props, Children.None, Null](SlateReact.EditorComponent)
    .addFacade[SlateReact.EditorComponent]

  type RenderOutput = Element | Null
  type DecorateNodeFn = js.Function1[Node, js.Array[js.Object]]
  type RenderNextFn = js.Function0[Element]
  type KeyDownNextFn = js.Function0[Change]

  def props(
    autoFocusParam: Boolean,
    placeholderParam: String,
    valueParam: Value,
    readOnlyParam: Boolean,
    onChangeParam: Value => Callback,
    onKeyDownParam: (KeyboardEvent, Editor, KeyDownNextFn) => Callback,
    renderNodeParam: (RenderNodeProps, Editor, RenderNextFn) => RenderOutput,
    renderMarkParam: (RenderMarkProps, Editor, RenderNextFn) => RenderOutput
  ): Props = {
    new Props {
      override val autoFocus = autoFocusParam
      override val placeholder = placeholderParam
      override val value = valueParam
      override val readOnly = readOnlyParam
      override val onChange = js.defined { change =>
        onChangeParam(change.value).runNow()
      }
      override val onKeyDown = js.defined { (e: KeyboardEvent, c: Editor, next: KeyDownNextFn) =>
        onKeyDownParam(e, c, next).runNow()
      }
      override val renderNode = js.defined { renderNodeParam }
      override val renderMark = js.defined { renderMarkParam }
    }
  }

  // Always use trait to create associate object in facade.
  // Otherwise, we will see a lot of issues.
  // For example, pressing Backspace, Enter or Delete keys don't work
  trait Props extends js.Object {
    val autoFocus: Boolean
    val placeholder: String
    val value: Value
    val readOnly: Boolean
    val onChange: js.UndefOr[js.Function1[Change, Unit]] = js.undefined
    val onKeyDown: js.UndefOr[js.Function3[KeyboardEvent, Editor, KeyDownNextFn, Unit]] = js.undefined
    val renderNode: js.UndefOr[js.Function3[RenderNodeProps, Editor, RenderNextFn, RenderOutput]] = js.undefined
    val renderMark: js.UndefOr[js.Function3[RenderMarkProps, Editor, RenderNextFn, RenderOutput]] = js.undefined
  }

  final class RenderMarkProps(
    val mark: Mark,
    val children: js.Object
  ) extends js.Object

  final class RenderNodeProps(
    val node: Node,
    val children: js.Object
  ) extends js.Object
}
