// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.scalajs.reactintersectionobserver

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

object ReactIntersectionObserver {

  @JSImport("@researchgate/react-intersection-observer", JSImport.Default, "Observer")
  @js.native
  object RawComponent extends js.Object

  @js.native
  trait IntersectionObserverEntry extends js.Object {
    val isIntersecting: Boolean = js.native
  }

  final class Props(
    val onChange: js.UndefOr[js.Function1[IntersectionObserverEntry, Unit]] = js.undefined
  ) extends js.Object
}
