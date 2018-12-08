package com.mukesh.imageproccessing

import android.graphics.Bitmap

interface OnProcessingCompletionListener {
  fun onProcessingComplete(bitmap: Bitmap)
}