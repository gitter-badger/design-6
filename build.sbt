ThisBuild / name := "Anduin Design"
ThisBuild / organization := "design.anduin"
ThisBuild / version := "0.2"
ThisBuild / scalaVersion := "2.12.8"

//noinspection SpellCheckingInspection
lazy val core = project
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.5",
      "com.github.japgolly.scalajs-react" %%% "core" % "1.4.0"
    ),
    Compile / npmDependencies ++= Seq(
      "downshift" -> "3.2.7",
      "focus-visible" -> "4.1.5",
      "popper.js" -> "1.14.7",
      "react" -> "16.7.0",
      "react-dom" -> "16.7.0",
      "react-text-mask" -> "5.4.3",
      "react-truncate-markup" -> "3.0.0",
      "react-virtualized" -> "9.21.0",
      "text-mask-addons" -> "3.8.0"
    )
  )

//noinspection SpellCheckingInspection
lazy val docsMacros = project
  .enablePlugins(ScalaJSPlugin)
  .settings(
    scalacOptions += "-P:scalajs:sjsDefinedByDefault -Yrangepos",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
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
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.5",
      "com.github.japgolly.scalajs-react" %%% "extra" % "1.4.0"
    ),
    Compile / npmDependencies ++= Seq(
      "highlight.js" -> "9.15.6",
      "marked" -> "0.6.2"
    )
  )

lazy val root = (project in file(".")).aggregate(docsSrc)
