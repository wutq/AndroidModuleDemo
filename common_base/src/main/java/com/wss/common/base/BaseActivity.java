package com.wss.common.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.wss.common.bean.Event;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.widget.dialog.LoadingDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;

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
        mContext = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if (isActionBar()) {
            setContentView(R.layout.activity_base);
            ((ViewGroup) findViewById(R.id.fl_content)).addView(getLayoutInflater().inflate(getLayoutId(), null));
        } else {
            setContentView(getLayoutId());
        }
        //初始化ButterKnife
        unbinder = ButterKnife.bind(this);

        //沉浸式状态栏
        initImmersionBar(R.color.blue);

        //加入Activity管理器
        BaseApplication.getApplication().getActivityManage().addActivity(this);
        if (regEvent()) {
            EventBusUtils.register(this);
        }
        loadingDialog = new LoadingDialog(mContext);

    }


    /**
     * 沉浸栏颜色
     */
    protected void initImmersionBar(int color) {
        mImmersionBar = ImmersionBar.with(this);
        if (color != 0) {
            mImmersionBar.statusBarColor(color);
        }
        mImmersionBar.init();
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
        BaseApplication.getApplication().getActivityManage().removeActivity(this);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        initView();
    }

    //***************************************空页面方法*************************************
    protected void showEmptyView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_data);
    }

    protected void showEmptyView() {
        showEmptyView(getString(R.string.no_data));
    }

    protected void showErrorView(String text) {
        showEmptyOrErrorView(text, R.drawable.bg_no_net);
    }

    protected void showErrorView() {
        showErrorView(getString(R.string.error_data));
    }

    public void showEmptyOrErrorView(String text, int img) {

        if (emptyView == null) {
            emptyView = findViewById(R.id.vs_empty);
        }
        emptyView.setVisibility(View.VISIBLE);
        findViewById(R.id.iv_empty).setBackgroundResource(img);
        ((TextView) findViewById(R.id.tv_empty)).setText(text);
        findViewById(R.id.ll_empty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPageClick();
            }
        });
    }

    protected void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 空页面被点击
     */
    protected void onPageClick() {

    }

    //***************************************空页面方法*********************************


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Logger.e("permissions:" + Arrays.toString(permissions) + " grantResults:" + Arrays.toString(grantResults));

        //如果有未授权权限则跳转设置页面
        if (!requestPermissionsResult(grantResults)) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }


    }

    /**
     * 判断授权结果
     */
    private boolean requestPermissionsResult(int[] grantResults) {
        for (int code : grantResults) {
            if (code == -1) {
                return false;
            }
        }
        return true;
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
