package com.wss.module.market.ui.goods.detail.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.GoodsComment;

import java.util.List;

/**
 * Describe：商品详情契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface GoodsCommentContract {

    interface Model extends IBaseModel {

    }

    interface View extends IBaseView {

        /**
         * 商品评论列表
         */
        void commentList(List<GoodsComment> commentList);
    }

    interface Presenter {
        /**
         * 获取商品评论列表
         */
        void getGoodsCommentList();
    }
}
