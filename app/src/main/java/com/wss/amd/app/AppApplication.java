package com.wss.amd.app;

import com.wss.amd.profile.ProfileFactory;
import com.wss.common.base.BaseApplication;
import com.wss.common.profile.ProfileManager;

/**
 * Describe： Application
 * Created by 吴天强 on 2018/10/15.
 */
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ProfileManager.inst.factory(new ProfileFactory());
    }
}
