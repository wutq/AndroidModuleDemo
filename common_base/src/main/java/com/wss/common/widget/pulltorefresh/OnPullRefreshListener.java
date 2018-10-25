package com.wss.common.widget.pulltorefresh;


/**
 * 刷新 加载监听
 */
public interface OnPullRefreshListener {


    /**
     * 刷新
     */
    void onRefresh();

    /**
     * 加载更多
     */
    void onLoadMore();
}
