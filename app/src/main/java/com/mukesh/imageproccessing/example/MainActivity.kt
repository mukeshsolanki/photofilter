package com.mukesh.imageproccessing.example

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.imageproccessing.OnProcessingCompletionListener
import com.mukesh.imageproccessing.PhotoFilter
import com.mukesh.imageproccessing.filters.AutoFix
import com.mukesh.imageproccessing.filters.Brightness
import com.mukesh.imageproccessing.filters.Contrast
import com.mukesh.imageproccessing.filters.CrossProcess
import com.mukesh.imageproccessing.filters.Documentary
import com.mukesh.imageproccessing.filters.DuoTone
import com.mukesh.imageproccessing.filters.FillLight
import com.mukesh.imageproccessing.filters.FishEye
import com.mukesh.imageproccessing.filters.FlipHorizontally
import com.mukesh.imageproccessing.filters.FlipVertically
import com.mukesh.imageproccessing.filters.Grain
import com.mukesh.imageproccessing.filters.Grayscale
import com.mukesh.imageproccessing.filters.Highlight
import com.mukesh.imageproccessing.filters.Lomoish
import com.mukesh.imageproccessing.filters.Negative
import com.mukesh.imageproccessing.filters.None
import com.mukesh.imageproccessing.filters.Posterize
import com.mukesh.imageproccessing.filters.Rotate
import com.mukesh.imageproccessing.filters.Saturate
import com.mukesh.imageproccessing.filters.Sepia
import com.mukesh.imageproccessing.filters.Sharpen
import com.mukesh.imageproccessing.filters.Temperature
import com.mukesh.imageproccessing.filters.Tint
import com.mukesh.imageproccessing.filters.Vignette
import kotlinx.android.synthetic.main.activity_main.effectView
import kotlinx.android.synthetic.main.activity_main.effectsRecyclerView
import kotlinx.android.synthetic.main.activity_main.saveButton
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.Date

class MainActivity : AppCompatActivity(), OnProcessingCompletionListener, OnFilterClickListener {
  private val REQUEST_PERMISSION: Int = 10001
  private lateinit var result: Bitmap
  private var photoFilter: PhotoFilter? = null

  override fun onProcessingComplete(bitmap: Bitmap) {
    // Do anything with the bitmap save it or add another effect to it
    result = bitmap
  }

  override fun onFilterClicked(effectsThumbnail: EffectsThumbnail) {
    photoFilter?.applyEffect(
        BitmapFactory.decodeResource(resources, R.drawable.skull), effectsThumbnail.filter
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initialize()
  }

  private fun initialize() {
    photoFilter = PhotoFilter(effectView, this)
    photoFilter?.applyEffect(BitmapFactory.decodeResource(resources, R.drawable.skull), None())
    effectsRecyclerView.layoutManager =
        LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
    effectsRecyclerView.setHasFixedSize(true)
    effectsRecyclerView.adapter = EffectsAdapter(getItems(), this@MainActivity)
    saveButton.setOnClickListener {
      checkPermissionAndSaveImage()
    }
  }

  private fun checkPermissionAndSaveImage() {
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
      if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
          != PackageManager.PERMISSION_GRANTED
      ) {
        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
      } else {
        saveImage()
      }
    } else {
      saveImage()
    }
  }

  override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
  ) {
    when (requestCode) {
      REQUEST_PERMISSION -> {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          saveImage()
        } else {
          Toast.makeText(this@MainActivity, "Permission Denied", Toast.LENGTH_SHORT)
              .show()
        }
        return
      }
    }
  }

  private fun saveImage() {
    val path = Environment.getExternalStorageDirectory()
        .toString()
    val fOut: OutputStream?
    val fileName = Date().time
    val file = File(path, "$fileName.jpg")
    fOut = FileOutputStream(file)
    result.compress(Bitmap.CompressFormat.JPEG, 85, fOut)
    fOut.flush()
    fOut.close()
    MediaStore.Images.Media.insertImage(contentResolver, file.absolutePath, file.name, file.name)
    Toast.makeText(this@MainActivity, "ImageSaved", Toast.LENGTH_SHORT)
        .show()
  }

  private fun getItems(): MutableList<EffectsThumbnail> {
    return mutableListOf(
        EffectsThumbnail("None", None()),
        EffectsThumbnail("AutoFix", AutoFix()),
        EffectsThumbnail("Highlight", Highlight()),
        EffectsThumbnail("Brightness", Brightness()),
        EffectsThumbnail("Contrast", Contrast()),
        EffectsThumbnail("Cross Process", CrossProcess()),
        EffectsThumbnail("Documentary", Documentary()),
        EffectsThumbnail("Duo Tone", DuoTone()),
        EffectsThumbnail("Fill Light", FillLight()),
        EffectsThumbnail("Fisheye", FishEye()),
        EffectsThumbnail("Flip Horizontally", FlipHorizontally()),
        EffectsThumbnail("Flip Vertically", FlipVertically()),
        EffectsThumbnail("Grain", Grain()),
        EffectsThumbnail("Grayscale", Grayscale()),
        EffectsThumbnail("Lomoish", Lomoish()),
        EffectsThumbnail("Negative", Negative()),
        EffectsThumbnail("Posterize", Posterize()),
        EffectsThumbnail("Rotate", Rotate()),
        EffectsThumbnail("Saturate", Saturate()),
        EffectsThumbnail("Sepia", Sepia()),
        EffectsThumbnail("Sharpen", Sharpen()),
        EffectsThumbnail("Temperature", Temperature()),
        EffectsThumbnail("Tint", Tint()),
        EffectsThumbnail("Vignette", Vignette())
    )
  }
}