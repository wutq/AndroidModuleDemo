package com.wss.module.main.ui.page;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.wss.common.base.ActionBarActivity;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.utils.PermissionsUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.NumberProgressBar;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.common.widget.dialog.DialogType;
import com.wss.module.main.R;
import com.wss.module.main.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Describe：对话框
 * Created by 吴天强 on 2018/10/26.
 */

public class DialogActivity extends ActionBarActivity {


    private NumberProgressBar progressBar;
    private int mProgress = 0;//下载进度
    private int mMaxProgress = 100;//百分比
    private AppDialog progressDialog;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_dialog;
    }

    @Override
    protected void initView() {
        setTitleText("对话框");
        PermissionsUtils.checkPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 注意：lib中使用  ButterKnife 绑定事件用R2 判断 用if else 判断里面用R
     */
    @OnClick({R2.id.btn_01, R2.id.btn_02, R2.id.btn_03, R2.id.btn_04, R2.id.btn_05, R2.id.btn_06,
            R2.id.btn_07})
    public void onBtnClick(View view) {
        if (view.getId() == R.id.btn_01) {
            new AppDialog(mContext)
                    .setContent("我的自定义默认对话框")
                    .show();
        } else if (view.getId() == R.id.btn_02) {
            new AppDialog(mContext, DialogType.INPUT)
                    .setTitle("来一段文字")
                    .setLeftButton("输好了", new AppDialog.OnButtonClickListener() {
                        @Override
                        public void onClick(String val) {
                            ToastUtils.showToast(mContext, val);
                        }
                    })
                    .setRightButton("不输了")
                    .show();
        } else if (view.getId() == R.id.btn_03) {
            new AppDialog(mContext, DialogType.COUNT)
                    .setTitle("修改数量")
                    .setNumber(1, 10, 2)
                    .setLeftButton("OK", new AppDialog.OnButtonClickListener() {
                        @Override
                        public void onClick(String val) {
                            ToastUtils.showToast(mContext, val);
                        }
                    })
                    .setLeftButtonTextColor(R.color.red)
                    .setRightButtonTextColor(R.color.gray)
                    .setRightButton("CANCEL")
                    .show();
        } else if (view.getId() == R.id.btn_04) {
            new AppDialog(mContext)
                    .setTitle("单个按钮")
                    .setContent("这是一段没有意义的文字")
                    .setSingleButton()
                    .show();
        } else if (view.getId() == R.id.btn_05) {
            new AppDialog(mContext, DialogType.NO_TITLE)
                    .setContent("我没有title,点我也没啥用")
                    .show();

        } else if (view.getId() == R.id.btn_06) {
            final List<String> list = new ArrayList<>();
            list.add("相机");
            list.add("相册");
            new AppDialog(mContext, DialogType.BOTTOM_IN)
                    .setTitle("多条目")
                    .setBottomItems(list, new AppDialog.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            ToastUtils.showToast(mContext, list.get(position));
                        }
                    })
                    .setBottomCancelText("再见")
                    .show();

        } else if (view.getId() == R.id.btn_07) {
            View progressView = View.inflate(mContext, R.layout.update_progress_layout, null);
            progressBar = progressView.findViewById(R.id.number_progress);
            progressDialog = new AppDialog(mContext);
            mProgress = 0;
            progressBar.setProgress(mProgress);

            progressDialog.setTitle("更新")
                    .addDialogView(progressView)
                    .show();
            update();

        }
    }

    private void update() {
        try {
            mProgress++;
            Message msg = handler.obtainMessage();
            msg.what = 100;
            msg.obj = mProgress;
            handler.sendMessage(msg);

            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = (int) msg.obj;
            progressBar.setProgress(progress);
            if (progress >= mMaxProgress) {
                ToastUtils.showToast(mContext, "下载完成");
                progressDialog.dismiss();
            } else {
                update();
            }
        }
    };

}
