package com.wss.common.manage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import com.squareup.picasso.Transformation;

/**
 * Describe：Picasso 加载图片转换器之模糊转换器
 * Created by 吴天强 on 2018/11/8.
 */

public class BlurTransformation implements Transformation {

    private RenderScript rs;
    private int radius = 15;//模糊半径 0 -25 之间 越大越模糊

    public BlurTransformation(Context context) {
        this(context, 15);
    }

    /**
     * @param context context
     * @param radius  模糊半径 0 -25 之间 越大越模糊
     */
    public BlurTransformation(Context context, int radius) {
        this.radius = radius;
        rs = RenderScript.create(context);
    }


    @SuppressLint("NewApi")
    @Override
    public Bitmap transform(Bitmap bitmap) {
        if (radius < 0 || radius > 25) {
            radius = 15;
        }

        // 创建一个Bitmap作为最后处理的效果Bitmap
        Bitmap blurredBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        // 分配内存
        Allocation input = Allocation.createFromBitmap(rs, blurredBitmap, Allocation.MipmapControl.MIPMAP_FULL, Allocation.USAGE_SHARED);
        Allocation output = Allocation.createTyped(rs, input.getType());

        // 根据我们想使用的配置加载一个实例
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        script.setInput(input);

        // 设置模糊半径
        script.setRadius(radius);

        //开始操作
        script.forEach(output);

        // 将结果copy到blurredBitmap中
        output.copyTo(blurredBitmap);

        //释放资源
        bitmap.recycle();

        return blurredBitmap;
    }

    @Override
    public String key() {
        return "blur";
    }
}
