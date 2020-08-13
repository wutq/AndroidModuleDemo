package com.wss.common.base;


import com.gyf.immersionbar.ImmersionBar;
import com.wss.common.base.mvp.BasePresenter;

/**
 * Describe：布局需要伸入状态栏下面的Activity继承该类，
 * 并通过setContentView来设置layout，重新注册ButterKnife
 * Created by 吴天强 on 2020/4/28.
 */
public abstract class BaseFullScreenActivity<P extends BasePresenter> extends BaseMvpActivity<P> {

    @Override
    protected boolean isFullScreenLayout() {
        return true;
    }

    @Override
    protected int getLayoutId() {
        //该方法仅仅是为了实现父类的抽象方法，无任何意义，忽略即可
        return R.layout.layout_nothing;
    }

    @Override
    protected void initView() {
        //布局可伸入状态栏
        ImmersionBar.with(this)
                .statusBarColor(R.color.transparent)
                .statusBarDarkFont(true)
                .fullScreen(true)
                .init();
    }
}
