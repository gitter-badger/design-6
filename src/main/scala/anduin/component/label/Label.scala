// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.label

import japgolly.scalajs.react.component.Scala.Unmounted

import anduin.component.util.ComponentUtils

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Label(
  label: String,
  labelType: Label.LabelType = Label.Default,
  keyOpt: Option[String] = None
) {
  def apply(): Unmounted[_, _, _] = {
    val c = Label.component
    keyOpt.fold(c(this))(c.withKey(_)(this))
  }
}

object Label {

  sealed trait LabelType {
    val className: String
  }
  object Default extends LabelType {
    override val className: String = ""
  }
  object Primary extends LabelType {
    override val className: String = "-primary"
  }
  object Success extends LabelType {
    override val className: String = "-success"
  }
  object Warning extends LabelType {
    override val className: String = "-warning"
  }
  object Danger extends LabelType {
    override val className: String = "-danger"
  }

  private val ComponentName = ComponentUtils.name(this)

  private case class Backend(scope: BackendScope[Label, _]) {

    def render(props: Label): VdomElement = {
      <.span(^.cls := s"at-label ${props.labelType.className}", props.label)
    }
  }

  private val component = ScalaComponent
    .builder[Label](ComponentName)
    .stateless
    .renderBackend[Backend]
    .build

  def default(label: String, keyOpt: Option[String] = None): Unmounted[_, _, _] =
    Label(label, Default, keyOpt)()
  def primary(label: String, keyOpt: Option[String] = None): Unmounted[_, _, _] =
    Label(label, Primary, keyOpt)()
  def success(label: String, keyOpt: Option[String] = None): Unmounted[_, _, _] =
    Label(label, Success, keyOpt)()
  def warning(label: String, keyOpt: Option[String] = None): Unmounted[_, _, _] =
    Label(label, Warning, keyOpt)()
  def danger(label: String, keyOpt: Option[String] = None): Unmounted[_, _, _] =
    Label(label, Danger, keyOpt)()
}
