// Copyright (C) 2014-2019 Anduin Transactions Inc.

ThisBuild / organization := "design.anduin"
ThisBuild / version := "0.2"

ThisBuild / scalaVersion := Dependencies.Versions.scala

//noinspection SpellCheckingInspection
lazy val web = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    name := "web",
    scalacOptions += "-language:existentials",
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    libraryDependencies ++= List(
      Dependencies.Libraries.scalajsDom.value,
      Dependencies.Libraries.scalaJavaTime.value,
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
    scalacOptions += "-Yrangepos",
    libraryDependencies ++= List(
      Dependencies.Libraries.scalajsReact.core.value,
      Dependencies.Libraries.scalaReflect
    )
  )

//noinspection SpellCheckingInspection
lazy val docsSrc = project
  .dependsOn(docsMacros, web)
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    scalacOptions += "-Yrangepos",
    scalacOptions += "-language:existentials",
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    scalaJSUseMainModuleInitializer := true,
    webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
    webpackBundlingMode in fullOptJS := BundlingMode.LibraryOnly(),
    webpackCliVersion := Dependencies.Versions.webpackCli,
    webpack / version := Dependencies.Versions.webpack,
    startWebpackDevServer / version := Dependencies.Versions.webpackDevServer,
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

lazy val root = (project in file("."))
  .settings(name := "anduin-design")
  .aggregate(docsSrc)

