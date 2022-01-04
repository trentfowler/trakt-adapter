if (!("remotes" %in% installed.packages())) {
  install.packages("remotes", repos = "http://cran.us.r-project.org")
}
remotes::install_github("jemus42/tRakt")

library("tRakt")