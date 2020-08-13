package com.wss.common.net.request;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Describe：请求参数封装类
 * Created by 吴天强 on 2017/9/19.
 */
public class RequestParam {

    /**
     * 存放请求参数的Map
     */
    private Map<String, Object> params;

    public RequestParam() {
        params = new LinkedHashMap<>();
    }

    /**
     * 添加请求参数
     *
     * @param key   key
     * @param value 值
     */
    private void buildParameter(String key, Object value) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, value);
    }

    /**
     * 添加普通文本
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, String value) {
        buildParameter(key, value);
    }

    /**
     * 添加int类型
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Integer value) {
        buildParameter(key, value);
    }

    /**
     * 添加long类型
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Long value) {
        buildParameter(key, value);
    }

    /**
     * 添加float类型
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Float value) {
        buildParameter(key, value);
    }

    /**
     * 添加double类型
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Double value) {
        buildParameter(key, value);
    }

    /**
     * 添加boolean类型
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Boolean value) {
        buildParameter(key, value);
    }

    /**
     * 添加Object类型
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Object value) {
        buildParameter(key, value);
    }

    /**
     * 获取请求参数
     *
     * @return Map
     */
    public Map<String, Object> getParameter() {
        if (null == params) {
            params = new LinkedHashMap<>();
        }
        return params;
    }


    @NotNull
    @Override
    public String toString() {
        StringBuilder stringBuffer = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            stringBuffer.append(entry.getKey());
            stringBuffer.append(":");
            stringBuffer.append(entry.getValue());
            stringBuffer.append("\t");
        }
        return stringBuffer.toString();
    }

}
