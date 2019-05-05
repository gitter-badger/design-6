// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.button

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait ButtonType {
  def normal: TagMod
  def disabled: TagMod
}

object ButtonType {

  // https://developer.mozilla.org/en-US/docs/Web/HTML/Element/a
  trait Link extends ButtonType {
    def href: String
    def target: Target
    final def normal: TagMod = TagMod(^.href := href, target.mod)
    final def disabled: TagMod = TagMod.empty
  }

  sealed trait Target {
    def target: TagMod
    // "rel" is added for security reason.
    // - See: https://mathiasbynens.github.io/rel-noopener/
    final def mod: TagMod = TagMod(target, ^.rel := "noreferrer noopener")
  }
  object Target {
    trait Blank extends Target { def target: TagMod = ^.target.blank }
    trait Self extends Target { def target: TagMod = ^.target.self }
    trait Parent extends Target { def target: TagMod = ^.target.parent }
  }

  // https://developer.mozilla.org/en-US/docs/Web/HTML/Element/button#attr-type
  sealed trait Button extends ButtonType {
    def isAutoFocus: Boolean
    def text: String

    final def normal: TagMod = TagMod(^.tpe := text, ^.autoFocus := isAutoFocus)
    final def disabled: TagMod = TagMod(normal, ^.disabled := true)
  }
  trait Plain extends Button { final def text: String = "button" }
  trait Submit extends Button { final def text: String = "submit" }
  trait Reset extends Button { final def text: String = "reset" }

}
