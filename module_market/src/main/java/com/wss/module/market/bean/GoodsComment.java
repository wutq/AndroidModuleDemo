package com.wss.module.market.bean;

import com.wss.common.base.bean.BaseBean;

import lombok.Getter;
import lombok.Setter;

/**
 * Describe：商品评论
 * Created by 吴天强 on 2018/11/1.
 */

@Getter
@Setter
public class GoodsComment extends BaseBean {

    private String userHead;
    private String userName;
    private String comment;//评论内容
    private float star;//星级
}
