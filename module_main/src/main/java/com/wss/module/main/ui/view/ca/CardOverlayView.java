package com.wss.module.main.ui.view.ca;

import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created on 2018/11/30.
 *
 * @author Zhu Xiangdong
 */
public class CardOverlayView extends AbstractOverlayView {
    public static final int ORIENTATION_HORIZONTAL = 0x01;

    public static final int ORIENTATION_VERTICAL = 0x02;

    private static final float CARD_RATIO = 8.0f / 5.0f;

    private RectF mRectF = new RectF();

    private int mOrientation = ORIENTATION_HORIZONTAL;

    public CardOverlayView(Context context) {
        super(context);
    }

    public CardOverlayView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardOverlayView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Set orientation.
     *
     * @param orientation orientation
     */
    public void setOrientation(final int orientation) {
        this.mOrientation = orientation;

        this.invalidate();
    }

    @Override
    protected void buildPath(@NonNull final Path path, final int viewWidth, final int viewHeight) {
        final int width;
        final int height;

        if (this.mOrientation == CardOverlayView.ORIENTATION_HORIZONTAL) {
            width = (int) (viewWidth * 0.8f);
            height = (int) (width / CardOverlayView.CARD_RATIO);
        } else if (this.mOrientation == CardOverlayView.ORIENTATION_VERTICAL) {
            height = (int) (viewHeight * 0.8f);
            width = (int) (height / CardOverlayView.CARD_RATIO);
        } else {
            width = 0;
            height = 0;
        }

        final int widthPadding = (viewWidth - width) / 2;
        final int heightPadding = (viewHeight - height) / 2;

        this.mRectF.set(widthPadding, heightPadding, viewWidth - widthPadding, viewHeight - heightPadding);

        path.addRoundRect(this.mRectF, 20, 20, Path.Direction.CW);
    }

    @Override
    public Rect getMaskBounds() {
        final Rect rect = new Rect();
        this.mRectF.round(rect);
        return rect;
    }
}

