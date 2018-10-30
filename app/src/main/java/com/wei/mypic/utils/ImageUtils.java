package com.wei.mypic.utils;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtils {
    //加载本地图片
    public static void loadLocalSmallPic(Context context, String path, ImageView imageView) {
        //这个地方可以调清晰度，数值越大 清晰度越高
        loadLocalPic(context, path, imageView, 320, 320);
    }

    //加载本地图片
    public static void loadLocalVerySmallPic(Context context, String path, ImageView imageView) {
        //这个地方可以调清晰度，数值越大 清晰度越高
        loadLocalPic(context, path, imageView, 100, 100);
    }

    public static void loadLocalPic(Context context, String path, ImageView imageView, int width, int height) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        Glide.with(context)
                .load(path)
                .override(width, height)
                .centerCrop()
                .into(imageView);
    }
    //本地原图
    public static void loadLocalPicNoOverride(Context context, String path, ImageView imageView) {
        if (TextUtils.isEmpty(path)) {
            return;
        }
        Glide.with(context)
                .load(path)
                .into(imageView);
    }
}
