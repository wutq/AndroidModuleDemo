package com.wss.module.market.bean;

import com.wss.common.base.bean.BaseBean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：商品信息
 * Created by 吴天强 on 2018/11/1.
 */

@Getter
@Setter
public class GoodsInfo extends BaseBean {

    private String goodsId;
    private String goodsName;
    private String goodsPrice;//商品当前价格
    private String goodsOldPrice;//老价格
    private List<String> goodsHeadImg;//商品头图
    private String goodsMasterImg;//商品主图
    private String praiseRate;//好评率
    private String commentCount;//用户点评数
}
