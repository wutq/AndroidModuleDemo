package com.wss.common.net;

import android.os.Handler;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.rxjava.rxlife.RxLife;
import com.wss.common.base.BaseApplication;
import com.wss.common.base.R;
import com.wss.common.constants.Constants;
import com.wss.common.exception.NetErrorException;
import com.wss.common.manage.ActivityToActivity;
import com.wss.common.net.request.RequestParam;
import com.wss.common.net.response.BaseResponse;
import com.wss.common.net.response.DownloadResponse;
import com.wss.common.profile.ProfileManager;
import com.wss.common.secret.AesUtils;
import com.wss.common.utils.JsonUtils;
import com.wss.common.utils.NetworkUtil;
import com.wss.common.utils.ToastUtils;
import com.wss.common.utils.Utils;
import com.wss.common.utils.ValidUtils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.param.RxHttp;
import rxhttp.wrapper.param.RxHttpFormParam;
import rxhttp.wrapper.param.RxHttpJsonParam;
import rxhttp.wrapper.param.RxHttpNoBodyParam;


/**
 * Describe：网络请求帮助类
 * Created by 吴天强 on 2020/1/3.
 */
@SuppressWarnings("unchecked")
public class NetworkManage {
    /**
     * 取第一个进来的跳转到登录页
     */
    public volatile static int count = 0;
    private static final String GET = "GET";
    private static final String POST_FORM = "POST_FORM";
    private static final String POST_JSON = "POST_JSON";
    private static final String PUT_FORM = "PUT_FORM";
    private static final String PUT_JSON = "PUT_JSON";
    private static final String DELETE_FORM = "DELETE_FORM";
    private static final String DELETE_JSON = "DELETE_JSON";
    /**
     * 账户相关的报错信息
     */
    private static final List<String> ACCOUNT_ERROR_CODE = new ArrayList<>();

    /**
     * 无须加密白名单
     */
    private static final List<String> NO_DECRYPTION_LIST = new ArrayList<>();

