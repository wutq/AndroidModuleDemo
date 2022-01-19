package com.wss.amd.note.designpattern.fracory.normalfactory;

import com.wss.amd.BuildConfig;
import com.wss.amd.note.designpattern.fracory.simplefactory.config.DevConfig;
import com.wss.amd.note.designpattern.fracory.simplefactory.config.IConfig;
import com.wss.amd.note.designpattern.fracory.simplefactory.config.ProConfig;

/**
 * Describe：创建环境工厂
 * Created by 吴天强 on 2022/1/14.
 */
public class ConfigFactory implements IConfigFactory {

    @Override
    public IConfig createConfig() {
        if (!BuildConfig.DEBUG) {
            return new ProConfig();
        }
        return new DevConfig();
    }
}
