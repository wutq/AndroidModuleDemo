package com.wss.module.wan.ui.wxnumber.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public interface IWXArticleView extends IBaseView {

    int getPage();

    /**
     * 公众号ID
     */
    int getWXNumberId();

    void wxArticleList(List<Article> wxArticles);
}
