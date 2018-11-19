package com.wss.module.wan.ui.search.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public interface ISearchView extends IBaseView {
    String getWord();

    int getPage();

    void searchData(List<Article> searchs);
}
