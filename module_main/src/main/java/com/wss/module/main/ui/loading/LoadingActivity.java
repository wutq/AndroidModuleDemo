package com.wss.module.main.ui.loading;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.wss.common.base.BaseActivity;
import com.wss.module.main.R;
import com.wss.module.main.ui.main.MainActivity;

/**
 * Describe：应用启动页
 * Created by 吴天强 on 2018/10/16.
 */

public class LoadingActivity extends BaseActivity {

    private long loadingTime = 1500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handler.sendEmptyMessageDelayed(0, loadingTime);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_loading;
    }
}
