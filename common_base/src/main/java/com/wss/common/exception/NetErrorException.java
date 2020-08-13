package com.wss.common.exception;

/**
 * Describe：网络请求异常类
 * Created by 吴天强 on 2019/6/15.
 */
public class NetErrorException extends Exception {

    /**
     * 网络请求错误码
     */
    private String errorCode;
    /**
     * 响应的Data
     */
    private String responseData;

    public NetErrorException() {
    }

    public NetErrorException(String message) {
        this("", message);
    }

    public NetErrorException(String errorCode, String message) {
        this(errorCode, message, "");

    }

    public NetErrorException(String errorCode, String message, String responseData) {
        super(message);
        this.errorCode = errorCode;
        this.responseData = responseData;
    }

    /**
     * 返回网络请求错误码
     *
     * @return errorCode
     */
    public String getNetErrorCode() {
        return errorCode;
    }

    /**
     * 返回网路响应的DATA
     *
     * @return responseData
     */
    public String getNetResponseData() {
        return responseData;
    }
}
