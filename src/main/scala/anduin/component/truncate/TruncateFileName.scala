// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.truncate

import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

object TruncateFileName {

  // This can be used to truncate a long file name.
  // - Use this if the file name might be displayed inside a table
  // (Since CSS truncate doesn't work with table layout)
  // - Use the CSS `truncate` class (`Style.typography.truncate`) if you are sure that
  // the file name isn't shown inside a table
  def apply(fileName: String): VdomElement = {
    TruncateMarkup(
      renderTarget = <.span(
        ^.title := fileName,
        // @TODO: This is bad but it's the only way (so far) because
        // TruncateMarkup won't work unless there are more than 2 words
        // TruncateMarkup requires all elements inside visible, so `Style.display.none` here doesn't work
        TagMod.unless(fileName.contains(" ")) {
          <.span(^.fontSize := "1px", Style.visibility.invisible, "a ")
        },
        fileName
      )
    )()
  }
}
