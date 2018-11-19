package com.wss.module.main.ui.refresh.mvp;

import com.wss.common.base.mvp.IBaseView;

import java.util.List;

/**
 * Describe： View
 * Created by 吴天强 on 2018/10/23.
 */

public interface IStringView extends IBaseView {

    int getPage();

    void stringList(List<String> articles);
}
