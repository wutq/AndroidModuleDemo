package com.wss.module.main.ui.page.file;

import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.wss.common.base.BaseActionBarActivity;
import com.wss.common.utils.DateUtils;
import com.wss.common.utils.FileUtils;
import com.wss.common.utils.ImageUtils;
import com.wss.common.utils.PermissionsUtils;
import com.wss.module.main.R;
import com.wss.module.main.R2;
import com.wss.module.main.ui.page.file.mvp.FileUploadDownloadPresenter;
import com.wss.module.main.ui.page.file.mvp.contract.FileUploadDownloadContract;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Describe：文件上传下载示例
 * Created by 吴天强 on 2021/11/15.
 */
public class FileUploadDownloadActivity extends BaseActionBarActivity<FileUploadDownloadPresenter>
        implements FileUploadDownloadContract.View {

    public static final int REQUEST_CODE_TAKE_PICTURE = 1001;

    @BindView(R2.id.iv_image)
    ImageView imageView;

    /**
     * 拍照临时文件
     */
    private File tempFile;

    @Override
    protected FileUploadDownloadPresenter createPresenter() {
        return new FileUploadDownloadPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_file_upload_download;
    }

    @Override
    protected void initView() {
        setCenterText("文件上传、下载");
        getPresenter().start();
    }

    @OnClick({R2.id.btn_upload_file, R2.id.btn_download_file, R2.id.iv_image})
    public void onViewClicked(@NotNull View v) {
        if (v.getId() == R.id.btn_upload_file) {
            //上传文件
            File file = ImageUtils.compressImage(tempFile.getAbsolutePath());
            getPresenter().upload(file);
        } else if (v.getId() == R.id.btn_download_file) {
            //下载文件
            PermissionsUtils.checkStorage(this).subscribe(
                    aBoolean -> {
                        if (aBoolean) {
                            getPresenter().download();
                        }
                    });
        } else if (v.getId() == R.id.iv_image) {
            //选择图片
            PermissionsUtils.checkCamera(this).subscribe(
                    aBoolean -> {
                        if (aBoolean) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            tempFile = new File(FileUtils.getExternalStorePath() + "/IMG" + DateUtils.getCurrentTimeStamp() + ".jpg");
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            }
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtils.getUriForFile(context, tempFile));
                            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
                ImageUtils.loadImage(imageView, tempFile);
            }
        }
    }
}
