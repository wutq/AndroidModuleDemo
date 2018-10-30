package com.wss.common.net;

/**
 * Describe：接口
 * Created by 吴天强 on 2018/10/17.
 */

public interface Api {

    /**
     * 获取Banner
     */
    String GET_BANNER_LIST = "/banner/json";

    /**
     * 获取文章列表
     */
    String GET_ARTICLE_LIST = "/article/list/%s/json";

    /**
     * 下载文件
     */
    String DOWNLOAD_FILE = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201512/03/SogouInput_android_v7.11_sweb.apk";
}
