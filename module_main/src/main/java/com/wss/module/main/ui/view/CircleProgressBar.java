package com.wss.module.main.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wss.common.utils.PxUtils;
import com.wss.module.main.R;

import org.jetbrains.annotations.NotNull;

/**
 * Describe：圆形进度条
 * Created by 吴天强 on 2020/9/7.
 */
public class CircleProgressBar extends View {
    private Context mContext;

    private int mDefaultSize = PxUtils.dp2px(Constant.DEFAULT_SIZE);
    /**
     * 是否开启抗锯齿
     */
    private boolean antiAlias = true;
    /**
     * 绘制提示
     */
    private TextPaint mHintPaint;
    private CharSequence mHint;
    private int mHintColor;
    private float mHintSize;
    private float mHintOffset;

    /**
     * 单位
     */
    private CharSequence mUnit;

    /**
     * 绘制数值
     */
    private TextPaint mValuePaint;
    private float mValue;
    private float mMaxValue;
    private float mValueOffset;
    private int mPrecision;
    private String mPrecisionFormat;
    private int mValueColor;
    private float mValueSize;

    /**
     * 绘制圆弧
     */
    private Paint mArcPaint;
    private float mArcWidth;
    private float mStartAngle, mSweepAngle;
    private RectF mRectF;
    private int[] mGradientColors = {Color.GREEN, Color.YELLOW, Color.RED};
    /**
     * 当前精度，[0.0f,1.0f]
     */
    private float mPercent;
    /**
     * 动画时间
     */
    private long mAnimTime;
    /**
     * 属性动画
     */
    private ValueAnimator mAnimator;

    /**
     * 绘制背景圆弧
     */
    private Paint mBgArcPaint;
    private int mBgArcColor;
    private float mBgArcWidth;

    /**
     * 圆心坐标，半径
     */
    private Point mCenterPoint;

    public CircleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        mAnimator = new ValueAnimator();
        mRectF = new RectF();
        mCenterPoint = new Point();
        initAttrs(attrs);
        initPaint();
        setValue(mValue);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        mHint = typedArray.getString(R.styleable.CircleProgressBar_cpbHint);
        mHintColor = typedArray.getColor(R.styleable.CircleProgressBar_cpbHintColor, Color.BLACK);
        mHintSize = typedArray.getDimension(R.styleable.CircleProgressBar_cpbHintSize, PxUtils.dp2px(Constant.DEFAULT_HINT_SIZE));

        mValue = typedArray.getFloat(R.styleable.CircleProgressBar_cpbValue, 0);
        mMaxValue = typedArray.getFloat(R.styleable.CircleProgressBar_cpbMaxValue, Constant.DEFAULT_MAX_VALUE);
        //内容数值精度格式
        mPrecision = typedArray.getInt(R.styleable.CircleProgressBar_cpbPrecision, 0);
        mPrecisionFormat = getPrecisionFormat(mPrecision);
        mValueColor = typedArray.getColor(R.styleable.CircleProgressBar_cpbValueColor, Color.BLACK);
        mValueSize = typedArray.getDimension(R.styleable.CircleProgressBar_cpbValueSize, PxUtils.dp2px(Constant.DEFAULT_VALUE_SIZE));

        mUnit = typedArray.getString(R.styleable.CircleProgressBar_cpbUnit);

