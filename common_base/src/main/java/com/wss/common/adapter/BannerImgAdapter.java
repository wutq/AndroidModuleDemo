package com.wss.common.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.wss.common.base.R;
import com.wss.common.utils.ImageUtils;

/**
 * Describe：轮播图默认适配器 适合只加载一张图的Banner
 * Created by 吴天强 on 2018/11/1.
 */
public class BannerImgAdapter implements CBViewHolderCreator {

    @Override
    public Holder createHolder(View itemView) {

        return new Holder<String>(itemView) {
            LinearLayout layout;
            ImageView imageView;

            @Override
            protected void initView(View itemView) {
                layout = itemView.findViewById(R.id.ll_img_parent);
                if (getImageView() != null) {
                    layout.removeAllViews();
                    imageView = getImageView();
                    layout.addView(imageView);
                } else {
                    imageView = itemView.findViewById(R.id.image);
                }
            }

            @Override
            public void updateUI(String data) {
                ImageUtils.loadImage(imageView, data);
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_banner_img;
    }

    /**
     * 可以传入自定义的ImageView
     */
    public ImageView getImageView() {
        return null;
    }
}
