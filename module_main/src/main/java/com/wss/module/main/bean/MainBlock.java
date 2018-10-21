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

    private String text;
    private int type;
    private int res;
    private String url;

    public MainBlock(String text, String url, int res) {
        this.text = text;
        this.url = url;
        this.res = res;
    }
}
