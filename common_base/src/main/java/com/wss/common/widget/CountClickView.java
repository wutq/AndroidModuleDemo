package com.wss.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.InputType;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.utils.PxUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.Utils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.common.widget.dialog.DialogType;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Describe：加减Viw，最大支持10000，最小支持 0
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

    /**
     * 减 控件父类
     */
    @BindView(R2.id.ll_minus)
    LinearLayout llMinus;
    /**
     * 加 控件父类
     */
    @BindView(R2.id.ll_plus)
    LinearLayout llPlus;

    private int maxCount = MAX_COUNT;
    private int minCount = MIN_COUNT;
    private int currentCount = INIT_COUNT;
    private int textColor = Color.BLACK;
    private int textSize = 14;

    /**
     * 是否支持如输入 默认不支持
     */
    private boolean input = false;

    private OnClickAfterListener afterClickListener = null;

    public CountClickView(Context context) {
        this(context, null);

    }

    public CountClickView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CountClickView);
        maxCount = a.getInt(R.styleable.CountClickView_ccvMax, maxCount);
        minCount = a.getInt(R.styleable.CountClickView_ccvMin, minCount);
        currentCount = a.getInt(R.styleable.CountClickView_ccvCurrent, currentCount);
        input = a.getBoolean(R.styleable.CountClickView_ccvInput, input);
        textColor = a.getColor(R.styleable.CountClickView_ccvTextColor, textColor);
        textSize = a.getDimensionPixelSize(R.styleable.EnhanceEditText_eTextSize, textSize);
        a.recycle();
        init();
    }

    private void init() {
        this.setBackgroundResource(android.R.color.transparent);
        ButterKnife.bind(this, View.inflate(getContext(), R.layout.layout_count_click_view, this));
        setTextColor(textColor);
        setTextSize(textSize);
        setCurrCount(currentCount);
        judgeTheViews(getCount());
    }

    @OnClick({R2.id.ll_minus, R2.id.ll_plus})
    public void onClick(@NotNull View v) {
        int count = getCount();
        if (R.id.ll_plus == v.getId()) {
            if (count < getMaxCount()) {
                tvCount.setText(String.valueOf(++count));
            }
        } else if (R.id.ll_minus == v.getId()) {
            if (count > getMinCount()) {
                tvCount.setText(String.valueOf(--count));
            }
        } else if (R.id.tv_count == v.getId()) {
        }
        judgeTheViews(count);
        if (afterClickListener != null) {
            afterClickListener.onAfter(this, getCount());
        }
    }

    @OnClick({R2.id.tv_count})
    public void onCountClick() {
        if (input) {
            new AppDialog.Builder(getContext())
                    .setDialogType(DialogType.INPUT)
                    .setInputType(InputType.TYPE_CLASS_NUMBER)
                    .setInputDefaultText(String.valueOf(getCount()))
                    .setTitle("输入数量")
                    .setRightButton(getContext().getString(R.string.confirm), val -> {
                        if (Utils.isNumber(val)) {
                            int count = Integer.parseInt(val);
                            if (count > MAX_COUNT) {
                                count = maxCount;
                                ToastUtils.show("超过了设置的最大值");
                            } else if (count < MIN_COUNT) {
                                count = MIN_COUNT;
                                ToastUtils.show("超过了设置的最小值");
                            }
                            setCurrCount(count);
                        }
                    })
                    .create()
                    .show();
        }
    }


    /**
     * 根据加减 设置显示的控件View
     *
     * @param count 数量
     */
    private void judgeTheViews(int count) {
        ivMinus.setEnabled(count > getMinCount());
        ivPlus.setEnabled(count < getMaxCount());
    }

    /**
     * 判断最大值
     *
     * @return 返回最大值
     */
    @Contract(pure = true)
    private int getMaxCount() {
        return Math.min(maxCount, MAX_COUNT);
    }

    /**
     * 判断最小值
     *
     * @return 返回最小值
     */
    @Contract(pure = true)
    private int getMinCount() {
        return Math.max(minCount, MIN_COUNT);
    }

    /**
     * 设置监听
     *
     * @param afterClickListener l
     */
    public void setAfterClickListener(OnClickAfterListener afterClickListener) {
        this.afterClickListener = afterClickListener;
    }

    /**
     * 设置是否支出输入  默认不支持
     *
     * @param input 输入
     */
    public void setInput(boolean input) {
        this.input = input;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor 颜色
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        tvCount.setTextColor(textColor);
    }

    /**
     * 设置文字大小
     *
     * @param textSize 大小
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
        tvCount.setTextSize(this.textSize);
    }

    /**
     * 设置默认显示的数量
     *
     * @param count 数量
     */
    public void setCurrCount(int count) {
        tvCount.setText(String.valueOf(count));
        judgeTheViews(count);
    }

    /**
     * 设置最大值
     *
     * @param maxCount 最大值
     */
    public void setMaxCount(int maxCount) {
        if (maxCount < getMinCount()) {
            maxCount = INIT_COUNT;
        }
        this.maxCount = maxCount;
        judgeTheViews(getCount());
    }

    /**
     * 设置最小值
     *
     * @param minCount 最小值
     */
    public void setMinCount(int minCount) {
        if (minCount > getMaxCount()) {
            minCount = INIT_COUNT;
        }
        this.minCount = minCount;
        judgeTheViews(getCount());
    }


    /**
     * 返回操作后的数量
     *
     * @return 数量
     */
    public int getCount() {
        String text = tvCount.getText().toString().trim();
        if (TextUtils.isEmpty(text)) {
            return INIT_COUNT;
        }
        return Integer.parseInt(text);
    }


    /**
     * 设置 按钮父类的大小
     */
    public void setBtnParentSize(int width, int height) {
        llMinus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(width), PxUtils.dp2px(height)));
        llPlus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(width), PxUtils.dp2px(height)));
        //如果设置了该处大小 则需要更新中间View的高度
        LinearLayout.LayoutParams layoutParams = (LayoutParams) tvCount.getLayoutParams();
        layoutParams.height = PxUtils.dp2px(height);
    }

    /**
     * 设置 按钮父类背景
     */
    public void setBtnParentBg(int bgColor) {
        llMinus.setBackgroundColor(ContextCompat.getColor(getContext(), bgColor));
        llPlus.setBackgroundColor(ContextCompat.getColor(getContext(), bgColor));
    }


    /**
     * 设置加减按钮大小
     */
    public void setBtnSize(int width, int height) {
        ivPlus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(width),
                PxUtils.dp2px(height)));
        ivMinus.setLayoutParams(new LinearLayout.LayoutParams(PxUtils.dp2px(width),
                PxUtils.dp2px(height)));
    }

    /**
     * 设置加减控件的资源文件
     */
    public void setButtonRes(int addDrawable, int minusDrawable) {
        ivPlus.setBackgroundResource(addDrawable);
        ivMinus.setBackgroundResource(minusDrawable);
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
        layoutParams.setMargins(PxUtils.dp2px(marginLeft), 0, PxUtils.dp2px(marginRight), 0);
        tvCount.setBackgroundColor(ContextCompat.getColor(getContext(), bgColor));
        if (textColor != 0) {
            tvCount.setTextColor(ContextCompat.getColor(getContext(), textColor));
        }
    }

    /**
     * 操作之后监听回调
     */
    public interface OnClickAfterListener {

        /**
         * 操作之后
         *
         * @param view  CountClickView
         * @param value 值
         */
        void onAfter(CountClickView view, int value);
    }


}
