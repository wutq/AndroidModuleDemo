package com.wss.module.main.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.wss.module.main.R;

import androidx.annotation.Nullable;


/**
 * Describe：千里之行始于足下，点滴汇聚成河
 * 波浪View
 * Created by 吴天强 on 2017/8/30.
 */
public class WaveView extends View {

    public static final int MODE_TRIANGLE = 0;
    public static final int MODE_CIRCLE = 1;

    private int mWaveMode;
    private int mWaveCount;
    /**
     * 波浪的宽度
     */
    private int mWaveWidth;
    /**
     * 波浪的高度
     */
    private float mWaveHeight;

    private int mWidth;//View宽度
    private int mHeight;//View高度
    private float mRectWidth;//矩阵高=宽度
    private float mRectHeight;//矩阵高度

    private Paint mPaint;
    private RectF mRectF;
    private Path mPath;

    private float left;
    private float top;
    private float right;
    private float bottom;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WaveView);
        mWaveCount = a.getInt(R.styleable.WaveView_waveCount, 20);
        mWaveWidth = a.getInt(R.styleable.WaveView_waveWidth, 20);
        mWaveMode = a.getInt(R.styleable.WaveView_waveMode, MODE_CIRCLE);
        int mWaveColor = a.getColor(R.styleable.WaveView_waveColor, Color.BLACK);
        a.recycle();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mWaveColor);
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //测量宽度
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mWidth = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                //如果设置为wrap_content就默认给它一个值
                mWidth = 600;
            }
        }
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                //如果设置为wrap_content就默认给它一个值
                mHeight = 550;
            }
        }
        //矩阵的宽高为View宽度的0.7倍
        mRectWidth = (float) ((mWidth - getPaddingLeft() - getPaddingRight()) * 0.7);
        mRectHeight = (float) ((mHeight - getPaddingLeft() - getPaddingRight()) * 0.7);

        //计算出每个波浪的高度，这样保证波浪与view两边你的绝对融合
        mWaveHeight = mRectHeight / mWaveCount;


        //计算出矩阵的位置让它显示在View的中间
        left = (mWidth - mRectWidth) / 2;
        top = (mHeight - mRectHeight) / 2;
        right = (mWidth - mRectWidth) / 2 + mRectWidth;
        bottom = (mHeight - mRectHeight) / 2 + mRectHeight;
        mRectF = new RectF(left, top, right, bottom);
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setBackgroundColor(Color.LTGRAY);
        canvas.drawRect(mRectF, mPaint);
        if (mWaveMode == MODE_TRIANGLE) {
            //尖角型的
            float startX = left;
            float startY = top;
            mPath.moveTo(startX, startY);
            for (int i = 0; i < mWaveCount; i++) {
                //先画左边
                mPath.lineTo(startX - mWaveWidth, startY + mWaveHeight / 2 + i * mWaveHeight);
                mPath.lineTo(startX, startY + (i + 1) * mWaveHeight);
            }
            canvas.drawPath(mPath, mPaint);

            startX = left + mRectWidth;
            mPath.moveTo(startX, startY);
            for (int i = 0; i < mWaveCount; i++) {
                //再右边
                mPath.lineTo(startX + mWaveWidth, startY + mWaveHeight / 2 + i * mWaveHeight);
                mPath.lineTo(startX, startY + (i + 1) * mWaveHeight);
            }
            canvas.drawPath(mPath, mPaint);
        } else {

            //圆角型的
            for (int i = 0; i < mWaveCount; i++) {
                RectF mWaveRectF = new RectF(left - mWaveWidth / 2, top + i * mWaveHeight,
                        left + mWaveWidth / 2, top + (i + 1) * mWaveHeight);
                canvas.drawArc(mWaveRectF, 90, 180, true, mPaint);
            }
            for (int i = 0; i < mWaveCount; i++) {
                RectF mWaveRectF = new RectF(left + mRectWidth - mWaveWidth / 2, top + i * mWaveHeight,
                        left + mRectWidth + mWaveWidth / 2, top + (i + 1) * mWaveHeight);
                canvas.drawArc(mWaveRectF, 270, 180, true, mPaint);
            }
        }
    }
}
