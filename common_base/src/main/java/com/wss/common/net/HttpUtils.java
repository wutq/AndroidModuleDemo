package com.wss.common.net;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.tamic.novate.Novate;
import com.tamic.novate.callback.ResponseCallback;
import com.tamic.novate.callback.RxFileCallBack;
import com.wss.common.base.BaseApplication;
import com.wss.common.net.callback.OnResultCallBack;

import java.io.File;
import java.util.List;

/**
 * Describe：网络请求帮助类
 * Created by 吴天强 on 2017/9/19.
 */

public class HttpUtils {

    private static final int REQUEST_GET = 0;
    private static final int REQUEST_POST = 1;
    private static final int REQUEST_JSON = 2;
    private Novate.Builder builder;
    private static HttpUtils httpUtils;
    private String baseUrl;

    private HttpUtils() {
        builder = new Novate.Builder(BaseApplication.getApplication());
        builder.addCookie(true); //是否同步cooike 默认不同步

        //https配置 xxx.cer放在asset目录下
//        builder.skipSSLSocketFactory(true);//信任所有证书
//        builder.addSSLSocketFactory(NovateHttpsFactroy.creatSSLSocketFactory(
//                BaseApplication.getApplication().getBaseContext(), "xxx.cer"));

//        builder.addHeader(headers); //添加公共请求头
//        builder.addParameters(parameters);//公共参数
//        builder.connectTimeout(10);  //连接时间 可以忽略
//        builder.addCache(true);  //是否缓存 默认缓存
//        builder.addCache(cache, cacheTime);   //自定义缓存
//        builder.addLog(true);//是否开启log
//        builder.cookieManager(new NovateCookieManager()); // 自定义cooike，可以忽略
//        builder.addInterceptor(); // 自定义Interceptor
//        builder.addNetworkInterceptor(); // 自定义NetworkInterceptor
//        builder.proxy(proxy); //代理
//        builder.client(client); //clent 默认不需要
    }

    public static synchronized HttpUtils getInstance() {
        if (httpUtils == null) {
            httpUtils = new HttpUtils();
        }
        return new HttpUtils();
    }

    public HttpUtils setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    ////////////////////////////////////////// GET请求 /////////////////////////////////////////////

    /**
     * Get无参无Tag
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void getRequest(String url, OnResultCallBack callback) {
        request(REQUEST_GET, url, new RequestParam(), null, callback);
    }

    /**
     * Get 无参有Tag
     *
     * @param url      请求地址
     * @param tag      标签
     * @param callback 回调
     */
    public void getRequest(String url, String tag, OnResultCallBack callback) {
        request(REQUEST_GET, url, new RequestParam(), tag, callback);
    }

    /**
     * Get有参无Tag
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void getRequest(String url, RequestParam params, OnResultCallBack callback) {
        request(REQUEST_GET, url, params, null, callback);
    }

    /**
     * Get有参有Tag
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      标签
     * @param callback 回调
     */
    public void getRequest(String url, RequestParam params, String tag, OnResultCallBack callback) {
        request(REQUEST_GET, url, params, tag, callback);
    }


    ////////////////////////////////////////// POST请求 /////////////////////////////////////////////

    /**
     * Post 无参无TAG
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void postRequest(String url, OnResultCallBack callback) {
        request(REQUEST_POST, url, new RequestParam(), null, callback);
    }

    /**
     * Post 无参有TAG
     *
     * @param url      请求地址
     * @param tag      标签
     * @param callback 回调
     */
    public void postRequest(String url, String tag, OnResultCallBack callback) {
        request(REQUEST_POST, url, new RequestParam(), tag, callback);
    }

    /**
     * Post 有参无TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void postRequest(String url, RequestParam params, OnResultCallBack callback) {
        request(REQUEST_POST, url, params, null, callback);
    }

    /**
     * Post 有参有TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      标签
     * @param callback 回调
     */
    public void postRequest(String url, RequestParam params, String tag, OnResultCallBack callback) {
        request(REQUEST_POST, url, params, tag, callback);
    }

    ////////////////////////////////////////// JSON格式请求 /////////////////////////////////////////

