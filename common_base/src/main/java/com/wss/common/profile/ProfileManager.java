package com.wss.common.profile;

/**
 * Describe：编译环境相关的配置 管理类
 * Created by 吴天强 on 2019/5/23.
 */
public class ProfileManager {

    private IProfileFactory mFactory;
    private IProfile mProfile;
    public static final ProfileManager inst;

    public void factory(IProfileFactory factory) {
        this.mFactory = factory;
    }

    public static IProfile profile() {
        return inst.getProfile();
    }

    private IProfile getProfile() {
        if (this.mProfile == null) {
            this.mProfile = this.mFactory.createProfile();
        }
        return this.mProfile;
    }

    private ProfileManager() {
    }

    static {
        inst = Holder.holder;
    }

    private static final class Holder {
        private static final ProfileManager holder = new ProfileManager();

        private Holder() {
        }
    }
}
