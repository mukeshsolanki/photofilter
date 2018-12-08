package com.mukesh.imageproccessing.filters

import android.graphics.Color

data class DuoTone(
  var firstColor: Int = Color.YELLOW,
  var secondColor: Int = Color.BLACK
) : Filter()