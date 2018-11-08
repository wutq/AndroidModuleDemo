package com.wss.module.market.db;

import com.wss.common.dao.BaseDBManager;
import com.wss.module.market.bean.GoodsInfo;

import org.greenrobot.greendao.AbstractDao;

/**
 * Describe：购物车商品DB操作类
 * Created by 吴天强 on 2018/11/5.
 */

public class GoodsInfoManage extends BaseDBManager<GoodsInfo, String> {
    public GoodsInfoManage(AbstractDao dao) {
        super(dao);
    }

}
