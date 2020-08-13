package com.mukesh.imageproccessing.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mukesh.imageproccessing.example.EffectsAdapter.EffectsViewHolder
import kotlinx.android.synthetic.main.effect_thumbnail.view.effectName
import kotlinx.android.synthetic.main.effect_thumbnail.view.effectsRootView

class EffectsAdapter(
  private val items: MutableList<EffectsThumbnail>,
  private val listener: OnFilterClickListener
) :
    RecyclerView.Adapter<EffectsViewHolder>() {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): EffectsViewHolder {
    val hashTagItemLayoutView = LayoutInflater.from(parent.context)
        .inflate(
            R.layout.effect_thumbnail, parent, false
        )
    return EffectsViewHolder(hashTagItemLayoutView)
  }

  override fun onBindViewHolder(
    holder: EffectsViewHolder,
    position: Int
  ) {
    val thumbnail = items[position]
    holder.effectTitle.text = thumbnail.title
    holder.effectRootView.setOnClickListener {
      listener.onFilterClicked(thumbnail)
    }
  }

  override fun getItemCount(): Int = items.size

  class EffectsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val effectTitle: TextView = view.effectName
    val effectRootView: LinearLayout = view.effectsRootView
  }
}
