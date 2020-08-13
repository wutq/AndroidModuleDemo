package com.wss.common.view.gallery;

import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

/**
 * Describe：图片查看器适配器
 * Created by 吴天强 on 2018年11月16日
 */
class ImageViewerAdapter extends PagerAdapter {
    private List<View> views;

    ImageViewerAdapter(List<View> views) {
        this.views = views;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NotNull Object object) {
        if (position == 0 && views.size() == 0) {
            return;
        }
        if (position == views.size()) {
            container.removeView(views.get(--position));
        } else {
            container.removeView(views.get(position));
        }
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

}