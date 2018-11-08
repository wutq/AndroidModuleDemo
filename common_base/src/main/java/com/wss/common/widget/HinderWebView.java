package com.wss.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Describe: 滑动中阻碍事件传递 当滑动到顶部 将事件传递给父类
 * Created by 吴天强 on 2018/11/1.
 */
public class HinderWebView extends WebView {
    public float oldY;
    private int t;

    public HinderWebView(Context context) {
        super(context);
    }

    public HinderWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HinderWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float Y = ev.getY();
                float Ys = Y - oldY;

                //滑动到顶部让父控件重新获得触摸事件
                if (Ys > 0 && t == 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;

            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                oldY = ev.getY();
                break;

            case MotionEvent.ACTION_UP:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;

            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        this.t = t;
        super.onScrollChanged(l, t, oldl, oldt);
    }

}
