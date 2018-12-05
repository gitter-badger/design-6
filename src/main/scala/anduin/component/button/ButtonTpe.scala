// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.button

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[button] object ButtonTpe {

  object Target {
    trait Blank extends Button.Target { val mod: TagMod = ^.target.blank }
    trait Self extends Button.Target { val mod: TagMod = ^.target.self }
    trait Parent extends Button.Target { val mod: TagMod = ^.target.parent }
  }

  object Tpe {
    trait Link extends Button.Tpe {
      def href: String
      def target: Button.Target
      final def normal: TagMod = TagMod(^.href := href, target.mod)
      final def disabled: TagMod = TagMod.empty
    }
    sealed trait NonLink extends Button.Tpe {
      def isAutoFocus: Boolean
      def text: String

      final def normal: TagMod = TagMod(^.tpe := text, ^.autoFocus := isAutoFocus)
      final def disabled: TagMod = TagMod(normal, ^.disabled := true)
    }
    trait Button extends NonLink { final def text: String = "button" }
    trait Submit extends NonLink { final def text: String = "submit" }
    trait Reset extends NonLink { final def text: String = "reset" }
  }

}
