package com.wss.module.main.ui.page;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.widget.CountClickView;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import butterknife.BindView;

/**
 * Describe：数量加减控件
 * Created by 吴天强 on 2018/10/24.
 */

public class CountViewActivity extends ActionBarActivity {

    @BindView(R2.id.ccv_dialog)
    CountClickView ccvDialog;

    @BindView(R2.id.ccv_cust)
    CountClickView ccvCust;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_count_view;
    }

    @Override
    protected void initView() {
        setTitleText("数量加减控件");

        //显示输入框
        ccvDialog.setInput(true);

        //自定义背景图
        ccvCust.setButtonRes(R.drawable.main_stepper_reduce, R.drawable.main_stepper_reduce,
                R.drawable.main_stepper_add, R.drawable.main_stepper_add_disable);
        ccvCust.setBtnParentBg(R.color.white);
        ccvCust.setBtnSize(20, 20);
        ccvCust.setCountViewAttr(R.color.transparent, 0, 4, 4);
    }

}
