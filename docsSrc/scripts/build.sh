#!/usr/bin/env bash

PURPLE='\033[0;35m'
RESET='\033[0m'

ROOT=$(dirname $0)/../../

echo -e "${PURPLE}docs/build 1/4${RESET} Clearing docs..."
rm -rf ${ROOT}/docs
mkdir ${ROOT}/docs

echo -e "${PURPLE}docs/build 2/4${RESET} Building docs/core-style.css..."
yarn run core-style
cp    ${ROOT}/core/target/style.css       ${ROOT}/docs/core-style.css

echo -e "${PURPLE}docs/build 3/4${RESET} Building docs/app.js..."
sbt docsSrc/fullOptJS::webpack
JS_OUTPUT=${ROOT}/docsSrc/target/scala-2.12/scalajs-bundler/main
cp    ${JS_OUTPUT}/docssrc-opt.js         ${ROOT}/docs/app.js
cp    ${JS_OUTPUT}/docssrc-opt-library.js ${ROOT}/docs/app-library.js
cp    ${JS_OUTPUT}/docssrc-opt-loader.js  ${ROOT}/docs/app-loader.js

echo -e "${PURPLE}docs/build 4/4${RESET} Copying public assets to docs..."
cp -R ${ROOT}/docsSrc/public/.            ${ROOT}/docs

echo -e "${PURPLE}docs/build success${RESET} Built at ./docs!"
