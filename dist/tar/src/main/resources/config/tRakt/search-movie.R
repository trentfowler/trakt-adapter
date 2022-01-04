#!/usr/bin/env Rscript
args = commandArgs(trailingOnly=TRUE)
library(dplyr) # for convenience
library(tRakt)
movie_info <- search_query(args[1], type = "movie")
glimpse(movie_info)
