package com.wss.module.main.ui.main.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.module.main.bean.AppInfo;

/**
 * Describe：入口View
 * Created by 吴天强 on 2018/11/20.
 */

public interface IMainView extends IBaseView {

    void needUpdate(AppInfo appInfo);

}
