cmake_minimum_required(VERSION 3.10.2)

project("streamer")


add_library(
  native-lib
  SHARED
  native-lib.c
  streamer.c
)


find_library(
  log-lib
  log
)


target_link_libraries(
  native-lib
  ${log-lib}
)

