// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.facade.draftconvert

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

import org.scalajs.dom.raw.HTMLElement

import anduin.facade.draftjs.{ContentState, DraftInlineStyle}

// See https://github.com/HubSpot/draft-convert

@JSImport("draft-convert", JSImport.Namespace, "DraftConvert")
@js.native
object DraftConvert extends js.Object {
  def convertFromHTML(options: ConvertFromHtmlOptions): js.Function2[String, FlatOption, ContentState] = js.native // linter:ignore UnusedParameter
}

@js.native
trait FlatOption extends js.Object {
  val flat: Boolean = js.native
}

object FlatOption {
  def apply(flat: Boolean): FlatOption = js.Dynamic.literal(flat = flat).asInstanceOf[FlatOption]
}

@js.native
trait ConvertFromHtmlOptions extends js.Object

object ConvertFromHtmlOptions {

  type HtmlToStyleFn = js.Function3[String, HTMLElement, DraftInlineStyle, DraftInlineStyle]
  type HtmlToEntityFn = js.Function3[String, HTMLElement, CreateEntityFn, |[Unit, String]]
  type CreateEntityFn = js.Function3[String, String, js.Object, String]
  type HtmlToBlockFn = js.Function4[String, HTMLElement, String, String, |[String, js.Object]]

  def apply(
    htmlToStyle: HtmlToStyleFn,
    htmlToEntity: HtmlToEntityFn,
    htmlToBlock: HtmlToBlockFn
  ): ConvertFromHtmlOptions = {
    js.Dynamic.literal(
      htmlToStyle = htmlToStyle,
      htmlToEntity = htmlToEntity,
      htmlToBlock = htmlToBlock
    ).asInstanceOf[ConvertFromHtmlOptions]
  }
}
