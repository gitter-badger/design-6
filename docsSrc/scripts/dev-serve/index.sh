#!/usr/bin/env bash

PURPLE='\033[0;35m'
RESET='\033[0m'

# Ensure we are at here
cd $(dirname $0)

echo -e "${PURPLE}docs/dev-serve 1/2${RESET} Verifying dependencies..."
yarn | sed -e 's/^/    /;'

echo -e "${PURPLE}docs/dev-serve 2/2${RESET} Starting local web server..."
node server.js | sed -e 's/^/    /;'
