package com.wss.common.widget.scaleImg;

import android.app.Activity;

import java.io.File;

/**
 * Describe：图片查看器 下载图片抽象接口
 * Created by 吴天强 on 2018年11月16日
 */
public interface ImageDownloader {
    File downLoad(String url, Activity activity);
}