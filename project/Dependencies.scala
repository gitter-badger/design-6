// Copyright (C) 2014-2019 Anduin Transactions Inc.

import sbt._
import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._

//noinspection SpellCheckingInspection
object Dependencies {

  object Versions {
    val scala = "2.12.8"
    val webpack = "4.29.6"
    val webpackCli = "3.3.0"
    val webpackDevServer = "3.2.1"
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
    val focusVisible = "focusVisible" -> "4.1.5"
    val popper = "popper.js" -> "1.14.7"
    val react = "react" -> "16.7.0"
    val reactDom = "reactDom" -> "16.7.0"
    val reactTextMask = "reactTextMask" -> "5.4.3"
    val reactTruncateMarkup = "reactTruncateMarkup" -> "3.0.0"
    val reactVirtualized = "reactVirtualized" -> "9.21.0"
    val textMaskAddons = "textMaskAddons" -> "3.8.0"
    // Docs
    val highlight = "highlight.js" -> "9.15.6"
    val marked = "marked" -> "0.6.2"
  }

}
