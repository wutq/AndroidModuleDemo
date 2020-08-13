package com.wss.amd.profile;


import com.wss.common.profile.IProfile;
import com.wss.common.profile.IProfileFactory;

/**
 * Describe：创建环境工厂
 * Created by 吴天强 on 2020/4/13.
 */
public class ProfileFactory implements IProfileFactory {

    @Override
    public IProfile createProfile() {
        //根据不同的环境生成不同的配置文件，这里仅生成DEV配置文件
        return new DevProfile();
    }
}
