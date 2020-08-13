package com.wss.common.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wss.common.bean.Event;
import com.wss.common.bean.HorizontalTabTitle;
import com.wss.common.utils.ValidUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe：所有Fragment的基类
 * Created by 吴天强 on 2018/10/17.
 */
public abstract class BaseFragment extends Fragment {

    protected Context context;
    private ViewStub emptyView;
    private View rootView;
    private Unbinder unBinder;
    private HorizontalTabTitle tabTitle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.layout_base, container, false);
        ((ViewGroup) rootView.findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(), null));
        unBinder = ButterKnife.bind(this, rootView);
        if (registerEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unBinder != null) {
            unBinder.unbind();
        }
        if (registerEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    //***************************************空页面方法 start *************************************

    /**
     * 数据为空页面
     */
    protected void showEmptyView() {
        showEmptyView(getString(R.string.no_data));
    }

    /**
     * 数据为空页面
     *
     * @param text 显示文案
     */
    protected void showEmptyView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_data, false);
    }


    /**
     * 请求数据报错页面
     */
    protected void showErrorView() {
        showErrorView(getString(R.string.network_error_server_error));
    }

    /**
     * 请求数据报错页面
     *
     * @param text 显示文案
     */
    protected void showErrorView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_net, true);
    }


    /**
     * 请求数据报错或者为空页面
     *
     * @param text      提示文案
     * @param iconResId 显示的icon
     */
    public void showEmptyOrErrorView(String text, int iconResId, boolean showRefreshButton) {
        if (emptyView == null) {
            emptyView = rootView.findViewById(R.id.vs_empty);
        }
        emptyView.setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.iv_empty).setBackgroundResource(iconResId);
        ((TextView) rootView.findViewById(R.id.tv_empty)).setText(text);
        View refreshButton = rootView.findViewById(R.id.tv_try_again);
        if (showRefreshButton) {
            refreshButton.setVisibility(View.VISIBLE);
            refreshButton.setOnClickListener(v -> onRefreshRetry());
        } else {
            refreshButton.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏空页面
     */
    protected void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 刷新重试
     */
    protected void onRefreshRetry() {
    }

    //***************************************空页面方法 end*********************************

    /**
     * 停止刷新和加载更多
     *
     * @param layout 刷新layout
     */
    protected void stopRefresh(SmartRefreshLayout layout) {
        if (layout == null) {
            return;
        }
        layout.finishLoadMore();
        layout.finishRefresh();
    }

    /**
     * 返回Fragment的根布局
     *
     * @return View
     */
    protected View getRootView() {
        return rootView;
    }


    /**
     * 给Fragment设置TabTile数据
     *
     * @param data tabtitle
     */
    public void setTabTitle(HorizontalTabTitle data) {
        this.tabTitle = data;
    }

    /**
     * 返回ViewPager+Fragment滑动 设置给子Fragment的Tab数据
     *
     * @return HorizontalTabTitle
     */
    public HorizontalTabTitle getTabTitle() {
        return ValidUtils.isValid(tabTitle) ? tabTitle : new HorizontalTabTitle("");
    }

    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
    }

    /**
     * 需要接收事件 重新该方法 并返回true
     */
    protected boolean registerEventBus() {
        return false;
    }

    /**
     * 返回页面layout
     *
     * @return layout
     */
    protected abstract int getLayoutId();

    /**
     * 初始化View
     */
    protected abstract void initView();

}
