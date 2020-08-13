package com.wss.common.bean;


import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：水平选项卡标题
 * Created by 吴天强 on 2018/10/22.
 */
@Getter
@Setter
public class HorizontalTabTitle<T> extends BaseBean {
    /**
     * 显示标题文字
     */
    private String title;
    /**
     * 标题其他附带数据
     */
    private T data;

    public HorizontalTabTitle(String title) {
        this.title = title;
    }

    public HorizontalTabTitle(String title, T data) {
        this.title = title;
        this.data = data;

    }

}
