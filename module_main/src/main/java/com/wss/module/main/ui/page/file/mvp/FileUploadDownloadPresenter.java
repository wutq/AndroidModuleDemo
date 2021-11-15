package com.wss.module.main.ui.page.file.mvp;

import com.wss.common.base.BaseApplication;
import com.wss.common.base.mvp.BasePresenter;
import com.wss.common.manage.MediaScannerManage;
import com.wss.common.utils.DateUtils;
import com.wss.common.utils.FileUtils;
import com.wss.common.utils.ToastUtils;
import com.wss.common.widget.dialog.AppDialog;
import com.wss.common.widget.dialog.ProgressBarDialog;
import com.wss.module.main.R;
import com.wss.module.main.ui.page.file.mvp.contract.FileUploadDownloadContract;
import com.wss.module.main.ui.page.file.mvp.model.FileUploadDownloadModel;

import java.io.File;

/**
 * Describe：
 * Created by 吴天强 on 2021/11/15.
 */
public class FileUploadDownloadPresenter extends BasePresenter<FileUploadDownloadModel, FileUploadDownloadContract.View>
        implements FileUploadDownloadContract.Presenter {

    /**
     * 带进度条的加载框
     */
    private ProgressBarDialog progressBarDialog;

    @Override
    protected FileUploadDownloadModel createModule() {
        return new FileUploadDownloadModel(getLifecycleOwner());
    }

    @Override
    public void start() {
        progressBarDialog = new ProgressBarDialog.Builder(getContext()).create();

    }

    @Override
    public void download() {
        progressBarDialog.show();
        //文件下载地址
        String downloadUrl = "http://www.xxx.xxx.abc.png";
        String suffix = downloadUrl.substring(downloadUrl.lastIndexOf("."));
        String fileName = System.currentTimeMillis() + suffix;

        //按照年月日存放
        String dateDirectory = String.format("%s年%s月%s日", DateUtils.getCurrentDateStr("yyyy"),
                DateUtils.getCurrentDateStr("MM"), DateUtils.getCurrentDateStr("dd"));
        String rootPath = FileUtils.IMAGE_PATH;
        //下载文件存放位置
        String filePath = String.format("%s/%s/%s", rootPath, dateDirectory, fileName);
        //最后提示用户的目录
        String tipsText = String.format("%s%s/%s", BaseApplication.i().getPackageName(), FileUtils.Constant.IMAGE, dateDirectory);
        getModel().downloadFile(downloadUrl, filePath).subscribe(
                downloadResponse -> {
                    if (downloadResponse.isSuccess()) {
                        //下载完成
                        progressBarDialog.dismiss();
                        new AppDialog.Builder(getContext())
                                .setContent(getContext().getString(R.string.main_download_tips, tipsText))
                                .setSingleButton(getContext().getString(R.string.confirm))
                                .create()
                                .show();
                        MediaScannerManage.getInstance(getContext()).scanFile(new File(filePath));
                    } else {
                        progressBarDialog.setProgress(downloadResponse.getProgress().getProgress());
                    }
                }, t -> {
                    progressBarDialog.dismiss();
                    ToastUtils.show(t.getMessage());
                }
        );

    }

    @Override
    public void upload(File file) {
        showLoading();
        getModel().uploadImg(file).subscribe(
                s -> {
                    dismissLoading();
                    ToastUtils.show("上传成功");
                }, t -> {
                    dismissLoading();
                    ToastUtils.show(t.getMessage());
                }
        );


    }
}
