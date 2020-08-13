package com.wss.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.TextView;

import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wss.common.bean.Event;
import com.wss.common.utils.EventBusUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe：所有Activity的基类
 * Created by 吴天强 on 2018/10/15.
 */
public abstract class BaseActivity extends FragmentActivity {

    private Unbinder unbinder;
    private ViewStub emptyView;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加入Activity管理器
        BaseApplication.i().getActivityManage().addActivity(this);
        context = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (!isFullScreenLayout()) {
            setContentView(R.layout.activity_base);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(), null));
            unbinder = ButterKnife.bind(this);
        }
        if (registerEventBus()) {
            EventBusUtils.register(this);
        }
    }

    /**
     * 设置沉浸栏颜色
     *
     * @param statusBarColor 颜色
     */
    protected void setImmersionBarColor(int statusBarColor) {
        setImmersionBarColor(statusBarColor, false);
    }

    /**
     * 设置沉浸栏颜色
     *
     * @param statusBarColor 沉浸栏颜色
     */
    protected void setImmersionBarColor(int statusBarColor, boolean fitsSystemWindows) {
        setImmersionBarColor(statusBarColor, R.color.black, fitsSystemWindows);
    }


    /**
     * 设置沉浸栏颜色
     *
     * @param statusBarColor     沉浸栏颜色
     * @param navigationBarColor 沉浸栏图标颜色
     */
    protected void setImmersionBarColor(int statusBarColor, int navigationBarColor, boolean fitsSystemWindows) {
        ImmersionBar.with(this)
                .statusBarColor(statusBarColor)
                .navigationBarColor(navigationBarColor)
                .fitsSystemWindows(fitsSystemWindows)
                .autoDarkModeEnable(true)
                .statusBarDarkFont(true)
                .init();
    }

    /**
     * 隐藏状态栏、导航栏
     */
    protected void setImmersionBarHide() {
        ImmersionBar.with(this)
                .fitsSystemWindows(false)
                .hideBar(BarHide.FLAG_HIDE_BAR)
                .init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (registerEventBus()) {
            EventBusUtils.unregister(this);
        }
        //将Activity从管理器移除
        BaseApplication.i().getActivityManage().removeActivity(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }


    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isAnimate()) {
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (isAnimate()) {
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
        }
    }

    @Override
    public void finish() {
        if (isAnimate()) {
            overridePendingTransition(R.anim.anim_right_in, R.anim.anim_right_out);
        }
        super.finish();
    }


    //***************************************空页面方法 start*************************************

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
        showEmptyOrErrorView(text, R.drawable.bg_no_net                                                                                                                             , true);
    }


    /**
     * 请求数据报错或者为空页面
     *
     * @param text      提示文案
     * @param iconResId 显示的icon
     */
    public void showEmptyOrErrorView(String text, int iconResId, boolean showRefreshButton) {
        if (emptyView == null) {
            emptyView = findViewById(R.id.vs_empty);
        }
        emptyView.setVisibility(View.VISIBLE);
        findViewById(R.id.iv_empty).setBackgroundResource(iconResId);
        ((TextView) findViewById(R.id.tv_empty)).setText(text);
        if (showRefreshButton) {
            View refreshButton = findViewById(R.id.tv_try_again);
            refreshButton.setVisibility(View.VISIBLE);
            refreshButton.setOnClickListener(v -> onRefreshRetry());
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
     * 是否布局伸入状态栏类型Activity
     *
     * @return boolean
     */
    protected boolean isFullScreenLayout() {
        return false;
    }

    /**
     * 切换页面是否需要过渡动画
     *
     * @return boolean
     */
    protected boolean isAnimate() {
        return true;
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     *
     * @return boolean
     */
    protected boolean registerEventBus() {
        return false;
    }

    /**
     * 子类接受事件 重写该方法
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {
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
