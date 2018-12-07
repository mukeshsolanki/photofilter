package com.mukesh.imageproccessing.example

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

class MainActivity : AppCompatActivity(), OnProcessingCompletionListener, OnFilterClickListener {

  private var photoFilter: PhotoFilter? = null

  override fun onProcessingComplete(bitmap: Bitmap) {
    // Do anything with the bitmap save it or add another effect to it
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