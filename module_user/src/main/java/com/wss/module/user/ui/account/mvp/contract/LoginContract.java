package com.wss.module.user.ui.account.mvp.contract;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.User;

import io.reactivex.Observable;

/**
 * Describe：登录契约类
 * Created by 吴天强 on 2018/11/21.
 */
public interface LoginContract {

    interface Model {

        /**
         * 登录
         *
         * @param userName 账号
         * @param password 密码
         * @return 用户
         */
        Observable<User> login(String userName, String password);
    }

    interface View extends IBaseView {

    }

    interface Presenter {

        /**
         * 登录
         *
         * @param userName 用户名
         * @param password 密码
         */
        void login(String userName, String password);
    }
}
