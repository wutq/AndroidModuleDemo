package com.wss.amd.profile;


import com.wss.common.profile.IProfile;

/**
 * Describe：开发环境配置
 * Created by 吴天强 on 2020/4/13.
 */
public class DevProfile implements IProfile {

    /**
     * Api服务器地址
     */
    private static final String SERVICE_BAR_URL = "https://www.wanandroid.com";

    @Override
    public String getServiceBase() {
        return SERVICE_BAR_URL;
    }
}
