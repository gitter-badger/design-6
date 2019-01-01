// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js

class GetItemPropsOptions[A](
  val index: Number,
  val item: A,
  val disabled: js.UndefOr[Boolean] = js.undefined
) extends js.Object

private[downshift] trait DownshiftPropsGetter[A] extends js.Object {
  type JsDict = js.Dictionary[js.Any]
  def getRootProps: js.Function0[JsDict]
  def getToggleButtonProps: js.Function0[JsDict]
  def getLabelProps: js.Function0[JsDict]
  def getMenuProps: js.Function0[JsDict]
  def getInputProps: js.Function0[JsDict]
  def getItemProps: js.Function1[GetItemPropsOptions[A], JsDict]
}

trait DownshiftRenderProps[A] extends js.Object with DownshiftState[A] with DownshiftPropsGetter[A]
