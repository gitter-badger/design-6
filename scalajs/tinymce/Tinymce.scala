// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.tinymce

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.|

import org.scalajs.dom.raw.Element

// We can't import `tinymce/tinymce` from npm package
// We have to [download and compile](https://www.npmjs.com/package/tinymce#bundle-themes-and-plugins-into-a-single-file)
// it manually on local
@JSImport("./external/js/tinymce/tinymce-4.8.1.min.js", JSImport.Default)
@js.native
object Tinymce extends js.Object {
  def init(options: InitOptions): Unit = js.native
  def remove(rawEditor: RawEditor): Unit = js.native
}

class InitOptions(
  val target: Element,
  val branding: Boolean = false,
  val inline: Boolean = false,
  val menubar: String | Boolean = false,
  val statusbar: Boolean = true,
  // The list of plugins already included in `tinymce-4.8.1.min.js` above
  // advlist,autolink,autoresize,autosave,contextmenu,hr,image,link,lists,paste,preview,
  // searchreplace,spellchecker,textcolor,table,wordcount
  val plugins: Array[String] = Array(
    "advlist autolink autoresize autosave contextmenu hr image link lists paste preview searchreplace",
    "spellchecker textcolor table wordcount"
  ),
  val toolbar: String | Array[String] =
    "undo redo | formatselect | bold italic underline | alignleft aligncenter alignright alignjustify | numlist bullist outdent indent",
  val table_toolbar: String = "tableprops tabledelete | tableinsertrowbefore tableinsertrowafter tabledeleterow | "
    + "tableinsertcolbefore tableinsertcolafter tabledeletecol",
  val skin: String = "lightgray",
  val skin_url: String,
  val theme: String = "modern",
  val setup: js.Function1[RawEditor, Unit]
) extends js.Object

@js.native
trait RawEditor extends js.Object {
  def getContent(): String = js.native
  def setContent(content: String): Unit = js.native
  def on(event: String, handler: js.Function1[js.Object, Unit]): Unit = js.native
}
