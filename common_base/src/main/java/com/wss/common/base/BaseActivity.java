package com.wss.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.gyf.barlibrary.ImmersionBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Describe：所有Activity的基类
 * Created by 吴天强 on 2018/10/15.
 */

public abstract class BaseActivity extends Activity {

    private Unbinder unbinder;
    protected Context mContext;
    protected ImmersionBar mImmersionBar;

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
            EventBus.getDefault().register(this);
        }

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
            EventBus.getDefault().unregister(this);
        }
        //必须调用该方法，防止内存泄漏
        if (mImmersionBar != null) {
            mImmersionBar.destroy();
        }
        //将Activity从管理器移除
        BaseApplication.getApplication().getActivityManage().removeActivityty(this);
    }

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
    public void onEventBus(Object event) {
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
