package com.wss.module.market.main.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.GoodsInfo;
import com.wss.module.market.bean.GoodsComment;

import java.util.List;

/**
 * Describe：商品详情View
 * Created by 吴天强 on 2018/11/1.
 */

public interface IGoodsDetailView extends IBaseView {


    /**
     * 获取商品ID
     */
    String getGoodsId();

    /**
     * 商品详情
     */
    void goodsInfo(GoodsInfo goodsInfo);

    /**
     * 推荐商品列表
     */
    void recommendList(List<List<GoodsInfo>> recommendList);

    /**
     * 商品评论列表
     */
    void commentList(List<GoodsComment> commentList);
}
