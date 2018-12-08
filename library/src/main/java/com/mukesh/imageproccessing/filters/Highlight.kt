package com.mukesh.imageproccessing.filters

data class Highlight(
  var black: Float = .1f,
  var white: Float = .7f
) : Filter()