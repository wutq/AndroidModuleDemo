package com.wss.module.main.bean;

import com.wss.common.base.BaseActivity;
import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：首页模块
 * Created by 吴天强 on 2018/10/18.
 */
@Getter
@Setter
public class MainBlock extends BaseBean {

    private String title;
    private int res;
    private String url;
    private String describe;
    private Class clazz;

    public MainBlock(String title, int res) {
        this(title, "", res);
    }

    public MainBlock(String title, String url, int res) {
        this(title, res, url, null, "");
    }

    public MainBlock(String title, int res, Class<? extends BaseActivity> clazz, String describe) {
        this(title, res, "", clazz, describe);
    }

    public MainBlock(String title, int res, String url, Class<? extends BaseActivity> clazz, String describe) {
        this.title = title;
        this.res = res;
        this.url = url;
        this.clazz = clazz;
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "MainBlock{" +
                "title='" + title + '\'' +
                ", res=" + res +
                ", url='" + url + '\'' +
                '}';
    }
}