        //圆弧宽度
        mArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_cpbArcWidth, PxUtils.dp2px(Constant.DEFAULT_ARC_WIDTH));
        mStartAngle = typedArray.getFloat(R.styleable.CircleProgressBar_cpbStartAngle, Constant.DEFAULT_START_ANGLE);
        mSweepAngle = typedArray.getFloat(R.styleable.CircleProgressBar_cpbSweepAngle, Constant.DEFAULT_SWEEP_ANGLE);

        mBgArcColor = typedArray.getColor(R.styleable.CircleProgressBar_cpbBgArcColor, Color.GRAY);
        //背景弧宽度，默认和圆度宽度一致
        mBgArcWidth = typedArray.getDimension(R.styleable.CircleProgressBar_cpbBgArcWidth, mArcWidth);

        mAnimTime = typedArray.getInt(R.styleable.CircleProgressBar_cpbAnimTime, Constant.DEFAULT_ANIM_TIME);

        //进度条颜色
        int arcColor = typedArray.getColor(R.styleable.CircleProgressBar_cpbArcColor, 0);
        if (arcColor != 0) {
            mGradientColors = new int[2];
            mGradientColors[0] = arcColor;
            mGradientColors[1] = arcColor;
        }
        typedArray.recycle();
    }

    private void initPaint() {

        mValuePaint = new TextPaint();
        mValuePaint.setAntiAlias(antiAlias);
        mValuePaint.setTextSize(mValueSize);
        mValuePaint.setColor(mValueColor);
        // 设置Typeface对象，即字体风格，包括粗体，斜体以及衬线体，非衬线体等
        mValuePaint.setTypeface(Typeface.DEFAULT_BOLD);
        mValuePaint.setTextAlign(Paint.Align.CENTER);


        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(antiAlias);
        // 设置画笔的样式，为FILL，FILL_OR_STROKE，或STROKE
        mArcPaint.setStyle(Paint.Style.STROKE);
        // 设置画笔粗细
        mArcPaint.setStrokeWidth(mArcWidth);
        // 当画笔样式为STROKE或FILL_OR_STROKE时，设置笔刷的图形样式，如圆形样式
        // Cap.ROUND,或方形样式 Cap.SQUARE
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mBgArcPaint = new Paint();
        mBgArcPaint.setAntiAlias(antiAlias);
        mBgArcPaint.setColor(mBgArcColor);
        mBgArcPaint.setStyle(Paint.Style.STROKE);
        mBgArcPaint.setStrokeWidth(mBgArcWidth);
        mBgArcPaint.setStrokeCap(Paint.Cap.ROUND);

        mHintPaint = new TextPaint();
        // 设置抗锯齿,会消耗较大资源，绘制图形速度会变慢。
        mHintPaint.setAntiAlias(antiAlias);
        // 设置绘制文字大小
        mHintPaint.setTextSize(mHintSize);
        // 设置画笔颜色
        mHintPaint.setColor(mHintColor);
        // 从中间向两边绘制，不需要再次计算文字
        mHintPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(PxUtils.measure(widthMeasureSpec, mDefaultSize),
                PxUtils.measure(heightMeasureSpec, mDefaultSize));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.i("onSizeChanged: w = " + w + "; h = " + h + "; oldw = " + oldw + "; oldh = " + oldh);
        //求圆弧和背景圆弧的最大宽度
        float maxArcWidth = Math.max(mArcWidth, mBgArcWidth);
        //求最小值作为实际值
        int minSize = Math.min(w - getPaddingLeft() - getPaddingRight() - 2 * (int) maxArcWidth,
                h - getPaddingTop() - getPaddingBottom() - 2 * (int) maxArcWidth);
        //减去圆弧的宽度，否则会造成部分圆弧绘制在外围
        float mRadius = minSize >> 1;
        //获取圆的相关参数
        mCenterPoint.x = w / 2;
        mCenterPoint.y = h / 2;
        //绘制圆弧的边界
        mRectF.left = mCenterPoint.x - mRadius - maxArcWidth / 2;
        mRectF.top = mCenterPoint.y - mRadius - maxArcWidth / 2;
        mRectF.right = mCenterPoint.x + mRadius + maxArcWidth / 2;
        mRectF.bottom = mCenterPoint.y + mRadius + maxArcWidth / 2;
        //计算文字绘制时的 baseline
        //由于文字的baseline、descent、ascent等属性只与textSize和typeface有关，所以此时可以直接计算
        //若value、hint、unit由同一个画笔绘制或者需要动态设置文字的大小，则需要在每次更新后再次计算
        mValueOffset = mCenterPoint.y - getBaselineOffsetFromY(mValuePaint);
        mHintOffset = mCenterPoint.y + PxUtils.measureTextHeight(mHintPaint) * 2;
        updateArcPaint();
        Logger.i("onSizeChanged: 控件大小 = " + "(" + w + ", " + h + ")"
                + "圆心坐标 = " + mCenterPoint.toString()
                + ";圆半径 = " + mRadius
                + ";圆的外接矩形 = " + mRectF.toString());
    }

    private float getBaselineOffsetFromY(Paint paint) {
        return PxUtils.measureTextHeight(paint) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawText(canvas);
        drawArc(canvas);
    }

    /**
     * 绘制内容文字
     *
     * @param canvas 画布
     */
    private void drawText(@NotNull Canvas canvas) {
        // 计算文字宽度，由于Paint已设置为居中绘制，故此处不需要重新计算
        String value = String.format(mPrecisionFormat, mValue) + mUnit.toString();
        canvas.drawText(value, mCenterPoint.x, mValueOffset, mValuePaint);
        if (mHint != null) {
            canvas.drawText(mHint.toString(), mCenterPoint.x, mHintOffset, mHintPaint);
        }
    }

    /**
     * 画圆弧
     *
     * @param canvas 画布
     */
    private void drawArc(@NotNull Canvas canvas) {
        // 绘制背景圆弧
        // 从进度圆弧结束的地方开始重新绘制，优化性能
        canvas.save();
        float currentAngle = mSweepAngle * mPercent;
        canvas.rotate(mStartAngle, mCenterPoint.x, mCenterPoint.y);
        canvas.drawArc(mRectF, currentAngle, mSweepAngle - currentAngle + 2, false, mBgArcPaint);
        // 第一个参数 oval 为 RectF 类型，即圆弧显示区域
        // startAngle 和 sweepAngle  均为 float 类型，分别表示圆弧起始角度和圆弧度数
        // 3点钟方向为0度，顺时针递增
        // 如果 startAngle < 0 或者 > 360,则相当于 startAngle % 360
        // useCenter:如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形
        canvas.drawArc(mRectF, 2, currentAngle, false, mArcPaint);
        canvas.restore();
    }

    /**
     * 更新圆弧画笔
     */
    private void updateArcPaint() {
        // 设置渐变
        /**
         * 渐变的颜色是360度，如果只显示270，那么则会缺失部分颜色
         */
        SweepGradient mSweepGradient = new SweepGradient(mCenterPoint.x, mCenterPoint.y, mGradientColors, null);
        mArcPaint.setShader(mSweepGradient);
    }


    public CharSequence getHint() {
        return mHint;
    }

    public void setHint(CharSequence hint) {
        mHint = hint;
    }

    public CharSequence getUnit() {
        return mUnit;
    }

    public void setUnit(CharSequence unit) {
        mUnit = unit;
    }

    public float getValue() {
        return mValue;
    }

    /**
     * 设置当前值
     *
     * @param value
     */
    public void setValue(float value) {
        if (value > mMaxValue) {
            value = mMaxValue;
        }
        float start = mPercent;
        float end = value / mMaxValue;
        startAnimator(start, end, mAnimTime);
    }

    private void startAnimator(float start, float end, long animTime) {
        mAnimator = ValueAnimator.ofFloat(start, end);
        mAnimator.setDuration(animTime);
        mAnimator.addUpdateListener(animation -> {
            mPercent = (float) animation.getAnimatedValue();
            mValue = mPercent * mMaxValue;
            Logger.i("onAnimationUpdate: percent = " + mPercent
                    + ";currentAngle = " + (mSweepAngle * mPercent)
                    + ";value = " + mValue);
            invalidate();
        });
        mAnimator.start();
    }

    /**
     * 获取最大值
     *
     * @return mMaxValue
     */
    public float getMaxValue() {
        return mMaxValue;
    }

    /**
     * 设置最大值
     *
     * @param maxValue mMaxValue
     */
    public void setMaxValue(float maxValue) {
        mMaxValue = maxValue;
    }

    /**
     * 获取精度
     *
     * @return mMaxValue
     */
    public int getPrecision() {
        return mPrecision;
    }

    /**
     * 设置精度
     *
     * @param precision precision
     */
    public void setPrecision(int precision) {
        mPrecision = precision;
        mPrecisionFormat = getPrecisionFormat(precision);
    }

    /**
     * 设置渐变
     *
     * @param gradientColors 颜色数组
     */
    public void setGradientColors(int[] gradientColors) {
        mGradientColors = gradientColors;
        updateArcPaint();
    }

    public long getAnimTime() {
        return mAnimTime;
    }

    public void setAnimTime(long animTime) {
        mAnimTime = animTime;
    }

    /**
     * 重置
     */
    public void reset() {
        startAnimator(mPercent, 0.0f, 1000L);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //释放资源
    }

    /**
     * 获取数值精度格式化字符串
     *
     * @param precision
     * @return
     */
    public static String getPrecisionFormat(int precision) {
        return "%." + precision + "f";
    }



    /**
     * 一些设置的常量
     */
    interface Constant {

        int DEFAULT_SIZE = 120;
        /**
         * 开始的角度，3点钟方向为0度，默认12点钟方向
         */
        int DEFAULT_START_ANGLE = 270;
        /**
         * 圆弧度数
         */
        int DEFAULT_SWEEP_ANGLE = 360;

        /**
         * 动画默认持续时间
         */
        int DEFAULT_ANIM_TIME = 1000;

        /**
         * 最大值
         */
        int DEFAULT_MAX_VALUE = 100;

        int DEFAULT_HINT_SIZE = 12;
        int DEFAULT_UNIT_SIZE = 16;
        int DEFAULT_VALUE_SIZE = 16;
        /**
         * 圆弧默认宽度，背景宽度也取值这个
         */
        int DEFAULT_ARC_WIDTH = 12;
    }

}

