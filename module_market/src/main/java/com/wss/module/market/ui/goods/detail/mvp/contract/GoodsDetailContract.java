package com.wss.module.market.ui.goods.detail.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.GoodsComment;
import com.wss.module.market.bean.GoodsInfo;

import java.util.List;

/**
 * Describe：商品评论契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface GoodsDetailContract {

    interface Model extends IBaseModel {

    }

    interface View extends IBaseView {

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

    interface Presenter {
        /**
         * 获取商品详情
         */
        void getGoodsInfo();

        /**
         * 推荐商品
         */
        void getRecommendList();

        /**
         * 商品精彩评论
         */
        void getCommentList();
    }
}
