package com.wss.module.user.ui.account.mvp.contract;

import com.wss.common.base.mvp.IBaseModel;
import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.User;
import com.wss.common.net.callback.OnResultCallBack;

/**
 * Describe：注册契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface RegisterContract {

    interface Model extends IBaseModel {
        /**
         * 注册
         *
         * @param user     用户信息
         * @param callback 回调
         */
        void register(User user, OnResultCallBack callback);
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

        /**
         * 注册
         */
        void register();
    }
}
