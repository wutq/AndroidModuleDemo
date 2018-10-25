package com.wss.module.main.bean;

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

    public MainBlock(String title, int res) {
        this(title, "", res);
    }

    public MainBlock(String title, String url, int res) {
        this(title, res, url, "");
    }

    public MainBlock(String title, int res, String describe) {
        this(title, res, "", describe);
    }

    public MainBlock(String title, int res, String url, String describe) {
        this.title = title;
        this.res = res;
        this.url = url;
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
