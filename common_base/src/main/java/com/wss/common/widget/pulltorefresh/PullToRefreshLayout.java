package com.wss.common.widget.pulltorefresh;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.wss.common.base.R;
import com.wss.common.utils.PxUtils;


/**
 * 下拉刷新 上拉加载更多控件
 */
public class PullToRefreshLayout extends FrameLayout {

    private IRefreshView mHeaderView;
    private IRefreshView mFooterView;
    private View mChildView;

    private static final long ANIM_TIME = 300;
    private static int HEAD_HEIGHT = 60;
    private static int FOOT_HEIGHT = 60;

    private static int head_height;
    private static int head_height_2;
    private static int foot_height;
    private static int foot_height_2;

    private float mTouchY;
    private float mCurrentY;

    private boolean canLoadMore = true;
    private boolean canRefresh = true;
    private boolean isRefresh;
    private boolean isLoadMore;

    //滑动的最小距离
    private int mTouchSlope;

    private OnPullRefreshListener refreshListener;


    private View loadingView, errorView, emptyView;
    private int loading = R.layout.layout_loading, empty = R.layout.layout_empty, error = R.layout.layout_error;

    public void setOnPullRefreshListener(OnPullRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public PullToRefreshLayout(Context context) {
        this(context, null);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefreshLayout, defStyleAttr, 0);
        error = a.getResourceId(R.styleable.PullToRefreshLayout_view_error, error);
        loading = a.getResourceId(R.styleable.PullToRefreshLayout_view_loading, loading);
        empty = a.getResourceId(R.styleable.PullToRefreshLayout_view_empty, empty);
        a.recycle();
        init();
    }

