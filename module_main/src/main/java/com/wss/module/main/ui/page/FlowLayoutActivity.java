package com.wss.module.main.ui.page;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;

/**
 * Describe：流式布局
 * Created by 吴天强 on 2018/10/29.
 */
public class FlowLayoutActivity extends BaseActionBarActivity {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_flow_layout;
    }

    @Override
    protected void initView() {
        setCenterText("流式布局");
    }
}
