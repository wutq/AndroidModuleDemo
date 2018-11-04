package com.wss.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wss.common.base.R;

import java.util.Locale;

/**
 * 水平滑动TabView
 * wtq 2016年6月28日14:09:46
 */
public class PagerSlidingTabStrip extends HorizontalScrollView {

    private static final String TAG = PagerSlidingTabStrip.class.getSimpleName();

    public interface IconTabProvider {
        int getPageIconResId(int position);
    }

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int tabCount;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private int indicatorColor = 0XFFFF7F00;//指示器颜色
    private int underlineColor = 0XFFB9B9B9;//上下横向颜色
    private int dividerColor = 0X00FF7F00;//水平分割线颜色
    private int textDefaultColor = 0XFF999999;//标题默认颜色
    private int textSelectColor = indicatorColor;//标题选中颜色

    private boolean shouldExpand = true;//tab是否平均分配在屏幕上
    private boolean textAllCaps = true;
    private boolean bold = false;//是否加粗
    private boolean topLine = true;//是否显示顶部横向
    private boolean bottomLine = true;//是否显示底部横向

    private int scrollOffset = 52;
    private int indicatorHeight = 4;
    private float underlineHeight = 0.5f;
    private int dividerPadding = 12;
    private int tabPadding = 17;
    private int dividerWidth = 1;
    private int redDotTop = 15;
    private int redDotWidth = 20;
    private int tabTextSize = 16;
    private int lastScrollX = 0;

    private Locale locale;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs,
                                int defStyle) {
        super(context, attrs, defStyle);

        setFillViewport(true);
        setWillNotDraw(false);

        tabsContainer = new LinearLayout(context);
        tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
        tabsContainer.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        addView(tabsContainer);

        DisplayMetrics dm = getResources().getDisplayMetrics();

        scrollOffset = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
        indicatorHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
        underlineHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
        dividerPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
        tabPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
        dividerWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
        tabTextSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);
        redDotTop = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, redDotTop, dm);
        redDotWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, redDotWidth, dm);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);

        indicatorColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstsIndicatorColor,
                indicatorColor);
        textSelectColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstsTextSelectedColor,
                textSelectColor);
        textDefaultColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstsTextDefaultColor,
                textDefaultColor);
        underlineColor = a.getColor(
                R.styleable.PagerSlidingTabStrip_pstsUnderlineColor,
                underlineColor);
        tabTextSize = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsTextSize, tabTextSize);

        dividerColor = a
                .getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor,
                        dividerColor);
        indicatorHeight = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight,
                indicatorHeight);
        bold = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsBold,
                bold);
        topLine = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsHasTopLine,
                topLine);
        bottomLine = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsHasBottomLine,
                bottomLine);
        dividerPadding = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsDividerPadding,
                dividerPadding);
        tabPadding = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight,
                tabPadding);
        tabPadding = a.getDimensionPixelSize(
                R.styleable.PagerSlidingTabStrip_pstsTextSize, tabPadding);

        shouldExpand = a
                .getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand,
                        shouldExpand);
        scrollOffset = a
                .getDimensionPixelSize(
                        R.styleable.PagerSlidingTabStrip_pstsScrollOffset,
                        scrollOffset);
        textAllCaps = a.getBoolean(
                R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);

        a.recycle();

        rectPaint = new Paint();
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Style.FILL);

        dividerPaint = new Paint();
        dividerPaint.setAntiAlias(true);
        dividerPaint.setStrokeWidth(dividerWidth);

        if (locale == null) {
            locale = getResources().getConfiguration().locale;
        }
    }


    public void setViewPager(ViewPager pager) {
        this.pager = pager;

        if (pager.getAdapter() == null) {
            throw new IllegalStateException(
                    "ViewPager does not have adapter instance.");
        }

        pager.addOnPageChangeListener(pageListener);
        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {

        tabsContainer.removeAllViews();

        tabCount = pager.getAdapter().getCount();

        for (int i = 0; i < tabCount; i++) {

            if (pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i,
                        ((IconTabProvider) pager.getAdapter())
                                .getPageIconResId(i));
            } else {
                String title = pager.getAdapter().getPageTitle(i).toString();
                addTextTab(i, title);
            }

        }

        updateTabStyles();

        getViewTreeObserver().addOnGlobalLayoutListener(
                new OnGlobalLayoutListener() {

                    @SuppressWarnings("deprecation")
                    @SuppressLint("NewApi")
                    @Override
                    public void onGlobalLayout() {

                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                            getViewTreeObserver().removeGlobalOnLayoutListener(
                                    this);
                        } else {
                            getViewTreeObserver().removeOnGlobalLayoutListener(
                                    this);
                        }

                        currentPosition = pager.getCurrentItem();
                        scrollToChild(currentPosition, 0);
                    }
                });

    }

    private void addTextTab(final int position, String title) {

        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setGravity(Gravity.CENTER);
        tab.setSingleLine();
        if (position != 0) {
            tab.setTextColor(textDefaultColor);
            tab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        } else {
            if (bold) {
                tab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }
            tab.setTextColor(textSelectColor);
        }

        addTab(position, tab);
    }

    private void addIconTab(final int position, int resId) {

        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);

        addTab(position, tab);

    }

    private void addTab(final int position, View tab) {
        tab.setFocusable(true);
        tab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPagerTabClick != null) {
                    onPagerTabClick.onClick(position);
                }
                pager.setCurrentItem(position);
            }
        });

        tab.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout = new LinearLayout(getContext());
        if (shouldExpand) {
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    LayoutParams.MATCH_PARENT, 1.0f));
        } else {
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LayoutParams.MATCH_PARENT));
        }

        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f));
        linearLayout.setPadding(tabPadding, 0, tabPadding, 0);
        linearLayout.setGravity(Gravity.CENTER);
