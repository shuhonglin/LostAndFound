package com.moonlight.nasa.lostandfound.Util;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageTask;
import in.srain.cube.image.iface.ImageLoadHandler;
import in.srain.cube.image.impl.DefaultImageLoadHandler;

/**
 * Created by NaSa on 2015/7/12.
 */
public class MainPageImageLoadHandler implements ImageLoadHandler {
    @Override
    public void onLoading(ImageTask imageTask, CubeImageView cubeImageView) {
        if (cubeImageView != null) {
            cubeImageView.setImageDrawable(new ColorDrawable(Color.GRAY));
        }
    }

    @Override
    public void onLoadFinish(ImageTask imageTask, CubeImageView cubeImageView, BitmapDrawable bitmapDrawable) {
        if (bitmapDrawable != null && cubeImageView != null) {
            cubeImageView.setImageDrawable(bitmapDrawable);
            DefaultImageLoadHandler defaultImageLoadHandler;
        }
    }

    @Override
    public void onLoadError(ImageTask imageTask, CubeImageView cubeImageView, int i) {
        if (cubeImageView != null) {
            cubeImageView.setImageDrawable(new ColorDrawable(Color.RED));
        }
    }
}
