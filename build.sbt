// Copyright (C) 2014-2019 Anduin Transactions Inc.

ThisBuild / name := "Anduin Design"
ThisBuild / organization := "anduin.design"
ThisBuild / version := "0.2"

ThisBuild / scalaVersion := Dependencies.scalaVersion

//noinspection SpellCheckingInspection
lazy val core = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    libraryDependencies ++= List(
      Dependencies.Libraries.scalajsDom.value,
      Dependencies.Libraries.scalajsReact.core.value
    ),
    Compile / npmDependencies ++= List(
      Dependencies.NPM.downshift,
      Dependencies.NPM.focusVisible,
      Dependencies.NPM.popper,
      Dependencies.NPM.react,
      Dependencies.NPM.reactDom,
      Dependencies.NPM.reactTextMask,
      Dependencies.NPM.reactTruncateMarkup,
      Dependencies.NPM.reactVirtualized,
      Dependencies.NPM.textMaskAddons
    )
  )

//noinspection SpellCheckingInspection
lazy val docsMacros = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalacOptions += "-P:scalajs:sjsDefinedByDefault -Yrangepos",
    libraryDependencies ++= List(
      Dependencies.Libraries.scalajsReact.core.value,
      Dependencies.Libraries.scalaReflect
    )
  )

//noinspection SpellCheckingInspection
lazy val docsSrc = project
  .dependsOn(docsMacros, core)
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    scalaJSUseMainModuleInitializer := true,
    webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
    webpackBundlingMode in fullOptJS := BundlingMode.Application,
    webpackCliVersion := "3.3.0",
    webpack / version := "4.29.6",
    startWebpackDevServer / version := "3.2.1",
    libraryDependencies ++= List(
      Dependencies.Libraries.scalajsDom.value,
      Dependencies.Libraries.scalajsReact.core.value,
      Dependencies.Libraries.scalajsReact.extra.value
    ),
    Compile / npmDependencies ++= List(
      Dependencies.NPM.react,
      Dependencies.NPM.reactDom,
      Dependencies.NPM.highlight,
      Dependencies.NPM.marked
    )
  )

lazy val root = (project in file(".")).aggregate(docsSrc)
