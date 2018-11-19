package com.wss.module.wan.ui.main.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.Template;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.BannerInfo;
import com.wss.module.wan.bean.WXNumber;

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
    void blockList(List<Template> blockList);

    int getPage();

    void articleList(List<Article> articles);

    void wxNumber(List<WXNumber> wxNumbers);

}
