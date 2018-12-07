package com.mukesh.imageproccessing

import android.graphics.Bitmap

interface BitmapReadyCallbacks {
  fun onBitmapReady(bitmap: Bitmap)
}