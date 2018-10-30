package com.wss.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe：流式布局
 * Created by 吴天强 on 2018年9月27日18:13:09
 */
public class FlowGroupView extends ViewGroup {

    /**
     * 储存所有的view 按行记录
     */
    private List<List<View>> mAllViews = new ArrayList<List<View>>();
    /**
     * 记录每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<Integer>();
    private String TAG = "TAG";

    public FlowGroupView(Context context, AttributeSet attrs,
                         int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowGroupView(Context context) {
        super(context);
    }

    /**
     * 所有childView的位置的布局
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 当前行的最大高度
        int lineHeight = 0;
        // 存储每一行所有的childView  
        List<View> lineViews;
        int left = 0;
        int top = 0;
        // 得到总行数  
        int lineNums = mAllViews.size();
        for (int i = 0; i < lineNums; i++) {
            // 每一行的所有的views  
            lineViews = mAllViews.get(i);
            // 当前行的最大高度  
            lineHeight = mLineHeight.get(i);
//            Logger.e(TAG, "第" + i + "行 ：" + lineViews.size() + " , " + lineViews);
//            Logger.e(TAG, "第" + i + "行， ：" + lineHeight);

            // 遍历当前行所有的View
            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                //计算childView的left,top,right,bottom  
//                int lc = left + lp.leftMargin;
//                int tc = top + lp.topMargin;
                int lc = left + 2;
                int tc = top + 2;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + 4/*lp.rightMargin+ lp.leftMargin*/;
            }
            left = 0;
            top += lineHeight;
        }
        Log.v(TAG, "onLayout   mAllViews.size() -- > " + mAllViews.size() + "   mLineHeight.size() -- > " + mLineHeight.size());
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 置空 view 容器 和 lineHeight 容器  重新赋值
        mAllViews.clear();
        mLineHeight.clear();

        //得到上级容器为其推荐的宽高和计算模式
        int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int specHeighMode = MeasureSpec.getMode(heightMeasureSpec);
        int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
        int specHeighSize = MeasureSpec.getSize(heightMeasureSpec);
        // 计算出所有的 child 的 宽和高
        measureChildren(specWidthSize, specHeighSize);
        // 记录如果是 warp_content 是设置的宽和高
        int width = 0;
        int height = 0;
        // 得到子view的个数
        int cCount = getChildCount();
        /**
         * 记录每一行的宽度，width不断取最大宽度 
         */
        int lineWidth = 0;
        /**
         * 每一行的高度，累加至height 
         */
        int lineHeight = 0;

        // 存储每一行所有的childView
        List<View> lineViews = new ArrayList<>();
        for (int i = 0; i < cCount; i++) {
            // 得到每个子View
            View child = getChildAt(i);
            // 测量每个子View的宽高
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            // 当前子view的lp
//        	MarginLayoutParams lp = () child.getLayoutParams();
            // 子view的宽和高
            int cWidth = 0;
            int cheight = 0;
            // 当前子 view 实际占的宽
            cWidth = child.getMeasuredWidth() + 4 /*lp.leftMargin + lp.rightMargin*/;
            // 当前子View 实际占的高
            cheight = child.getMeasuredHeight() + 4 /*lp.topMargin + lp.bottomMargin*/;

            // 需要换行
            if (lineWidth + cWidth > specWidthSize) {
                width = Math.max(lineWidth, cWidth);// 取最大值
                lineWidth = cWidth; // 开启新行的时候重新累加width
                height += lineHeight; // 开启新行时累加 height
                lineHeight = cheight; // 记录下一行的高度
                mAllViews.add(lineViews);
                mLineHeight.add(lineHeight);
                lineViews = new ArrayList<View>();
                // 换行的时候把该 view 放进 集合里
                lineViews.add(child);// 这个  view(child) 是下一行的第一个view
                Log.v(TAG, "onl  mAllViews.size()  --  > " + mAllViews.size());
            } else {
                // 不需要换行
                lineWidth += cWidth;//
                height = Math.max(lineHeight, cheight);// 取最大值
                // 不需要换行时 把子View add 进集合
                lineViews.add(child);
            }

            if (i == cCount - 1) {
                // 如果是最后一个view
                width = Math.max(lineWidth, cWidth);
                height += lineHeight;
            }
        }
        // 循环结束后 把最后一行内容add进集合中
        mLineHeight.add(lineHeight); // 记录最后一行
        mAllViews.add(lineViews);
        // MeasureSpec.EXACTLY 表示设置了精确的值
        // 如果 mode 是 MeasureSpec.EXACTLY 时候，则不是 warp_content 用计算来的值，否则则用上级布局分给它的值
        setMeasuredDimension(specWidthMode == MeasureSpec.EXACTLY ? specWidthSize : width
                , specHeighMode == MeasureSpec.EXACTLY ? specHeighSize : height);
        Log.v(TAG, "onLayout  onMeasure   mAllViews.size() -- > " + mAllViews.size() + "   mLineHeight.size() -- > " + mLineHeight.size());
    }

    /**
     * 这个一定要设置，否则会包强转错误
     * 设置它支持 marginLayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        return new MarginLayoutParams(getContext(), attrs);
    }

}
