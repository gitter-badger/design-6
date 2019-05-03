package anduin.guide.scalajs.highlight

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object Highlight {

  @JSImport("highlight.js/lib/highlight", JSImport.Default)
  @js.native
  object Core extends js.Object {
    def registerLanguage(name: String, language: Language): Unit = js.native
    def listLanguages(): js.Array[String] = js.native
    def highlight(name: String, value: String, ignore_illegals: Boolean): Result = js.native
  }

  sealed trait Result extends js.Object {
    def language: String
    def relevance: Int
    def value: String
  }

  private[highlight] sealed trait Language extends js.Object
  object Language {
    @JSImport("highlight.js/lib/languages/scala", JSImport.Default)
    @js.native
    object Scala extends Language
  }
}
