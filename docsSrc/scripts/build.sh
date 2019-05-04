#!/usr/bin/env bash

PURPLE='\033[0;35m'
RESET='\033[0m'

echo -e "${PURPLE}docs-build 1/4${RESET} Clean docs..."
# rm -rf ${docs}
# mkdir ${docs}

echo -e "${PURPLE}docs-build 2/4${RESET} Build docs/core-style.css..."
yarn run core-style

echo -e "${PURPLE}docs-build 3/4${RESET} Build docs/app.js..."
sbt docsSrc/fullOptJS::webpack
# cp ${core_target}/core-opt-bundle.js ${docs}/app.js

echo -e "${PURPLE}docs-build 4/4${RESET} Copy public assets to docs..."
# cp -R ${core_res}/public/. ${docs}/

echo -e "${PURPLE}docs-build success${RESET} Built at ./docs!"
