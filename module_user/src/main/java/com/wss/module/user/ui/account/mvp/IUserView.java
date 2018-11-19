package com.wss.module.user.ui.account.mvp;

import com.wss.common.base.mvp.IBaseView;
import com.wss.common.bean.User;

/**
 * Describe：登录View
 * Created by 吴天强 on 2018/11/13.
 */

public interface IUserView extends IBaseView {

    String getName();

    String getPassword();

    void loginSuccess(User user);

    void registerSuccess(User user);

}
