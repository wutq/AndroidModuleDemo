package com.wss.module.market.bean;

import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：商城数据
 * Created by 吴天强 on 2018/10/19.
 */

@Getter
@Setter
public class MarketInfo extends BaseBean {

    private Integer id;
    private String title;
    private String type;//类型
    private String img;
    private String price;
    private String desc;

    public MarketInfo(Integer id, String title, String img, String price) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.price = price;
    }
}
