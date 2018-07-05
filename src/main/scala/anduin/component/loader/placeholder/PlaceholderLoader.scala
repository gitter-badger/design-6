// Copyright (C) 2014-2018 Anduin Transactions Inc.

package anduin.component.loader.placeholder

import japgolly.scalajs.react.vdom.{SvgAttrAndStyles => sa, SvgTags => st}

// scalastyle:off underscore.import
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
// scalastyle:on underscore.import

final case class PlaceholderLoader(props: PlaceholderProps) {
  def apply(children: VdomNode*): VdomElement = PlaceholderLoader.component(props)(children: _*)
}

object PlaceholderLoader {

  private val component = ScalaComponent
    .builder[PlaceholderProps](getClass.getSimpleName)
    .stateless
    .noBackend
    // scalastyle:off multiple.string.literals
    .render_PC((p, c) => {
      val id = Math.random().toString.substring(2)
      val clipId = s"clip-$id"
      val gradientId = s"gradient-$id"

      st.svg(
        sa.viewBox := s"0 0 ${p.width} ${p.height}",
        VdomAttr("version") := "1.1",
        sa.preserveAspectRatio := p.preserveAspectRatio,
        st.rect(
          sa.fill := s"url(#$gradientId)",
          sa.clipPath := s"url(#$clipId)",
          sa.x := "0",
          sa.y := "0",
          sa.width := p.width,
          sa.height := p.height
        ),
        st.defs(
          // st.clipPathTag generate `clipPathTag` which is not correct
          HtmlTag("clipPath")(
            sa.id := clipId,
            // Children
            if (c.isEmpty) {
              st.rect(
                sa.x := "0",
                sa.y := "0",
                sa.rx := "5",
                sa.ry := "5",
                sa.width := p.width,
                sa.height := p.height
              )
            } else {
              c
            }
          ),
          st.linearGradient(
            sa.id := gradientId,
            st.stop(
              sa.offset := "0%",
              sa.stopColor := p.primaryColor,
              sa.stopOpacity := p.primaryOpacity,
              TagMod.when(p.animate)(
                st.animate(
                  sa.attributeName := "offset",
                  sa.values := "-2; 1",
                  sa.dur := s"${p.speed}s",
                  sa.repeatCount := "indefinite"
                )
              )
            ),
            st.stop(
              sa.offset := "50%",
              sa.stopColor := p.secondaryColor,
              sa.stopOpacity := p.secondaryOpacity,
              TagMod.when(p.animate)(
                st.animate(
                  sa.attributeName := "offset",
                  sa.values := "-1.5; 1.5",
                  sa.dur := s"${p.speed}s",
                  sa.repeatCount := "indefinite"
                )
              )
            ),
            st.stop(
              sa.offset := "100%",
              sa.stopColor := p.primaryColor,
              sa.stopOpacity := p.primaryOpacity,
              TagMod.when(p.animate)(
                st.animate(
                  sa.attributeName := "offset",
                  sa.values := "-1; 2",
                  sa.dur := s"${p.speed}s",
                  sa.repeatCount := "indefinite"
                )
              )
            )
          )
        )
      )
    })
    // scalastyle:on multiple.string.literals
    .build
}
