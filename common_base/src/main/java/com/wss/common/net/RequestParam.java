package com.wss.common.net;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Describe：请求参数封装类
 * Created by 吴天强 on 2017/9/19.
 */
public class RequestParam {

    private Map<String, Object> params;

    public RequestParam() {
        params = new LinkedHashMap<>();
    }

    /**
     * 普通文本参数
     *
     * @param key   key
     * @param value 值
     */
    public void addParameter(String key, Object value) {
        if (this.params == null) {
            params = new LinkedHashMap<>();
        }
        params.put(key, value);
    }


    public void addParameter(String key, int value) {
        addParameter(key, String.valueOf(value));
    }

    public void addParameter(String key, long value) {
        addParameter(key, String.valueOf(value));
    }

    public void addParameter(String key, float value) {
        addParameter(key, String.valueOf(value));
    }

    public void addParameter(String key, double value) {
        addParameter(key, String.valueOf(value));
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


    /**
     * 请求参数转Json
     *
     * @return String
     */
    String toJson() {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                json.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

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
