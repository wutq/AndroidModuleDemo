package com.wss.common.net.callback;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.wss.common.net.NetConfig;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;

import okhttp3.ResponseBody;

/**
 * Describe：返回对象类型数据
 * Created by 吴天强 on 2017/9/28.
 */

public abstract class OnResultObjectCallBack<T> extends OnResultCallBack<T> {

    @Override
    public T onHandleResponse(ResponseBody response) throws Exception {
        Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return transform(new String(response.bytes()), entityClass);
    }

    public T transform(String response, final Class classOfT) throws ClassCastException {
        Logger.e(response);
        try {
            JSONObject jsonObject = new JSONObject(response);

            code = jsonObject.optInt(NetConfig.Code.CODE);
            msg = jsonObject.optString(NetConfig.Code.MSG);
            success = jsonObject.optBoolean(NetConfig.Code.SUCCESS);
            dataStr = jsonObject.opt(NetConfig.Code.MODEL).toString();
            dataResponse = (T) new Gson().fromJson(dataStr, classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }
}

