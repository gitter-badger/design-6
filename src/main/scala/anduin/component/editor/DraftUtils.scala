// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.editor

import scala.scalajs.js
import scala.util.Try

import org.scalajs.dom.raw.HTMLElement

import anduin.scalajs.draftconvert.ConvertFromHtmlOptions.CreateEntityFn
import anduin.scalajs.draftconvert.{ConvertFromHtmlOptions, DraftConvert, FlatOption}

// scalastyle:off underscore.import
import anduin.scalajs.draftjs._
// scalastyle:on underscore.import

object DraftUtils {

  def createStateFromRaw(rawContent: String): EditorState = {
    if (rawContent.isEmpty) {
      EditorState.createEmpty()
    } else {
      val content = js.JSON.parse(rawContent).asInstanceOf[Draft.RawDraftContentState]
      val contentState = Draft.convertFromRaw(content)
      EditorState.createWithContent(contentState)
    }
  }

  // scalastyle:off cyclomatic.complexity
  def createStateFromHtml(rawMarkup: String, maxCharsOpt: Option[Int] = None): EditorState = {
    if (rawMarkup.isEmpty) {
      EditorState.createEmpty()
    } else {
      val contentState = DraftConvert.convertFromHTML(
        ConvertFromHtmlOptions(
          htmlToStyle = (_: String, node: HTMLElement, currentStyle: DraftInlineStyle) => {
            Option(node.style).fold(currentStyle) { nodeStyle =>
              val textAlign = nodeStyle.textAlign
              if (textAlign.nonEmpty) {
                textAlign.trim.toLowerCase match {
                  case "right" => currentStyle.add(StyleMap.RightTextAlign)
                  case "center" => currentStyle.add(StyleMap.CenterTextAlign)
                  case _ => currentStyle
                }
              } else {
                currentStyle
              }
            }
          },
          htmlToEntity = (nodeName: String, node: HTMLElement, createEntity: CreateEntityFn) => {
            if (nodeName == "img") {
              createEntity(
                "IMAGE",
                "IMMUTABLE",
                js.Dynamic.literal(
                  src = node.getAttribute("src"),
                  width = node.getAttribute("width"),
                  height = node.getAttribute("height"),
                  alt = node.getAttribute("alt")
                )
              )
            } else if (nodeName == "a") {
              createEntity(
                "LINK",
                "MUTABLE",
                js.Dynamic.literal(
                  url = node.getAttribute("href")
                )
              )
            } else {
              ()
            }
          },
          htmlToBlock = (nodeName: String, _: HTMLElement, _: String, inBlock: String) => {
            if (nodeName == "img" && inBlock != "atomic") {
              "atomic"
            } else {
              ""
            }
          }
        )
      )(rawMarkup, FlatOption(flat = true))

      val newContentState = maxCharsOpt.fold(contentState) { maxChar =>
        val currentText = contentState.getPlainText()
        if (currentText.length > maxChar) {
          ContentState.createFromText(s"${currentText.substring(0, maxChar)} ...")
        } else {
          contentState
        }
      }
      EditorState.createWithContent(newContentState)
    }
  }
  // scalastyle:on cyclomatic.complexity

  def getPlainText(rawMarkup: String, maxCharsOpt: Option[Int] = None): String = {
    createStateFromHtml(rawMarkup, maxCharsOpt).getCurrentContent().getPlainText()
  }

  def anyToString(value: js.Any): Option[String] = {
    if (value == null) { // scalastyle:ignore null
      None
    } else {
      Some(value.toString)
    }
  }

  def contentStateGetEntity(contentState: ContentState, key: String): Option[DraftEntityInstance] = {
    val `try` = Try {
      contentState.getEntity(key)
    }
    `try`.toOption
  }
}
