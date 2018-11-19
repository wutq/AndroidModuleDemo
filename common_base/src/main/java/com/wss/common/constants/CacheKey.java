package com.wss.common.constants;

import com.wss.common.base.BaseApplication;

/**
 * Describe：缓存key
 * Created by 吴天强 on 2018/11/13.
 */

public interface CacheKey {

    /**
     * 保存用户信息
     */
    String USER_INFO = BaseApplication.getApplication().getPackageName() + ".UserInfo";

    /**
     * 保存登录状态
     */
    String USER_LOGGED = BaseApplication.getApplication().getPackageName() + ".UserLogged";
}
