package com.mukesh.imageproccessing.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mukesh.imageproccessing.OnProcessingCompletionListener;
import com.mukesh.imageproccessing.PhotoFilter;
import com.mukesh.imageproccessing.filters.None;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements OnProcessingCompletionListener {
  private GLSurfaceView mEffectView;
  private PhotoFilter photoFilter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initialize();
  }

  private void initialize() {
    mEffectView = findViewById(R.id.effectsview);
    photoFilter = new PhotoFilter(mEffectView, this);
    photoFilter.applyEffect(BitmapFactory.decodeResource(getResources(), R.drawable.skull),
        new None());
  }

  @Override public void onProcessingComplete(@NotNull final Bitmap bitmap) {
    // Do anything with the bitmap save it or add another effect to it
  }
}
