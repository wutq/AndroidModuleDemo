package com.wss.amd.main;

import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.wss.amd.R;
import com.wss.common.base.BaseFullScreenActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.manage.ActivityToActivity;
import com.wss.module.main.ui.main.MainActivity;

import androidx.annotation.Nullable;

/**
 * Describe：应用启动页
 * Created by 吴天强 on 2018/10/16.
 */
public class LoadingActivity extends BaseFullScreenActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(() -> {
            ActivityToActivity.toActivity(this, MainActivity.class);
        }, 2200);


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
