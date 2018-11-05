package com.wss.module.market.goods.detail.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.GoodsComment;

import java.util.List;

/**
 * Describe：商品评论Module
 * Created by 吴天强 on 2018/11/5.
 */

public interface IGoodsCommentView extends IBaseView {


    /**
     * 商品评论列表
     */
    void commentList(List<GoodsComment> commentList);
}
