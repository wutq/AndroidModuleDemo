package com.wss.common.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.utils.PxUtils;


/**
 * Describe：自定义的ActionBar
 * Created by 吴天强 on 2017/8/22.
 */

public class ActionBar extends RelativeLayout {

    private LinearLayout llActionbar;
    private LinearLayout llLeft;
    private LinearLayout llCenter;
    private LinearLayout llRight;

    /**
     * 总布局
     */
    private View rootView;

    /**
     * 字体颜色
     */
    private int leftTextColor = R.color.white;
    private int centerTextColor = R.color.white;
    private int rightTextColor = R.color.white;
    /**
     * 字体大小
     */
    private int lefTextSize = 16;
    private int centerTextSize = 16;
    private int rightTextSize = 16;


    public ActionBar(Context context) {
        this(context, null);
    }

    public ActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public void setRightTextColor(int rightTextColor) {
        this.rightTextColor = rightTextColor;
    }

    public void setLeftTextColor(int leftTextColor) {
        this.leftTextColor = leftTextColor;
    }

    public void setCenterTextColor(int centerTextColor) {
        this.centerTextColor = centerTextColor;
    }

    public void setLefTextSize(int lefTextSize) {
        this.lefTextSize = lefTextSize;
    }


    public void setCenterTextSize(int centerTextSize) {
        this.centerTextSize = centerTextSize;
    }

    public void setRightTextSize(int rightTextSize) {
        this.rightTextSize = rightTextSize;
    }

    /**
     * 获取跟布局
     */
    public View getRootView() {
        return rootView;
    }

    /**
     * 初始化界面
     */
    private void initView(Context context) {
        rootView = View.inflate(context, R.layout.action_bar, this);
        llActionbar = findViewById(R.id.ll_actionbar);
        llLeft = findViewById(R.id.ll_actionbar_left);
        llCenter = findViewById(R.id.ll_actionbar_centre);
        llRight = findViewById(R.id.ll_actionbar_right);
        showBackImg(true);
    }

    /**
     * 设置左边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setLeftIcon(int res, OnClickListener l) {
        llLeft.setVisibility(VISIBLE);
        llLeft.setVisibility(VISIBLE);
        llLeft.removeAllViews();
        ImageView ivLeft = new ImageView(getContext());
        ivLeft.setImageResource(res);
        llLeft.addView(ivLeft);
        llLeft.setOnClickListener(l);
    }

    /**
     * 设置中间图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setCenterIcon(int res, OnClickListener l) {
        llCenter.setVisibility(VISIBLE);
        llCenter.removeAllViews();
        ImageView center = new ImageView(getContext());
        center.setImageResource(res);
        llCenter.addView(center);
        llCenter.setOnClickListener(l);
    }

    /**
     * 设置右边图标
     *
     * @param res 图片资源
     * @param l   监听器
     */
    public void setRightIcon(int res, OnClickListener l) {
        llRight.setVisibility(VISIBLE);
        llRight.removeAllViews();
        ImageView right = new ImageView(getContext());
        right.setImageResource(res);
        llRight.addView(right);
        if (l != null) {
            llRight.setOnClickListener(l);
        }
    }

    /**
     * 设置左边文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setLeftText(CharSequence text, OnClickListener l) {
        llLeft.setVisibility(VISIBLE);
        llLeft.removeAllViews();
        TextView left = getTextView();
        left.setText(text);
        left.setTextColor(getResources().getColor(leftTextColor));
        left.setTextSize(lefTextSize);
        llLeft.addView(left);
        if (l != null) {
            llLeft.setOnClickListener(l);
        }
    }

    /**
     * 设置左边文字
     *
     * @param text 文字
     */
    public void setLeftText(CharSequence text) {
        setLeftText(text, null);
    }


    /**
     * 设置中间文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setCenterText(CharSequence text, OnClickListener l) {
        llCenter.setVisibility(VISIBLE);
        llCenter.removeAllViews();
        TextView center = getTextView();
        center.setTextSize(centerTextSize);
        center.setText(text);
        center.setEllipsize(TextUtils.TruncateAt.END);
        center.setMaxLines(1);
        center.setTextColor(getResources().getColor(centerTextColor));
        llCenter.addView(center);
        if (l != null) {
            llCenter.setOnClickListener(l);
        }
    }

    /**
     * 设置中间文字
     *
     * @param text 文字
     */
    public void setCenterText(CharSequence text) {
        setCenterText(text, null);
    }


    /**
     * 设置右边文字
     *
     * @param text 文字
     * @param l    监听器
     */
    public void setRightText(CharSequence text, OnClickListener l) {
        llRight.setVisibility(VISIBLE);
        llRight.removeAllViews();
        TextView tvRight = getTextView();
        tvRight.setText(text);
        tvRight.setTextSize(rightTextSize);
        tvRight.setTextColor(ContextCompat.getColor(getContext(), rightTextColor));
        llRight.addView(tvRight);
        if (l != null) {
            llRight.setOnClickListener(l);
        }
    }


    /**
     * 设置右边文字
     *
     * @param text 文字
     */
    public void setRightText(CharSequence text) {
        setRightText(text, null);
    }


    /**
     * 得到右边的布局
     *
     * @return View
     */
    public LinearLayout getRightView() {
        return llRight;
    }

    /**
     * 得到中间的布局
     *
     * @return View
     */
    public LinearLayout getCenterView() {
        return llCenter;
    }

    /**
     * 得到左边的布局
     *
     * @return View
     */
    public LinearLayout getLeftView() {
        return llLeft;
    }

    /**
     * 设置左边布局
     *
     * @param v v
     */
    public void setLeftView(View v) {
        llLeft.setVisibility(VISIBLE);
        llLeft.removeAllViews();
        llLeft.addView(v);
    }

    /**
     * 设置中间布局
     *
     * @param v v
     */
    public void setCenterView(View v) {
        llCenter.setVisibility(VISIBLE);
        llCenter.removeAllViews();
        llCenter.addView(v);
    }

    /**
     * 设置右边的view
     *
     * @param v v
     */
    public void setRightView(View v) {
        setRightView(v, null);
    }

    public void setRightView(View v, OnClickListener listener) {
        llRight.removeAllViews();
        llRight.addView(v);
        if (listener != null) {
            v.setOnClickListener(listener);
        }
    }

    /**
     * 显示默认的左边的按钮
     */
    public void showBackImg(boolean show) {
        if (show) {
            setLeftIcon(R.drawable.ic_back, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context ctx = ActionBar.this.getContext();
                    if (ctx instanceof Activity) {
                        ((Activity) ctx).onBackPressed();
                    }
                }
            });
        } else {
            llLeft.setVisibility(INVISIBLE);
        }
    }


    /**
     * ActionBar整个替换
     *
     * @param v v
     */
    public void setActionBarView(View v) {
        llActionbar.removeAllViews();
        llActionbar.addView(v);
    }

    /**
     * 获取textView
     */
    public TextView getTextView() {
        TextView tv = new TextView(getContext());
        tv.setTextSize(centerTextSize);
        tv.setTextColor(ContextCompat.getColor(getContext(), centerTextColor));
        return tv;
    }

    /**
     * 获取imageView
     */
    public ImageView getImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setLayoutParams(new LayoutParams(PxUtils.dp2px(getContext(), 25), PxUtils.dp2px(getContext(), 25)));
        return iv;

    }
}
