package com.wss.common.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.utils.PxUtils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.common.widget.dialog.DialogType;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Describe：加减Viw
 * Created by 吴天强 on 2018年9月27日18:13:09
 */
public class CountClickView extends LinearLayout {

    public static final int MIN_COUNT = 0;
    public static final int INIT_COUNT = 1;
    public static final int MAX_COUNT = 10000;


    @BindView(R2.id.tv_count)
    TextView tvCount;

    @BindView(R2.id.iv_plus)
    ImageView ivPlus;

    @BindView(R2.id.iv_minus)
    ImageView ivMinus;

    @BindView(R2.id.ll_minus)
    LinearLayout llMinus;//减 控件父类

    @BindView(R2.id.ll_plus)
    LinearLayout llPlus;//加 控件父类
    private Context mContext;
    private int maxCount = MAX_COUNT;
    private int minCount = MIN_COUNT;

    //控件资源
    private int minusCan = R.drawable.input_minus_default;
    private int minusNot = R.drawable.input_minus_disabled;
    private int addCan = R.drawable.input_add_default;
    private int addNot = R.drawable.input_add_disabled;
    private boolean input = false;//是否支持如输入 默认不支持

    public CountClickView(Context context) {
        this(context, null);

    }

    public CountClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private OnClickAfterListener afterClickListener = null;


    private void init(Context context) {
        this.mContext = context;
        this.setBackgroundResource(android.R.color.transparent);
        ButterKnife.bind(this, View.inflate(context, R.layout.layout_count_click_view, this));

        tvCount.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (input) {
                    new AppDialog(mContext, DialogType.COUNT)
                            .setTitle("修改数量")
                            .setRightButton(new AppDialog.OnButtonClickListener() {
                                @Override
                                public void onClick(String val) {
                                    tvCount.setText(val);
                                    if (afterClickListener != null) {
                                        afterClickListener.onAfter(getCount());
                                        if (getCount() == getMinCount()) {
                                            afterClickListener.onMin();
                                        }
                                    }
                                    judgeTheViews(Integer.valueOf(val));
                                }
                            })
                            .setNumber(minCount, maxCount, getCount())
                            .show();
                }
            }
        });

        judgeTheViews(getCount());
    }

    public int getCount() {
        String text = tvCount.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            return INIT_COUNT;
        }
        return Integer.valueOf(text);
    }

    @OnClick({R2.id.iv_plus, R2.id.iv_minus})
    public void onClick(View v) {
        int count = Integer.valueOf(tvCount.getText().toString().trim());

        if (R.id.iv_plus == v.getId()) {
            if (count < getMaxCount())
                tvCount.setText(String.valueOf(++count));
        }
        if (R.id.iv_minus == v.getId()) {
            if (count > getMinCount())
                tvCount.setText(String.valueOf(--count));
        }

        judgeTheViews(count);

        if (afterClickListener != null) {
            afterClickListener.onAfter(getCount());
            if (getCount() == getMinCount()) {
                afterClickListener.onMin();
            }
        }
    }

    private void judgeTheViews(int count) {
        if (count <= getMinCount()) {
            ivMinus.setImageResource(minusNot);
        } else {
            ivMinus.setImageResource(minusCan);
        }
        if (count >= getMaxCount()) {
            ivPlus.setImageResource(addNot);
        } else {
            ivPlus.setImageResource(addCan);
        }
    }

    /**
     * 设置 按钮父类的大小
     */
    public void setBtnParentSize(int width, int height) {
        llMinus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
        llPlus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
        //如果设置了该处大小 则需要更新中间View的高度
        LinearLayout.LayoutParams layoutParams = (LayoutParams) tvCount.getLayoutParams();
        layoutParams.height = PxUtils.dp2px(mContext, height);
    }

    /**
     * 设置 按钮父类背景
     */
    public void setBtnParentBg(int bgColor) {
        llMinus.setBackgroundColor(getResources().getColor(bgColor));
        llPlus.setBackgroundColor(getResources().getColor(bgColor));
    }


    /**
     * 设置加减按钮大小
     */
    public void setBtnSize(int width, int height) {
        ivPlus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
        ivMinus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(mContext, width),
                PxUtils.dp2px(mContext, height)));
    }

    /**
     * 设置加减控件的资源文件
     */
    public void setButtonRes(int minusCan, int minusNot, int addCan, int addNot) {
        this.minusCan = minusCan;
        this.minusNot = minusNot;
        this.addCan = addCan;
        this.addNot = addNot;
        //给按钮设置默认值
        ivMinus.setImageResource(minusCan);
        ivPlus.setImageResource(addCan);
    }

    /***
     * 设置中间View一些属性
     * @param bgColor 背景色
     * @param textColor 字体颜色  使用默认颜色 给0 即可
     * @param marginLeft marginLeft
     * @param marginRight marginRight
     */
    public void setCountViewAttr(int bgColor, int textColor, int marginLeft, int marginRight) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) tvCount.getLayoutParams();
        layoutParams.setMargins(PxUtils.dp2px(mContext, marginLeft), 0, PxUtils.dp2px(mContext, marginRight), 0);
        tvCount.setBackgroundColor(getResources().getColor(bgColor));
        if (textColor != 0) {
            tvCount.setTextColor(getResources().getColor(textColor));
        }
    }


    /**
     * 是否支出输入
     */
    public void setInput(boolean input) {
        this.input = input;
    }


    public void setCurrCount(int count) {
        tvCount.setText(String.valueOf(count));
        judgeTheViews(count);
    }

    public void setMaxCount(int maxCount) {
        if (maxCount < getMinCount()) {
            maxCount = INIT_COUNT;
        }

        this.maxCount = maxCount;
        judgeTheViews(getCount());
    }

    public void setMinCount(int minCount) {
        if (minCount > getMaxCount()) {
            minCount = INIT_COUNT;
        }
        this.minCount = minCount;
        judgeTheViews(getCount());
    }


    private int getMaxCount() {
        return maxCount < MAX_COUNT ? maxCount : MAX_COUNT;
    }

    private int getMinCount() {
        return minCount > MIN_COUNT ? minCount : MIN_COUNT;
    }

    public interface OnClickAfterListener {

        void onAfter(int value);

        void onMin();
    }

    public void setAfterClickListener(OnClickAfterListener afterClickListener) {
        this.afterClickListener = afterClickListener;
    }

}
