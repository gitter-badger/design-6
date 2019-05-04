// Copyright (C) 2014-2019 Anduin Transactions Inc.

import sbt._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

//noinspection SpellCheckingInspection
object Dependencies {

  object Versions {
    val scala = "2.12.8"
    val webpack = "4.29.6"
    val webpackCli = "3.3.0"
    val webpackDevServer = "3.3.1"
  }

  object Libraries {
    val scalaReflect = "org.scala-lang" % "scala-reflect" % Versions.scala
    val scalajsDom = Def.setting("org.scala-js" %%% "scalajs-dom" % "0.9.7")
    val scalaJavaTime = Def.setting("io.github.cquiroz" %%% "scala-java-time" % "2.0.0-RC1")
    object scalajsReact {
      val version = "1.4.1"
      val core = Def.setting("com.github.japgolly.scalajs-react" %%% "core" % version)
      val extra = Def.setting("com.github.japgolly.scalajs-react" %%% "extra" % version)
    }
  }

  //noinspection TypeAnnotation
  object NPM {
    // Core
    val downshift = "downshift" -> "3.2.7"
    val focusVisible = "focus-visible" -> "4.1.5"
    val popper = "popper.js" -> "1.14.7"
    val react = "react" -> "16.7.0"
    val reactDom = "react-dom" -> "16.7.0"
    val reactTextMask = "react-text-mask" -> "5.4.3"
    val reactTruncateMarkup = "react-truncate-markup" -> "3.0.0"
    val reactVirtualized = "react-virtualized" -> "9.21.0"
    val textMaskAddons = "text-mask-addons" -> "3.8.0"
    // Docs
    val highlight = "highlight.js" -> "9.15.6"
    val marked = "marked" -> "0.6.2"
  }

}
