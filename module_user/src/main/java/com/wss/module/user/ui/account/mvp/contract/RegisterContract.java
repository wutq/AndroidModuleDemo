package com.wss.module.user.ui.account.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.User;

import io.reactivex.Observable;

/**
 * Describe：注册契约类
 * Created by 吴天强 on 2018/11/21.
 */

public interface RegisterContract {

    interface Model {
        /**
         * 注册
         *
         * @param userName 用户名
         * @param password 密码
         * @return 用户
         */
        Observable<User> register(String userName, String password);
    }

    interface View extends IBaseView {


    }

    interface Presenter {

        /**
         * 注册
         *
         * @param userName        用户名
         * @param password        密码
         * @param passwordConfirm 密码
         */
        void register(String userName, String password, String passwordConfirm);
    }
}
