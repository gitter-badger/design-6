#!/usr/bin/env bash

GREEN='\033[0;32m'
DIM='\033[0;2m'
PURPLE='\033[0;35m'
NC='\033[0m'

. ./scripts/_prepare.sh

# === Find out where is stargazer
sgz_src_config=sgz_src.txt
if [[ ! -f ${sgz_src_config} ]]; then
  echo "Please enter the absolute path to stargazer on your local:"
  read sgz_src
  echo ${sgz_src} > ${sgz_src_config}
else
  read -r sgz_src < ${sgz_src_config}
  echo "Found stargazer at ${sgz_src}."
fi

# === Link Config
echo -e "${PURPLE}1/3${NC} Linking config..."

echo -e "${DIM}    - .scalafmt.conf"
ln -sf ${sgz_src}/.scalafmt.conf ./

# === Link CSS
echo -e "${PURPLE}2/3${NC} Linking CSS..."
sgz_css=${sgz_src}/gondor/webResources/src/main/assets/stylesheets

echo -e "${DIM}    - tailwind.js"
rm -f ${core_css}/tailwind.js
ln -s ${sgz_src}/apps/gondor/gondorAppClient/webpack/tailwind.js ${core_css}/

echo -e "${DIM}    - utilities-custom"
rm -rf ${core_css}/utilities-custom
ln -s ${sgz_css}/utilities-custom ${core_css}/

echo -e "${DIM}    - _base.scss"
rm -f ${core_css}/_base.scss
ln -s ${sgz_css}/base/_base.scss ${core_css}/

# === Link JS
echo -e "${PURPLE}3/3${NC} Linking JS (Scala, actually)..."
sgz_js=${sgz_src}/platform/stargazerJs/src/main/scala/anduin
sgz_js_dep=${sgz_src}/platform/stargazerJSDependency/src/main/scala/anduin/scalajs

# Style
echo -e "${DIM}    - anduin.style"
rm -rf ${core_js}/style
ln -s ${sgz_js}/style ${core_js}/

# Facades
echo -e "${DIM}    - anduin.scalajs"
rm -rf ${core_js}/scalajs
. ./scripts/link/link_paths.sh \
  ${sgz_js_dep} ${core_js}/scalajs facade_paths

# Components
echo -e "${DIM}    - anduin.component"
rm -rf ${core_js}/component
. ./scripts/link/link_paths.sh \
  ${sgz_js}/component ${core_js}/component component_paths

echo -e "${GREEN}success${NC} Linked with stargazer!"
