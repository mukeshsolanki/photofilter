package com.mukesh.image_processing.example;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.mukesh.image_processing.ImageProcessor;

public class MainActivity extends AppCompatActivity {
  private ImageView mOriginalImageView, mProcessImageView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mOriginalImageView = (ImageView) findViewById(R.id.imageView);
    mProcessImageView = (ImageView) findViewById(R.id.imageView2);
    ImageProcessor imageProcessor = new ImageProcessor();

    Bitmap bitmap = ((BitmapDrawable) mOriginalImageView.getDrawable()).getBitmap();
    mProcessImageView.setImageBitmap(imageProcessor.doGamma(bitmap, 1.8, 1.8, 1.8));
  }
}
