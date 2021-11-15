package com.wss.amd.profile;


import com.wss.amd.BuildConfig;
import com.wss.common.profile.IProfile;
import com.wss.common.profile.IProfileFactory;

/**
 * Describe：创建环境工厂
 * Created by 吴天强 on 2020/4/13.
 */
public class ProfileFactory implements IProfileFactory {
    /**
     * 开发环境
     */
    private static final String DEV = "DEV";
    /**
     * 生产环境
     */
    private static final String PRO = "PRO";

    @Override
    public IProfile createProfile() {
        switch (BuildConfig.FLAVOR) {
            case PRO:
                return new ProProfile();
            case DEV:
            default:
                return new DevProfile();
        }
    }
}
