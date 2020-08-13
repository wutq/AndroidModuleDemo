package com.wss.common.view;

import android.view.View;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.BaseFragment;
import com.wss.common.base.R;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.Dic;
import com.wss.common.utils.ARouterUtils;

import androidx.fragment.app.FragmentTransaction;

/**
 * Describe：跳板Activity,主要是跳转一些Fragment
 * 统一采用ARouter方式跳转
 * Created by 吴天强 on 2020/5/7.
 */
public class SpringboardActivity extends BaseActionBarActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_springboard;
    }

    @Override
    protected void initView() {
        boolean isActionBar = getIntent().getBooleanExtra(Dic.IS_ACTION_BAR, false);
        String titleText = getIntent().getStringExtra(Dic.TITLE_TEXT);
        getTitleBar().setVisibility(isActionBar ? View.VISIBLE : View.GONE);
        setCenterText(titleText);
        BaseFragment fragment = ARouterUtils.getFragment(getIntent().getStringExtra(Dic.TARGET_FRAGMENT_PATH));
        //给Fragment设置带入参数
        fragment.setArguments(getIntent().getBundleExtra(Dic.BUNDLE_DATA));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_content, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}