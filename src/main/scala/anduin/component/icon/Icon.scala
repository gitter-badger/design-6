// Copyright (C) 2014-2017 Anduin Transactions Inc.

package anduin.component.icon

import japgolly.scalajs.react.vdom.TagOf
import japgolly.scalajs.react.vdom.all.svg

// scalastyle:off underscore.import
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.dom.raw._
// scalastyle:on underscore.import

// scalastyle:off line.size.limit

/** List of icons provided by the component library:
  * http://acl.anduintransact.com/docs/icon.html
  */
object Icon {

  private def apply(
    className: String,
    additionalClassName: String,
    viewBox: String,
    svgSolidContent: TagOf[SVGPolygonElement],
    svgLineContent: Option[TagOf[SVGPolygonElement]],
    isSolid: Boolean
  ): TagOf[SVGSVGElement] = {
    val iconClassName = if (isSolid) {
      s"icon-$className-solid"
    } else {
      s"icon-$className"
    }

    svg.svg(
      ^.cls := s"icon-svg $iconClassName $additionalClassName",
      svg.xmlns := "http://www.w3.org/2000/svg",
      svg.viewBox := viewBox,
      if (isSolid) {
        svgSolidContent
      } else {
        svgLineContent.getOrElse(svgSolidContent)
      }
    )
  }

  def cross(additionalClassName: String = "", isSolid: Boolean = false): TagOf[SVGSVGElement] = apply(
    "cross",
    additionalClassName,
    "0 0 12 16",
    svg.polygon(svg.points := "9.485 3.00049 5.95 6.53549 2.414 3.00049 1 4.41449 4.535 7.94949 1 11.48549 2.414 12.89949 5.95 9.36449 9.485 12.89949 10.899 11.48549 7.364 7.94949 10.899 4.41449"),
    None,
    isSolid
  )
}
