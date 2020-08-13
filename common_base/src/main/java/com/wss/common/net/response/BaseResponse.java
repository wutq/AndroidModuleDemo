package com.wss.common.net.response;


import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：响应基类
 * Created by 吴天强 on 2020/5/6.
 */
@Getter
@Setter
public class BaseResponse extends BaseBean {
    /**
     * 响应码
     */
    private String code;
    /**
     * 响应message
     */
    private String msg;
    /**
     * 响应数据
     */
    private String data;

    public BaseResponse(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
