// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.suggest

import anduin.component.input.textbox.{TextBoxSize, TextBoxStyle, TextBox => OrgTextBox}
import anduin.scalajs.util.ScalaJSUtils
import org.scalajs.dom.raw.HTMLElement

import scala.scalajs.js

// scalastyle:off underscore.import
import anduin.scalajs.downshift._
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

class Suggest[A] {

  def apply(): Props.type = Props

  private val DownshiftA = new Downshift[A]

  private val SuggestTarget = (new SuggestTarget[A])()
  private val SuggestContent = (new SuggestContent[A])()
  private val SuggestStateReducer = new SuggestStateReducer[A]

  case class Props(
    value: String,
    onChange: String => Callback,
    opts: Suggest.Opts[A],
    textBox: Suggest.TextBox[A] = Suggest.TextBox[A](),
    // ADVANCED
    selection: Option[Suggest.Selection] = None
  ) {
    def apply(): VdomElement = component(this)
  }

  private def itemToString(opts: Suggest.Opts[A])(item: js.|[A, Null]): String =
    ScalaJSUtils.jsNullToOption(item).fold("")(opts.getValueString)

  private def onChange(props: Props)(value: String): Unit =
    props.onChange(value).runNow()

  private def onOptSelect(props: Props)(opt: A): Unit =
    props.selection.foreach { submission =>
      submission.onSelect(props.opts.getValueString(opt)).runNow()
    }

  private def filterItems(props: Props) = {
    val inputValue = props.value.toLowerCase
    val items = props.opts.items
      .filter(opt => props.opts.getFilterString(opt.value).toLowerCase.contains(inputValue))
      // Don't suggest too much options to avoid performance issue. In
      // practical this is perfectly ok with a "Suggest" behaviour
      .take(20)
    props.opts.copy(items = items)
  }

  private class Backend(scope: BackendScope[Props, _]) {

    // We don't need the scope here, just need Backend because of the ref
    private val _ = scope
    private val targetRef = Ref[HTMLElement]

    private def renderChildren(orgProps: Props)(
      downshift: DownshiftRenderProps[A]
    ): raw.React.Node = {
      // From now on we only care about the filtered options short list
      val props: Props = orgProps.copy(opts = filterItems(orgProps))
      <.div(
        <.div.withRef(targetRef)(SuggestTarget(props, downshift)()),
        TagMod.when(downshift.isOpen && props.opts.items.nonEmpty) {
          SuggestContent(props, downshift, targetRef)()
        }
      ).rawNode
    }

    def render(props: Props): VdomElement = {
      val downshiftProps = new DownshiftA.Props(
        onChange = onOptSelect(props),
        itemToString = itemToString(props.opts),
        stateReducer = SuggestStateReducer.get,
        children = renderChildren(props),
        // ===
        defaultHighlightedIndex = js.undefined,
        initialInputValue = js.undefined,
        inputValue = js.defined(props.value),
        onInputValueChange = js.defined(onChange(props)),
        selectedItem = js.defined(null) // scalastyle:ignore null
      )
      DownshiftA.component(downshiftProps)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}

object Suggest {

  final case class Opt[A](
    value: A,
    isDisabled: Boolean = false
  )

  final case class Opts[A](
    items: Seq[Suggest.Opt[A]],
    // These are methods here instead of attributes in `Opt` to avoid executing
    // unnecessarily when Suggest's popover (the list of options) may not be
    // rendered at all
    render: A => VdomNode,
    getFilterString: A => String,
    getValueString: A => String
  )

  final case class Selection(
    triggerKeys: Seq[String] = Seq.empty,
    onSelect: String => Callback
  )

  final case class GetTagModParams[A](
    suggest: Suggest[A]#Props,
    downshift: DownshiftRenderProps[A]
  )

  final case class TextBox[A](
    style: TextBoxStyle = OrgTextBox.Style.Full,
    size: TextBoxSize = OrgTextBox.Size.Px32,
    isAutoFocus: Boolean = false
  )
}
