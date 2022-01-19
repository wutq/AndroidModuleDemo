package com.wss.amd.note.designpattern.fracory.simplefactory.config;

/**
 * Describe：开发环境
 * Created by 吴天强 on 2022/1/14.
 */
public class DevConfig implements IConfig {

    @Override
    public String getServiceUrl() {
        return "http://192.168.0.1";
    }
}
