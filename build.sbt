name := "anduin-design"

lazy val commonSettings = Seq(
  organization := "co.anduin",
  version := "0.1",
  scalaVersion := "2.12.6",
  libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "1.4.0",
  scalacOptions += "-Yrangepos"
)

lazy val mcro = (project in file("mcro"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    commonSettings,
    libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
  )

lazy val core = (project in file("core"))
  .dependsOn(mcro)
  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
  .settings(
    commonSettings,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    scalaJSUseMainModuleInitializer := true,
    // Temporarily give up on scalajs-split :(
    // scalaJSLinkerConfig ~= {
    //   _.withESFeatures(_.withUseECMAScript2015(true))
    // },
    webpackBundlingMode in fastOptJS := BundlingMode.LibraryOnly(),
    webpackBundlingMode in fullOptJS := BundlingMode.Application,
    version in webpack := "4.18.0",
    version in startWebpackDevServer := "3.1.4",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.5" % Test,
      "org.scala-js" %%% "scalajs-dom" % "0.9.5",
      "com.github.japgolly.scalajs-react" %%% "extra" % "1.4.0"
    ),
    // https://github.com/webjars/webjars/issues/1789
    dependencyOverrides += "org.webjars.npm" % "js-tokens" % "3.0.2",
    npmDependencies in Compile ++= Seq(
      "react" -> "16.7.0",
      "react-dom" -> "16.7.0",
      "react-truncate-markup" -> "3.0.0",
      "react-virtualized" -> "9.21.0",
      "react-text-mask" -> "5.4.3",
      "text-mask-addons" -> "3.8.0",
      "popper.js" -> "1.14.7",
      "marked" -> "0.3.19",
      "focus-visible" -> "4.1.5",
      "downshift" -> "3.2.7"
    )
  )

lazy val root = (project in file(".")).aggregate(core)
