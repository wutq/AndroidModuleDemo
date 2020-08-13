package com.wss.module.user.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.widget.TextView;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.Utils;
import com.wss.module.user.R;
import com.wss.module.user.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：关于
 * Created by 吴天强 on 2018/11/13.
 */
public class AboutActivity extends BaseActionBarActivity {
    @BindView(R2.id.tv_version)
    TextView tvVersion;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_about;
    }

    @Override
    protected void initView() {
        setCenterText("关于");
        tvVersion.setText(String.format("当前版本：%s", Utils.getVersionName()));
    }


    @OnClick(R2.id.tv_wan)
    public void onViewClicked() {
        Utils.toSystemBrowser(context, "http://www.wanandroid.com");
    }
}
