#!/usr/bin/env bash

PURPLE='\033[0;35m'
GREEN='\033[0;32m'
RESET='\033[0m'

# Ensure we are at root
cd $(dirname $0)/../..

echo -e "${PURPLE}docs/build 1/4${RESET} Clearing docs..."
rm -rf ./docs && mkdir ./docs

echo -e "${PURPLE}docs/build 2/4${RESET} Building docs/web.css..."
yarn run web-style-build | sed -e 's/^/    /;'
cp    ./web/target/style.css             ./docs/web.css

echo -e "${PURPLE}docs/build 3/4${RESET} Building docs/app.js..."
sbt docsSrc/fullOptJS::webpack | sed -e "s/^/    /;"
JS_OUTPUT=./docsSrc/target/scala-2.12/scalajs-bundler/main
cp    ${JS_OUTPUT}/docssrc-opt.js         ./docs/app.js
cp    ${JS_OUTPUT}/docssrc-opt-library.js ./docs/app-library.js
cp    ${JS_OUTPUT}/docssrc-opt-loader.js  ./docs/app-loader.js

echo -e "${PURPLE}docs/build 4/4${RESET} Copying public assets to docs..."
cp -R ./docsSrc/public/.                  ./docs

echo -e "    ${GREEN}success${RESET} Built at <root>/docs!"
