// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.onlyoffice

import scala.scalajs.js

// See https://api.onlyoffice.com/editors/config/
class Config(
  val editorConfig: Config.EditorConfig,
  val document: Config.Document,
  // Can be text, spreadsheet, presentation
  val documentType: String = "text",
  val height: String = "100%",
  val width: String = "100%"
) extends js.Object

object Config {

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
    val customization: Customization,
    val user: User
  ) extends js.Object

  class Customization(
    val compactToolbar: Boolean = false,
    val chat: Boolean = true,
    val comments: Boolean = true
  ) extends js.Object

  class User(
    val id: String
  ) extends js.Object
}
