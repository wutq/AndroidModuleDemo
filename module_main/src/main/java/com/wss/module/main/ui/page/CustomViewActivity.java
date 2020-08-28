package com.wss.module.main.ui.page;

import android.widget.TextView;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.view.VerificationCodeView;

import butterknife.BindView;

/**
 * Describe：自定义View展示页
 * Created by 吴天强 on 2020/8/27.
 */
public class CustomViewActivity extends BaseActionBarActivity {

    @BindView(R2.id.vc_code)
    VerificationCodeView vcCode;

    @BindView(R2.id.tv_vc_code)
    TextView tvVcCode;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_custom_view;
    }

    @Override
    protected void initView() {
        setCenterText("自定义View展览区");
        vcCode.addOnCodeChangeListener(code -> tvVcCode.setText(String.format("验证码是:%s", code)));
        tvVcCode.setText(String.format("验证码是：%s", vcCode.getText()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vcCode != null) {
            vcCode.destroy();
        }
    }
}
