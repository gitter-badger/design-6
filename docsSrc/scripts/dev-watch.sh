PURPLE='\033[0;35m'
RESET='\033[0m'

echo -e "${PURPLE}docs/dev-watch 1/2${RESET} Verifying core/style..."
yarn run core-style

echo -e "${PURPLE}docs/dev-watch 2/2${RESET} Starting sbt watch mode..."
echo "~docsSrc/fastOptJS::webpack" | sbt
