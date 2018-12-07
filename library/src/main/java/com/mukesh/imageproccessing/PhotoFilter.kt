package com.mukesh.imageproccessing

import android.graphics.Bitmap
import android.graphics.Color
import android.media.effect.Effect
import android.media.effect.EffectContext
import android.media.effect.EffectFactory
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import com.mukesh.imageproccessing.FILTERS.AUTO_FIX
import com.mukesh.imageproccessing.FILTERS.BLACK_AND_WHITE
import com.mukesh.imageproccessing.FILTERS.BRIGHTNESS
import com.mukesh.imageproccessing.FILTERS.CONTRAST
import com.mukesh.imageproccessing.FILTERS.CROSS_PROCESS
import com.mukesh.imageproccessing.FILTERS.DOCUMENTARY
import com.mukesh.imageproccessing.FILTERS.DUO_TONE
import com.mukesh.imageproccessing.FILTERS.FILL_LIGHT
import com.mukesh.imageproccessing.FILTERS.FISH_EYE
import com.mukesh.imageproccessing.FILTERS.FLIP_HORIZONTALLY
import com.mukesh.imageproccessing.FILTERS.FLIP_VERTICALLY
import com.mukesh.imageproccessing.FILTERS.GRAIN
import com.mukesh.imageproccessing.FILTERS.GRAYSCALE
import com.mukesh.imageproccessing.FILTERS.LOMOISH
import com.mukesh.imageproccessing.FILTERS.NEGATIVE
import com.mukesh.imageproccessing.FILTERS.NONE
import com.mukesh.imageproccessing.FILTERS.POSTERIZE
import com.mukesh.imageproccessing.FILTERS.ROTATE
import com.mukesh.imageproccessing.FILTERS.SATURATE
import com.mukesh.imageproccessing.FILTERS.SEPIA
import com.mukesh.imageproccessing.FILTERS.SHARPEN
import com.mukesh.imageproccessing.FILTERS.TEMPERATURE
import com.mukesh.imageproccessing.FILTERS.TINT
import com.mukesh.imageproccessing.FILTERS.VIGNETTE
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
  private val mTexRenderer = TextureRenderer()
  private var mInitialized = false
  private var mEffectContext: EffectContext? = null
  private var mCurrentEffect: FILTERS = NONE
  private val mTextures = IntArray(2)
  private var mImageWidth: Int = 0
  private var mImageHeight: Int = 0
  private var mEffect: Effect? = null

  init {
    effectsView.setEGLContextClientVersion(2)
    effectsView.setRenderer(this)
    effectsView.renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
  }

  fun applyEffect(
    bitmap: Bitmap,
    effect: FILTERS
  ) {
    this.bitmap?.recycle()
    this.bitmap = bitmap
    mInitialized = false
    mCurrentEffect = effect
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
    mTexRenderer.updateViewSize(width, height)
  }

  override fun onDrawFrame(gl: GL10) {
    if (!mInitialized) {
      mEffectContext = EffectContext.createWithCurrentGlContext()
      mTexRenderer.init()
      loadTextures()
      mInitialized = true
    }
    if (mCurrentEffect != NONE) {
      initEffect()
      applyEffect()
    }
    renderResult()
  }

  private fun loadTextures() {
    if (bitmap != null) {
      GLES20.glGenTextures(2, mTextures, 0)
      mImageWidth = bitmap?.width!!
      mImageHeight = bitmap?.height!!
      mTexRenderer.updateTextureSize(mImageWidth, mImageHeight)

      GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextures[0])
      GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

      GLToolbox.initTexParams()
    }
  }

  private fun initEffect() {
    val effectFactory = mEffectContext?.factory
    if (mEffect != null) {
      mEffect?.release()
    }
    when (mCurrentEffect) {
      NONE -> return
      AUTO_FIX -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_AUTOFIX)
        mEffect?.setParameter("scale", 0.5f)
      }
      BLACK_AND_WHITE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_BLACKWHITE)
        mEffect?.setParameter("black", .1f)
        mEffect?.setParameter("white", .7f)
      }
      BRIGHTNESS -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_BRIGHTNESS)
        mEffect?.setParameter("brightness", 2.0f)
      }
      CONTRAST -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_CONTRAST)
        mEffect?.setParameter("contrast", 1.4f)
      }
      CROSS_PROCESS -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_CROSSPROCESS)
      DOCUMENTARY -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_DOCUMENTARY)
      DUO_TONE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_DUOTONE)
        mEffect?.setParameter("first_color", Color.YELLOW)
        mEffect?.setParameter("second_color", Color.DKGRAY)
      }
      FILL_LIGHT -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FILLLIGHT)
        mEffect?.setParameter("strength", .8f)
      }
      FISH_EYE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FISHEYE)
        mEffect?.setParameter("scale", .5f)
      }
      FLIP_VERTICALLY -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FLIP)
        mEffect?.setParameter("vertical", true)
      }
      FLIP_HORIZONTALLY -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_FLIP)
        mEffect?.setParameter("horizontal", true)
      }
      GRAIN -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_GRAIN)
        mEffect?.setParameter("strength", 1.0f)
      }
      GRAYSCALE -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_GRAYSCALE)
      LOMOISH -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_LOMOISH)
      NEGATIVE -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_NEGATIVE)
      POSTERIZE -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_POSTERIZE)
      ROTATE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_ROTATE)
        mEffect?.setParameter("angle", 180)
      }
      SATURATE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_SATURATE)
        mEffect?.setParameter("scale", .5f)
      }
      SEPIA -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_SEPIA)
      SHARPEN -> mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_SHARPEN)
      TEMPERATURE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_TEMPERATURE)
        mEffect?.setParameter("scale", .9f)
      }
      TINT -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_TINT)
        mEffect?.setParameter("tint", Color.MAGENTA)
      }
      VIGNETTE -> {
        mEffect = effectFactory?.createEffect(EffectFactory.EFFECT_VIGNETTE)
        mEffect?.setParameter("scale", .5f)
      }
    }
  }

  private fun applyEffect() {
    mEffect?.apply(mTextures[0], mImageWidth, mImageHeight, mTextures[1])
  }

  private fun renderResult() {
    if (mCurrentEffect == NONE) {
      mTexRenderer.renderTexture(mTextures[0])
    } else {
      mTexRenderer.renderTexture(mTextures[1])
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
    val bb = ByteBuffer.allocateDirect(screenshotSize * 4);
    bb.order(ByteOrder.nativeOrder());
    gl.glReadPixels(
        0, 0, effectsView.width, effectsView.height, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, bb
    );
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