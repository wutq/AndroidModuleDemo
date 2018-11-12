package com.wss.module.main.ui.page;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.MultipleItemView;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import butterknife.BindView;

/**
 * Describe：多功能Item
 * Created by 吴天强 on 2018/10/25.
 */

public class MultipleItemActivity extends ActionBarActivity {

    @BindView(R2.id.miv_02)
    MultipleItemView miv02;

    @BindView(R2.id.miv_03)
    MultipleItemView miv03;

    @BindView(R2.id.miv_04)
    MultipleItemView miv04;

    @BindView(R2.id.miv_05)
    MultipleItemView miv05;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_multifunctional_item;
    }

    @Override
    protected void initView() {
        setTitleText("多功能横向Item");
        miv02.setRightTextSize(10);
        miv03.setLeftIconSize(50, 50);
        miv04.setLeftIconMargin(10, 0, 30, 0);
        miv05.setContentPadding(30);
    }

}
