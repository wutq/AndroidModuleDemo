package com.wss.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

/**
 * 自动改变其显示行数的TextView
 * create by yangwei
 * on 2020/6/8 17:12
 */
public class AutoTextView extends androidx.appcompat.widget.AppCompatTextView {
    public AutoTextView(Context context) {
        super(context);
    }

    public AutoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // 标记当前TextView的展开/收缩状态
    // true，已经展开
    // false，以及收缩
    private boolean expandableStatus = false;
    private final int MAX_LINES = 2;//默认最多显示多少行
    private int lines;//如果textview内容完全神展开需要显示多少

    private void init() {
        final ViewTreeObserver mViewTreeObserver = this.getViewTreeObserver();

        mViewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                // 避免重复监听
                AutoTextView.this.getViewTreeObserver().removeOnPreDrawListener(this);
                lines = getLineCount();
                return true;
            }
        });
        this.setMaxLines(MAX_LINES);
    }

    /**
     * 是否展开
     *
     * @param isExpand
     */
    public void setExpandable(boolean isExpand) {
        if (isExpand) {

            setMaxLines(lines + 1);
        } else {

            setMaxLines(MAX_LINES);
        }

        expandableStatus = isExpand;
    }

    public boolean getExpandableStatus() {
        return expandableStatus;
    }
}
