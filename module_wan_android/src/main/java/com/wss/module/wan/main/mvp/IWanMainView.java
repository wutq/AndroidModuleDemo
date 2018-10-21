package com.wss.module.wan.main.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：View
 * Created by 吴天强 on 2018/10/18.
 */

public interface IWanMainView extends IBaseView {

    void articleList(List<Article> articleList);

}
