package com.wss.common.widget.scaleImg;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Describe：图片查看器适配器
 * Created by 吴天强 on 2018年11月16日
 */
class ImageViewerAdapter extends PagerAdapter {

    private List<View> views;
    private Dialog dialog;

    ImageViewerAdapter(List<View> views, Dialog dialog) {
        this.views = views;
        this.dialog = dialog;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, Object object) {
        if (position == 0 && views.size() == 0) {
            dialog.dismiss();
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