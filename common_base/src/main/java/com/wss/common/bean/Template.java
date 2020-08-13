package com.wss.common.bean;

import com.wss.common.base.bean.BaseBean;
import com.wss.common.constants.Constants;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：可跳转的模板
 * Created by 吴天强 on 2018/11/13.
 */
@Getter
@Setter
public class Template extends BaseBean {

    private String title;
    private int res;
    private String describe;
    private Class clazz;
    /**
     * 模块外跳转链接
     */
    private String url;
    /**
     * 页面跳转类型
     */
    private int type;
    /**
     * 其他附加参数
     */
    private Map<String, ?> params;

    public Template(String title, String url, int type, String describe) {
        this.title = title;
        this.url = url;
        this.type = type;
        this.describe = describe;
    }

    public Template(String title, Class clazz, String describe) {
        this.title = title;
        this.clazz = clazz;
        this.type = Constants.TemplateType.ACTIVITY;
        this.describe = describe;
    }

    public Template(String title, int res, Class clazz) {
        this.title = title;
        this.res = res;
        this.clazz = clazz;
    }
}
