package com.wss.module.main.ui.page.file.mvp.model;

import com.wss.common.base.mvp.BaseModel;
import com.wss.common.net.Api;
import com.wss.common.net.NetworkManage;
import com.wss.common.net.request.RequestParam;
import com.wss.common.net.response.DownloadResponse;
import com.wss.module.main.ui.page.file.mvp.contract.FileUploadDownloadContract;

import java.io.File;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;

/**
 * Describe：下载、上传文件Model
 * Created by 吴天强 on 2021/11/15.
 */
public class FileUploadDownloadModel extends BaseModel implements FileUploadDownloadContract.Model {
    public FileUploadDownloadModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }

    @Override
    public Observable<DownloadResponse> downloadFile(String downloadUrl, String localPath) {
        return NetworkManage.createGet().requestDownload(getLifecycleOwner(), downloadUrl, localPath);
    }

    /**
     * 上传文件
     *
     * @param file 图片列表
     * @return 文件的服务器保存信息
     */
    @Override
    public Observable<String> uploadImg(File file) {
        RequestParam params = new RequestParam();
        params.addParameter("attach", 6);
        params.addParameter("imageFile", file);
        return NetworkManage.createPostForm().request(getLifecycleOwner(), Api.UPLOAD_FILE, params);
    }
}
