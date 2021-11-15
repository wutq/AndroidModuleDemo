package com.wss.module.main.ui.page.file.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.net.response.DownloadResponse;

import java.io.File;

import io.reactivex.Observable;

/**
 * Describe：下载、上传文件契约类
 * Created by 吴天强 on 2021/11/15.
 */
public interface FileUploadDownloadContract {

    interface Model {
        /**
         * 下载文件
         *
         * @param downloadUrl 文件地址
         * @param localPath   文件下载本地地址
         * @return 文件下载响应
         */
        Observable<DownloadResponse> downloadFile(String downloadUrl, String localPath);

        /**
         * 上传文件
         *
         * @param file 文件
         * @return 结果
         */
        Observable<String> uploadImg(File file);
    }

    interface View extends IBaseView {

    }

    interface Presenter {


        /**
         * 下载
         */
        void download();

        /**
         * 上传
         *
         * @param file 文件
         */
        void upload(File file);
    }
}
