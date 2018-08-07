// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.tinymce

import org.scalajs.dom.raw.Element

import anduin.scalajs.tinymce.{InitOptions, RawEditor, Tinymce}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class Editor(
  content: String,
  onChange: String => Callback
) {
  def apply(): VdomElement = Editor.component(this)
}

object Editor {

  private class Backend(scope: BackendScope[Editor, _]) {

    private val targetRef = Ref[Element]
    private var rawEditor: Option[RawEditor] = None // scalastyle:ignore var.field

    def init(): Callback = {
      for {
        props <- scope.props
        target <- targetRef.get
        _ <- Callback {
          Tinymce.init(
            new InitOptions(
              target = target,
              skin_url = "/web/gondor/tinymce/skins/lightgray",
              setup = editor => {
                rawEditor = Some(editor)
                editor.on("init", _ => {
                  editor.setContent(props.content)
                  editor.on("change keyup setcontent", _ => {
                    val content = editor.getContent()
                    props.onChange(content).runNow()
                  })
                })
              }
            )
          )
        }
      } yield ()
    }

    def destroy(): Callback = {
      Callback.traverseOption(rawEditor) { editor =>
        Callback {
          Tinymce.remove(editor)
        }
      }
    }

    def render(): VdomElement = {
      <.div.withRef(targetRef)
    }
  }

  private val component = ScalaComponent
    .builder[Editor](getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .componentDidMount(_.backend.init())
    .componentWillUnmount(_.backend.destroy())
    .build
}
