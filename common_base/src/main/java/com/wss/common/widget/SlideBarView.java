package com.wss.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wss.common.utils.PxUtils;

/**
 * Describe:选择城市右边的字母栏，可以滚动选择开头字母
 * Created by 吴天强 on 2018/11/1.
 */
public class SlideBarView extends View {


    public static final char[] SPECIAL_CHAR_INDEX = {'#', 'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private char[] indexs = SPECIAL_CHAR_INDEX;

    Paint mPaint = null;

    public interface OnSlideBarBaseViewFlipListener {

        void onFlip(int index, String mChar);

        void onFlipUp();
    }

    private OnSlideBarBaseViewFlipListener flipListener = null;

    /**
     * Set SlideBar OnFlip Listener
     */
    public void setFlipListener(OnSlideBarBaseViewFlipListener mListener) {
        this.flipListener = mListener;
    }

    public SlideBarView(Context context) {
        super(context);
        init();
    }

    public SlideBarView(Context context, AttributeSet attrs,
                        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SlideBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setTextSize(PxUtils.dp2px(14));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setColor(Color.parseColor("#FF333333"));
        mPaint.setTypeface(Typeface.DEFAULT_BOLD);
        mPaint.setAntiAlias(true);
    }

    /**
     * 設置索引
     */
    public void setIndexs(char[] wantedIndexs) {
        this.indexs = wantedIndexs;
        invalidate();
    }

    public void setTextColor(int color) {
        mPaint.setColor(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //均分
        int itemHeight = getHeight() / SPECIAL_CHAR_INDEX.length;
        //计算从哪一个开始
        float startHeight = 0.0f;
        if (indexs.length <= SPECIAL_CHAR_INDEX.length) {
            startHeight = ((SPECIAL_CHAR_INDEX.length - indexs.length) >> 1) * itemHeight;
        }
        for (int i = 0; i < indexs.length; i++) {
            float xPos = getWidth() >> 1;
            float yPos;
            if (startHeight == 0) {
                //数目超过既定最大数 使用另一种算法
                yPos = (getHeight() >> indexs.length) * i + getHeight() >> indexs.length;
            } else {
                yPos = startHeight + itemHeight * i;
            }
            canvas.drawText(String.valueOf(indexs[i]), xPos, yPos, mPaint);
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchY = event.getY();
        float everyHeight = ((float) getHeight() / indexs.length);
        int touchId = (int) (touchY / everyHeight);
        if (touchId < 0) {
            touchId = 0;
        }
        if (touchId > indexs.length - 1) {
            touchId = indexs.length - 1;
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN
                || event.getAction() == MotionEvent.ACTION_MOVE) {
            if (this.flipListener != null) {
                this.flipListener.onFlip(touchId, String.valueOf(indexs[touchId]));
            }
        } else {
            if (this.flipListener != null) {
                this.flipListener.onFlipUp();
            }
        }
        return true;
    }
}
