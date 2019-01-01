// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.onlyoffice

import scala.scalajs.js
import scala.scalajs.js.annotation.JSGlobal

@JSGlobal("DocsAPI.DocEditor")
@js.native
class Editor(
  val id: String,
  val config: Config
) extends js.Object

object Editor {
  def apply(idParam: String, configParam: Config): Editor = {
    new Editor(idParam, configParam)
  }
}
