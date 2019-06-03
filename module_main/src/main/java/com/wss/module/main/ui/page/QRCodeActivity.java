package com.wss.module.main.ui.page;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.PermissionsUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.zhangke.qrcodeview.QRCodeView;

import butterknife.BindView;

/**
 * Describe：二维码扫描页面
 * Created by 吴天强 on 2019/3/20.
 */

public class QRCodeActivity extends ActionBarActivity {


    @BindView(R2.id.qr_code_view)
    QRCodeView qrCodeView;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_qr_code;
    }

    @Override
    protected void initView() {

        setTitleText("二维码扫描");
        PermissionsUtils.checkCamera(this).subscribe(r -> {
            if (r) {
                qrCodeView.startPreview();
                qrCodeView.setOnQRCodeListener(result -> {
                    ToastUtils.show(mContext, result.getText());
                    finish();
                });
            } else {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        qrCodeView.stopPreview();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
