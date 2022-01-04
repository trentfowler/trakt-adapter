#!/bin/bash
DIR="/maven/trakt-adapter/config/tRakt"
# Search string needs to be quoted
Rscript "${DIR}/search-movie.R" "$1" 2>/dev/null | grep title | awk -F "\"" '{ print $2 }' | sed "s/\"//g"
