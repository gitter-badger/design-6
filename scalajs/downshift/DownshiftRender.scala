// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.scalajs.downshift

import scala.scalajs.js
import scala.scalajs.js.|

class DownshiftItem[A](
  val index: Number,
  val item: A,
  val disabled: js.UndefOr[Boolean] = js.undefined
) extends js.Object

trait DownshiftRenderProps[A] extends js.Object with DownshiftState[A] {
  private type Dict = js.Dictionary[js.Any]
  private type UDict = js.UndefOr[Dict]
  // Props Getter
  def getRootProps(options: UDict = js.undefined): Dict
  def getToggleButtonProps(option: UDict = js.undefined): Dict
  def getLabelProps(options: UDict = js.undefined): Dict
  def getMenuProps(options: UDict = js.undefined): Dict
  def getInputProps(options: UDict = js.undefined): Dict
  def getItemProps(item: DownshiftItem[A]): Dict
}
