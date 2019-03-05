// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.resizeobserver

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import japgolly.scalajs.react.Callback
import org.scalajs.dom.raw.Element

@JSImport("resize-observer-polyfill", JSImport.Default, "ResizeObserver")
@js.native
class ResizeObserver(callback: js.Function2[js.Array[ResizeObserverEntry], ResizeObserver, Unit]) extends js.Object {
  def observe(target: Element): Unit = js.native
  def unobserve(target: Element): Unit = js.native
  def disconnect(): Unit = js.native
}

object ResizeObserver {
  def apply(
    callback: (js.Array[ResizeObserverEntry], ResizeObserver) => Callback
  ): ResizeObserver = {
    new ResizeObserver((entries, observer) => callback(entries, observer).runNow())
  }
}

trait ResizeObserverEntry extends js.Object {
  val target: Element
  val contentRect: DomRect
}

trait DomRect extends js.Object {
  val x: Double
  val y: Double
  val width: Double
  val height: Double
  val top: Double
  val right: Double
  val bottom: Double
  val left: Double
}
