package com.wss.module.market.db;

import com.wss.common.base.BaseApplication;

/**
 * Describe：商城模块DB工厂  管理所有的DB操作类
 * Created by 吴天强 on 2018/11/5.
 */

public class MarketDBFactory {

    private static MarketDBFactory mInstance = null;
    private GoodsInfoManage goodsInfoManage;

    public static MarketDBFactory getInstance() {
        if (mInstance == null) {
            synchronized (MarketDBFactory.class) {
                if (mInstance == null) {
                    mInstance = new MarketDBFactory();
                }
            }
        }
        return mInstance;
    }

    /**
     * 购物车商品Mange
     *
     * @return GoodsInfoManage
     */
    public GoodsInfoManage getGoodsInfoManage() {
        if (goodsInfoManage == null) {
            goodsInfoManage = new GoodsInfoManage(MarketDBManage.getInstance(BaseApplication.getApplication()).getDaoSession().getGoodsInfoDao());
        }
        return goodsInfoManage;
    }
}
