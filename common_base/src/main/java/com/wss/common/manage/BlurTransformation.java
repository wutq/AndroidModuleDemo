package com.wss.common.manage;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.wss.common.utils.BlurBitmapUtil;

import java.security.MessageDigest;

/**
 * Describe：Picasso 加载图片转换器之模糊转换器
 * Created by 吴天强 on 2018/11/8.
 */

public class BlurTransformation extends BitmapTransformation {
    private Context context;

    public BlurTransformation(Context context) {
        this.context = context;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return BlurBitmapUtil.instance().blurBitmap(context, toTransform, 20, outWidth, outHeight);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
