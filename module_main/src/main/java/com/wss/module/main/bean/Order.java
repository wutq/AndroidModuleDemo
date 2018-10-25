package com.wss.module.main.bean;

import com.wss.common.base.bean.BaseBean;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：订单
 * Created by 吴天强 on 2018/10/23.
 */
@Getter
@Setter
public class Order extends BaseBean {

    private String orderId;
    private String orderDate;
    private int orderState;// 1待支付 2待发货 3待确认 4已失效
    private String orderTotal;
    private List<Goods> goodsList;
}


