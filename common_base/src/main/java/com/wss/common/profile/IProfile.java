package com.wss.common.profile;

/**
 * Describe：编译环境相关的配置
 * 该类定义 由APP模块的具体实现类创建不同环境的配置
 * Created by 吴天强 on 2019/5/23.
 */
public interface IProfile {

    /**
     * 服务地址
     *
     * @return API地址
     */
    String getServiceBase();

}
