package com.wss.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Describe：带渐变颜色的TextView
 * Created by 吴天强 on 2019/7/1.
 */
public class GradientTextView extends AppCompatTextView {

    private Rect mTextBound = new Rect();
    private boolean gradient;

    public GradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        int mViewWidth = getMeasuredWidth();
        Paint mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        LinearGradient mLinearGradient;
        if (gradient) {
            mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                    new int[]{0xFF1A3AFF, 0xFF10E17D}, null, Shader.TileMode.REPEAT);
            mPaint.setFakeBoldText(true);
        } else {
            mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                    new int[]{0xFF000000, 0xFF000000}, null, Shader.TileMode.REPEAT);
            mPaint.setFakeBoldText(false);
        }
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, (getMeasuredWidth() >> 1) - (mTextBound.width() >> 1), (getMeasuredHeight() >> 1) + (mTextBound.height() >> 1), mPaint);
        invalidate();
    }

    /**
     * 设置是否渐变
     */
    public void setGradient(boolean gradient) {
        this.gradient = gradient;
        postInvalidate();
    }
}