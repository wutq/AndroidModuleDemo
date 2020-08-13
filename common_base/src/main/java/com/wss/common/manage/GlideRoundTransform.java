package com.wss.common.manage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.wss.common.utils.PxUtils;

import org.jetbrains.annotations.Contract;

import java.security.MessageDigest;

import androidx.annotation.NonNull;

/**
 * Describe：加载图片转换器之圆角转换器
 * Created by 吴天强 on 2019/7/2.
 */
public class GlideRoundTransform extends BitmapTransformation {

    private float radius = 0f;

    public GlideRoundTransform(Context context, int dp) {
        super();
        radius = PxUtils.dp2px(dp);
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    @Contract("_, null -> null")
    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null){ return null;}
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    public String getId() {
        return getClass().getName() + Math.round(radius);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    }
}
