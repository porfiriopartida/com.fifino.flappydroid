package com.kilobolt.framework.implementation;

import android.graphics.Bitmap;

import com.fifino.framework.BitmapTransform;
import com.kilobolt.framework.Image;
import com.kilobolt.framework.Graphics.ImageFormat;

public class AndroidImage implements Image {
    Bitmap bitmap;
    ImageFormat format;

    public AndroidImage(Bitmap bitmap, ImageFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    @Override
    public int getWidth() {
        return bitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return bitmap.getHeight();
    }

    @Override
    public ImageFormat getFormat() {
        return format;
    }

    @Override
    public void dispose() {
        bitmap.recycle();
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }
    public void rotate(int angle){
    	setBitmap( BitmapTransform.rotate(bitmap, angle));
    }
    public void scale(int width, int height){
    	setBitmap( BitmapTransform.scale(bitmap, width, height));
    }
}
