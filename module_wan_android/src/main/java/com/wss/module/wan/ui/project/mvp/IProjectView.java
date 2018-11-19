package com.wss.module.wan.ui.project.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.wan.bean.Article;
import com.wss.module.wan.bean.Classification;

import java.util.List;

/**
 * Describe：View
 * Created by 吴天强 on 2018/11/15.
 */

public interface IProjectView extends IBaseView {

    int getPage();

    int getTypeId();

    void projectType(List<Classification> types);

    void projectList(List<Article> projects);
}
