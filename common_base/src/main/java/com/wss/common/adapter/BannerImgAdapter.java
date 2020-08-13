package com.wss.common.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.wss.common.base.R;
import com.wss.common.bean.Banner;
import com.wss.common.utils.ImageUtils;

/**
 * Describe：轮播图默认适配器 适合只加载一张图的Banner
 * Created by 吴天强 on 2018/11/1.
 */
public class BannerImgAdapter implements CBViewHolderCreator {
    /**
     * 是否加载圆角的
     */
    private boolean circle = false;

    public BannerImgAdapter() {
    }

    public BannerImgAdapter(boolean circle) {
        this.circle = circle;
    }

    @Override
    public Holder createHolder(View itemView) {
        return new Holder<Banner>(itemView) {
            LinearLayout layout;
            ImageView imageView;

            @Override
            protected void initView(View itemView) {
                layout = itemView.findViewById(R.id.ll_img_parent);
                imageView = getImageView();
                if (imageView != null) {
                    layout.removeAllViews();
                    layout.addView(imageView);
                } else {
                    imageView = itemView.findViewById(R.id.image);
                }
            }

            @Override
            public void updateUI(Banner data) {
                if (circle) {
                    ImageUtils.loadImageCircleBead(imageView, data.getImageUrl(), 4);
                } else {
                    ImageUtils.loadImage(imageView, data.getImageUrl());
                }
            }
        };
    }

    @Override
    public int getLayoutId() {
        return R.layout.layout_banner_img;
    }

    /**
     * 可以传入自定义的ImageView
     *
     * @return ImageView
     */
    public ImageView getImageView() {
        return null;
    }
}
