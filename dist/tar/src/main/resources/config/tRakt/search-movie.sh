#!/bin/bash
DIR="/maven/trakt-adapter/config/tRakt"
# Search string needs to be quoted
Rscript "${DIR}/search-movie.R" "$1" 2>/dev/null | grep slug | awk '{ print $4 }' | sed "s/\"//g"
