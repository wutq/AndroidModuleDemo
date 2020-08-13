package com.wss.common.view.file.mvp.model;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.MediaStore;

import com.wss.common.base.BaseApplication;
import com.wss.common.base.mvp.BaseModel;
import com.wss.common.constants.Constants;
import com.wss.common.utils.FileUtils;
import com.wss.common.view.file.bean.FileInfo;
import com.wss.common.view.file.mvp.contract.SelectFileContract;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Describe：选择文件Model
 * Created by 吴天强 on 2020/6/20.
 */
public class SelectFileModel extends BaseModel implements SelectFileContract.Model {
    /**
     * 视频后缀
     */
    private static final String[] VIDEO_SUFFIX = {".mp4", ".rmvb", ".avi", ".flv"};
    /**
     * 语音后缀
     */
    private static final String[] VOICE_SUFFIX = {".mp3", ".wav", ".ogg", ".midi"};
    /**
     * 文档后缀
     */
    private static final String[] DOCUMENT_SUFFIX = {".doc", ".docx", ".xls", ".xlsx", ".ppt", ".pptx", ".pdf"};
    /**
     * 压缩包后缀
     */
    private static final String[] PACKAGE_SUFFIX = {".jar", ".zip", ".rar", ".gz", ".apk", ".img"};
    /**
     * 其他文件后缀
     */
    private static final String[] OTHER_SUFFIX = {".htm", ".html", ".php", ".jsp", ".txt", ".java", ".c", ".cpp", ".py", ".xml", ".json", ".log"};

    public SelectFileModel(LifecycleOwner lifecycleOwner) {
        super(lifecycleOwner);
    }


    @Override
    public Observable<List<FileInfo>> queryFile(int type) {
        return Observable.<List<FileInfo>>create(
                subscriber -> {
                    switch (type) {
                        case Constants.FileType.IMAGE:
                            //读取手机图片
                            subscriber.onNext(queryImages());
                            break;
                        case Constants.FileType.VIDEO:
                            //视频
                            subscriber.onNext(readSdCardFile(getQuerySql(VIDEO_SUFFIX)));
                            break;
                        case Constants.FileType.AUDIO:
                            //音频
                            subscriber.onNext(readSdCardFile(getQuerySql(VOICE_SUFFIX)));
                            break;
                        case Constants.FileType.DOC:
                            //文档
                            subscriber.onNext(readSdCardFile(getQuerySql(DOCUMENT_SUFFIX)));
                            break;
                        case Constants.FileType.PACKAGE:
                            //压缩包
                            subscriber.onNext(readSdCardFile(getQuerySql(PACKAGE_SUFFIX)));
                            break;
                        case Constants.FileType.OTHER:
                        default:
                            //其他
                            subscriber.onNext(readSdCardFile(getQuerySql(OTHER_SUFFIX)));
                            break;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 读取手机里的图片
     *
     * @return 图片列表
     */
    @NotNull
    private List<FileInfo> queryImages() {
        List<FileInfo> imageList = new ArrayList<>();
        ContentResolver mContentResolver = BaseApplication.i().getApplicationContext().getContentResolver();
        String[] projection = {
                "_id",
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.SIZE};

        Cursor mCursor = mContentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, "("
                        + MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? or "
                        + MediaStore.Images.Media.MIME_TYPE + "=? ) and _size > 0",
                new String[]{"image/jpg", "image/png", "image/jpeg"},
                MediaStore.Images.Media.DATE_TAKEN + " desc");

        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                FileInfo fileInfo = new FileInfo();
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                fileInfo.setFilePath(path);
                fileInfo.setFileSize(size);
                fileInfo.setFileName(displayName);
                imageList.add(fileInfo);
            }
            mCursor.close();
        }
        return imageList;
    }


    /**
     * 生成读手机影音SQL
     *
     * @param suffixList 文件后缀
     * @return 查询SQL
     */
    @NotNull
    private String getQuerySql(@NotNull String[] suffixList) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (String suffix : suffixList) {
            sb.append(MediaStore.Files.FileColumns.DATA);
            sb.append(" LIKE '%").append(suffix).append("' OR ");
        }
        return sb.substring(0, sb.length() - 3) + ")";
    }


    /**
     * 读取手机中的文件
     *
     * @param sql 查询SQL
     * @return 文件列表
     */
    @NotNull
    private List<FileInfo> readSdCardFile(String sql) {
        List<FileInfo> result = new ArrayList<>();
        String[] columns = new String[]{
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Files.FileColumns.DATE_MODIFIED,
                MediaStore.Files.FileColumns.DATA};
        ContentResolver contentResolver = BaseApplication.i().getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"),
                columns, sql, null, null);
        if (cursor != null) {
            int columnIndexOrThrowData = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);
            while (cursor.moveToNext()) {
                String path = cursor.getString(columnIndexOrThrowData);
                FileInfo document = formatFile(new File(path));
                //去除空文件
                if (document.getFileSize() > 0) {
                    result.add(document);
                }
            }
            cursor.close();
        }
        return result;
    }

    /**
     * 格式化读取的文件
     *
     * @param file 源文件
     * @return 格式化以后的文件
     */
    @NotNull
    private FileInfo formatFile(@NotNull File file) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getName());
        fileInfo.setFilePath(file.getPath());
        fileInfo.setFileSize(file.length());
        fileInfo.setTime(FileUtils.getFileLastModifiedTime(file));
        return fileInfo;
    }
}
