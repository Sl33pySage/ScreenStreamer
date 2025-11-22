#ifndef STREAMER_H
#define STREAMER_H


void streamer_on_frame(
  const unsigned char* frameData,
  int dataLength,
  int width,
  int height,
  int pixelStride,
  int rowStride
);

#endif
