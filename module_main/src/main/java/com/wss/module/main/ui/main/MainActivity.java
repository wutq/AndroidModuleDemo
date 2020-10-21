package com.wss.module.main.ui.main;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.BaseApplication;
import com.wss.common.base.BaseFragment;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.utils.ARouterUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.main.fragment.CaseFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;

/**
 * Describe：首页
 * Created by 吴天强 on 2018/10/15.
 */
public class MainActivity extends BaseActionBarActivity {


    @BindView(R2.id.rg_main)
    RadioGroup mainTab;

    private long mExitTime;
    /**
     * 存放切换Fragment
     */
    private List<Fragment> mFragmentList = new ArrayList<>();

    /**
     * 玩android模块Fragment
     */
    private BaseFragment wanFragment = ARouterUtils.getFragment(ARouterConfig.WAN_MAIN_FRAGMENT);

    /**
     * 我的模块Fragment
     */
    private BaseFragment userFragment = ARouterUtils.getFragment(ARouterConfig.USER_MAIN_FRAGMENT);

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void initView() {
        changeFragment(CaseFragment.class.getName());
        getTitleBar().showBackImg(false);
        setCenterText(R.string.main_tab_center);
        mainTab.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rb_main) {
                changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
                setCenterText(R.string.main_tab_home);
            } else if (checkedId == R.id.rb_center) {
                changeFragment(CaseFragment.class.getName());
                setCenterText(R.string.main_tab_center);
            } else if (checkedId == R.id.rb_user) {
                changeFragment(ARouterConfig.USER_MAIN_FRAGMENT);
                setCenterText(R.string.main_tab_user);
            } else {
                changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
                setCenterText(R.string.main_tab_home);
            }

        });
    }


    /**
     * Fragment 发生改变
     *
     * @param tag Fragment 类名
     */
    public void changeFragment(String tag) {
        hideFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            if (TextUtils.equals(tag, ARouterConfig.WAN_MAIN_FRAGMENT)) {
                fragment = wanFragment;
            } else if (TextUtils.equals(tag, CaseFragment.class.getName())) {
                fragment = new CaseFragment();
            } else if (TextUtils.equals(tag, ARouterConfig.USER_MAIN_FRAGMENT)) {
                fragment = userFragment;
            } else {
                fragment = new CaseFragment();
            }
            if (fragment == null) {
                return;
            }
            mFragmentList.add(fragment);
            transaction.add(R.id.fl_context, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
    }


    /**
     * 隐藏所有Fragment
     */
    private void hideFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment f : mFragmentList) {
            transaction.hide(f);
        }
        transaction.commit();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.show(context, getString(R.string.main_exit_app));
                mExitTime = System.currentTimeMillis();
            } else {
                BaseApplication.i().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
