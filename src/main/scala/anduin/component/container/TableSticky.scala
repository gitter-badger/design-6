// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.container

import org.scalajs.dom.html
import org.scalajs.dom.raw.DOMList

import anduin.component.util.NodeListSeq
import anduin.style.Style

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

private[component] final case class TableSticky(
  widths: List[String],
  styles: TagMod,
  body: VdomElement,
  head: VdomElement,
  headStickyOffset: Int
) {
  def apply(): VdomElement = TableSticky.component(this)
}

object TableSticky {

  type Props = TableSticky

  private class Backend {

    private val headTableRef = Ref[html.Table]
    private val bodyTableRef = Ref[html.Table]

    private def getThs(table: html.Table) = {
      val collection = table.getElementsByTagName("th")
      @SuppressWarnings(Array("org.wartremover.warts.AsInstanceOf")) // scalastyle:ignore
      val domList = collection.asInstanceOf[DOMList[html.TableHeaderCell]]
      NodeListSeq(domList)
    }

    // this updates HeadTable > th's width to be the same with
    // BodyTable > th's width
    def updateWidths(): Callback = {
      for {
        body <- bodyTableRef.get
        head <- headTableRef.get
      } yield {
        val bodyThs = getThs(body)
        val widths = bodyThs.map(th => th.clientWidth).toList
        val headThs = getThs(head)
        headThs.zipWithIndex.foreach { tuple =>
          val (th, index) = tuple
          th.style.width = s"${widths(index)}px"
        }
      }
    }

    def render(props: Props): VdomElement = {
      val headTable = <.table.withRef(headTableRef)(
        Style.position.sticky.zIndex.idx1,
        ^.top := s"${props.headStickyOffset.toString}px",
        props.styles,
        props.head
      )
      val bodyTable = <.table.withRef(bodyTableRef)(
        // 36px is the height of head + 2*1px border.
        // This will work for a long time
        ^.marginTop := "-38px",
        props.styles,
        props.head,
        props.body
      )
      <.div(headTable, bodyTable)
    }
  }

  private val component = ScalaComponent
    .builder[Props](this.getClass.getSimpleName)
    .stateless
    .renderBackend[Backend]
    .componentDidMount(_.backend.updateWidths())
    .build
}
