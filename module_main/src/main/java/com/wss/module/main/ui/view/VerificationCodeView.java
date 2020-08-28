package com.wss.module.main.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.wss.module.main.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

/**
 * 自定义View  验证码
 * Created by 吴天强 on 2017/8/29.
 */
public class VerificationCodeView extends View {

    /**
     * 控件宽度
     */
    private int mWidth;
    /**
     * 控件高度
     */
    private int mHeight;

    private int mTextSize;
    /**
     * 绘制验证码的画笔
     */
    private Paint mTextPaint;
    /**
     * 绘制干扰点画笔
     */
    private Paint mPointPaint;
    /**
     * 绘制干扰线画笔
     */
    private Paint mPathPaint;

    /**
     * 干扰点坐标集合
     */
    private List<PointF> mPoints = new ArrayList<>();
    private Random mRandom = new Random();

    /**
     * 绘制贝塞尔曲线的路径集合
     */
    private List<Path> mPaths = new ArrayList<>();
    private String mText;
    private int mTextCount;

    /**
     * 验证码字符串的显示宽度
     */
    private float mTextWidth;
    private OnCodeChangeListener onCodeChangeListener;


    public VerificationCodeView(Context context) {
        this(context, null);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerificationCodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView);

        mTextSize = a.getDimensionPixelSize(R.styleable.VerificationCodeView_textSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        mTextCount = a.getInt(R.styleable.VerificationCodeView_textCount, 4);
        a.recycle();
        setOnClickListener(v -> resetCode());
        init();
    }


    /**
     * 测量方法
     * EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
     * AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
     * UNSPECIFIED：表示子布局想要多大就多大，很少使用
     */
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
                mWidth = Math.min((int) (mTextWidth * 1.8f), specSize);

            }
        }
        //测量高度
        specMode = MeasureSpec.getMode(heightMeasureSpec);
        specSize = MeasureSpec.getSize(heightMeasureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            mHeight = specSize;
        } else {
            if (specMode == MeasureSpec.AT_MOST) {
                mHeight = Math.min((int) (mTextWidth * 16f), specSize);
            }
        }
        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initData();
        float charLength = mTextWidth / mTextCount;
        for (int i = 1; i <= mTextCount; i++) {
            int offsetDegree = mRandom.nextInt(15);
            // 这里只会产生0和1，如果是1那么正旋转正角度，否则旋转负角度
            offsetDegree = mRandom.nextInt(2) == 1 ? offsetDegree : -offsetDegree;
            canvas.save();
            canvas.rotate(offsetDegree, mWidth >> 1, mHeight >> 1);
            // 给画笔设置随机颜色
            mTextPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
            canvas.drawText(String.valueOf(mText.charAt(i - 1)), (i - 1) * charLength * 1.6f + 30, mHeight * 2 / 3f, mTextPaint);
            canvas.restore();
        }

        // 产生干扰效果1 -- 干扰点
        for (PointF pointF : mPoints) {
            mPointPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
            canvas.drawPoint(pointF.x, pointF.y, mPointPaint);
        }

        // 产生干扰效果2 -- 干扰线
        for (Path path : mPaths) {
            mPathPaint.setARGB(255, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20, mRandom.nextInt(200) + 20);
            canvas.drawPath(path, mPathPaint);
        }
    }


    /**
     * 重置验证码
     */
    private void resetCode() {
        mText = getCharAndNum(mTextCount);
        invalidate();
        if (onCodeChangeListener != null) {
            onCodeChangeListener.onCodeChanged(mText);
        }
    }

    /**
     * 初始化
     */
    private void init() {

        mText = getCharAndNum(mTextCount);

        //初始化验证码画笔
        mTextPaint = new Paint();
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setTextSize(mTextSize);

        //初始化干扰点画笔
        mPointPaint = new Paint();
        mPointPaint.setStrokeWidth(6);
        // 设置断点处为圆形
        mPointPaint.setStrokeCap(Paint.Cap.ROUND);

        //初始化干扰线画笔
        mPathPaint = new Paint();
        mPathPaint.setStrokeWidth(5);
        mPathPaint.setColor(Color.GRAY);
        // 设置画笔为空心
        mPathPaint.setStyle(Paint.Style.STROKE);
        // 设置断点处为圆形
        mPathPaint.setStrokeCap(Paint.Cap.ROUND);
        // 取得验证码字符串显示的宽度值
        mTextWidth = mTextPaint.measureText(mText);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mPoints.clear();
        // 生成干扰点坐标
        for (int i = 0; i < 150; i++) {
            PointF pointF = new PointF(mRandom.nextInt(mWidth) + 10, mRandom.nextInt(mHeight) + 10);
            mPoints.add(pointF);
        }

        mPaths.clear();
        // 生成干扰线坐标
        for (int i = 0; i < (mRandom.nextInt(10) % 3) + 1; i++) {
            Path path = new Path();
            int startX = mRandom.nextInt(mWidth / 3) + 10;
            int startY = mRandom.nextInt(mHeight / 3) + 10;
            int endX = mRandom.nextInt(mWidth / 2) + mWidth / 2 - 10;
            int endY = mRandom.nextInt(mHeight / 2) + mHeight / 2 - 10;
            path.moveTo(startX, startY);
            path.quadTo(Math.abs(endX - startX) >> 1, Math.abs(endY - startY) >> 1, endX, endY);
            mPaths.add(path);
        }

    }

    /**
     * 设置验证码字体大小
     *
     * @param textSize 字体大小 单位sp
     */
    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
    }

    /**
     * 设置验证位数
     *
     * @param textCount 位数
     */
    public void setTextCount(int textCount) {
        this.mTextCount = textCount;
    }

    /**
     * 得到验证码
     *
     * @return String
     */
    public String getText() {
        return mText;
    }

    /**
     * 当验证码
     *
     * @param listener 监听
     */
    public void addOnCodeChangeListener(OnCodeChangeListener listener) {
        this.onCodeChangeListener = listener;
    }


    /**
     * 随机生成字符串和数值
     *
     * @param length length
     * @return String
     */
    @NotNull
    private String getCharAndNum(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (choice + random.nextInt(26)));
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                // 数字
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }


    /**
     * 验证码发生变化监听
     */
    public interface OnCodeChangeListener {

        /**
         * 验证码发生了变化
         *
         * @param code 验证码
         */
        void onCodeChanged(String code);
    }
}
