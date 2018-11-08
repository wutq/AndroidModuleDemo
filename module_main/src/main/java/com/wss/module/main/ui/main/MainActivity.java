package com.wss.module.main.ui.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wss.common.base.BaseActivity;
import com.wss.common.base.BaseApplication;
import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.main.fragment.CenterFragment;
import com.wss.module.main.ui.main.fragment.HomeFragment;
import com.wss.module.main.ui.main.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：程序入口
 * Created by 吴天强 on 2018/10/15.
 */

public class MainActivity extends BaseActivity {


    @BindView(R2.id.rg_main)
    RadioGroup mainTab;

    @BindView(R2.id.tv_title)
    TextView tvTitle;


    private long mExitTime;
    /**
     * 存放切换Fragment
     */
    private List<Fragment> mFragmentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void initView() {
        changeFragment(HomeFragment.class.getSimpleName());
        mainTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_main) {
                    changeFragment(HomeFragment.class.getName());
                    tvTitle.setText(R.string.main_tab_home);
                } else if (checkedId == R.id.rb_center) {
                    changeFragment(CenterFragment.class.getName());
                    tvTitle.setText(R.string.main_tab_center);
                } else if (checkedId == R.id.rb_user) {
                    changeFragment(UserFragment.class.getName());
                    tvTitle.setText(R.string.main_tab_user);
                } else {
                    changeFragment(HomeFragment.class.getName());
                    tvTitle.setText(R.string.main_tab_home);
                }

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

            if (TextUtils.equals(tag, HomeFragment.class.getName())) {
                fragment = new HomeFragment();
            } else if (TextUtils.equals(tag, CenterFragment.class.getName())) {
                fragment = new CenterFragment();
            } else if (TextUtils.equals(tag, UserFragment.class.getName())) {
                fragment = new UserFragment();
            } else {
                fragment = new HomeFragment();
            }
            mFragmentList.add(fragment);
            transaction.add(R.id.fl_context, fragment, fragment.getClass().getName());
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
    protected boolean regEvent() {
        return true;
    }

    @Override
    public void onEventBus(Event event) {
        if (TextUtils.equals(event.getAction(), EventAction.EVENT_MARKET_CLICK)) {
            ToastUtils.showToast(mContext, "main 模块收到" + event.getData().toString());
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showToast(mContext, getString(R.string.main_exit_app));
                mExitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
