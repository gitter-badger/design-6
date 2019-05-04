#!/usr/bin/env bash

. ./scripts/_prepare.sh

GREEN='\033[0;32m'
DIM='\033[0;2m'
PURPLE='\033[0;35m'
NC='\033[0m'

# Clean
echo -e "${PURPLE}1/5${NC} Clearing previous generation..."
rm -rf ${docs}
mkdir ${docs}

# Resources
echo -e "${PURPLE}2/5${NC} Copying resources (public content)..."
cp -R ${core_res}/public/. ${docs}/

# CSS
echo -e "${PURPLE}3/5${NC} Compiling CSS..."
postcss ${core_css_main} --config ${core_css_config} --output ${docs}/app.css

# JS
echo -e "${PURPLE}4/5${NC} Compiling JS (sbt)..."
sbt fullOptJS::webpack

echo -e "${PURPLE}5/5${NC} Copying JS..."
cp ${core_target}/core-opt-bundle.js ${docs}/app.js

# Temporarily give up scalajs-split
# echo -e "${PURPLE}5/5${NC} Optimizing JS (scalajs-split)..."
# npx scalajs-split ./core
# cp -r ${core_target}/scalajs-split/scripts ${docs}/scripts

echo -e "${GREEN}success${NC} Built at ./docs!"
