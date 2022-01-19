package com.wss.amd.note.designpattern.fracory.simplefactory.config;

/**
 * Describe：生产环境
 * Created by 吴天强 on 2022/1/14.
 */
public class ProConfig implements IConfig {

    @Override
    public String getServiceUrl() {
        return "http://pro.wss.com";
    }
}