//        ImageView view = new ImageView(getContext());
//        view.setLayoutParams(new LayoutParams(redDotWidth, redDotWidth));
//        view.setPadding(0, redDotTop, 0, 0);
//        view.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.red));
//        view.setVisibility(View.GONE);
        linearLayout.addView(tab);
//        linearLayout.addView(view);
        tabsContainer.addView(linearLayout);
    }

    private void updateTabStyles() {

        for (int i = 0; i < tabCount; i++) {

            View v = ((ViewGroup) tabsContainer.getChildAt(i)).getChildAt(0);

            if (v instanceof TextView) {

                TextView tab = (TextView) v;
                if (i != 0) {
                    tab.setTextColor(textDefaultColor);
                    tab.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    if (bold) {
                        tab.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }
                    tab.setTextColor(textSelectColor);
                }
                tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);

                tab.setAllCaps(textAllCaps);
            }
        }

    }

    private void scrollToChild(int position, int offset) {

        if (tabCount == 0) {
            return;
        }

        int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;

        if (position > 0 || offset > 0) {
            newScrollX -= scrollOffset;
        }

        if (newScrollX != lastScrollX) {
            lastScrollX = newScrollX;
            scrollTo(newScrollX, 0);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isInEditMode() || tabCount == 0) {
            return;
        }

        final int height = getHeight();

        // draw indicator line

        rectPaint.setColor(indicatorColor);

        // default: line below current tab
        ViewGroup currentTab = (ViewGroup) tabsContainer.getChildAt(currentPosition);
        if (currentTab == null) {
            return;
        }

        float lineLeft = currentTab.getLeft();
        float lineRight = currentTab.getRight();

        // if there is an offset, start interpolating left and right coordinates
        // between current and next tab
        if (currentPositionOffset > 0f && currentPosition < tabCount - 1) {
            View nextTab = tabsContainer.getChildAt(currentPosition + 1);
            final float nextTabLeft = nextTab.getLeft();
            final float nextTabRight = nextTab.getRight();

            lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset)
                    * lineLeft);
            lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset)
                    * lineRight);
        }

        canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height,
                rectPaint);

        // draw underline
        rectPaint.setColor(underlineColor);
        // 是否显示顶部横线
        if (topLine) {
            canvas.drawRect(0, 0, tabsContainer.getWidth(),
                    underlineHeight, rectPaint);
        }
        if (bottomLine) {
            canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(),
                    height, rectPaint);
        }
        // draw divider

        dividerPaint.setColor(dividerColor);
        for (int i = 0; i < tabCount - 1; i++) {
            View tab = tabsContainer.getChildAt(i);
            canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(),
                    height - dividerPadding, dividerPaint);
        }
    }

    private class PageListener implements OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

            currentPosition = position;
            currentPositionOffset = positionOffset;

            if (tabsContainer.getChildAt(position) != null)
                scrollToChild(position, (int) (positionOffset * tabsContainer
                        .getChildAt(position).getWidth()));

            invalidate();

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
            }

            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (delegatePageListener != null) {
                delegatePageListener.onPageSelected(position);
            }
            int count = tabsContainer.getChildCount();
            for (int i = 0; i < count; i++) {
                TextView textView = (TextView) ((ViewGroup) tabsContainer
                        .getChildAt(i)).getChildAt(0);
                if (position != i) {
                    textView.setTextColor(textDefaultColor);
                    textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                } else {
                    if (bold) {
                        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    }
                    textView.setTextColor(textSelectColor);
                }
            }
            if (onViewPagerRefresh != null) {
                onViewPagerRefresh.onRefresh(position);
            }
        }

    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    private OnViewPagerRefresh onViewPagerRefresh;

    public void setOnViewPagerRefresh(OnViewPagerRefresh onViewPagerRefresh) {
        this.onViewPagerRefresh = onViewPagerRefresh;
    }

    private OnPagerTabClick onPagerTabClick;

    public void setOnPagerTabClick(OnPagerTabClick onPagerTabClick) {
        this.onPagerTabClick = onPagerTabClick;
    }

    public interface OnViewPagerRefresh {
        void onRefresh(int position);
    }

    public interface OnPagerTabClick {
        void onClick(int position);
    }

    public void showPosition(int position) {
        View view = tabsContainer.getChildAt(position);
        if (view != null) {
            ViewGroup viewGroup = (ViewGroup) view;
            viewGroup.getChildAt(1).setVisibility(View.VISIBLE);
        }
    }

    /**
     * 下方滑动指示器颜色
     */
    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    /**
     * 下方滑动指示器颜色
     */
    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 指示器高度
     */
    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    /**
     * 顶部底部横线颜色
     */
    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    /**
     * 顶部底部横线颜色
     */
    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 顶部底部横线高度
     */
    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    /**
     * 水平分割线颜色
     */
    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    /**
     * 水平分割线颜色
     */
    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 水平分割线padding
     */
    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    /**
     * 滑动偏移量
     */
    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    /**
     * Item是否平分布局
     */
    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    /**
     * Item是否全部大写
     */
    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
        invalidate();
    }

    /**
     * 文字大小
     */
    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }


    /**
     * 文字默认颜色
     */
    public void setTextDefaultColor(int textDefaultColor) {
        this.textDefaultColor = textDefaultColor;
        invalidate();
    }

    /**
     * 文字默认颜色
     */
    public void setTextDefaultColorResource(int resId) {
        this.textDefaultColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 文字选中颜色
     */
    public void setTextSelectColor(int textSelectColor) {
        this.textSelectColor = textDefaultColor;
        invalidate();
    }

    /**
     * 文字选中颜色
     */
    public void setTextSelectColorResource(int resId) {
        this.textSelectColor = getResources().getColor(resId);
        invalidate();
    }

    /**
     * 设置item左右padding
     */
    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    /**
     * 选中Item是否需要加粗
     */
    public void setBold(boolean bold) {
        this.bold = bold;
        invalidate();
    }

    /**
     * 是否显示顶部横线
     */
    public void setShowTopLine(boolean show) {
        this.topLine = show;
        invalidate();
    }


}
