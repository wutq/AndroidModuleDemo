package com.wss.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wss.common.base.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

/**
 * Describe：图片工具类
 * Created by 吴天强 on 2018/10/17.
 */

public class ImageUtils {


    public static void loadImage(Context context, String url, ImageView imageView) {
        Picasso.get()
                .load(url)
                .placeholder(R.drawable.bg_load_failed)
                .error(R.drawable.bg_load_failed)
                .into(imageView);
    }

    /**
     * 加载轮播图
     *
     * @param banner 轮播图控件
     * @param images 图片集合
     */
    public static void loadBanner(Banner banner, List<String> images) {
        if (banner == null || images == null) {
            return;
        }
        //如果没有图片集合 则给出一个默认占位图
        if (images.size() == 0) {
            images.add("R.drawable.bg_load_failed");
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new PicassoImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
//        banner.isAutoPlay(false);
        //设置轮播时间
        banner.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //设置图片集合
        banner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


    /**
     * Banner图片加载器
     */
    private static class PicassoImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            loadImage(context, (String) path, imageView);
        }
    }
}
