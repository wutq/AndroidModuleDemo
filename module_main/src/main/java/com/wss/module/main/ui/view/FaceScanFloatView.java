package com.wss.module.main.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.wss.common.utils.PxUtils;
import com.wss.module.main.R;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Describe：人脸扫描动画浮层
 * Created by 吴天强 on 2020/11/20.
 */
public class FaceScanFloatView extends View {
    /**
     * 动画时间
     */
    private static final int ANIM_TIME = 1000;
    /**
     * 圆心偏移量 正值 往下 负值往上
     */
    private static final int CIRCLE_OFFSET = 120;


    private Path mPath;
    /**
     * 圆圈属性动画
     */
    private ValueAnimator mAnimator;
    /**
     * 已完成之后提示文字的动画
     */
    private ValueAnimator completeAnimator;

    /**
     * 中心圆画笔
     */
    private Paint innerCirclePaint;
    /**
     * 外圆画笔
     */
    private Paint outerCirclePaint;
    /**
     * 提示文案画笔
     */
    private Paint textPaint;
    /**
     * 成功的Icon画笔
     */
    private Paint completeIconPaint;

    /**
     * 画提示文案的矩阵
     */
    private Rect textRect;
    /**
     * 圆圈以外区域背景颜色
     */
    private int mBackgroundColor = 0xAF000000;

    /**
     * 圆圈的最大半径
     */
    private int radius = 300;
    /**
     * 扫描完成显示的圆圈半径
     */
    private int completeRadius;
    /**
     * 提示文案
     */
    private String tipsText = "请正面面对摄像头";
    /**
     * 已完成的文案
     */
    private String completeText;
    /**
     * 俩圆间距
     */
    private int circleSpacing = 30;
    /**
     * 提示文案距离外圆间距
     */
    private int textToCircleSpacing = 100;

    private Bitmap completeBitMap;
    private boolean isComplete;


    public FaceScanFloatView(Context context) {
        super(context);
        init();
    }

    public FaceScanFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceScanFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        innerCirclePaint = new Paint();
        innerCirclePaint.setStyle(Paint.Style.STROKE);
        innerCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.transparent));
        innerCirclePaint.setStrokeWidth(PxUtils.dp2px(1));
        innerCirclePaint.setAntiAlias(true);

        outerCirclePaint = new Paint();
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.theme));
        outerCirclePaint.setStrokeWidth(PxUtils.dp2px(1));
        outerCirclePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(ContextCompat.getColor(getContext(), R.color.white));
        textPaint.setTextSize(PxUtils.sp2px(16));
        textPaint.setTextAlign(Paint.Align.LEFT);

        completeIconPaint = new Paint();

        mPath = new Path();
        textRect = new Rect();
        mAnimator = new ValueAnimator();
        completeAnimator = new ValueAnimator();
