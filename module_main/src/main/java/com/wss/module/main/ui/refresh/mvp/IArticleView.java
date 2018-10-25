package com.wss.module.main.ui.refresh.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.main.bean.Article;

import java.util.List;

/**
 * Describe：文章View
 * Created by 吴天强 on 2018/10/23.
 */

public interface IArticleView extends IBaseView {

    int getPage();

    void articleList(List<Article> articles);
}
