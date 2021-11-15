package com.wss.common.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.orhanobut.logger.Logger;
import com.wss.common.base.BaseApplication;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

/**
 * Describe：文件帮助类
 * Created by 吴天强 on 2018/10/25.
 */
public class FileUtils {
    public static final String APPS_ROOT_DIR = getExternalStorePath() + File.separator + BaseApplication.i().getPackageName();
    public static final String IMAGE_PATH = APPS_ROOT_DIR + Constant.IMAGE;
    public static final String TEMP_PATH = APPS_ROOT_DIR + Constant.TEMP;
    public static final String APP_CRASH_PATH = APPS_ROOT_DIR + Constant.APP_CRASH;
    public static final String FILE_PATH = APPS_ROOT_DIR + Constant.FILE;

    /**
     * 存放文件的目录
     */
    public interface Constant {
        String IMAGE = "/Image";
        String TEMP = "/Temp";
        String FILE = "/File";
        String APP_CRASH = "/AppCrash";
    }


    /**
     * 外置存储卡的路径
     *
     * @return
     */
    @Nullable
    public static String getExternalStorePath() {
        if (isExistExternalStore()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 是否有外存卡
     *
     * @return
     */
    public static boolean isExistExternalStore() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @org.jetbrains.annotations.Nullable
    private static File create(String path) {
        if (!isExistExternalStore()) {
            Logger.e("储存卡已拔出");
            return null;
        }
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    /**
     * 返回图片存放目录
     *
     * @return File
     */
    @Nullable
    public static String getImagePath() {
        File file = create(IMAGE_PATH);
        return ValidUtils.isValid(file) ? file.getAbsolutePath() : "";
    }

    /**
     * 返回临时存放目录
     *
     * @return File
     */
    @Nullable
    public static String getTempPath() {
        File file = create(TEMP_PATH);
        return ValidUtils.isValid(file) ? file.getAbsolutePath() : "";
    }

    /**
     * 存储日志文件目录
     *
     * @return File
     */
    @NotNull
    public static String getAppCrashPath() {
        File file = create(APP_CRASH_PATH);
        return ValidUtils.isValid(file) ? file.getAbsolutePath() : "";
    }

    /**
     * 存储文件目录
     *
     * @return File
     */
    @NotNull
    public static String getFilePath() {
        File file = create(FILE_PATH);
        return ValidUtils.isValid(file) ? file.getAbsolutePath() : "";
    }

    /**
     * 7.0以上拍照 安装应用等文件问题
     *
     * @param context context
     * @param file    file
     * @return Uri
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            fileUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * URI转文件
     *
     * @param context context
     * @param uri     uri
     * @return File
     */
    @org.jetbrains.annotations.Nullable
    public static String uriToFile(@NotNull Activity context, @NotNull Uri uri) {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String filePath = null;
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // 如果是document类型的 uri, 则通过document id来进行处理
                String documentId = DocumentsContract.getDocumentId(uri);
                if (isMediaDocument(uri)) { // MediaProvider
                    // 使用':'分割
                    String type = documentId.split(":")[0];
                    String id = documentId.split(":")[1];

                    String selection = MediaStore.Images.Media._ID + "=?";
                    String[] selectionArgs = {id};
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    filePath = getDataColumn(context, contentUri, selection, selectionArgs);
                } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(documentId));
                    filePath = getDataColumn(context, contentUri, null, null);
                } else if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        filePath = Environment.getExternalStorageDirectory() + "/" + split[1];
                    }
                }  //Log.e("路径错误");

            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果是 content 类型的 Uri
                filePath = getDataColumn(context, uri, null, null);
            } else if ("file".equals(uri.getScheme())) {
                // 如果是 file 类型的 Uri,直接获取图片对应的路径
                filePath = uri.getPath();
            }
            return filePath;
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return "";
    }


    /**
     * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
     *
     * @return
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String path = null;

        String[] projection = new String[]{MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(projection[0]);
                path = cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is MediaProvider
     */
    private static boolean isMediaDocument(@NotNull Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private static boolean isExternalStorageDocument(@NotNull Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri the Uri to check
     * @return Whether the Uri authority is DownloadsProvider
     */
    private static boolean isDownloadsDocument(@NotNull Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * 获取目录下的文件列表
     *
     * @param strPath strPath
     */
    public static LinkedList<File> listLinkedFiles(String strPath) {
        File dir = new File(strPath);
        File file[] = dir.listFiles();
        LinkedList<File> list = null;
        if (dir.exists() && file != null && file.length > 0) {
            list = new LinkedList<>();
            Collections.addAll(list, file);
        }
        return list;
    }

    /**
     * 删除多个文件
     *
     * @param filesName filesName
     */
    public static void deleteListFiles(String filesName) {
        LinkedList<File> files = listLinkedFiles(filesName);
        if (files != null && files.size() > 0) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    /**
     * 删除指定文件
     *
     * @param filesName filesName
     */
    public static void deleteFile(String filesName) {
        File file = new File(filesName);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 读取文件的最后修改时间的方法
     */
    public static String getFileLastModifiedTime(File f) {
        Calendar cal = Calendar.getInstance();
        long time = f.lastModified();
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        cal.setTimeInMillis(time);
        return formatter.format(cal.getTime());
    }
}
