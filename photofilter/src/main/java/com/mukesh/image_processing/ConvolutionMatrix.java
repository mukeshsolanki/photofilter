package com.mukesh.image_processing;

import android.graphics.Bitmap;
import android.graphics.Color;

class ConvolutionMatrix {
  private static final int SIZE = 3;

  double[][] Matrix;
  double Factor = 1;
  double Offset = 1;

  ConvolutionMatrix(int size) {
    Matrix = new double[size][size];
  }

  void setAll(double value) {
    for (int x = 0; x < SIZE; ++x) {
      for (int y = 0; y < SIZE; ++y) {
        Matrix[x][y] = value;
      }
    }
  }

  void applyConfig(double[][] config) {
    for (int x = 0; x < SIZE; ++x) {
      System.arraycopy(config[x], 0, Matrix[x], 0, SIZE);
    }
  }

  static Bitmap computeConvolution3x3(Bitmap src, ConvolutionMatrix matrix) {
    int width = src.getWidth();
    int height = src.getHeight();
    Bitmap result = Bitmap.createBitmap(width, height, src.getConfig());
    int A, R, G, B;
    int sumR, sumG, sumB;
    int[][] pixels = new int[SIZE][SIZE];
    for (int y = 0; y < height - 2; ++y) {
      for (int x = 0; x < width - 2; ++x) {
        for (int i = 0; i < SIZE; ++i) {
          for (int j = 0; j < SIZE; ++j) {
            pixels[i][j] = src.getPixel(x + i, y + j);
          }
        }
        A = Color.alpha(pixels[1][1]);
        sumR = sumG = sumB = 0;
        for (int i = 0; i < SIZE; ++i) {
          for (int j = 0; j < SIZE; ++j) {
            sumR += (Color.red(pixels[i][j]) * matrix.Matrix[i][j]);
            sumG += (Color.green(pixels[i][j]) * matrix.Matrix[i][j]);
            sumB += (Color.blue(pixels[i][j]) * matrix.Matrix[i][j]);
          }
        }
        R = (int) (sumR / matrix.Factor + matrix.Offset);
        if (R < 0) {
          R = 0;
        } else if (R > 255) {
          R = 255;
        }
        G = (int) (sumG / matrix.Factor + matrix.Offset);
        if (G < 0) {
          G = 0;
        } else if (G > 255) {
          G = 255;
        }
        B = (int) (sumB / matrix.Factor + matrix.Offset);
        if (B < 0) {
          B = 0;
        } else if (B > 255) {
          B = 255;
        }
        result.setPixel(x + 1, y + 1, Color.argb(A, R, G, B));
      }
    }
    return result;
  }
}