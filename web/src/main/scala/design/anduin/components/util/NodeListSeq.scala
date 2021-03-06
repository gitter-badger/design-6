// Copyright (C) 2014-2019 Anduin Transactions Inc.

package design.anduin.components.util

import org.scalajs.dom.DOMList

// See https://www.scala-js.org/doc/sjs-for-js/es6-to-scala-part3.html
case class NodeListSeq[T](nodes: DOMList[T]) extends IndexedSeq[T] {

  override def foreach[U](f: T => U): Unit = {
    for (i <- 0 until nodes.length) {
      f(nodes(i))
    }
  }

  override def length: Int = nodes.length

  override def apply(idx: Int): T = nodes(idx)
}
