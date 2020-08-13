package com.wss.common.view.gallery;

import android.app.Activity;

import java.io.File;

/**
 * Describe：图片查看器 下载图片抽象接口
 * Created by 吴天强 on 2018年11月16日
 */
public interface ImageDownloader {

    /**
     * 下载
     *
     * @param url      URL
     * @param activity activity
     * @return File
     */
    File downLoad(String url, Activity activity);
}