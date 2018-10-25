package com.wss.module.main.bean;

import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：商品
 * Created by 吴天强 on 2018/10/23.
 */
@Getter
@Setter
public class Goods extends BaseBean {

    private String goodsId;
    private String goodsName;
    private String goodsImg;
    private String goodsPrice;
    private int goodsNum;

}
