package com.mukesh.imageproccessing.example;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
  private Bitmap[] mBitmapArray;
  private Context context;
  private int itemBackground;

  public ImageAdapter(Context c, Bitmap[] bitmapArray) {
    context = c;
    mBitmapArray = bitmapArray;
    TypedArray a = context.obtainStyledAttributes(R.styleable.MyGallery);
    itemBackground = a.getResourceId(R.styleable.MyGallery_android_galleryItemBackground, 0);
    a.recycle();
  }

  public int getCount() {
    return mBitmapArray.length;
  }

  public Object getItem(int position) {
    return position;
  }

  public long getItemId(int position) {
    return position;
  }

  public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView = new ImageView(context);
    imageView.setImageBitmap(mBitmapArray[position]);
    imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
    imageView.setBackgroundResource(itemBackground);
    return imageView;
  }
}