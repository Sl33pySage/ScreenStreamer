package com.example.streamer

import android.app.Activity
import android.content.Intent
import android.media.projection.MediaProjectionManager
import android.os.Bundle
import android.widjet.Button

// Requests permission and starts a foreground service that will handle capture.
class MainActivity : Activity () {
  private lateinit var projectionManager: MediaProjectionManager
  private val REQUEST_MEDIA_PROJECTION = 1001

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    projectionManager = getSystemService(MediaProjectionManager::class.java)

    val button = Button(this).apply {
      text = "Start Screen Streaming"
      setOnClickListener {
        startActivityForResult(
          projectionManager.createScreenCaptureIntent(),
          REQUEST_MEDIA_PROJECTION
        )
      }
    }
    setContentView(button)
  }
  
  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_MEDIA_PROJECTION && resultCode == RESULT_OK && data != null) {
      val intent = Intent(this, ScreenCaptureService::class.java).apply {
        putExtra("resultCode", resultCode)
        putExtra("data", data)
      }
      startService(intent)
    }
    super.onActivityResult(requestCode, resultCode, data)
  }
}

