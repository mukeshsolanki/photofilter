package com.mukesh.image_processing;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.ColorInt;
import java.util.Random;

public class ImageProcessor {

  public Bitmap doHighlightImage(Bitmap originalImage, int radius, @ColorInt int highlightColor) {
    Bitmap resultingBitmap =
        Bitmap.createBitmap(originalImage.getWidth() + 96, originalImage.getHeight() + 96,
            Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(resultingBitmap);
    canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    Paint paintBlur = new Paint();
    paintBlur.setMaskFilter(new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL));
    int[] offsetXY = new int[2];
    Bitmap bitmapAlpha = originalImage.extractAlpha(paintBlur, offsetXY);
    Paint paintAlphaColor = new Paint();
    paintAlphaColor.setColor(highlightColor);
    canvas.drawBitmap(bitmapAlpha, offsetXY[0], offsetXY[1], paintAlphaColor);
    bitmapAlpha.recycle();
    canvas.drawBitmap(originalImage, 0, 0, null);
    return resultingBitmap;
  }

  public Bitmap doInvert(Bitmap originalImage) {
    Bitmap resultingBitmap =
        Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(),
            originalImage.getConfig());
    int A, R, G, B;
    int pixelColor;
    int height = originalImage.getHeight();
    int width = originalImage.getWidth();
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        pixelColor = originalImage.getPixel(x, y);
        A = Color.alpha(pixelColor);
        R = 255 - Color.red(pixelColor);
        G = 255 - Color.green(pixelColor);
        B = 255 - Color.blue(pixelColor);
        resultingBitmap.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return resultingBitmap;
  }

  public Bitmap doGreyScale(Bitmap originalImage) {
    final double GS_RED = 0.299;
    final double GS_GREEN = 0.587;
    final double GS_BLUE = 0.114;
    Bitmap resultingBitmap =
        Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(),
            originalImage.getConfig());
    int A, R, G, B;
    int pixel;
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);
        R = G = B = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
        resultingBitmap.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return resultingBitmap;
  }

  public Bitmap doGamma(Bitmap originalImage, double red, double green, double blue) {
    Bitmap bmOut = Bitmap.createBitmap(originalImage.getWidth(), originalImage.getHeight(),
        originalImage.getConfig());
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int A, R, G, B;
    int pixel;
    final int MAX_SIZE = 256;
    final double MAX_VALUE_DBL = 255.0;
    final int MAX_VALUE_INT = 255;
    final double REVERSE = 1.0;
    int[] gammaR = new int[MAX_SIZE];
    int[] gammaG = new int[MAX_SIZE];
    int[] gammaB = new int[MAX_SIZE];
    for (int i = 0; i < MAX_SIZE; ++i) {
      gammaR[i] = (int) Math.min(MAX_VALUE_INT,
          (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
      gammaG[i] = (int) Math.min(MAX_VALUE_INT,
          (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
      gammaB[i] = (int) Math.min(MAX_VALUE_INT,
          (int) ((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
    }
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = gammaR[Color.red(pixel)];
        G = gammaG[Color.green(pixel)];
        B = gammaB[Color.blue(pixel)];
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap doColorFilter(Bitmap originalImage, double red, double green, double blue) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    int A, R, G, B;
    int pixel;
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = (int) (Color.red(pixel) * red);
        G = (int) (Color.green(pixel) * green);
        B = (int) (Color.blue(pixel) * blue);
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap createSepiaToningEffect(Bitmap originalImage, int depth, double red, double green,
      double blue) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    final double GS_RED = 0.3;
    final double GS_GREEN = 0.59;
    final double GS_BLUE = 0.11;
    int A, R, G, B;
    int pixel;
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);
        B = G = R = (int) (GS_RED * R + GS_GREEN * G + GS_BLUE * B);
        R += (depth * red);
        if (R > 255) {
          R = 255;
        }
        G += (depth * green);
        if (G > 255) {
          G = 255;
        }
        B += (depth * blue);
        if (B > 255) {
          B = 255;
        }
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap decreaseColorDepth(Bitmap originalImage, int bitOffset) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    int A, R, G, B;
    int pixel;
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);
        R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);
        if (R < 0) {
          R = 0;
        }
        G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);
        if (G < 0) {
          G = 0;
        }
        B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);
        if (B < 0) {
          B = 0;
        }
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap createContrast(Bitmap originalImage, double value) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    int A, R, G, B;
    int pixel;
    double contrast = Math.pow((100 + value) / 100, 2);
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        R = (int) (((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (R < 0) {
          R = 0;
        } else if (R > 255) {
          R = 255;
        }
        G = Color.red(pixel);
        G = (int) (((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (G < 0) {
          G = 0;
        } else if (G > 255) {
          G = 255;
        }
        B = Color.red(pixel);
        B = (int) (((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
        if (B < 0) {
          B = 0;
        } else if (B > 255) {
          B = 255;
        }
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap rotate(Bitmap originalImage, float degree) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    return Bitmap.createBitmap(originalImage, 0, 0, originalImage.getWidth(),
        originalImage.getHeight(), matrix, true);
  }

  public Bitmap doBrightness(Bitmap originalImage, int value) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    int A, R, G, B;
    int pixel;
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);
        R += value;
        if (R > 255) {
          R = 255;
        } else if (R < 0) {
          R = 0;
        }
        G += value;
        if (G > 255) {
          G = 255;
        } else if (G < 0) {
          G = 0;
        }
        B += value;
        if (B > 255) {
          B = 255;
        } else if (B < 0) {
          B = 0;
        }
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap applyGaussianBlur(Bitmap originalImage) {
    double[][] GaussianBlurConfig = new double[][] {
        { 1, 2, 1 }, { 2, 4, 2 }, { 1, 2, 1 }
    };
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.applyConfig(GaussianBlurConfig);
    convMatrix.Factor = 16;
    convMatrix.Offset = 0;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap createShadow(Bitmap originalImage) {
    double[][] GaussianBlurConfig = new double[][] {
        { -1, 0, -1 }, { 0, 4, 0 }, { -1, 0, -1 }
    };
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.applyConfig(GaussianBlurConfig);
    convMatrix.Factor = 16;
    convMatrix.Offset = 0;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap sharpen(Bitmap originalImage, double weight) {
    double[][] SharpConfig = new double[][] {
        { 0, -2, 0 }, { -2, weight, -2 }, { 0, -2, 0 }
    };
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.applyConfig(SharpConfig);
    convMatrix.Factor = weight - 8;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap applyMeanRemoval(Bitmap originalImage) {
    double[][] MeanRemovalConfig = new double[][] {
        { -1, -1, -1 }, { -1, 9, -1 }, { -1, -1, -1 }
    };
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.applyConfig(MeanRemovalConfig);
    convMatrix.Factor = 1;
    convMatrix.Offset = 0;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap smooth(Bitmap originalImage, double value) {
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.setAll(1);
    convMatrix.Matrix[1][1] = value;
    convMatrix.Factor = value + 8;
    convMatrix.Offset = 1;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap emboss(Bitmap originalImage) {
    double[][] EmbossConfig = new double[][] {
        { -1, 0, -1 }, { 0, 4, 0 }, { -1, 0, -1 }
    };
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.applyConfig(EmbossConfig);
    convMatrix.Factor = 1;
    convMatrix.Offset = 127;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap engrave(Bitmap originalImage) {
    ConvolutionMatrix convMatrix = new ConvolutionMatrix(3);
    convMatrix.setAll(0);
    convMatrix.Matrix[0][0] = -2;
    convMatrix.Matrix[1][1] = 2;
    convMatrix.Factor = 1;
    convMatrix.Offset = 95;
    return ConvolutionMatrix.computeConvolution3x3(originalImage, convMatrix);
  }

  public Bitmap boost(Bitmap originalImage, int type, double percent) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    int A, R, G, B;
    int pixel;
    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        pixel = originalImage.getPixel(x, y);
        A = Color.alpha(pixel);
        R = Color.red(pixel);
        G = Color.green(pixel);
        B = Color.blue(pixel);
        switch (type) {
          case ImageProcessingConstants.RED:
            R = (int) (R * (1 + percent));
            if (R > 255) R = 255;
            break;
          case ImageProcessingConstants.GREEN:
            G = (int) (G * (1 + percent));
            if (G > 255) G = 255;
            break;
          case ImageProcessingConstants.BLUE:
            B = (int) (B * (1 + percent));
            if (B > 255) B = 255;
            break;
        }
        bmOut.setPixel(x, y, Color.argb(A, R, G, B));
      }
    }
    return bmOut;
  }

  public Bitmap roundCorner(Bitmap originalImage, double round) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(result);
    canvas.drawARGB(0, 0, 0, 0);
    final Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setColor(Color.BLACK);
    final Rect rect = new Rect(0, 0, width, height);
    final RectF rectF = new RectF(rect);
    float newRound = (float) round;
    canvas.drawRoundRect(rectF, newRound, newRound, paint);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
    canvas.drawBitmap(originalImage, rect, rect, paint);
    return result;
  }

  public Bitmap waterMark(Bitmap originalImage, String watermark, Point location,
      @ColorInt int color, int alpha, int size, boolean underline) {
    int w = originalImage.getWidth();
    int h = originalImage.getHeight();
    Bitmap result = Bitmap.createBitmap(w, h, originalImage.getConfig());
    Canvas canvas = new Canvas(result);
    canvas.drawBitmap(originalImage, 0, 0, null);
    Paint paint = new Paint();
    paint.setColor(color);
    paint.setAlpha(alpha);
    paint.setTextSize(size);
    paint.setAntiAlias(true);
    paint.setUnderlineText(underline);
    canvas.drawText(watermark, location.x, location.y, paint);
    return result;
  }

  public Bitmap flip(Bitmap originalImage, int type) {
    Matrix matrix = new Matrix();
    if (type == ImageProcessingConstants.FLIP_VERTICAL) {
      matrix.preScale(1.0f, -1.0f);
    } else if (type == ImageProcessingConstants.FLIP_HORIZONTAL) {
      matrix.preScale(-1.0f, 1.0f);
    } else {
      return null;
    }
    return Bitmap.createBitmap(originalImage, 0, 0, originalImage.getWidth(),
        originalImage.getHeight(), matrix, true);
  }

  public Bitmap tintImage(Bitmap originalImage, int degree) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pix = new int[width * height];
    originalImage.getPixels(pix, 0, width, 0, 0, width, height);
    int RY, GY, BY, RYY, GYY, BYY, R, G, B, Y;
    double angle = (ImageProcessingConstants.PI * (double) degree)
        / ImageProcessingConstants.HALF_CIRCLE_DEGREE;
    int S = (int) (ImageProcessingConstants.RANGE * Math.sin(angle));
    int C = (int) (ImageProcessingConstants.RANGE * Math.cos(angle));
    for (int y = 0; y < height; y++)
      for (int x = 0; x < width; x++) {
        int index = y * width + x;
        int r = (pix[index] >> 16) & 0xff;
        int g = (pix[index] >> 8) & 0xff;
        int b = pix[index] & 0xff;
        RY = (70 * r - 59 * g - 11 * b) / 100;
        GY = (-30 * r + 41 * g - 11 * b) / 100;
        BY = (-30 * r - 59 * g + 89 * b) / 100;
        Y = (30 * r + 59 * g + 11 * b) / 100;
        RYY = (S * BY + C * RY) / 256;
        BYY = (C * BY - S * RY) / 256;
        GYY = (-51 * RYY - 19 * BYY) / 100;
        R = Y + RYY;
        R = (R < 0) ? 0 : ((R > 255) ? 255 : R);
        G = Y + GYY;
        G = (G < 0) ? 0 : ((G > 255) ? 255 : G);
        B = Y + BYY;
        B = (B < 0) ? 0 : ((B > 255) ? 255 : B);
        pix[index] = 0xff000000 | (R << 16) | (G << 8) | B;
      }

    Bitmap outBitmap = Bitmap.createBitmap(width, height, originalImage.getConfig());
    outBitmap.setPixels(pix, 0, width, 0, 0, width, height);
    pix = null;
    return outBitmap;
  }

  public Bitmap applyFleaEffect(Bitmap originalImage) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    Random random = new Random();
    int index = 0;
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        index = y * width + x;
        int randColor = Color.rgb(random.nextInt(ImageProcessingConstants.COLOR_MAX),
            random.nextInt(ImageProcessingConstants.COLOR_MAX),
            random.nextInt(ImageProcessingConstants.COLOR_MAX));
        pixels[index] |= randColor;
      }
    }
    Bitmap bmOut = Bitmap.createBitmap(width, height, originalImage.getConfig());
    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
    return bmOut;
  }

  public Bitmap applyBlackFilter(Bitmap originalImage) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    Random random = new Random();
    int R, G, B, index = 0, thresHold = 0;
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        index = y * width + x;
        R = Color.red(pixels[index]);
        G = Color.green(pixels[index]);
        B = Color.blue(pixels[index]);
        thresHold = random.nextInt(ImageProcessingConstants.COLOR_MAX);
        if (R < thresHold && G < thresHold && B < thresHold) {
          pixels[index] =
              Color.rgb(ImageProcessingConstants.COLOR_MIN, ImageProcessingConstants.COLOR_MIN,
                  ImageProcessingConstants.COLOR_MIN);
        }
      }
    }
    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
    return bmOut;
  }

  public Bitmap applySnowEffect(Bitmap originalImage) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    Random random = new Random();

    int R, G, B, index = 0, thresHold = 50;
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        index = y * width + x;
        R = Color.red(pixels[index]);
        G = Color.green(pixels[index]);
        B = Color.blue(pixels[index]);
        thresHold = random.nextInt(ImageProcessingConstants.COLOR_MAX);
        if (R > thresHold && G > thresHold && B > thresHold) {
          pixels[index] =
              Color.rgb(ImageProcessingConstants.COLOR_MAX, ImageProcessingConstants.COLOR_MAX,
                  ImageProcessingConstants.COLOR_MAX);
        }
      }
    }
    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
    return bmOut;
  }

  public Bitmap applyShadingFilter(Bitmap originalImage, int shadingColor) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    int index = 0;
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        index = y * width + x;
        pixels[index] &= shadingColor;
      }
    }
    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
    return bmOut;
  }

  public Bitmap applySaturationFilter(Bitmap originalImage, int level) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    float[] HSV = new float[3];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    int index = 0;
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        index = y * width + x;
        Color.colorToHSV(pixels[index], HSV);
        HSV[1] *= level;
        HSV[1] = (float) Math.max(0.0, Math.min(HSV[1], 1.0));
        pixels[index] |= Color.HSVToColor(HSV);
      }
    }
    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
    return bmOut;
  }

  public Bitmap applyHueFilter(Bitmap originalImage, int level) {
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    float[] HSV = new float[3];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    int index = 0;
    for (int y = 0; y < height; ++y) {
      for (int x = 0; x < width; ++x) {
        index = y * width + x;
        Color.colorToHSV(pixels[index], HSV);
        HSV[0] *= level;
        HSV[0] = (float) Math.max(0.0, Math.min(HSV[0], 360.0));
        pixels[index] |= Color.HSVToColor(HSV);
      }
    }
    Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
    return bmOut;
  }

  public Bitmap applyReflection(Bitmap originalImage) {
    final int reflectionGap = 4;
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    Matrix matrix = new Matrix();
    matrix.preScale(1, -1);
    Bitmap reflectionImage =
        Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
    Bitmap bitmapWithReflection =
        Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmapWithReflection);
    canvas.drawBitmap(originalImage, 0, 0, null);
    Paint defaultPaint = new Paint();
    canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
    canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
    Paint paint = new Paint();
    LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,
        bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
        Shader.TileMode.CLAMP);
    paint.setShader(shader);
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
    canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);
    return bitmapWithReflection;
  }

  public Bitmap replaceColor(Bitmap originalImage, @ColorInt int fromColor,
      @ColorInt int targetColor) {
    if (originalImage == null) {
      return null;
    }
    int width = originalImage.getWidth();
    int height = originalImage.getHeight();
    int[] pixels = new int[width * height];
    originalImage.getPixels(pixels, 0, width, 0, 0, width, height);
    for (int x = 0; x < pixels.length; ++x) {
      pixels[x] = (pixels[x] == fromColor) ? targetColor : pixels[x];
    }
    Bitmap newImage = Bitmap.createBitmap(width, height, originalImage.getConfig());
    newImage.setPixels(pixels, 0, width, 0, 0, width, height);
    return newImage;
  }
}
