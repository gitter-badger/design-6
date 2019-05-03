// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.input.suggest

import anduin.component.input.textbox.TextBox
import anduin.scalajs.downshift.DownshiftRenderProps
import anduin.scalajs.util.ScalaJSUtils

import scala.scalajs.js

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[suggest] class SuggestTarget[A] {

  def apply(): Props.type = Props

  case class Props(
    suggest: Suggest[A]#Props,
    downshift: DownshiftRenderProps[A]
  ) {
    def apply(): VdomElement = component(this)
  }

  private def parseValue(valueOpt: js.WrappedDictionary[js.Any]): String = {
    valueOpt.headOption.map(_._2).fold("") { value: js.Any =>
      /* We need to broaden the type of value here (from js.Any to Any) so that
       * we can pattern match it with "String". Although String can be
       * implicitly converted to js.Any (i.e. `val foo: js.Any = "foo"` works),
       * it is not a subtype of js.Any, but only Any */
      val valueAny: Any = value
      valueAny match { case s: String => s; case _ => "" }
    }
  }

  private type Event = ReactKeyboardEventFromInput

  // Submit if trigger keys are pressed
  @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
  private def onKeyDown(props: Props)(event: Event): Unit =
    props.suggest.selection.foreach { selection =>
      if (selection.triggerKeys.contains(event.key)) {
        /* It's better if we take control here so all trigger keys work in
         * the same way (or else, "Enter" will be controlled by Downshift and
         * would be a little bit different than others). See:
         * - https://www.scala-js.org/doc/interoperability/types.html#dynamically-typed-interface-jsdynamic
         * - https://github.com/downshift-js/downshift#customizing-handlers */
        event.asInstanceOf[js.Dynamic].nativeEvent.preventDownshiftDefault = true
        /* We don't want to record the key, especially non-control ones such
         * as "," or ";" */
        event.preventDefault()
        if (ScalaJSUtils.jsNullToOption(props.downshift.highlightedIndex).isDefined) {
          props.downshift.selectHighlightedItem()
        } else {
          selection.onSelect(props.downshift.inputValue).runNow()
        }
        props.downshift.closeMenu()
      }
    }

  private def render(props: Props): VdomElement = {
    val boundOnKeyDown: js.Function1[Event, Unit] = onKeyDown(props)
    val (value, others) = props.downshift
      .getInputProps(js.defined(js.Dictionary(("onKeyDown", boundOnKeyDown))))
      .partition(_._1 == "value")
    val textBox = props.suggest.textBox
    TextBox(
      value = parseValue(value),
      isAutoFocus = textBox.isAutoFocus,
      style = textBox.style,
      size = textBox.size,
      unsafeTagMod = ScalaJSUtils.jsPropsToTagMod(others)
    )()
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .render_P(render)
    .build
}
