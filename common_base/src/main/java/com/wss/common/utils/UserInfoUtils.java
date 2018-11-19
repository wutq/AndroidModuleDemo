package com.wss.common.utils;

import com.wss.common.base.BaseApplication;
import com.wss.common.bean.User;
import com.wss.common.constants.CacheKey;

/**
 * Describe：用户信息帮助类
 * Created by 吴天强 on 2018/11/13.
 */

public class UserInfoUtils {


    /**
     * 保存用户信息
     *
     * @param user user
     */
    public static void saveUser(User user) {
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_INFO, user);
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_LOGGED, true);
    }

    /**
     * 获取用户信息
     *
     * @return user
     */
    public static User getUser() {
        User user = (User) CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_INFO);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    /**
     * 清除用户信息
     */
    public static void cleanUser() {
        saveUser(null);
        CacheUtils.get(BaseApplication.getApplication())
                .put(CacheKey.USER_LOGGED, false);
    }

    /**
     * 是否登录
     *
     * @return boolean
     */
    public static boolean isLogged() {
        Object result = CacheUtils.get(BaseApplication.getApplication())
                .getAsObject(CacheKey.USER_LOGGED);
        return result != null && (boolean) result;
    }

}
