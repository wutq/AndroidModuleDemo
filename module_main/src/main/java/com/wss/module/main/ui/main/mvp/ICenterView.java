package com.wss.module.main.ui.main.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.Template;

import java.util.List;

/**
 * Describe：中间View
 * Created by 吴天强 on 2018/10/22.
 */

public interface ICenterView extends IBaseView {

    void tabList(List<Template> blockList);
}
