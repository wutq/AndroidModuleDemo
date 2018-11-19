package com.wss.module.wan.ui.collection.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;

import java.util.List;

/**
 * Describe：
 * Created by 吴天强 on 2018/11/16.
 */

public interface ICollectionView extends IBaseView {

    int getPage();

    void collectionList(List<Article> collections);
}
