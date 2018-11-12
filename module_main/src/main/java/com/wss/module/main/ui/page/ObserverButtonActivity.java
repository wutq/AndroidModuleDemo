package com.wss.module.main.ui.page;

import android.view.View;
import android.widget.EditText;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.ObserverButton;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：利用观察者模式对输入框进行监听
 * Created by 吴天强 on 2018/10/26.
 */

public class ObserverButtonActivity extends ActionBarActivity {

    @BindView(R2.id.edt_name)
    EditText edtName;

    @BindView(R2.id.edt_pwd)
    EditText edtPwd;

    @BindView(R2.id.edt_confirm)
    EditText edtConfirm;

    @BindView(R2.id.edt_age)
    EditText edtAge;

    @BindView(R2.id.ob_btn)
    ObserverButton obBtn;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_observer_button;
    }

    @Override
    protected void initView() {
        setTitleText("注册");
        obBtn.observer(edtName, edtPwd, edtConfirm, edtAge);
    }

    @OnClick(R2.id.ob_btn)
    public void onClick(View v) {
        ToastUtils.showToast(mContext, "可点击");
    }


}
