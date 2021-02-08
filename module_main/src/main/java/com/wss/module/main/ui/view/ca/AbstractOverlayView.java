package com.wss.module.main.ui.view.ca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created on 28/07/2018.
 *
 * @author Qiang Lili
 */
public abstract class AbstractOverlayView extends View {
    private Path mPath = new Path();

    private Paint mPaint;

    private Paint mBackgroundPaint;

    private int mBackgroundColor = Color.BLACK;

    public AbstractOverlayView(Context context) {
        super(context);
        this.init();
    }

    public AbstractOverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public AbstractOverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    private void init() {
        this.mPaint = new Paint();
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(Color.GRAY);
        this.mPaint.setStrokeWidth(2);
    }

    private Paint getBackgroundPaint() {
        if (this.mBackgroundPaint != null) {
            return mBackgroundPaint;
        }
        this.mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBackgroundPaint.clearShadowLayer();
        this.mBackgroundPaint.setStyle(Paint.Style.FILL);
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
        return this.mBackgroundPaint;
    }

    protected abstract void buildPath(@NonNull final Path path, final int viewWidth,
                                      final int viewHeight);

    public abstract Rect getMaskBounds();

    /**
     * Set background color.
     *
     * @param color color.
     */
    @Override
    public void setBackgroundColor(@ColorInt final int color) {
        this.mBackgroundColor = color;

        this.invalidate();
    }

    /**
     * Set mask path color.
     *
     * @param color color
     */
    public void setMaskPathColor(@ColorInt final int color) {
        this.mPaint.setColor(color);

        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        this.mPath.reset();

        this.buildPath(this.mPath, this.getWidth(), this.getHeight());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(this.mPath);
            canvas.drawColor(this.mBackgroundColor);
            canvas.restore();
            canvas.drawPath(this.mPath, this.mPaint);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            canvas.clipPath(this.mPath, Region.Op.DIFFERENCE);
            canvas.drawColor(this.mBackgroundColor);
            canvas.restore();
            canvas.drawPath(this.mPath, this.mPaint);
        } else {
            @SuppressLint("DrawAllocation")
            final Path path = new Path();
            @SuppressLint("DrawAllocation")
            final RectF board = new RectF(0, 0, this.getMeasuredWidth(), this.getMeasuredHeight());
            path.addRect(board, Path.Direction.CCW);
            path.addPath(this.mPath);
            canvas.drawPath(path, this.getBackgroundPaint());
            canvas.drawPath(this.mPath, this.mPaint);
            canvas.restore();
        }
    }
}
