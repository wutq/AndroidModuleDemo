package com.wss.module.main.home.mvp;

import com.wss.module.main.bean.BannerInfo;
import com.wss.module.main.bean.MainBlock;
import com.wss.common.base.mvp.IBaseView;

import java.util.List;

/**
 * Describe：首页View
 * Created by 吴天强 on 2018/10/17.
 */

public interface IHomeView extends IBaseView {


    /**
     * banner列表
     */
    void bannerList(List<BannerInfo> banners);

    /**
     * 模块列表
     */
    void blockList(List<MainBlock> blockList);
}
