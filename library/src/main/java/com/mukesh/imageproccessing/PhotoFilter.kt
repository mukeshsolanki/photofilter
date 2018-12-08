package com.mukesh.imageproccessing

import android.graphics.Bitmap
import android.media.effect.Effect
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import com.mukesh.imageproccessing.filters.AutoFix
import com.mukesh.imageproccessing.filters.Highlight
import com.mukesh.imageproccessing.filters.Brightness
import com.mukesh.imageproccessing.filters.Contrast
import com.mukesh.imageproccessing.filters.CrossProcess
import com.mukesh.imageproccessing.filters.Documentary
import com.mukesh.imageproccessing.filters.DuoTone
import com.mukesh.imageproccessing.filters.FillLight
import com.mukesh.imageproccessing.filters.Filter
import com.mukesh.imageproccessing.filters.FishEye
import com.mukesh.imageproccessing.filters.FlipHorizontally
import com.mukesh.imageproccessing.filters.FlipVertically
import com.mukesh.imageproccessing.filters.Grain
import com.mukesh.imageproccessing.filters.Grayscale
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
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.opengles.GL10

class PhotoFilter(
  var effectsView: GLSurfaceView,
  var onProcessingCompletionListener: OnProcessingCompletionListener
) : GLSurfaceView.Renderer {
  private var bitmap: Bitmap? = null
  private val textureRenderer = TextureRenderer()
  private var initialized = false
  private var effectContext: EffectContext? = null
  private var currentEffect: Filter = None()
  private val textures = IntArray(2)
  private var imageWidth: Int = 0
  private var imageHeight: Int = 0
  private var imageEffect: Effect? = null

  init {
    effectsView.setEGLContextClientVersion(2)
    effectsView.setRenderer(this)
    effectsView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
  }

  fun applyEffect(
    bitmap: Bitmap,
    effect: Filter
  ) {
    this.bitmap?.recycle()
    this.bitmap = bitmap
    initialized = false
    currentEffect = effect
    effectsView.requestRender()
  }

  override fun onSurfaceCreated(
    gl: GL10,
    config: EGLConfig
  ) {
  }

  override fun onSurfaceChanged(
    gl: GL10,
    width: Int,
    height: Int
  ) {
    textureRenderer.updateViewSize(width, height)
  }

  override fun onDrawFrame(gl: GL10) {
    if (!initialized) {
      effectContext = EffectContext.createWithCurrentGlContext()
      textureRenderer.init()
      loadTextures()
      initialized = true
    }
    if (currentEffect !is None) {
      initEffect()
      applyEffect()
    }
    renderResult()
  }

  private fun loadTextures() {
    if (bitmap != null) {
      GLES20.glGenTextures(2, textures, 0)
      imageWidth = bitmap?.width!!
      imageHeight = bitmap?.height!!
      textureRenderer.updateTextureSize(imageWidth, imageHeight)

      GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])
      GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

      GLToolbox.initTexParams()
    }
  }

  private fun initEffect() {
    val effectFactory = effectContext?.factory
    if (imageEffect != null) {
      imageEffect?.release()
    }
    when (currentEffect) {
      is None -> return
      is AutoFix -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_AUTOFIX)
        imageEffect?.setParameter("scale", (currentEffect as AutoFix).scale)
      }
      is Highlight -> (currentEffect as Highlight).let {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_BLACKWHITE)
        imageEffect?.setParameter("black", it.black)
        imageEffect?.setParameter("white", it.white)
      }
      is Brightness -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_BRIGHTNESS)
        imageEffect?.setParameter("brightness", (currentEffect as Brightness).brightness)
      }
      is Contrast -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_CONTRAST)
        imageEffect?.setParameter("contrast", (currentEffect as Contrast).contrast)
      }
      is CrossProcess -> imageEffect =
          effectFactory?.createEffect(EffectFactory.EFFECT_CROSSPROCESS)
      is Documentary -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_DOCUMENTARY)
      is DuoTone -> (currentEffect as DuoTone).let {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_DUOTONE)
        imageEffect?.setParameter("first_color", it.firstColor)
        imageEffect?.setParameter("second_color", it.secondColor)
      }
      is FillLight -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FILLLIGHT)
        imageEffect?.setParameter("strength", (currentEffect as FillLight).strength)
      }
      is FishEye -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FISHEYE)
        imageEffect?.setParameter("scale", (currentEffect as FishEye).scale)
      }
      is FlipVertically -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FLIP)
        imageEffect?.setParameter("vertical", true)
      }
      is FlipHorizontally -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FLIP)
        imageEffect?.setParameter("horizontal", true)
      }
      is Grain -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_GRAIN)
        imageEffect?.setParameter("strength", (currentEffect as Grain).strength)
      }
      is Grayscale -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_GRAYSCALE)
      is Lomoish -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_LOMOISH)
      is Negative -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_NEGATIVE)
      is Posterize -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_POSTERIZE)
      is Rotate -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_ROTATE)
        imageEffect?.setParameter("angle", (currentEffect as Rotate).angle)
      }
      is Saturate -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_SATURATE)
        imageEffect?.setParameter("scale", (currentEffect as Saturate).scale)
      }
      is Sepia -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_SEPIA)
      is Sharpen -> imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_SHARPEN)
      is Temperature -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_TEMPERATURE)
        imageEffect?.setParameter("scale", (currentEffect as Temperature).scale)
      }
      is Tint -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_TINT)
        imageEffect?.setParameter("tint", (currentEffect as Tint).tint)
      }
      is Vignette -> {
        imageEffect = effectFactory?.createEffect(EffectFactory.EFFECT_VIGNETTE)
        imageEffect?.setParameter("scale", (currentEffect as Vignette).scale)
      }
      else -> return
    }
  }

  private fun applyEffect() {
    imageEffect?.apply(textures[0], imageWidth, imageHeight, textures[1])
  }

  private fun renderResult() {
    if (currentEffect is None) {
      textureRenderer.renderTexture(textures[0])
    } else {
      textureRenderer.renderTexture(textures[1])
    }
    captureBitmap()
  }

  private fun captureBitmap() {
    val egl = EGLContext.getEGL() as EGL10
    val gl = egl.eglGetCurrentContext().gl as GL10
    val resultBitmap = createBitmapFromGLSurface(gl)!!
    onProcessingCompletionListener.onProcessingComplete(resultBitmap)
  }

  private fun createBitmapFromGLSurface(
    gl: GL10
  ): Bitmap? {
    val screenshotSize = effectsView.width * effectsView.height
    val bb = ByteBuffer.allocateDirect(screenshotSize * 4)
    bb.order(ByteOrder.nativeOrder())
    gl.glReadPixels(
        0, 0, effectsView.width, effectsView.height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb
    )
    val pixelsBuffer = IntArray(screenshotSize)
    bb.asIntBuffer()
        .get(pixelsBuffer)

    for (i in 0 until screenshotSize) {
      pixelsBuffer[i] = ((pixelsBuffer[i] and -0xff0100)) or
          ((pixelsBuffer[i] and 0x000000ff) shl 16) or ((pixelsBuffer[i] and 0x00ff0000) shr 16)
    }

    val bitmap = Bitmap.createBitmap(effectsView.width, effectsView.height, Bitmap.Config.ARGB_8888)
    bitmap.setPixels(
        pixelsBuffer, screenshotSize - effectsView.width, -effectsView.width, 0, 0,
        effectsView.width, effectsView.height
    )
    return bitmap
  }
}