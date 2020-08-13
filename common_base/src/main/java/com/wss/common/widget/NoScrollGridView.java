package com.wss.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.annotation.Nullable;

/**
 * 不可滑动的GridView
 *
 * @author 杨伟
 * create by 2020-4-27
 */
public class NoScrollGridView extends GridView {

    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置GridView不可滑动
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }
}