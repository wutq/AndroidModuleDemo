package com.wss.module.main.ui.page.navigation.bean;

import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：城市
 * Created by 吴天强 on 2020/8/20.
 */
@Getter
@Setter
public class City extends BaseBean {

    private String initials;
    private String name;

    public City(String initials, String name) {
        this.initials = initials;
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "initials='" + initials + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
