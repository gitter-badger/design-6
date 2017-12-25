// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.slate

import org.scalajs.dom.FileList

import anduin.component.icon.Iconv2
import anduin.component.popover.Popover
import anduin.component.tooltip.Tooltip
import anduin.component.uploader.BrowseFileButton
import anduin.scalajs.slate.Slate.{Change, Value}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Toolbar(
  value: Value,
  onChange: Change => Callback,
  onSelectFilesOpt: Option[FileList => Callback],
  shareDocsFromWorkspaceButton: Option[TagMod],
  showAttachmentIcon: Boolean
) {
  def apply(children: VdomNode*): ScalaComponent.Unmounted[_, _, _] = Toolbar.component(this)(children: _*)
}

object Toolbar {

  private val ComponentName = this.getClass.getSimpleName

  private case class Backend(scope: BackendScope[Toolbar, _]) {

    // scalastyle:off method.length multiple.string.literals
    def render(props: Toolbar, children: PropsChildren): VdomElement = {
      <.div(^.cls := "editor-toolbar flex padding-all-small items-center",
        <.div(^.cls := "btn-group flex items-center",
          TagMod.when(props.showAttachmentIcon) {
            TagMod(
              if (props.shareDocsFromWorkspaceButton.isDefined) {
                Popover(
                  toggler = Tooltip(
                    tip = "Upload documents"
                  )(Iconv2.clippie()),
                  placement = Popover.Placement.BottomRight,
                  classes = "pointer"
                )(
                  _ => <.ul(^.cls := "no-bullet",
                    <.li(^.cls := "item mb1",
                      BrowseFileButton(
                        classes = s"popover-menu-item ${if (props.onSelectFilesOpt.isEmpty) "disabled" else ""}",
                        onBrowse = fileList => Callback.traverseOption(props.onSelectFilesOpt)(_ (fileList))
                      )(<.span("Upload documents"))
                    ),
                    props.shareDocsFromWorkspaceButton.whenDefined { component =>
                      <.li(^.cls := "item", component)
                    }
                  )
                )()
              } else {
                Tooltip(
                  tip = "Upload documents",
                  offset = 4
                )(BrowseFileButton(
                  classes = s"btn -plain -icon-only ${if (props.onSelectFilesOpt.isEmpty) "disabled" else ""}",
                  onBrowse = fileList => Callback.traverseOption(props.onSelectFilesOpt)(_ (fileList))
                )(Iconv2.clippie()))
              },
              <.span(^.cls := "divider margin-horizontal-small", "-------")
            )
          },

          MarkButtonBar(props.value, props.onChange)()
        ),

        <.div(^.cls := "flex margin-left-auto",
          children
        )
      )
    }
    // scalastyle:on method.length multiple.string.literals
  }

  private val component = ScalaComponent.builder[Toolbar](ComponentName)
    .stateless
    .renderBackendWithChildren[Backend]
    .build
}