//        completeBitMap = ((BitmapDrawable) getContext().getDrawable(R.drawable.ahtu_page_icon_rfcg)).getBitmap();
        completeBitMap = ((BitmapDrawable) getContext().getDrawable(R.drawable.icon_loading)).getBitmap();
    }

    @Override
    public void setBackgroundColor(@ColorInt final int color) {
        mBackgroundColor = color;
        invalidate();
    }

    /**
     * 设置外圆的颜色
     *
     * @param color 颜色
     */
    public void setMaskPathColor(@ColorInt final int color) {
        outerCirclePaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        //计算圆心
        int circleX = getWidth() / 2;
        int circleY = getHeight() / 2 - CIRCLE_OFFSET;

        if (isComplete && radius <= completeRadius) {
            //完全圆设置画笔透明
            outerCirclePaint.setColor(ContextCompat.getColor(getContext(), R.color.transparent));
            //画已完成的icon
            canvas.drawBitmap(getCircleBitmap(completeBitMap, completeBitMap.getWidth(), completeBitMap.getHeight()),
                    circleX - radius, circleY - radius, completeIconPaint);
            this.tipsText = completeText;
            if (isRotate) {
                handler.postDelayed(runnable, 16);
            }
        }
        //处理画布和中空内圆
        mPath.reset();
        mPath.addCircle(circleX, circleY, radius, Path.Direction.CCW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipOutPath(mPath);
            canvas.drawColor(mBackgroundColor);
            canvas.restore();
            canvas.drawPath(mPath, innerCirclePaint);
        } else {
            canvas.clipPath(mPath, Region.Op.DIFFERENCE);
            canvas.drawColor(mBackgroundColor);
            canvas.restore();
            canvas.drawPath(mPath, innerCirclePaint);
        }
        //添加外圆
        canvas.drawCircle(circleX, circleY, radius + circleSpacing, outerCirclePaint);

        //添加提示文案
        textPaint.getTextBounds(tipsText, 0, tipsText.length(), textRect);
        int textX = getWidth() / 2 - textRect.width() / 2;
        //文字的Y轴位置 = 圆心Y轴 + 圆半径 + 俩圆间距 +文字与圆间距
        int textY = circleY + radius + circleSpacing + textToCircleSpacing;
        canvas.drawText(tipsText, textX, textY, textPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Logger.e("onMeasure");
    }

    /**
     * 设置圆圈的最大半径
     *
     * @param radius 半径
     */
    public void setMaxRadius(int radius) {
        this.radius = radius;
        invalidate();
    }


    /**
     * 设置俩圆的间距
     *
     * @param circleSpacing 间距
     */
    public void setCircleSpacing(int circleSpacing) {
        this.circleSpacing = circleSpacing;
        invalidate();
    }

    /**
     * 设置提示文案距离外圆的间距
     *
     * @param textToCircleSpacing 间距
     */
    public void setTextToCircleSpacing(int textToCircleSpacing) {
        this.textToCircleSpacing = textToCircleSpacing;
        invalidate();
    }

    /**
     * 设置提示文案
     *
     * @param tipsText 提示文案
     */
    public void setTipsText(String tipsText) {
        this.tipsText = tipsText;
        invalidate();
    }

    /**
     * 设置提示文案颜色
     *
     * @param color 颜色
     */
    public void setTipsTextColor(int color) {
        textPaint.setColor(ContextCompat.getColor(getContext(), color));
        invalidate();
    }

    /**
     * 设置提示文案大小
     *
     * @param size 大小
     */
    public void setTipsTextSize(int size) {
        textPaint.setTextSize(PxUtils.sp2px(size));
        invalidate();
    }

    /**
     * 设置是否完成
     */
    public void isComplete() {
        isComplete("");
    }


    /**
     * 设置是否完成
     *
     * @param tipsText 提示文案
     */
    public void isComplete(String tipsText) {
        this.isComplete = true;
        this.completeText = tipsText;
        //计算半径 已完成 中间空心圆半径为完成icon的半径
        completeRadius = completeBitMap.getWidth() / 2;
        startAnimator(radius, completeRadius);
        startCompleteAnimator();
    }


    /**
     * 设置完成的icon
     *
     * @param drawable icon
     */
    public void setCompleteImage(int drawable) {
        completeBitMap = ((BitmapDrawable) getContext().getDrawable(drawable)).getBitmap();
        invalidate();
    }

    /**
     * 开始动画
     */
    public void startAnimator() {
        startAnimator(radius, radius * 5 / 7);
    }

    /**
     * 开始动画
     *
     * @param maxRadius 最大半径
     * @param minRadius 最小半径
     */
    private void startAnimator(int maxRadius, int minRadius) {
        Logger.e("startAnimator");
        mAnimator = ValueAnimator.ofInt(maxRadius, minRadius);
        mAnimator.setDuration(ANIM_TIME);
        mAnimator.addUpdateListener(animation -> {
            radius = (int) animation.getAnimatedValue();
            invalidate();
        });
        mAnimator.start();
    }

    /**
     * 开始动画
     */
    private void startCompleteAnimator() {
        completeAnimator = ValueAnimator.ofInt(textToCircleSpacing, 50);
        completeAnimator.setDuration(ANIM_TIME);
        completeAnimator.addUpdateListener(animation -> {
            textToCircleSpacing = (int) animation.getAnimatedValue();
            invalidate();
        });
        completeAnimator.start();
    }

    private Bitmap bitmap;
    private boolean isCreateBitmap = false;
    private Canvas canvas;
    private PorterDuffXfermode pdf;
    private Paint bitmapPaint;
    private float rotateSD = 0.8f;//每次旋转的角度--建议范围0.1f-1，否则会抖动
    private boolean isRotate = true;//控制是否旋转

    private Bitmap getCircleBitmap(Bitmap image, int width, float rotate) {
        if (!isCreateBitmap) {
            //节约资源所以这些代码只需要执行一次
            bitmapPaint = new Paint();
            bitmapPaint.setAntiAlias(true);
            bitmapPaint.setDither(true);
            bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
            isCreateBitmap = true;
            canvas = new Canvas(bitmap);
            canvas.drawCircle(width / 2, width / 2, width / 2, bitmapPaint);
            pdf = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        }
        bitmapPaint.setXfermode(pdf);
        canvas.rotate(rotate, width / 2, width / 2);
        canvas.drawBitmap(image, 0, 0, bitmapPaint);
        bitmapPaint.setXfermode(null);
        return bitmap;
    }

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();//刷新界面
        }
    };

}
