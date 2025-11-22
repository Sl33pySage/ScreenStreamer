#include <stdio.h>
#include <android/log.h>
#include "streamer.h"



#define LOGI(...)__android_log_print(ANDROID_LOG_INFO, "STREAMER", __VA_ARGS__)


void streamer_on_frame(
  const unsigned char* frameData,
  int dataLength,
  int width,
  int height,
  int pixelStride,
  int rowStride
) {
  LOGI("Processing frame: %dx%d | data=%d bytes | ps=%d rs=%d",
       width, height, dataLength, pixelStride, rowStride);


// TODO: this is where the C code will:
  // - Convert RGBA to RGB or YUV if needed
  // - Feed frame to an encoder
  // - Send frame over the socket
  // - Or write frames to a test file
  // This is now a clean, expandable foundation
}
