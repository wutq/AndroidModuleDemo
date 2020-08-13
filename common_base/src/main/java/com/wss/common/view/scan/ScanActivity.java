package com.wss.common.view.scan;

import android.view.View;

import com.orhanobut.logger.Logger;
import com.wss.common.base.BaseActivity;
import com.wss.common.base.R;
import com.wss.common.base.R2;
import com.wss.common.bean.Event;
import com.wss.common.constants.EventAction;
import com.wss.common.utils.EventBusUtils;
import com.wss.common.utils.PermissionsUtils;
import com.wss.common.utils.ToastUtils;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import me.devilsen.czxing.code.BarcodeFormat;
import me.devilsen.czxing.view.ScanBoxView;
import me.devilsen.czxing.view.ScanListener;
import me.devilsen.czxing.view.ScanView;


/**
 * Describe：扫一扫首页
 * Created by 吴天强 on 2019/12/27.
 */
public class ScanActivity extends BaseActivity {

    @BindView(R2.id.qr_code_view)
    ScanView qrCodeView;

    @Override
    protected void initView() {
        PermissionsUtils.checkCamera(this).subscribe(
                aBoolean -> {
                    if (aBoolean) {
                        qrCodeView.setScanListener(new ScanListener() {
                            @Override
                            public void onScanSuccess(String result, BarcodeFormat format) {
                                Logger.e("二维码扫描结果：" + result);
                                ToastUtils.show(result);
                                EventBusUtils.sendEvent(new Event<>(EventAction.SCAN_QR_CODE_RESULT, result));
                                finish();
                            }

                            @Override
                            public void onOpenCameraError() {
                                ToastUtils.show(context, "打开相机出错");
                            }
                        });
                        ScanBoxView scanBox = qrCodeView.getScanBox();
                        scanBox.setCornerColor(getResources().getColor(R.color.theme));//扫描四角颜色
                        scanBox.setScanLineColor(Arrays.asList(getResources().getColor(R.color.theme),
                                getResources().getColor(R.color.theme), getResources().getColor(R.color.theme)));//扫描线条颜色

                    } else {
                        finish();
                    }
                }
        );
    }


    @OnClick(R2.id.iv_back)
    public void onClick(View v) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (qrCodeView != null) {
            // 打开后置摄像头开始预览，但是并未开始识别
            qrCodeView.openCamera();
            // 显示扫描框，并开始识别
            qrCodeView.startScan();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (qrCodeView != null) {
            qrCodeView.stopScan();
            //关闭摄像头预览，并且隐藏扫描框
            qrCodeView.closeCamera();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (qrCodeView != null) {
            qrCodeView.onDestroy();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

}
