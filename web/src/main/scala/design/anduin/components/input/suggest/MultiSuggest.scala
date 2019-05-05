// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.input.suggest

import design.anduin.components.tag.{Tag, TagColor}
import design.anduin.style.Style
import org.scalajs.dom.html

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

class MultiSuggest[A] {

  def apply(): Props.type = Props

  case class Props(
    value: Seq[String],
    onChange: Seq[String] => Callback,
    opts: Suggest.Opts[A],
    selectKeys: Seq[String] = List("Enter"),
    valueToSelectedItem: String => MultiSuggest.SelectedItem = s => MultiSuggest.SelectedItem(s)
  ) {
    def apply(): VdomElement = component(this)
  }

  private val Selected = (new MultiSuggestSelected[A])()
  private val Input = (new MultiSuggestInput[A])()

  private val styles = TagMod(
    Style.flexbox.flex.flexbox.itemsCenter.flexbox.wrap,
    Style.border.all.borderColor.gray4.borderWidth.px1.borderRadius.px2,
    Style.borderColor.focusWithinPrimary4.shadow.focusWithinSpread,
    Style.transition.allWithShadow,
    ^.padding := "2px" // empty list should has 32-px height
  )

  private class Backend(scope: BackendScope[Props, _]) {

    // We don't need the scope here, just need Backend because of the ref
    private val _ = scope

    /* This ref will not be placed directly on the Suggest's "input" tag inside
     * "renderAdd" because it uses TextBox, which does not accept "ref" because
     * "ref" is already controlled by TextMask (its underlying controller).
     * See:
     * - https://github.com/anduintransaction/stargazer/blob/
     *   c82aba79544c0800851c0a071933141322061e8f/platform/stargazerJs/
     *   src/main/scala/anduin/component/input/textbox/TextBoxBody.scala#L102
     * - https://github.com/text-mask/text-mask/issues/687
     * Instead, we will have a ref to the container and do a query selector to
     * find the "input" element (see the "focusNewInput") */
    private val addRef = Ref[html.Element]

    def focusAddInput: Callback = addRef.map(_.querySelector("input")).foreach {
      case element: html.Input => element.focus(); case _ => ()
    }

    private def renderAdd(props: Props): VdomElement = {
      <.div.withRef(addRef)(
        Style.flexbox.fill,
        ^.minWidth := "128px",
        ^.padding := "3px",
        Input(
          onSelect = v => props.onChange(props.value ++ v),
          opts = props.opts,
          selectKeys = props.selectKeys
        )()
      )
    }

    private def renderSelected(props: Props)(tuple: (String, Int)): VdomElement = {
      <.div(
        Style.flexbox.none,
        ^.padding := "3px",
        Selected(
          multiSuggest = props,
          value = tuple,
          focusAddInput = focusAddInput
        )()
      )
    }

    def render(props: Props): VdomElement = {
      <.div(
        styles,
        props.value.zipWithIndex.toReactFragment(renderSelected(props)),
        renderAdd(props)
      )
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .build
}

object MultiSuggest {
  final case class SelectedItem(label: String, color: TagColor = Tag.Light.Gray)
}
