package com.example.streamer


import android.app.*
import android.content.Intent
import android.graphics.PixelFormat
import android.media.ImageReader
import android.media.projection.MediaProjection
import android.media.projection.MediaProjectionManager
import android.os.Ibinder
import android.util.Log


// This is where we sewt up MediaProjection + VirtualDisplay and feed frames to your C code.
// This gives you:
// *  Raw RGBA image frames
// *  Sent directly to your C code
// *  With complete stride/pixel layout info
// This is an excellent starting point.
class ScreenCaptureService : Service() {
  override fun onCreate() {
    super.onCreate()
    startForegroundService()
  }


  private fun startForegroundService() {
    val channelId = "screen_capture_channel"


    val channel = NotificationChannel(
        channelId,
        "Screen Capture",
        NotificationManager.IMPORTANCE_LOW
        )
    val manager = getSystemservice(NotificationManager::class.java)
    manager.createNotificationChannel(channel)


    val notif = Notification.Builder(this, channelId)
        .setContentTitle("Screen Capture running")
        .setSmallIcon(android.R.drawable.ic_media_play)
        .build()


    startForeground(1, notif)
  }




  private var projection: MediaProjection? = null


  companion object {
    init {
      System.loadLibrary.("native-lib")
    }
  }


  external fun onFrameAvailable(
    width: Int,
    height: Int,
    pixelStride: Int,
    rowStride: Int,
    buffer: ByteArray
  )


  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val code = intent?.getIntExtra("resultCode", Activity.RESULT_CANCELED) ?: return START_NOT_STICKY
    val data = intent.getParcelableExtra<Intent>("data") ?: return START_NOT_STICKY


    val projectionManager = getSystemService(MediaProjectionManager::class.java)
    projection = projectionManager.getMediaProjection(code, data)


    startProjection()
    

    return START_STICKY
  }


  private fun startProjection() {
    val width = 720
    val height = 1280
    val dpi = resources.displayMetrics.densityDpi


    val imageReader = imageReader.newInstance(width, height, PixelFormat.RGBA_8888, 2)


    projection?.createVirtualDisplay(
    "ScreenCapture",
  width, height, dpi,
0,
imageReader.surface,
null,
null
)


imageReader.setOnImageAvailableListener({ reader ->
  val image = reader.acquireLatestImage() ?: return@setOnImageAvailableListener


  val plane = image.planes[0]
  val buffer = plane.buffer
  val pixelStride = plane.pixelsStride
  val rowStride = plane.rowStride


  val data = ByteArray(buffer.remaining())
  buffer.get(data)

  // JNI call to the C code
  onFrameAvailable(width, height, pixelStride, rowStride, data)


  image.close()
}, null)
}


override fun onBind(intent: Intent?): IBinder? = null
}
