#!/usr/bin/env bash

PURPLE='\033[0;36m'
RESET='\033[0m'

# Ensure we are at here
cd $(dirname $0)

echo -e "${PURPLE}web/style/build 1/2${RESET} Verifying dependencies..."
yarn | sed -e 's/^/    /;'

echo -e "${PURPLE}web/style/build 2/2${RESET} Running PostCSS..."
node build.js | sed -e 's/^/    /;'
