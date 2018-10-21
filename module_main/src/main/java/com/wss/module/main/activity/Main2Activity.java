package com.wss.module.main.activity;

import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.common.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：
 * Created by 吴天强 on 2018/10/15.
 */

public class Main2Activity extends BaseActivity {

    @BindView(R2.id.tv_text)
    TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_main2;
    }

    @Override
    public void onEventBus(Object event) {
        textView.setText("我是Main2" + event);
    }

    @Override
    protected boolean regEvent() {
        return true;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R2.id.btn_open)
    public void onClick(View view) {

        ARouter.getInstance().build("/test/TestActivity").navigation();
    }
}
