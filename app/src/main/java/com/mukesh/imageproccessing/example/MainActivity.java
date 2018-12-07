package com.mukesh.imageproccessing.example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.mukesh.imageproccessing.BitmapReadyCallbacks;
import com.mukesh.imageproccessing.FILTERS;
import com.mukesh.imageproccessing.PhotoFilter;
import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BitmapReadyCallbacks {
  private GLSurfaceView mEffectView;
  private PhotoFilter p;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initialize();
  }

  private void initialize() {
    mEffectView = findViewById(R.id.effectsview);
    p = new PhotoFilter(mEffectView,
        BitmapFactory.decodeResource(getResources(), R.drawable.skull), this);
    p.setCurrentEffect(FILTERS.FLIP_VERTICALLY);
  }

  @Override public void onBitmapReady(@NotNull final Bitmap bitmap) {
    //try {
    //  String path = Environment.getExternalStorageDirectory().toString();
    //  OutputStream fOut;
    //  Integer counter = 0;
    //  File file = new File(path, "FitnessGirl" + counter + ".png");
    //  fOut = new FileOutputStream(file);
    //  bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
    //  fOut.flush();
    //  fOut.close();
    //  MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(),
    //      file.getName(), file.getName());
    //} catch (IOException e) {
    //  e.printStackTrace();
    //}
  }
}
