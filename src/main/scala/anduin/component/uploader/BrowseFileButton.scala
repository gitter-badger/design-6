// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.uploader

import japgolly.scalajs.react.vdom.Exports.VdomTagOf
import org.scalajs.dom.FileList
import org.scalajs.dom.html.Anchor

import anduin.component.util.JavaScriptUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

/**
  * Renders an input element for choosing local files
  */
final case class BrowseFileButton(
  multiple: Boolean = true,
  disabled: Boolean = false,
  classes: String = "",
  tip: String = "",
  onBrowse: FileList => Callback = _ => Callback.empty,
  keyOpt: Option[String] = None
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = {
    val component =
      keyOpt.map(BrowseFileButton.component.withKey(_)).getOrElse(BrowseFileButton.component.ctor)
    component(this)(children: _*)
  }
}

object BrowseFileButton {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[BrowseFileButton, _]) {

    private def onBrowseFiles(e: ReactEventFromInput) = {
      for {
        _ <- e.preventDefaultCB
        props <- scope.props
        _ <- props.onBrowse(e.target.files)
      } yield ()
    }

    def render(props: BrowseFileButton, children: PropsChildren): VdomTagOf[Anchor] = {
      <.a(
        ^.classSet(
          props.classes -> props.classes.nonEmpty,
          "browse-file" -> true
        ),
        ^.href := JavaScriptUtils.voidMethod,
        TagMod.when(props.tip.nonEmpty)(VdomAttr("data-tip") := props.tip),
        children,
        <.input(
          ^.cls := "input",
          ^.tpe := "file",
          ^.multiple := props.multiple,
          if (props.disabled) {
            ^.disabled := true
          } else {
            ^.onChange ==> onBrowseFiles
          }
        )
      )
    }
  }

  private val component = ScalaComponent
    .builder[BrowseFileButton](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
