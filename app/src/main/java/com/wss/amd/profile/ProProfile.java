package com.wss.amd.profile;


import com.wss.common.profile.IProfile;

/**
 * Describe：生产环境配置
 * Created by 吴天强 on 2021/11/15.
 */
public class ProProfile implements IProfile {

    /**
     * Api服务器地址
     */
    private static final String SERVICE_BAR_URL = "https://www.wanandroid.com";

    @Override
    public String getServiceBase() {
        return SERVICE_BAR_URL;
    }

    @Override
    public boolean isSecret() {
        return true;
    }

    @Override
    public String getAesSecretKey() {
        return "t96IBJiOSURJZOzdjQ36pw==";
    }
}