    /**
     * Post 无参无TAG
     *
     * @param url      请求地址
     * @param callback 回调
     */
    public void jsonRequest(String url, OnResultCallBack callback) {
        request(REQUEST_JSON, url, new RequestParam(), null, callback);
    }

    /**
     * Post 无参有TAG
     *
     * @param url      请求地址
     * @param tag      标签
     * @param callback 回调
     */
    public void jsonRequest(String url, String tag, OnResultCallBack callback) {
        request(REQUEST_JSON, url, new RequestParam(), tag, callback);
    }

    /**
     * Post 有参无TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param callback 回调
     */
    public void jsonRequest(String url, RequestParam params, OnResultCallBack callback) {
        request(REQUEST_JSON, url, params, null, callback);
    }

    /**
     * Post 有参有TAG
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param tag      标签
     * @param callback 回调
     */
    public void jsonRequest(String url, RequestParam params, String tag, OnResultCallBack callback) {
        request(REQUEST_JSON, url, params, tag, callback);
    }


    /**
     * 最终请求
     *
     * @param methodType 请求类型 get  post
     * @param url        接口
     * @param params     参数
     * @param tag        tag
     * @param callback   请求回调
     */
    private void request(int methodType, String url, RequestParam params, String tag, ResponseCallback callback) {
        checkBaseUrl();
        Logger.e(baseUrl + url + params.toJson());
        switch (methodType) {
            case REQUEST_POST:
                builder.build().rxPost(tag, url, params.getParameter(), callback);
                break;
            case REQUEST_GET:
                builder.build().rxGet(tag, url, params.getParameter(), callback);
                break;
            case REQUEST_JSON:
                builder.build().rxJson(tag, url, params.toJson(), callback);
                break;
            default:
                builder.build().rxGet(tag, url, params.getParameter(), callback);
                break;
        }
    }

    ////////////////////////////////////////// 上传文件 /////////////////////////////////////////////

    /**
     * 上传单个文件
     *
     * @param url      接口
     * @param file     文件
     * @param callback 回调
     */
    public void upLoadFile(String url, File file, OnResultCallBack callback) {
        upLoadFile(url, file, null, callback);
    }

    /**
     * 上传单个文件
     *
     * @param url      接口
     * @param file     文件
     * @param tag      标签
     * @param callback 回调
     */
    public void upLoadFile(String url, File file, String tag, ResponseCallback callback) {
        //使用Part 方式上传文件
        checkBaseUrl();
        Logger.e(NetConfig.Url.getBaseUrl() + url + file.getAbsolutePath());
        builder.build().rxUploadWithPart(tag, url, file, callback);
    }

    /**
     * 上传多个文件
     *
     * @param url      接口
     * @param files    文件
     * @param callback 回调
     */
    public void upLoadFile(String url, List<File> files, OnResultCallBack callback) {
        upLoadFile(url, files, null, callback);
    }


    /**
     * 上传多个文件
     *
     * @param url      接口
     * @param files    文件
     * @param tag      标签
     * @param callback 回调
     */
    public void upLoadFile(String url, List<File> files, String tag, ResponseCallback callback) {
        checkBaseUrl();
        Logger.e(NetConfig.Url.getBaseUrl() + url + files.size());
        builder.build().rxUploadWithPartListByFile(tag, url, files, callback);
    }

    ////////////////////////////////////////// 下载文件 /////////////////////////////////////////////

    /**
     * 文件下载
     *
     * @param url      文件路径
     * @param callBack 回调
     */
    public void downloadFile(String url, RxFileCallBack callBack) {
        checkBaseUrl();
        Logger.e(NetConfig.Url.getBaseUrl() + url);
        builder.build().rxDownload(url, callBack);
    }

    /**
     * 检查是否设置BaseURL
     */
    private void checkBaseUrl() {
        //如果没有设置请求BaseUrl  则使用默认的BaseUrl
        if (TextUtils.isEmpty(baseUrl)) {
            baseUrl = NetConfig.Url.getBaseUrl();
        }
        builder.baseUrl(baseUrl);
    }

}
