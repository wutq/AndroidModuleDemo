package com.wss.module.main.bean;

import com.wss.common.base.bean.BaseBean;

/**
 * Describe：
 * Created by 吴天强 on 2018/10/18.
 */

public class Article extends BaseBean {

    private String title;


    public Article(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
