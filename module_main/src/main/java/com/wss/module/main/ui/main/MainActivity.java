package com.wss.module.main.ui.main;

import android.Manifest;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.BaseApplication;
import com.wss.common.base.BaseFragment;
import com.wss.common.bean.AppInfo;
import com.wss.common.bean.Template;
import com.wss.common.constants.ARouterConfig;
import com.wss.common.manage.UpdateManager;
import com.wss.common.net.Api;
import com.wss.common.utils.ARouterUtils;
import com.wss.common.utils.PermissionsUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.main.fragment.CenterFragment;
import com.wss.module.main.ui.main.mvp.MainPresenter;
import com.wss.module.main.ui.main.mvp.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Describe：程序入口
 * Created by 吴天强 on 2018/10/15.
 */

public class MainActivity extends ActionBarActivity<MainPresenter> implements MainContract.View {


    @BindView(R2.id.rg_main)
    RadioGroup mainTab;

    private long mExitTime;
    /**
     * 存放切换Fragment
     */
    private List<Fragment> mFragmentList = new ArrayList<>();

    //玩android模块Fragment
    private BaseFragment wanFragment = ARouterUtils.getFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
    //我的模块Fragment
    private BaseFragment userFragment = ARouterUtils.getFragment(ARouterConfig.USER_MAIN_FRAGMENT);
    //中间FragmentData
    private ArrayList<Template> centerFragmentData = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main;
    }

    @Override
    protected void initView() {
        actionBar.showBackImg(false);
        changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
        setTitleText(R.string.main_tab_home);
        mainTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_main) {
                    changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
                    setTitleText(R.string.main_tab_home);
                } else if (checkedId == R.id.rb_center) {
                    changeFragment(CenterFragment.class.getName());
                    setTitleText(R.string.main_tab_center);
                } else if (checkedId == R.id.rb_user) {
                    changeFragment(ARouterConfig.USER_MAIN_FRAGMENT);
                    setTitleText(R.string.main_tab_user);
                } else {
                    changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
                    setTitleText(R.string.main_tab_home);
                }

            }
        });  //检查文件权限
        if (PermissionsUtils.checkPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            presenter.checkUpdate();
        }
        presenter.getTabList();
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
            } else if (TextUtils.equals(tag, CenterFragment.class.getName())) {
                fragment = CenterFragment.getInstance(centerFragmentData);
            } else if (TextUtils.equals(tag, ARouterConfig.USER_MAIN_FRAGMENT)) {
                fragment = userFragment;
            } else {
                fragment = wanFragment;
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

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void needUpdate(AppInfo appInfo) {
        String context = "版本更新！";
        if (appInfo != null && !TextUtils.isEmpty(appInfo.getDescribe())) {
            context = appInfo.getDescribe();
        }
        new AppDialog(mContext)
                .setTitle("提示更新")
                .setContent(context)
                .setRightButton("更新", new AppDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(String val) {
                        UpdateManager.getInstance(mContext).download(Api.DOWNLOAD_APK);
                    }
                })
                .show();
    }

    @Override
    public void tabList(List<Template> blockList) {
        this.centerFragmentData.addAll(blockList);
    }
}
