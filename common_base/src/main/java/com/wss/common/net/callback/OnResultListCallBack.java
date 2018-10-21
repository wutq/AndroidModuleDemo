package com.wss.common.net.callback;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wss.common.net.NetConfig;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;

/**
 * Describe：返回数组类型数据
 * Created by 吴天强 on 2017/9/28.
 */

public abstract class OnResultListCallBack<T> extends OnResultCallBack<T> {

    private Type collectionType;

    @Override
    public T onHandleResponse(ResponseBody response) throws Exception {
        if (collectionType == null) {
            collectionType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return transform(new String(response.bytes()), null);
    }

    public T transform(String response, final Class classOfT) throws ClassCastException {
        Logger.e(JSON.toJSONString(response));
        try {
            JSONObject jsonObject = new JSONObject(response);
            code = jsonObject.optInt(NetConfig.Code.CODE);
            msg = jsonObject.optString(NetConfig.Code.MSG);
            success = jsonObject.optBoolean(NetConfig.Code.SUCCESS);
            dataStr = jsonObject.opt(NetConfig.Code.MODEL).toString();
            dataResponse = new Gson().fromJson(dataStr, collectionType);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataResponse;
    }
}