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
     * 登录
     */
    String LOGIN = "/user/login";
    /**
     * 注册
     */
    String REGISTER = "/user/register";

    /**
     * 体系
     */
    String TREE = "/tree/json";
    /**
     * 体系下文章
     */
    String TREE_ARTICLE = "/article/list/%s/json?cid=%s";

    /**
     * 项目分类
     */
    String PROJECT = "/project/tree/json";

    /**
     * 项目列表
     */
    String PROJECT_LIST = "/project/list/%s/json?cid=%s";

    /**
     * 查询公众号列表
     */
    String WX_NUMBER = "/wxarticle/chapters/json";
    /**
     * 查询公众号文章
     */
    String WX_ARTICLE_LIST = "/wxarticle/list/%s/%s/json";

    /**
     * 搜索
     */
    String SEARCH_LIST = "/article/query/%s/json?k=%s";

    /**
     * 收藏列表
     */
    String COLLECTION_LIST = "/lg/collect/list/%s/json";

    /**
     * 下载文件
     */
    String DOWNLOAD_FILE = "http://wap.dl.pinyin.sogou.com/wapdl/hole/201512/03/SogouInput_android_v7.11_sweb.apk";
}
