package com.wss.module.market.main.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.market.bean.GoodsInfo;

import java.util.List;

/**
 * Describe：首页View
 * Created by 吴天强 on 2018/10/19.
 */

public interface IMarketMainView extends IBaseView {


    void dataList(List<GoodsInfo> dataList);

    void cartCount(long count);
}