    private void cal() {
        head_height = PxUtils.dp2px(getContext(), HEAD_HEIGHT);
        foot_height = PxUtils.dp2px(getContext(), FOOT_HEIGHT);
        head_height_2 = PxUtils.dp2px(getContext(), HEAD_HEIGHT * 2);
        foot_height_2 = PxUtils.dp2px(getContext(), FOOT_HEIGHT * 2);

        mTouchSlope = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private void init() {
        cal();
        int count = getChildCount();
        if (count != 1) {
            new IllegalArgumentException("child only can be one");
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mChildView = getChildAt(0);
        addHeadView();
        addFooterView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    private void addHeadView() {
        if (mHeaderView == null) {
            mHeaderView = new RefreshView(getContext());
        } else {
            removeView(mHeaderView.getView());
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        mHeaderView.getView().setLayoutParams(layoutParams);
        if (mHeaderView.getView().getParent() != null)
            ((ViewGroup) mHeaderView.getView().getParent()).removeAllViews();
        addView(mHeaderView.getView(), 0);
    }

    private void addFooterView() {
        if (mFooterView == null) {
            mFooterView = new RefreshView(getContext());
            mFooterView.setType(false);
        } else {
            removeView(mFooterView.getView());
        }
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        layoutParams.gravity = Gravity.BOTTOM;
        mFooterView.getView().setLayoutParams(layoutParams);
        if (mFooterView.getView().getParent() != null)
            ((ViewGroup) mFooterView.getView().getParent()).removeAllViews();
        addView(mFooterView.getView());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!canLoadMore && !canRefresh) return super.onInterceptTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchY = ev.getY();
                mCurrentY = mTouchY;
                break;
            case MotionEvent.ACTION_MOVE:
                float currentY = ev.getY();
                float dy = currentY - mCurrentY;
                if (canRefresh) {
                    boolean canChildScrollUp = canChildScrollUp();
                    if (dy > mTouchSlope && !canChildScrollUp) {
                        mHeaderView.begin();
                        return true;
                    }
                }
                if (canLoadMore) {
                    boolean canChildScrollDown = canChildScrollDown();
                    if (dy < -mTouchSlope && !canChildScrollDown) {
                        mFooterView.begin();
                        return true;
                    }
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isRefresh || isLoadMore) return true;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mCurrentY = event.getY();
                float dura = (mCurrentY - mTouchY) / 3.0f;
                if (dura > 0 && canRefresh) {
                    dura = Math.min(head_height_2, dura);
                    dura = Math.max(0, dura);
                    mHeaderView.getView().getLayoutParams().height = (int) dura;
                    ViewCompat.setTranslationY(mChildView, dura);
                    requestLayout();
                    mHeaderView.progress(dura, head_height);
                } else {
                    if (canLoadMore) {
                        dura = Math.min(foot_height_2, Math.abs(dura));
                        dura = Math.max(0, Math.abs(dura));
                        mFooterView.getView().getLayoutParams().height = (int) dura;
                        ViewCompat.setTranslationY(mChildView, -dura);
                        requestLayout();
                        mFooterView.progress(dura, foot_height);
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                float currentY = event.getY();
                final int dy1 = (int) (currentY - mTouchY) / 3;
                if (dy1 > 0 && canRefresh) {
                    if (dy1 >= head_height) {
                        createAnimatorTranslationY(State.REFRESH,
                                dy1 > head_height_2 ? head_height_2 : dy1, head_height,
                                new CallBack() {
                                    @Override
                                    public void onSuccess() {
                                        isRefresh = true;
                                        if (refreshListener != null) {
                                            refreshListener.onRefresh();
                                        }
                                        mHeaderView.loading();
                                    }
                                });
                    } else if (dy1 > 0 && dy1 < head_height) {
                        setFinish(dy1, State.REFRESH);
                        mHeaderView.normal();
                    }
                } else {
                    if (canLoadMore) {
                        if (Math.abs(dy1) >= foot_height) {
                            createAnimatorTranslationY(State.LOAD_MORE, Math.abs(dy1) > foot_height_2 ? foot_height_2 : Math.abs(dy1), foot_height, new CallBack() {
                                @Override
                                public void onSuccess() {
                                    isLoadMore = true;
                                    if (refreshListener != null) {
                                        refreshListener.onLoadMore();
                                    }
                                    mFooterView.loading();
                                }
                            });
                        } else {
                            setFinish(Math.abs(dy1), State.LOAD_MORE);
                            mFooterView.normal();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean canChildScrollDown() {
        return mChildView != null && ViewCompat.canScrollVertically(mChildView, 1);
    }

    private boolean canChildScrollUp() {
        return mChildView != null && ViewCompat.canScrollVertically(mChildView, -1);
    }

    /**
     * 创建动画
     */
    public void createAnimatorTranslationY(@State.REFRESH_STATE final int state, final int start,
                                           final int purpose, final CallBack callBack) {
        final ValueAnimator anim;
        anim = ValueAnimator.ofInt(start, purpose);
        anim.setDuration(ANIM_TIME);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int value = (int) valueAnimator.getAnimatedValue();
                if (state == State.REFRESH) {
                    mHeaderView.getView().getLayoutParams().height = value;
                    ViewCompat.setTranslationY(mChildView, value);
                    if (purpose == 0) { //代表结束加载
                        mHeaderView.finishing(value, head_height_2);
                    } else {
                        mHeaderView.progress(value, head_height);
                    }
                } else {
                    mFooterView.getView().getLayoutParams().height = value;
                    ViewCompat.setTranslationY(mChildView, -value);
                    if (purpose == 0) { //代表结束加载
                        mFooterView.finishing(value, head_height_2);
                    } else {
                        mFooterView.progress(value, foot_height);
                    }
                }
                if (value == purpose) {
                    if (callBack != null)
                        callBack.onSuccess();
                }
                requestLayout();


            }

        });
        anim.start();
    }

    /**
     * 结束下拉刷新
     */
    private void setFinish(int height, @State.REFRESH_STATE final int state) {
        createAnimatorTranslationY(state, height, 0, new CallBack() {
            @Override
            public void onSuccess() {
                if (state == State.REFRESH) {
                    isRefresh = false;
                    mHeaderView.normal();

                } else {
                    isLoadMore = false;
                    mFooterView.normal();
                }
            }
        });
    }

    private void setFinish(@State.REFRESH_STATE int state) {
        if (state == State.REFRESH) {
            if (mHeaderView != null && mHeaderView.getView().getLayoutParams().height > 0 && isRefresh) {
                setFinish(head_height, state);
            }
        } else {
            if (mFooterView != null && mFooterView.getView().getLayoutParams().height > 0 && isLoadMore) {
                setFinish(foot_height, state);
            }
        }
    }

    public interface CallBack {
        void onSuccess();
    }

    private void showLoadingView() {
        if (loadingView == null) {
            loadingView = LayoutInflater.from(getContext()).inflate(loading, null);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(loadingView, layoutParams);
        } else {
            loadingView.setVisibility(VISIBLE);
        }
    }

    private void showEmptyView() {
        if (emptyView == null) {
            emptyView = LayoutInflater.from(getContext()).inflate(empty, null);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(emptyView, layoutParams);
        } else {
            emptyView.setVisibility(VISIBLE);
        }
    }

    private void showErrorView() {
        if (errorView == null) {
            errorView = LayoutInflater.from(getContext()).inflate(error, null);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            addView(errorView, layoutParams);
        } else {
            errorView.setVisibility(VISIBLE);
        }
    }

    private void hideView(View view) {
        if (view != null)
            view.setVisibility(GONE);
    }

    private void switchView(int status) {
        switch (status) {
            case ViewStatus.CONTENT_STATUS:
                hideView(loadingView);
                hideView(emptyView);
                hideView(errorView);

                mChildView.setVisibility(VISIBLE);
                break;
            case ViewStatus.LOADING_STATUS:
                hideView(mChildView);
                hideView(emptyView);
                hideView(errorView);

                showLoadingView();
                break;
            case ViewStatus.EMPTY_STATUS:
                hideView(mChildView);
                hideView(loadingView);
                hideView(errorView);

                showEmptyView();
                break;
            case ViewStatus.ERROR_STATUS:
                hideView(mChildView);
                hideView(loadingView);
                hideView(emptyView);

                showErrorView();
                break;
            default:
                hideView(loadingView);
                hideView(emptyView);
                hideView(errorView);

                mChildView.setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 设置展示view (error,empty,loading)
     */
    public void showView(@ViewStatus.VIEW_STATUS int status) {
        switchView(status);
    }

    /**
     * 获取view (error,empty,loading)
     */
    public View getView(@ViewStatus.VIEW_STATUS int status) {
        switch (status) {
            case ViewStatus.EMPTY_STATUS:
                return emptyView;
            case ViewStatus.LOADING_STATUS:
                return loadingView;
            case ViewStatus.ERROR_STATUS:
                return errorView;
            case ViewStatus.CONTENT_STATUS:
                return mChildView;
        }
        return null;
    }

    public void autoRefresh() {
        createAnimatorTranslationY(State.REFRESH,
                0, head_height,
                new CallBack() {
                    @Override
                    public void onSuccess() {
                        isRefresh = true;
                        if (refreshListener != null) {
                            refreshListener.onRefresh();
                        }
                        mHeaderView.loading();
                    }
                });
    }

    /**
     * 结束刷新
     */
    public void finishRefresh() {
        setFinish(State.REFRESH);
    }

    /**
     * 结束加载更多
     */
    public void finishLoadMore() {
        setFinish(State.LOAD_MORE);
    }

    /**
     * 设置是否启用加载更多
     */
    public void setCanLoadMore(boolean canLoadMore) {
        this.canLoadMore = canLoadMore;
    }

    /**
     * 设置是否启用下拉刷新
     */
    public void setCanRefresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }

    /**
     * 设置是下拉刷新头部
     *
     * @param mHeaderView 需实现 HeadView 接口
     */
    public void setHeaderView(IRefreshView mHeaderView) {
        this.mHeaderView = mHeaderView;
        addHeadView();
    }

    /**
     * 设置是下拉刷新尾部
     *
     * @param mFooterView 需实现 IRefreshView 接口
     */
    public void setFooterView(IRefreshView mFooterView) {
        this.mFooterView = mFooterView;
        addFooterView();
    }


    /**
     * 设置刷新控件的高度
     *
     * @param dp 单位为dp
     */
    public void setHeadHeight(int dp) {
        head_height = PxUtils.dp2px(getContext(), dp);
    }

    /**
     * 设置加载更多控件的高度
     *
     * @param dp 单位为dp
     */
    public void setFootHeight(int dp) {
        foot_height = PxUtils.dp2px(getContext(), dp);
    }

    /**
     * 同时设置加载更多控件和刷新控件的高度
     *
     * @param dp 单位为dp
     */
    public void setAllHeight(int dp) {
        head_height = PxUtils.dp2px(getContext(), dp);
        foot_height = PxUtils.dp2px(getContext(), dp);
    }

    /**
     * 同时设置加载更多控件和刷新控件的高度
     *
     * @param refresh  刷新控件的高度 单位为dp
     * @param loadMore 加载控件的高度 单位为dp
     */
    public void setAllHeight(int refresh, int loadMore) {
        head_height = PxUtils.dp2px(getContext(), refresh);
        foot_height = PxUtils.dp2px(getContext(), loadMore);
    }

    /**
     * 设置刷新控件的下拉的最大高度 且必须大于本身控件的高度  最佳为2倍
     *
     * @param dp 单位为dp
     */
    public void setMaxHeadHeight(int dp) {
        if (head_height >= PxUtils.dp2px(getContext(), dp)) {
            return;
        }
        head_height_2 = PxUtils.dp2px(getContext(), dp);
    }

    /**
     * 设置加载更多控件的上拉的最大高度 且必须大于本身控件的高度  最佳为2倍
     *
     * @param dp 单位为dp
     */
    public void setMaxFootHeight(int dp) {
        if (foot_height >= PxUtils.dp2px(getContext(), dp)) {
            return;
        }
        foot_height_2 = PxUtils.dp2px(getContext(), dp);
    }

    /**
     * 同时设置加载更多控件和刷新控件的最大高度 且必须大于本身控件的高度  最佳为2倍
     *
     * @param dp 单位为dp
     */
    public void setAllMaxHeight(int dp) {
        if (head_height >= PxUtils.dp2px(getContext(), dp)) {
            return;
        }
        if (foot_height >= PxUtils.dp2px(getContext(), dp)) {
            return;
        }
        head_height_2 = PxUtils.dp2px(getContext(), dp);
        foot_height_2 = PxUtils.dp2px(getContext(), dp);
    }

    /**
     * 同时设置加载更多控件和刷新控件的最大高度 且必须大于本身控件的高度  最佳为2倍
     *
     * @param refresh  刷新控件下拉的最大高度 单位为dp
     * @param loadMore 加载控件上拉的最大高度 单位为dp
     */
    public void setAllMaxHeight(int refresh, int loadMore) {
        if (head_height >= PxUtils.dp2px(getContext(), refresh)) {
            return;
        }
        if (foot_height >= PxUtils.dp2px(getContext(), loadMore)) {
            return;
        }
        head_height_2 = PxUtils.dp2px(getContext(), refresh);
        foot_height_2 = PxUtils.dp2px(getContext(), loadMore);
    }


}
