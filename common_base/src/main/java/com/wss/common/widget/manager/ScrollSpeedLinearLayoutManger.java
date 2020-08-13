package com.wss.common.widget.manager;

import android.content.Context;
import android.graphics.PointF;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Describe：带效果的RecyclerView LinearLayoutManager滚动指定位置
 * Created by 吴天强 on 2019/5/14.
 */
public class ScrollSpeedLinearLayoutManger extends LinearLayoutManager {

    /**
     * 滚动速度因子
     */
    private float speed = 0.3f;

    public ScrollSpeedLinearLayoutManger(Context context) {
        super(context, VERTICAL, false);
    }

    public ScrollSpeedLinearLayoutManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    /**
     * 设置滚动速度因子 越大越快 越小越慢 取值在0.1~1.0之间即可
     *
     * @param speed 速度因子
     */
    public ScrollSpeedLinearLayoutManger setSpeed(float speed) {
        if (speed < 0 && speed < 1) {
            this.speed = speed;
        }
        return this;
    }

    @Override
    public void smoothScrollToPosition(@NonNull RecyclerView recyclerView, RecyclerView.State state, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return ScrollSpeedLinearLayoutManger.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }

        @Override
        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return speed;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }

}