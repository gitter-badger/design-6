// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.office

import scala.scalajs.js

// See https://api.onlyoffice.com/editors/config/
class OnlyOfficeConfig(
  val editorConfig: OnlyOfficeConfig.EditorConfig,
  val document: OnlyOfficeConfig.Document,
  // Can be text, spreadsheet, presentation
  val documentType: String = "text",
  val height: String = "100%",
  val width: String = "100%"
) extends js.Object

object OnlyOfficeConfig {

  // `document` options

  class Document(
    val fileType: String,
    val key: String,
    val title: String,
    val url: String
  ) extends js.Object

  // `editorConfig` options

  class EditorConfig(
    val callbackUrl: String,
    // Can be view or edit
    val mode: String = "edit",
    val customization: Customization
  ) extends js.Object

  class Customization(
    val compactToolbar: Boolean = false,
    val chat: Boolean = true,
    val comments: Boolean = true
  ) extends js.Object
}
