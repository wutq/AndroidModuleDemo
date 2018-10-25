package com.wss.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.wss.common.bean.Event;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe：所有Activity的基类
 * Created by 吴天强 on 2018/10/15.
 */

public abstract class BaseActivity extends FragmentActivity {


    private Unbinder unbinder;
    private ViewStub emptyView;
    protected Context mContext;
    protected ImmersionBar mImmersionBar;
    protected LoadingDialog loadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isActionBar()) {
            setContentView(R.layout.activity_base);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(), null));
        } else {
            setContentView(getLayoutId());
        }
        unbinder = ButterKnife.bind(this);
        mContext = this;
        //初始化ButterKnife
        //沉浸式状态栏
        initImmersionBar(R.color.blue);
        //加入Activity管理器
        BaseApplication.getApplication().getActivityManage().addActivity(this);
        if (regEvent()) {
            EventBusUtils.register(this);
        }
        loadingDialog = new LoadingDialog(mContext);

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (regEvent()) {
            EventBusUtils.unregister(this);
        }
        //必须调用该方法，防止内存泄漏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        //将Activity从管理器移除
        BaseApplication.getApplication().getActivityManage().removeActivityty(this);
    }

    //***************************************空页面方法*************************************
    protected void showEmptyView() {
        showEmptyOrErrorView(getString(R.string.no_data), R.drawable.bg_no_data);
    }


    protected void showErrorView() {
        showEmptyOrErrorView(getString(R.string.error_data), R.drawable.bg_no_net);
    }

    public void showEmptyOrErrorView(String text, int img) {
        emptyView = findViewById(R.id.vs_empty);

        if (emptyView != null) {
            emptyView.setVisibility(View.VISIBLE);
            findViewById(R.id.iv_empty).setBackgroundResource(img);
            ((TextView) findViewById(R.id.tv_empty)).setText(text);
            findViewById(R.id.ll_empty).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onEmptyViewClick();
                }
            });
        }
    }

    protected void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 空页面被点击
     */
    protected void onEmptyViewClick() {

    }

    //***************************************空页面方法*********************************

    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        if (mImmersionBar == null) {
            mImmersionBar = ImmersionBar.with(this);
            if (color != 0) {
                mImmersionBar.statusBarColor(color);
            }
            mImmersionBar.init();
        }
    }


    /**
     * 子类接受事件 重写该方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(Event event) {

    }


    /**
     * 是否需要ActionBar
     * TODO 暂时用此方法 后续优化
     */
    protected boolean isActionBar() {
        return false;
    }

    /**
     * 需要接收事件 重写该方法 并返回true
     */
    protected boolean regEvent() {
        return false;
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

}