    static {
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_DISABLE);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_QUIT);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_LOCKED);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_NO_AUTHORITY);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_NO_TEAM);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_MULTIPLE_TEAM);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_TOKEN_EXPIRED);
        ACCOUNT_ERROR_CODE.add(Constants.Net.Status.CODE_ACCOUNT_POSITION_ERROR);

        NO_DECRYPTION_LIST.add(Api.UPLOAD_FILE);
        NO_DECRYPTION_LIST.add(Api.DOWNLOAD_FILE);
    }

    /**
     * 构建Get请求
     */
    @NonNull
    public static Request createGet() {
        return create(GET);
    }

    /**
     * 构建PostFrom请求
     */
    @NonNull
    public static Request createPostForm() {
        return create(POST_FORM);
    }

    /**
     * 构建PostJson请求
     */
    @NonNull
    public static Request createPostJson() {
        return create(POST_JSON);
    }

    /**
     * 构建PutForm请求
     */
    @NonNull
    public static Request createPutForm() {
        return create(PUT_FORM);
    }

    /**
     * 构建PutJson请求
     */
    @NonNull
    public static Request createPutJson() {
        return create(PUT_JSON);
    }

    /**
     * 构建DeleteForm请求
     */
    @NonNull
    public static Request createDeleteForm() {
        return create(DELETE_FORM);
    }

    /**
     * 构建DeleteJson请求
     */
    @NonNull
    public static Request createDeleteJson() {
        return create(DELETE_JSON);
    }


    /**
     * 构建请求类型
     */
    @Contract(value = "_ -> new", pure = true)
    @NonNull
    private static Request create(String method) {
        return new Request(method);
    }

    /**
     * 网络请求主类
     */
    public static class Request {
        private String method;
        private String requestId;


        Request(String method) {
            this.method = method;
            requestId = UUID.randomUUID().toString().replaceAll("-", "");
        }

        /**
         * 无参请求返回String类型
         *
         * @param life 声明周期实现类
         * @param url  请求URL
         */
        public <T> Observable<T> request(LifecycleOwner life, String url) {
            return request(life, url, null, (Class<T>) String.class);
        }

        /**
         * 无参请求返回T类型
         *
         * @param life 声明周期实现类
         * @param url  请求URL
         * @param type 响应泛型类型
         */
        public <T> Observable<T> request(LifecycleOwner life, String url, Class<T> type) {
            return request(life, url, null, type);
        }

        /**
         * 有参请求返回String类型
         *
         * @param life  声明周期实现类
         * @param url   请求URL
         * @param param 请求参数
         */
        public <T> Observable<T> request(LifecycleOwner life, String url, RequestParam param) {
            return request(life, url, param, (Class<T>) String.class);
        }

        /**
         * 有参请求返回T类型
         *
         * @param life  声明周期实现类
         * @param url   请求URLu
         * @param param 请求参数
         * @param type  响应泛型类型
         */
        public <T> Observable<T> request(LifecycleOwner life, String url, RequestParam param, Class<T> type) {
            return Observable.<T>create(
                    subscriber -> {
                        if (!NetworkUtil.isNetworkEnabled(BaseApplication.i())) {
                            //无可用网络
                            subscriber.onError(new NetErrorException(BaseApplication.i().getString(R.string.network_error_no_net)));
                            return;
                        }
                        Disposable subscribe = checkRequest(url, param)
                                .as(RxLife.asOnMain(life))  //感知生命周期，并在主线程回调
                                .subscribe(response ->
                                        checkResponse(url, response).subscribe(baseResponse -> {
                                            if (type == BaseResponse.class) {
                                                subscriber.onNext((T) baseResponse);
                                            } else if (type == String.class) {
                                                subscriber.onNext((T) baseResponse.getData());
                                            } else {
                                                //根据类型解析
                                                subscriber.onNext(JsonUtils.getObject(baseResponse.getData(), type));
                                            }
                                        }, subscriber::onError), throwable -> subscriber.onError(handleError(throwable)));
                        BaseApplication.i().addNetDisposable(requestId, subscribe);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }

        /**
         * 无参请求返回List<T>类型
         *
         * @param life 声明周期实现类
         * @param url  请求URL
         * @param type 响应泛型类型
         */
        public <T> Observable<List<T>> requestList(LifecycleOwner life, String url, Class<T> type) {
            return requestList(life, url, null, type);
        }

        /**
         * 有参请求返回List<String>类型
         *
         * @param life  声明周期实现类
         * @param url   请求URL
         * @param param 请求参数
         */
        public <T> Observable<List<T>> requestList(LifecycleOwner life, String url, RequestParam param) {
            return requestList(life, url, param, (Class<T>) String.class);
        }

        /**
         * 有参请求返回List<T>类型
         *
         * @param life  声明周期实现类
         * @param url   请求URL
         * @param param 请求参数
         * @param type  响应泛型类型
         */
        public <T> Observable<List<T>> requestList(LifecycleOwner life, String url, RequestParam param, Class<T> type) {
            return Observable.<List<T>>create(
                    subscriber -> {
                        if (!NetworkUtil.isNetworkEnabled(BaseApplication.i())) {
                            //无可用网络
                            subscriber.onError(new NetErrorException(BaseApplication.i().getString(R.string.network_error_no_net)));
                            return;
                        }
                        Disposable subscribe = checkRequest(url, param)
                                .as(RxLife.asOnMain(life))  //感知生命周期，并在主线程回调
                                .subscribe(response -> checkResponse(url, response).subscribe(
                                        baseResponse -> subscriber.onNext(JsonUtils.getList(baseResponse.getData(), type)),
                                        subscriber::onError), throwable -> subscriber.onError(handleError(throwable)));
                        BaseApplication.i().addNetDisposable(requestId, subscribe);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }


        /**
         * 下载文件
         *
         * @param life        声明周期实现类
         * @param downloadUrl 文件的下载地址
         * @param localPath   本地保存的文件路径
         * @return 下载response
         */
        public Observable<DownloadResponse> requestDownload(LifecycleOwner life, String downloadUrl, String localPath) {
            return Observable.<DownloadResponse>create(
                    subscriber -> {
                        if (!NetworkUtil.isNetworkEnabled(BaseApplication.i())) {
                            //无可用网络
                            subscriber.onError(new NetErrorException(BaseApplication.i().getString(R.string.network_error_no_net)));
                            return;
                        }
                        Logger.e("文件下载地址：" + downloadUrl);
                        DownloadResponse downloadResponse = new DownloadResponse();
                        //下载失败，处理相关逻辑
                        RxHttp.get(downloadUrl)
                                .asDownload(localPath, progress -> {
                                    Logger.i("文件下载中，" + progress);
                                    //下载进度回调,0-100，仅在进度有更新时才会回调，最多回调101次，最后一次回调文件存储路径
                                    downloadResponse.setProgress(progress);
                                    subscriber.onNext(downloadResponse);
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .as(RxLife.as(life))
                                .subscribe(s -> {
                                    Logger.e("文件下载完成，保存：" + s);
                                    //下载完成，处理相关逻辑
                                    downloadResponse.setSuccess(true);
                                    subscriber.onNext(downloadResponse);
                                }, subscriber::onError);
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }


        /**
         * 检查响应
         *
         * @param apiUrl   接口地址
         * @param response 响应数据
         * @return BaseResponse
         */
        private Observable<BaseResponse> checkResponse(String apiUrl, String response) {
            return Observable.<BaseResponse>create(subscriber -> {
                BaseApplication.i().removeNetDisposable(requestId);
                Logger.e(String.format("Request-Id:%s\n响应:%s", requestId, response));
                String state = JsonUtils.getString(response, Constants.Net.STATE);
                String message = JsonUtils.getString(response, Constants.Net.MESSAGE);
                String data;
                try {
                    //防止返回的data为null导致解析json失败
                    data = JsonUtils.getString(response, Constants.Net.DATA);
                } catch (Exception e) {
                    data = "";
                }
                if (Constants.Net.Status.CODE_SUCCESS.equals(state)) {
                    subscriber.onNext(new BaseResponse(state, message, data));
                } else {
                    if (!Api.LOGIN.equals(apiUrl) && ACCOUNT_ERROR_CODE.contains(state)) {
                        //如果是非登录接口且错误码为定义的这部分，则需要跳转到登录页面
                        synchronized (NetworkManage.class) {
                            //防止多个线程一瞬间进来了，让需要进来的进行排队
                            if (count <= 0) {
                                //Token 过期 跳转登录页面
                                toLoginActivity(message);
                            }
                            count++;
                        }
                    } else {
                        //接口返回 false 把message、data扔出去
                        subscriber.onError(new NetErrorException(state, message, data));
                    }
                }
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }


        /**
         * 网络请求之前参数检查,组装公共请求参数，加密等操作
         *
         * @param url   接口地址
         * @param param 请求参数
         * @return Observable<String>
         */
        @NotNull
        private Observable<String> checkRequest(String url, RequestParam param) {
            if (!ValidUtils.isValid(param)) {
                param = new RequestParam();
            }
            //打印请求
            Logger.e(String.format("Request-Id:%s\n%s %s%s\n请求参数：%s", requestId, method, Api.BASE_URL, url, JsonUtils.toJson(param.getParameter())));
            RxHttp rxHttp = buildRequest(url, param);
            //添加Header
            for (Map.Entry<String, String> entry : buildHeader().entrySet()) {
                rxHttp.addHeader(entry.getKey(), entry.getValue());
            }
            return rxHttp.asString();
        }

        /**
         * 构建请求
         *
         * @param url   接口地址
         * @param param 请求参数
         * @return RxHttp
         */
        private RxHttp buildRequest(String url, @NotNull RequestParam param) {
            Map<String, File> fileMap = new HashMap<>(16);
            Map<String, List<File>> fileListMap = new HashMap<>(16);
            Map<String, Object> objectMap = new HashMap<>(16);

            //分离请求参数中的文件
            for (Map.Entry<String, Object> entry : param.getParameter().entrySet()) {
                if (entry.getValue() instanceof File) {
                    //单个文件
                    fileMap.put(entry.getKey(), (File) entry.getValue());
                    continue;
                }
                if (entry.getValue() instanceof List && ((List) entry.getValue()).size() > 0) {
                    if (((List) entry.getValue()).get(0) instanceof File) {
                        //文件list
                        fileListMap.put(entry.getKey(), (List<File>) entry.getValue());
                        continue;
                    }
                }

                if (entry.getValue() instanceof String) {
                    //把null转成""
                    String value = String.valueOf(entry.getValue());
                    if (!ValidUtils.isValid(value) || "null".equalsIgnoreCase(value)) {
                        value = "";
                    }
                    objectMap.put(entry.getKey(), value);
                    continue;
                }
                objectMap.put(entry.getKey(), entry.getValue());
            }

            Map<String, String> requestParam = new HashMap<>();
            String paramJson = JsonUtils.toJson(objectMap);
            /*
             *注：该加密方式为：把请求参数key、value以json的格式整体加密，然后再以key、value的形式发送给服务器
             */
            if (!NO_DECRYPTION_LIST.contains(url) && ProfileManager.profile().isSecret()) {
                //请求是否需要加密
                requestParam.put(Constants.Net.REQUEST_DATA, AesUtils.getInstance().encrypt(paramJson));
            } else {
                requestParam.put(Constants.Net.REQUEST_DATA, paramJson);
            }
            Logger.i(String.format("加密请求\nRequest-Id:%s\n%s %s%s\n请求参数：%s",
                    requestId, method, Api.BASE_URL, url, JsonUtils.toJson(requestParam)));
            RxHttp rxHttp;
            switch (method) {
                case POST_JSON:
                case PUT_JSON:
                case DELETE_JSON:
                    //JSON格式请求
                    if (TextUtils.equals(method, POST_JSON)) {
                        rxHttp = RxHttp.postJson(url);
                    } else if (TextUtils.equals(method, DELETE_JSON)) {
                        rxHttp = RxHttp.deleteJson(url);
                    } else {
                        rxHttp = RxHttp.putJson(url);
                    }
                    if (isUpLoadFile(url)) {
                        //上传文件传参特殊
                        ((RxHttpJsonParam) rxHttp).addAll(objectMap);
                    } else {
                        ((RxHttpJsonParam) rxHttp).addAll(requestParam);
                    }

                    //添加文件请求参数
                    if (!fileMap.isEmpty()) {
                        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                            ((RxHttpJsonParam) rxHttp).add(entry.getKey(), entry.getValue());
                        }
                    }
                    if (!fileListMap.isEmpty()) {
                        for (Map.Entry<String, List<File>> entry : fileListMap.entrySet()) {
                            ((RxHttpJsonParam) rxHttp).add(entry.getKey(), entry.getValue());
                        }
                    }
                    break;
                case POST_FORM:
                case PUT_FORM:
                case DELETE_FORM:
                    //POST格式请求
                    if (TextUtils.equals(method, POST_FORM)) {
                        rxHttp = RxHttp.postForm(url);
                    } else if (TextUtils.equals(method, DELETE_FORM)) {
                        rxHttp = RxHttp.deleteForm(url);
                    } else {
                        rxHttp = RxHttp.putForm(url);
                    }
                    if (isUpLoadFile(url)) {
                        //上传文件传参特殊
                        ((RxHttpFormParam) rxHttp).addAll(objectMap);
                    } else {
                        ((RxHttpFormParam) rxHttp).addAll(requestParam);
                    }//添加文件请求参数
                    if (!fileMap.isEmpty()) {
                        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                            ((RxHttpFormParam) rxHttp).addFile(entry.getKey(), entry.getValue());
                        }
                    }
                    if (!fileListMap.isEmpty()) {
                        for (Map.Entry<String, List<File>> entry : fileListMap.entrySet()) {
                            ((RxHttpFormParam) rxHttp).addFile(entry.getKey(), entry.getValue());
                        }
                    }
                    break;
                case GET:
                default:
                    rxHttp = RxHttp.get(url);
                    ((RxHttpNoBodyParam) rxHttp).addAll(requestParam);
                    //添加文件请求参数
                    if (!fileMap.isEmpty()) {
                        for (Map.Entry<String, File> entry : fileMap.entrySet()) {
                            ((RxHttpNoBodyParam) rxHttp).add(entry.getKey(), entry.getValue());
                        }
                    }
                    if (!fileListMap.isEmpty()) {
                        for (Map.Entry<String, List<File>> entry : fileListMap.entrySet()) {
                            ((RxHttpNoBodyParam) rxHttp).add(entry.getKey(), entry.getValue());
                        }
                    }
                    break;
            }
            return rxHttp;
        }

        /**
         * 构建请求头
         *
         * @return 请求头Map
         */
        @NotNull
        private Map<String, String> buildHeader() {
            String deviceId = BaseApplication.i().getDeviceId();
            String token = BaseApplication.i().getLoginToken();
            Map<String, String> header = new HashMap<>();
            header.put(Constants.Net.HEADER_CHANNEL, Constants.Common.CHANEL);
            header.put(Constants.Net.HEADER_API_VERSION, Constants.Common.API_VERSION);
            header.put(Constants.Net.HEADER_DEVICE_ID, ValidUtils.isValid(deviceId) ? deviceId : "");
            header.put(Constants.Net.HEADER_TOKEN, ValidUtils.isValid(token) ? token : "");
            header.put(Constants.Net.HEADER_APP_VERSION, Utils.getVersionName());
            header.put(Constants.Net.HEADER_IP, NetworkUtil.getIpAddress());
            header.put(Constants.Net.REQUEST_ID, requestId);
            Logger.i("请求Header:\n" + JsonUtils.toJson(header));
            return header;
        }

        /**
         * 检查是否上传文件
         *
         * @param url url
         * @return boolean
         */
        @Contract(value = "null -> false", pure = true)
        private boolean isUpLoadFile(String url) {
            return Api.UPLOAD_FILE.equals(url);
        }


        /**
         * 处理网络请求中的异常
         *
         * @param t Throwable
         * @return NetErrorException
         */
        @Contract("_ -> new")
        @NotNull
        private NetErrorException handleError(@NotNull Throwable t) {
            BaseApplication.i().removeNetDisposable(requestId);
            String errorMessage = null;
            String loggerMessage = "";
            if (t instanceof SocketTimeoutException) {
                errorMessage = BaseApplication.i().getString(R.string.request_time_out);
                loggerMessage = errorMessage;
            } else if (t instanceof HttpStatusCodeException) {
                HttpStatusCodeException codeException = (HttpStatusCodeException) t;
                try {
                    errorMessage = JsonUtils.getString(codeException.getResult(), Constants.Net.MESSAGE);
                } catch (Exception e) {
                    errorMessage = BaseApplication.i().getString(R.string.network_error_server_error);
                }
                loggerMessage = String.format("%s %s", codeException.getStatusCode(), errorMessage);
            } else {
                loggerMessage = t.getMessage();
            }
            if (!ValidUtils.isValid(errorMessage)) {
                errorMessage = BaseApplication.i().getString(R.string.network_error_server_error);
            }
            Logger.e(String.format("Request-Id:%s\n请求报错:%s", requestId, loggerMessage));
            return new NetErrorException(errorMessage);
        }

        /**
         * 跳转登录页
         *
         * @param errorMessage 错误信息
         */
        private void toLoginActivity(String errorMessage) {
            Observable.<String>create(
                    subscriber -> {
                        //检查是否存在未完成的请求，有就取消
                        for (Map.Entry<String, Disposable> entry : BaseApplication.i().getNetDisposables().entrySet()) {
                            if (!entry.getValue().isDisposed()) {
                                entry.getValue().dispose();
                            }
                        }
                        ToastUtils.show(errorMessage);
                        //延时1秒跳转到登录页，最大限度的，避免由于网络延时，带来的token过期退出到登录页的异常
                        new Handler().postDelayed(() -> {
                            ActivityToActivity.toLoginActivity(BaseApplication.i());
                            BaseApplication.i().loginOutClean();
                        }, 500);

                    })
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe();
        }

    }
}
