package com.wss.module.user.ui.main.mvp.contract;

import com.wss.common.base.mvp.IBaseView;

/**
 * Describe：契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface UserContract {

    interface Model {

    }

    interface View extends IBaseView {

    }

    interface Presenter {

        /**
         * 检查更新
         */
        void checkUpdate();
    }

}
