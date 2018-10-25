package com.wss.common.widget.pulltorefresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.widget.LVCircularRing;


/**
 * 刷新控件
 */
public class RefreshView extends FrameLayout implements IRefreshView {

    private TextView tv;
    private ImageView arrow;
    //    private ProgressBar progressBar;
    private LVCircularRing lvCircularRing;
    private boolean refresh = false;//是否是刷新

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void setType(boolean refresh) {
        this.refresh = refresh;
    }


    private void init(Context context) {
        View childView = View.inflate(context, R.layout.layout_refresh_view, null);
        addView(childView);
        tv = childView.findViewById(R.id.header_tv);
        arrow = childView.findViewById(R.id.header_arrow);
        lvCircularRing = childView.findViewById(R.id.header_progress);
    }

    @Override
    public void begin() {

    }

    @Override
    public void progress(float progress, float all) {
        float s = progress / all;
        if (s >= 0.9f) {
            arrow.setRotation(180);
        } else {
            arrow.setRotation(0);
        }
        if (progress >= all - 10) {
            tv.setText(refresh ? R.string.pull_loose_refreshing : R.string.pull_loose_load_more);
        } else {
            tv.setText(refresh ? R.string.pull_down_refresh : R.string.pull_up_loading);
        }
    }

    @Override
    public void finishing(float progress, float all) {
        lvCircularRing.stopAnim();
    }

    @Override
    public void loading() {
        arrow.setVisibility(GONE);
        lvCircularRing.setVisibility(VISIBLE);
        lvCircularRing.startAnim();
        tv.setText(R.string.loading);
    }

    @Override
    public void normal() {
        arrow.setVisibility(VISIBLE);
        lvCircularRing.setVisibility(GONE);
        lvCircularRing.stopAnim();
        tv.setText(refresh ? R.string.pull_down_refresh : R.string.pull_up_loading);
    }

    @Override
    public View getView() {
        return this;
    }
}
