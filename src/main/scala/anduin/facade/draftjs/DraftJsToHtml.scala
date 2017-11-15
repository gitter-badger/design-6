// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.facade.draftjs

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

import com.anduin.stargazer.client.facades.draftjs.Draft.RawDraftContentState

@JSImport("draftjs-to-html", JSImport.Namespace, "DraftJsToHtml")
@js.native
object DraftJsToHtml extends js.Object {

  @JSName("default")
  def convert(contentState: RawDraftContentState): String = js.native // linter:ignore UnusedParameter
}
