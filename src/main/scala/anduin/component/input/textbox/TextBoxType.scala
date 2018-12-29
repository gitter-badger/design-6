// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.input.textbox

import org.scalajs.dom.html.Input

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

sealed trait TextBoxType {
  def tag: VdomTag
  def getSize: TextBoxSize => TagMod
}

private[textbox] object TextBoxType {
  // <input type="..." />
  sealed trait Single extends TextBoxType {
    def tag: VdomTagOf[Input]
    def getSize: TextBoxSize => TagMod = _.single
  }
  trait Text extends Single {
   def tag: VdomTagOf[Input] = <.input.text
  }
  trait Password extends Single{
   def tag: VdomTagOf[Input] = <.input.password
  }

  // <textarea rows="..." />
  trait Area extends TextBoxType {
    def rows: Int
    def tag: VdomTag = <.textarea(^.rows := rows)
    def getSize: TextBoxSize => TagMod = _.area
  }
}
