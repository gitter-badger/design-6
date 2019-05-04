#!/usr/bin/env bash

PURPLE='\033[0;35m'
RESET='\033[0m'

PROJECT_PATH=$(dirname "$0")/..
PROJECT_CSS_PATH=${PROJECT_PATH}/src/main/resources/stylesheets

# CSS
echo -e "${PURPLE}1/2${RESET} Compiling CSS..."
npx postcss ${PROJECT_CSS_PATH}/main \
  --config ${PROJECT_CSS_PATH}/postcss.config.js \
  --output ${PROJECT_PATH}/target/scala-2.12/scalajs-bundler/main/docssrc-fastopt.css

# JS
#echo -e "${PURPLE}2/2${RESET} Compiling Starting sbt shell..."
#echo "~fastOptJS::webpack" | sbt