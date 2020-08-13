package com.wss.module.user.ui.account;

import android.widget.EditText;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.widget.ObserverButton;
import com.wss.module.user.R;
import com.wss.module.user.R2;
import com.wss.module.user.ui.account.mvp.RegisterPresenter;
import com.wss.module.user.ui.account.mvp.contract.RegisterContract;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：注册
 * Created by 吴天强 on 2018/11/13.
 */

public class RegisterActivity extends BaseActionBarActivity<RegisterPresenter> implements RegisterContract.View {

    @BindView(R2.id.edt_name)
    EditText edtName;
    @BindView(R2.id.edt_pwd)
    EditText edtPwd;
    @BindView(R2.id.edt_confirm)
    EditText edtConfirm;
    @BindView(R2.id.ob_register)
    ObserverButton obRegister;


    @Override
    protected int getLayoutId() {
        return R.layout.user_activity_register;
    }

    @Override
    protected void initView() {
        setCenterText("注册");
        obRegister.observer(edtName, edtPwd, edtConfirm);
    }


    @OnClick(R2.id.ob_register)
    public void viewClick() {
        getPresenter().register(edtName.getText().toString().trim(),
                edtPwd.getText().toString().trim(), edtConfirm.getText().toString().trim());
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }
}