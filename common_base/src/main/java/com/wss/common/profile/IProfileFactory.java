package com.wss.common.profile;

/**
 * Describe：环境配置工厂接口
 * Created by 吴天强 on 2019/5/23.
 */
public interface IProfileFactory {

    /**
     * 创建配置文件
     *
     * @return 配置文件
     */
    IProfile createProfile();
}
