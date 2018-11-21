package com.wss.module.user.ui.account.mvp.contract;

import com.tamic.novate.callback.ResponseCallback;
import com.wss.common.base.mvp.IBaseModule;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.User;

/**
 * Describe：注册契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface RegisterContract {

    interface Module extends IBaseModule {
        /**
         * 注册
         *
         * @param user     用户信息
         * @param callback 回调
         */
        void register(User user, ResponseCallback callback);
    }

    interface View extends IBaseView {

        /**
         * 返回用户信息
         */
        User getUserInfo();


        /**
         * 注册成功
         */
        void registerSuccess(User user);

    }

    interface Presenter {

    }
}
