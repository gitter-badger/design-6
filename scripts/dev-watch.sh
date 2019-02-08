#!/usr/bin/env bash

. ./scripts/_prepare.sh

PURPLE='\033[0;35m'
NC='\033[0m'

# CSS
echo -e "${PURPLE}1/2${NC} Compiling CSS..."
postcss ${core_css_main} --config ${core_css_config} --output ${core_target}/core-fastopt.css

# JS
echo -e "${PURPLE}2/2${NC} Compiling Starting sbt shell..."
echo "~fastOptJS::webpack" | sbt