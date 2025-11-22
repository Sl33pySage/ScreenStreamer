#include <jni.h>
#include <android/log.h>
#include "streamer.h"


#define LOGI(...)__android_log_print(ANDROID_LOG_INFO, "NATIVE", __VA_ARGS__)

JNIEXPORT void JNICALL
Java_com_example_streamer_ScreenCaptureService_onFrameAvailable(
  JNIEnv *env, jobject thiz,
  jint width, jint height,
  jint pixelStride, jint rowStride,
  jbyteArray buffer) {


  jbyte* data = (*env)->GetByteArrayelement(env, buffer, NULL);
  jsize len = (*env)->GetArrayLength(buffer);


  // For now, just log
  LOGI("Frame received: %dx%d, pixelStride=%d, rowStride=%d, size=%d",
       width, height, pixelStride, rowStride, len);
  
  LOGI("Frame received: %dx%d, pixelStride=%d, rowStride=%d, size=%d",
       width, height, pixelStride, rowStride, len);


  // TODO: process raw RGBA frame here
  // Delegate actual processing to pure C
  // streamer_on_frame((unsigned char*)data, len, height, pixelStride, rowStride);

  (*env)->ReleaseByteArrayElements(buffer, data, JNI_ABORT);
}

/*  This is your main C entry point.
 *  You now officially have:
 *  Android screen frames arriving in C
 *  Full resolution, color format, and stride 
 *  A clean path to build a streaming pipeline
 *  */
