package com.wss.module.main.ui.page;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.view.SwitchButton;

import butterknife.BindView;

/**
 * Describe：仿iOS风格的开关按钮 * Created by 吴天强 on 2020/9/18.
 */
public class SwitchButtonActivity extends BaseActionBarActivity {

    @BindView(R2.id.sb_01)
    SwitchButton switchButton;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_switch_button;
    }

    @Override
    protected void initView() {
        setCenterText("仿iOS风格开关按钮");
        switchButton.setOnCheckedChangeListener(
                (view, isChecked) -> ToastUtils.show("选中状态：" + isChecked));
    }
}
