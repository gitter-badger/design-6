// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.office

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

object OnlyOfficeFacade {

  @JSGlobal("DocsAPI.DocEditor")
  @js.native
  class Editor(
    val id: String,
    val config: OnlyOfficeConfig
  ) extends js.Object

  object Editor {
    def apply(idParam: String, configParam: OnlyOfficeConfig): Editor = {
      new Editor(idParam, configParam)
    }
  }
}
