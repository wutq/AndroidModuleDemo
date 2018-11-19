package com.wss.module.wan.ui.setup.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/15.
 */

public interface ISetupView extends IBaseView {

    void setupList(List<Classification> setups);

    /**
     * 返回二级菜单ID
     */
    String getSetupId();

    int getPage();

    void articleList(List<Article> articles);
}
