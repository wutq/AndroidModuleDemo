package com.wss.module.main.ui.page.viscosity.bean;

import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：TabView item
 * Created by 吴天强 on 2020/8/13.
 */
@Getter
@Setter
public class TabItem extends BaseBean {
    private int type;
    private int position;

    public TabItem() {
    }

    public TabItem(int type, int position) {
        this.type = type;
        this.position = position;
    }
}
