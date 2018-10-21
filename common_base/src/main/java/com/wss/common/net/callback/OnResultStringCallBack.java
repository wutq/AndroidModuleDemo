package com.wss.common.net.callback;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;
import com.wss.common.net.NetConfig;

import org.json.JSONObject;

import okhttp3.ResponseBody;

/**
 * Describe：String类型数据解析
 * Created by 吴天强 on 2017/9/28.
 */

public abstract class OnResultStringCallBack extends OnResultCallBack<String> {

    @Override
    public String onHandleResponse(ResponseBody response) throws Exception {
        return transform(new String(response.bytes()));
    }

    private String transform(String response) {
        Logger.e(JSON.toJSONString(response));
        try {
            JSONObject jsonObject = new JSONObject(response);
            code = jsonObject.optInt(NetConfig.Code.CODE);
            msg = jsonObject.optString(NetConfig.Code.MSG);
            success = jsonObject.optBoolean(NetConfig.Code.SUCCESS);
            dataStr = jsonObject.opt(NetConfig.Code.MODEL).toString();
            dataResponse = dataStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataResponse;
    }

}
