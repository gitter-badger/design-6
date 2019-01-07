// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.onlyoffice

import scala.scalajs.js

// See https://api.onlyoffice.com/editors/config/
trait Config extends js.Object {
  val editorConfig: Config.EditorConfig
  val document: Config.Document
  val documentType: String
  val height: String
  val width: String
  val events: Config.Events
}

object Config {

  def apply(
    editorConfigParam: Config.EditorConfig,
    documentParam: Config.Document,
    // Can be text, spreadsheet, presentation
    documentTypeParam: String = "text",
    heightParam: String = "100%",
    widthParam: String = "100%",
    eventsParam: Config.Events
  ): Config = {
    new Config {
      override val editorConfig = editorConfigParam
      override val document = documentParam
      override val documentType = documentTypeParam
      override val height = heightParam
      override val width = widthParam
      override val events = eventsParam
    }
  }

  // `document` options

  trait Document extends js.Object {
    val fileType: String
    val key: String
    val title: String
    val url: String
  }

  object Document {
    def apply(
      fileTypeParam: String,
      keyParam: String,
      titleParam: String,
      urlParam: String
    ): Document = {
      new Document {
        override val fileType = fileTypeParam
        override val key = keyParam
        override val title = titleParam
        override val url = urlParam
      }
    }
  }

  // `editorConfig` options

  trait EditorConfig extends js.Object {
    val callbackUrl: String
    val mode: String
    val customization: Customization
    val user: User
    val userdata: String
  }

  object EditorConfig {
    def apply(
      callbackUrlParam: String,
      // Can be view or edit
      modeParam: String = "edit",
      customizationParam: Customization,
      userParam: User,
      userdataParam: String = ""
    ): EditorConfig = {
      new EditorConfig {
        override val callbackUrl = callbackUrlParam
        override val mode = modeParam
        override val customization = customizationParam
        override val user = userParam
        override val userdata = userdataParam
      }
    }
  }

  // `customization` options

  trait Customization extends js.Object {
    val compactToolbar: Boolean
    val chat: Boolean
    val comments: Boolean
    val forcesave: Boolean
  }

  object Customization {
    def apply(
      compactToolbarParam: Boolean = false,
      chatParam: Boolean = true,
      commentsParam: Boolean = true,
      forcesaveParam: Boolean = false
    ): Customization = {
      new Customization {
        override val compactToolbar = compactToolbarParam
        override val chat = chatParam
        override val comments = commentsParam
        override val forcesave = forcesaveParam
      }
    }
  }

  // `user` option

  trait User extends js.Object {
    val id: String
  }

  object User {
    def apply(idParam: String): User = {
      new User {
        override val id = idParam
      }
    }
  }

  // `events` options

  trait Events extends js.Object {
    val onDocumentStateChange: js.Function1[DocumentStateChangeEvent, Unit]
  }

  object Events {
    def apply(
      onDocumentStateChangeParam: js.Function1[DocumentStateChangeEvent, Unit]
    ): Events = {
      new Events {
        override val onDocumentStateChange = onDocumentStateChangeParam
      }
    }
  }

  trait DocumentStateChangeEvent extends js.Object {
    val data: Boolean
  }
}
