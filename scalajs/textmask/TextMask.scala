// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.textmask

import scala.scalajs.js
import scala.scalajs.js.|

// scalastyle:off underscore.import
import scala.scalajs.js.JSConverters._
// scalastyle:off underscore.import

sealed abstract class TextMask

object TextMask {

  // We don't really need to have detailed structure for this
  // as we only use it via addons
  trait Pipe extends js.Object

  case class Array(value: Seq[Item]) extends TextMask
  case class Func(value: String => Array) extends TextMask
  case class FromJS(value: TextMask.Raw) extends TextMask

  sealed abstract class Item
  case class Char(value: String) extends Item
  case class RegExp(value: String) extends Item

  object Item {
    type Raw = String | js.RegExp
    def toRaw(item: Item): Raw = item match {
      case char: Char     => char.value
      case regExp: RegExp => js.RegExp(regExp.value)
    }
  }

  type RawArray = js.Array[Item.Raw]
  type RawFunc = js.Function1[String, RawArray]
  type Raw = RawArray | RawFunc | js.Object | Boolean
  // Why js.Object? https://git.io/fxtrx

  private def toRawArray(array: Array): RawArray = {
    array.value.map(Item.toRaw).toJSArray
  }

  private def toRawFunc(func: Func): RawFunc = (input: String) => {
    toRawArray(func.value(input))
  }

  def toRaw(mask: TextMask): Raw = mask match {
    case array: Array => toRawArray(array)
    case func: Func   => toRawFunc(func)
    case js: FromJS   => js.value
  }
}
