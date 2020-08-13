package com.wss.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wss.common.utils.PxUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import androidx.viewpager.widget.ViewPager;

/**
 * ViewPager和ScrollView嵌套空白问题
 *
 * @author 杨伟
 * create by 2020-4-17
 */
public class ViewPagerForScrollView extends ViewPager implements Serializable {

    private static final long serialVersionUID = 1L;


    private int current;
    private int height = 0;
    /**
     * 保存position与对于的View
     */
    private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<>();
    private boolean scroll = true;
    /**
     * 最小高度
     */
    private int minHeight;

    public ViewPagerForScrollView(Context context) {
        super(context);
    }

    public ViewPagerForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        minHeight = PxUtils.getScreenHeight(context);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            if (mChildrenViews.size() > current) {
                View child = mChildrenViews.get(current);
                if (child != null) {
                    child.measure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    height = child.getMeasuredHeight();
                    //如果计算出ViewPager的高度小于最小高度，则使用最小高度作为ViewPager高度
                    if (height < minHeight) {
                        height = minHeight;
                    }
                }
            }
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置ViewPager的高度
     *
     * @param current 第几页的
     */
    public void resetHeight(int current) {
        this.current = current;
        if (mChildrenViews.size() > current) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height = height;
            }

            setLayoutParams(layoutParams);
        }
    }

    /**
     * 保存position与对于的View
     */
    public void setObjectForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!scroll) {
            return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置ViewPager显示的最小高度
     *
     * @param minHeight 最小高度
     */
    public void setMinHeight(int minHeight) {
        this.minHeight = minHeight;
    }

    /**
     * 设置是否可滚动
     *
     * @param scroll 滚动
     */
    public void setScroll(boolean scroll) {
        this.scroll = scroll;
    }

}