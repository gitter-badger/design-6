// Copyright (C) 2014-2019 Anduin Transactions Inc.

package anduin.component.icon

// scalastyle:off underscore.import
import anduin.style.CssVar.Color._
// scalastyle:on underscore.import

object IconFolder {
  case class Color(stroke: String, fill1: String, fill2: String)
  object Color {
    object Brown extends Color("#D6A06B", "#E9D799", "#EABE9D")
    object Gray extends Color(gray5, gray2, gray4)
    object Red extends Color(red5, red3, red4)
    object Orange extends Color(orange5, orange3, orange4)
    object Green extends Color(green5, green3, green4)
    object Blue extends Color(blue5, blue3, blue4)
  }
}

// scalastyle:off line.size.limit
trait IconFolder extends IconFile {
  def color: IconFolder.Color
  def glyph: Option[IconGlyph]

  final def path16: String =
    s"""
       |  <path fill="#000" fill-opacity=".1" d="M15.56 4.71H9.87L8.13 3h-5.7C2.2 3 2 3.2 2 3.43v10.71c0 .48.4.86.88.86h12.25c.48 0 .87-.38.87-.86v-9c0-.23-.2-.43-.44-.43z"/>
       |  <path fill="url(#icon_folder_${color.getClass.getSimpleName}_16)" stroke="${color.stroke}" d="M8.23 3.79c.28.27.66.42 1.05.42h5.22v8.93c0 .2-.16.36-.38.36H1.88a.37.37 0 0 1-.38-.36V2.5h5.22a.5.5 0 0 1 .35.14L8.23 3.8z"/>
       |  <defs>
       |    <linearGradient id="icon_folder_${color.getClass.getSimpleName}_16" x1="-6" x2="5.86" y1="8" y2="21.84" gradientUnits="userSpaceOnUse">
       |      <stop stop-color="${color.fill1}"/>
       |      <stop offset="1" stop-color="${color.fill2}"/>
       |    </linearGradient>
       |  </defs>
    """.stripMargin
  final def path24: String =
    s"""
       |  <path fill="#000" fill-opacity=".1" d="M23.31 6.57h-8.93L11.62 4H2.7C2.3 4 2 4.29 2 4.64v16.07c0 .72.62 1.29 1.38 1.29h19.25c.75 0 1.37-.57 1.37-1.29V7.21c0-.35-.3-.64-.69-.64z"/>
       |  <path fill="url(#icon_folder_${color.getClass.getSimpleName}_24)" stroke="${color.stroke}" d="M13.03 5.94l.15.13H22.3c.14 0 .19.1.19.14v13.5c0 .4-.36.79-.88.79H2.38c-.51 0-.87-.38-.87-.79V3.64c0-.04.05-.14.19-.14h8.74l2.6 2.44z"/>
       |  <defs>
       |    <linearGradient id="icon_folder_${color.getClass.getSimpleName}_24" x1="-10" x2="7.64" y1="12" y2="33.56" gradientUnits="userSpaceOnUse">
       |      <stop stop-color="${color.fill1}"/>
       |      <stop offset="1" stop-color="${color.fill2}"/>
       |    </linearGradient>
       |  </defs>
    """.stripMargin
  final def path32: String =
    s"""
       |  <path fill="#000" fill-opacity=".1" fill-rule="evenodd" d="M5 5a2 2 0 0 0-2 2v20c0 1.1.9 2 2 2h24a2 2 0 0 0 2-2V10a2 2 0 0 0-2-2H16c-.85 0-2.5-1.5-3.4-2.38-.39-.39-.92-.62-1.47-.62H5z" clip-rule="evenodd"/>
       |  <path fill="url(#icon_folder_${color.getClass.getSimpleName}_32)" stroke="${color.stroke}" d="M2.5 6c0-.83.67-1.5 1.5-1.5h6.13c.41 0 .82.17 1.13.48.45.44 1.1 1.05 1.75 1.56.33.25.67.48.99.65.3.17.66.31 1 .31h13c.83 0 1.5.67 1.5 1.5v17c0 .83-.67 1.5-1.5 1.5H4A1.5 1.5 0 0 1 2.5 26V6z"/>
       |  <defs>
       |    <linearGradient id="icon_folder_${color.getClass.getSimpleName}_32" x1="-12" x2="11.72" y1="16" y2="43.67" gradientUnits="userSpaceOnUse">
       |      <stop stop-color="${color.fill1}"/>
       |      <stop offset="1" stop-color="${color.fill2}"/>
       |    </linearGradient>
       |  </defs>
       |  <path
       |    transform="translate(10, 12) scale(0.75)"
       |    fill="#000" fill-opacity=".6" fill-rule="evenodd"
       |    d="${glyph.fold("")(_.d)}"
       |  />
    """.stripMargin
}
// scalastyle:on line.size.limit
