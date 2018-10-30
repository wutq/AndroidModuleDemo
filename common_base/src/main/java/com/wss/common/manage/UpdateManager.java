package com.wss.common.manage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;

import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxFileCallBack;
import com.wss.common.base.BaseApplication;
import com.wss.common.base.R;
import com.wss.common.net.HttpUtils;
import com.wss.common.utils.FileUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.dialog.AppDialog;

import java.io.File;


/**
 * 更新下载APK
 * Created by wtq on 2016/7/22.
 */
@SuppressLint("StaticFieldLeak")
public class UpdateManager {

    private static final int DOWNLOADING = 0x00;
    private static final int DOWNLOAD_SUCCESS = 0x02;
    private static final int DOWNLOAD_FAILED = 0x03;
    private static final int DOWNLOAD_CANCEL = 0x04;
    private static String DOWNLOAD_PATH = "/updateApkFile/";
    private static String APK_NAME = BaseApplication.getApplication().getPackageName() + "Temp.apk";
    /**
     * 下载之后存放路径
     */
    private String apkPath = FileUtils.getTempPath() + DOWNLOAD_PATH + APK_NAME;

    private Context mContext;
    private ProgressBar progressBar;
    private AppDialog dialog;
    private static UpdateManager manager;
    private String downloadUrl;

    private UpdateManager(Context context) {
        this.mContext = context;
        //每次启动先删除本地原有APK
        FileUtils.deleteFile(apkPath);
    }

    public static synchronized UpdateManager getInstance(Context context) {
        if (manager == null) {
            manager = new UpdateManager(context);
        }
        return manager;
    }

    /**
     * 设置下载地址
     *
     * @param url url
     * @return UpdateManager
     */
    public UpdateManager setDownloadUrl(String url) {
        this.downloadUrl = url;
        return this;
    }

    /**
     * 弹出下载框
     */
    public void download() {
        if (TextUtils.isEmpty(downloadUrl)) {
            ToastUtils.showToast(mContext, "请设置下载Url");
            return;
        }

        View progressBarView = View.inflate(mContext, R.layout.laytou_update_progress, null);
        progressBar = progressBarView.findViewById(R.id.pb_update_progress);
        dialog = new AppDialog(mContext);
        dialog.setTitle("版本更新中···")
                .addContentView(progressBarView)
                .setSingleButton("后台下载", new AppDialog.OnButtonClickListener() {
                    @Override
                    public void onClick(String val) {
                        ToastUtils.showToast(mContext, "已切换到后台下载···");
                    }
                })
                .show();
        HttpUtils.getInstance(mContext)
                .downloadFile(downloadUrl, new RxFileCallBack(apkPath, "Temp.apk") {

                    @Override
                    public void onNext(Object tag, File file) {
                        updateHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                    }

                    @Override
                    public void onProgress(Object tag, float progress, long downloaded, long total) {
                        Message message = updateHandler.obtainMessage();
                        message.what = DOWNLOADING;
                        message.obj = (int) progress;
                        updateHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {
                        updateHandler.sendEmptyMessage(DOWNLOAD_FAILED);
                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {
                        updateHandler.sendEmptyMessage(DOWNLOAD_CANCEL);
                    }
                });
    }


    @SuppressLint("HandlerLeak")
    private Handler updateHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOADING:
                    progressBar.setProgress((Integer) msg.obj);
                    break;
                case DOWNLOAD_SUCCESS:
                    dialog.dismiss();
                    installApk();
                    break;
                case DOWNLOAD_FAILED:
                    dialog.dismiss();
                    ToastUtils.showToast(mContext, "下载失败");
                    break;
                case DOWNLOAD_CANCEL:
                    dialog.dismiss();
                    ToastUtils.showToast(mContext, "已取消下载");
                    break;
                default:
                    break;
            }
        }

    };

    /**
     * 安装apk
     */
    private void installApk() {
        // 安装，如果签名不一致，可能出现程序未安装提示
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
}
