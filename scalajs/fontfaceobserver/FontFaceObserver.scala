// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.fontfaceobserver

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, JSName}

@JSImport("fontfaceobserver", JSImport.Namespace, "FontFaceObserver")
@js.native
class FontFaceObserver(fontName: String) extends js.Object {
  def load(): Promise[js.Any] = js.native
}

@js.native
trait Promise[T] extends js.Object {
  @JSName("then")
  def andThen(onResolve: js.Function1[T, _], onReject: js.Function1[String, _] = ???): Promise[T] =
    js.native
}
