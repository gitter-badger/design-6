// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import anduin.scalajs.slate.{HtmlSerializer, Slate}

object Serializer {

  private final val htmlSerializer = new HtmlSerializer()

  def deserialize(rawHtml: String): Slate.Value = {
    htmlSerializer.deserialize(rawHtml)
  }
}
