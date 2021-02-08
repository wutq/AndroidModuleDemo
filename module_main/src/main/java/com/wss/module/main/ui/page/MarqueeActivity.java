package com.wss.module.main.ui.page;

import android.hardware.Camera;
import android.os.Bundle;

import com.orhanobut.logger.Logger;
import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.view.ca.SenseCamera;
import com.wss.module.main.ui.view.ca.SenseCameraPreview;

import java.io.IOException;

import androidx.annotation.Nullable;
import butterknife.BindView;

/**
 * Describe：跑马灯效果
 * Created by 吴天强 on 2020/11/11.
 */
public class MarqueeActivity extends BaseActionBarActivity {

    @BindView(R2.id.save_non_transition_alpha)
    SenseCameraPreview mCameraPreview;

    protected SenseCamera mCamera;
    protected static final int DEFAULT_PREVIEW_WIDTH = 1280;
    protected static final int DEFAULT_PREVIEW_HEIGHT = 960;
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_marquee;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCameraPreview.setStartListener(new SenseCameraPreview.StartListener() {
            @Override
            public void onFail() {
                Logger.e("setStartListener  onFail");
            }
        });
        this.mCamera = new SenseCamera.Builder(this).setRequestedPreviewSize(DEFAULT_PREVIEW_WIDTH,
                DEFAULT_PREVIEW_HEIGHT).build();

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            this.mCameraPreview.start(this.mCamera);
            this.mCamera.setOnPreviewFrameCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    Logger.e("data:"+data);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }
}
