// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.tree

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

// scalastyle:off underscore.import
import japgolly.scalajs.react._
// scalastyle:on underscore.import

sealed trait TreeLoader[A] {
  def load: TreeLoader.Load[A]
  def hasChildren: A => Boolean
}

private[tree] object TreeLoader {

  type Load[A] = (A, Seq[A] => Callback) => Callback

  trait Sync[A] extends TreeLoader[A] {
    def loadSync: A => Seq[A]

    final def hasChildren: A => Boolean = node => loadSync(node).nonEmpty
    final def load: Load[A] = (node, setChildren) => setChildren(loadSync(node))
  }

  trait Async[A] extends TreeLoader[A] {
    def loadAsync: A => Future[Seq[A]]
    def hasChildren: A => Boolean

    final def load: Load[A] = (node, setChildren) => {
      Callback.future(loadAsync(node).map(setChildren))
    }
  }

}
