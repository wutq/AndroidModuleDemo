package com.wss.common.manage;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Describe：扫描媒体文件帮助类（主要用户保存文件、图片之后刷新到相册 操作）
 * Created by 吴天强 on 2021/5/10.
 */
public class MediaScannerManage {

    private MediaScannerConnection mConn = null;
    private File mFile = null;

    private MediaScannerManage(Context context) {
        ScannerClient mClient = new ScannerClient();
        if (mConn == null) {
            mConn = new MediaScannerConnection(context, mClient);
        }
    }

    @NotNull
    @Contract("_ -> new")
    public static synchronized MediaScannerManage getInstance(Context context) {
        return new MediaScannerManage(context);
    }


    public void scanFile(File file) {
        mFile = file;
        mConn.connect();
    }

    private class ScannerClient implements MediaScannerConnection.MediaScannerConnectionClient {

        @Override
        public void onMediaScannerConnected() {
            if (mFile == null) {
                return;
            }
            scan(mFile);
        }

        @Override
        public void onScanCompleted(String path, Uri uri) {
            mConn.disconnect();
        }

        private void scan(@NotNull File file) {

            if (file.isFile()) {
                mConn.scanFile(file.getAbsolutePath(), null);
                return;
            }
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            File[] files1 = file.listFiles();
            if (files1 == null) {
                return;
            }
            for (File f : files1) {
                scan(f);
            }
        }
    }
}
