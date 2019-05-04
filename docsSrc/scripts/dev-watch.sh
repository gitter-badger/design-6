PURPLE='\033[0;35m'
RESET='\033[0m'

echo -e "${PURPLE}docs-dev-watch 1/2${RESET} verify core/style..."
yarn run core-style

echo -e "${PURPLE}docs-dev-watch 2/2${RESET} watch docsSrc..."
echo "~docsSrc/fastOptJS::webpack" | sbt
