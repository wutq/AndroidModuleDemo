package com.wss.common.net.callback;


import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxGenericsCallback;
import com.wss.common.net.NetConfig;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.ResponseBody;

/**
 * Describe：网络返回基类 泛型为必传项 默认 传入String即可
 * Created by 吴天强 on 2017/9/26.
 */
@SuppressWarnings("unchecked")
public abstract class OnResultCallBack<T> extends RxGenericsCallback<T, ResponseBody> {
    protected boolean success;


    @Override
    public T onHandleResponse(ResponseBody response) throws Exception {
        return transform(new String(response.bytes()));
    }

    private T transform(String response) {
        Logger.e(JSON.toJSONString(response));
        try {
            JSONObject jsonObject = new JSONObject(response);
            code = jsonObject.optInt(NetConfig.Code.CODE);
            msg = jsonObject.optString(NetConfig.Code.MSG);
            success = jsonObject.optBoolean(NetConfig.Code.SUCCESS);
            dataStr = jsonObject.opt(NetConfig.Code.MODEL).toString();

            if (dataStr.charAt(0) == 123) {
                //获取泛型类型
                Class<T> classOfT = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                if (classOfT == String.class) {
                    dataResponse = (T) dataStr;
                } else {
                    //对象
                    dataResponse = (new Gson()).fromJson(dataStr, classOfT);
                }
            } else if (dataStr.charAt(0) == 91) {
                //数组
                Type collectionType = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                dataResponse = new Gson().fromJson(dataStr, collectionType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;

    }

    @Override
    public void onError(Object tag, Throwable e) {
        e.printStackTrace();
        onFailure(tag, e);
    }


    @Override
    public void onCancel(Object tag, Throwable e) {
    }

    @Override
    public void onFailure(Call call, IOException e) {
        onFailure(tag, e);
    }

    @Override
    public void onNext(Object tag, int code, String message, T response) {
        onSuccess(success, code, msg, tag, response);
    }

    @Override
    public void onCompleted(Object tag) {
        super.onCompleted(tag);
        onCompleted();
    }


    public abstract void onSuccess(boolean success, int code, String msg, Object tag, T response);

    public abstract void onFailure(Object tag, Exception e);

    public abstract void onCompleted();

}
