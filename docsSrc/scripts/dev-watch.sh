#!/usr/bin/env bash

PURPLE='\033[0;35m'
RESET='\033[0m'

# Ensure we are at root
cd $(dirname $0)/../..

echo -e "${PURPLE}docs/dev-watch 1/2${RESET} Verifying web/style build..."
yarn run web-style-build | sed -e 's/^/    /;'

echo -e "${PURPLE}docs/dev-watch 2/2${RESET} Starting sbt watch mode..."
echo "~docsSrc/fastOptJS::webpack" | sbt | sed -e 's/^/    /;'
